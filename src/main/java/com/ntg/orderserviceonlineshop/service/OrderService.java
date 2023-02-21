package com.ntg.orderserviceonlineshop.service;

import com.ntg.orderserviceonlineshop.dto.InventoryResponseDTO;
import com.ntg.orderserviceonlineshop.dto.OrderRequest;
import com.ntg.orderserviceonlineshop.exception.ItemNotFoundException;
import com.ntg.orderserviceonlineshop.exception.NotEnoughCountException;
import com.ntg.orderserviceonlineshop.model.Order;
import com.ntg.orderserviceonlineshop.model.OrderLineItem;
import com.ntg.orderserviceonlineshop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final WebClient.Builder webClientBuilder;

    public String save(OrderRequest orderRequest) {
        Order order = modelMapper.map(orderRequest, Order.class);
        order.setOrderNumber(UUID.randomUUID().toString());

        List<String> skuCodes = order.getOrderLineItems().stream()
                .map(OrderLineItem::getSkuCode)
                .toList();

        List<InventoryResponseDTO> inventoryResponseDTOS = Objects.requireNonNull(webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventories",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build()
                )
                .retrieve()
                .toEntityList(InventoryResponseDTO.class)
                .block()).getBody();

        assert inventoryResponseDTOS != null;
        if (inventoryResponseDTOS.size() == 0) {
            String joining = String.join(", ", skuCodes);
            log.error("Product with name: {} not found", joining);
            throw new ItemNotFoundException("Product with name: %s not found".formatted(joining));
        }

        for (OrderLineItem orderLineItem : order.getOrderLineItems()) {
            for (int i = 0; i < Objects.requireNonNull(inventoryResponseDTOS).size(); i++) {
                if (!orderLineItem.getSkuCode().equals(inventoryResponseDTOS.get(i).getSkuCode())) {
                    log.error("Product with name: {} not found", orderLineItem.getSkuCode());
                    throw new ItemNotFoundException("Product with name: %s not found".formatted(orderLineItem.getSkuCode()));
                } else if (orderLineItem.getQuantity() > inventoryResponseDTOS.get(i).getQuantity() ||
                        inventoryResponseDTOS.get(i).getQuantity() <= 0
                ) {
                    log.error("Need {} pieces of {} but there are only {} pieces in stock",
                            orderLineItem.getQuantity(), orderLineItem.getSkuCode(), inventoryResponseDTOS.get(i).getQuantity());
                    throw new NotEnoughCountException("Need %d pieces of %s but there are only %d pieces in stock"
                            .formatted(orderLineItem.getQuantity(), orderLineItem.getSkuCode(), inventoryResponseDTOS.get(i).getQuantity()));
                }
            }
        }


        orderRepository.save(order);
        log.info("Order {} is saved", order.getOrderNumber());
        log.info("Order line items id {}", order.getOrderLineItems().get(0).getId());
        return "Order created";
    }

    public List<OrderRequest> findAll() {
        return orderRepository.findAll().stream()
                .map(order -> modelMapper.map(order, OrderRequest.class))
                .toList();
    }
}
