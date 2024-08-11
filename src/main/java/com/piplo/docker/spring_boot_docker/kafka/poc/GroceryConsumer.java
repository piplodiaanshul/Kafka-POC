package com.piplo.docker.spring_boot_docker.kafka.poc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.piplo.docker.spring_boot_docker.groceries.GroceryItem;
import com.piplo.docker.spring_boot_docker.groceries.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class GroceryConsumer {

   @Autowired private KafkaConsumer<String, String> kafkaConsumer;

    @Autowired private ItemRepository itemRepository;

    @PostConstruct
    public void startConsuming() {
        //Commented for testing new consumer
       // CompletableFuture.runAsync(this::consumeGroceryTopicMessages);
    }

    public void consumeGroceryTopicMessages() {
        //subscribe to a topic
        try {
        do {
            kafkaConsumer.subscribe(List.of(TopicNames.GROCERY_TOPIC));
            ConsumerRecords<String, String> polledRecords = kafkaConsumer.poll(Duration.ofSeconds(2));
            log.info("[CONSUMER] [TOPIC: {}] Total records fetched, Message: {}", TopicNames.GROCERY_TOPIC, polledRecords.count());
            polledRecords
                    .spliterator()
                    .forEachRemaining(item -> {
                        if(Objects.nonNull(item.key()) && Objects.nonNull(item.value())) {
                            log.info("[CONSUMER] [TOPIC: {}]  Record read from topic, Message: {}", TopicNames.GROCERY_TOPIC, item.value());
                            GroceryItem groceryItem = Mapper.toGroceryItem(item.value());
                            log.info("[CONSUMER] [MAPPER] [TOPIC: {}] Record transformed for saving into db, Message: {}", TopicNames.GROCERY_TOPIC, groceryItem);
                            itemRepository.save(groceryItem);
                            }
                        });
            kafkaConsumer.commitSync();
        } while (true);
        } catch (Exception e) {
            log.error("[CONSUMER] ERROR WHILE CONSUMING RECORDS: ", e);
        }finally {
            kafkaConsumer.close();
        }
    }

    @KafkaListener(topics = "grocery_topic",
            groupId = "NEW-GROCERY-CONSUMER-GROUP",
            containerFactory = "newGroceryConsumer",
            autoStartup = "true",
            concurrency = "3",
            batch = "true")
    public void newGroceryConsumerMessageListener(ConsumerRecords<String, String> batchGroceryRecords) {
        batchGroceryRecords.forEach(groceryRecord -> {
            log.info("Received message through MessageConverterUserListener [{}]", groceryRecord);
            String key = groceryRecord.key();
            String groceryRecordValue = groceryRecord.value();
            if(Objects.nonNull(key) && Objects.nonNull(groceryRecordValue)) {
                log.info("[CONSUMER] [TOPIC: {}]  Record read from topic, Message: {}", groceryRecord.topic(), groceryRecordValue);
                GroceryItem groceryItem = Mapper.toGroceryItem(groceryRecordValue);
                log.info("[CONSUMER] [MAPPER] [TOPIC: {}] Record transformed for saving into db, Message: {}", groceryRecord.topic(), groceryItem);
                itemRepository.save(groceryItem);
            }
        });
    }
}
