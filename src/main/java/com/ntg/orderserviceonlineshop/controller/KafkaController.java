package com.ntg.orderserviceonlineshop.controller;

import com.ntg.orderserviceonlineshop.event.CreateTaskEvent;
import com.ntg.orderserviceonlineshop.service.KafkaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor
public class KafkaController {
    private final KafkaService kafkaService;
    private final CreateTaskEvent createTaskEvent;

    @PostMapping
    public ResponseEntity<HttpStatus> sayHI(@RequestBody String createTaskEvent) {
        kafkaService.send(createTaskEvent);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<CreateTaskEvent> getEvent() {
        return new ResponseEntity<>(createTaskEvent, HttpStatus.OK);
    }
}
