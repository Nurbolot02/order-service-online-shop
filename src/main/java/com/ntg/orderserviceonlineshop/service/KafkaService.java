package com.ntg.orderserviceonlineshop.service;

import com.ntg.orderserviceonlineshop.event.CreateTaskEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    public void send(String createTaskEvent) {
        kafkaTemplate.send("topic-1", createTaskEvent);
    }
}
