package com.ntg.orderserviceonlineshop.config;

import com.ntg.orderserviceonlineshop.event.CreateTaskEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic kafkaProducer() {
        return TopicBuilder.name("topic-1")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public CreateTaskEvent createTaskEvent() {
        return CreateTaskEvent.builder()
                .message("Hi")
                .build();
    }
}
