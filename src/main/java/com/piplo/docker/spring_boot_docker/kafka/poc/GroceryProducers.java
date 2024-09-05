package com.piplo.docker.spring_boot_docker.kafka.poc;


import com.piplo.docker.spring_boot_docker.groceries.GroceryItem;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class GroceryProducers {

    public volatile boolean doProduceByKafkaTemplate = true;
    @Autowired
    private KafkaProducer<String, String> kafkaProducer;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * @param groceryItems - collection of {@link GroceryItem}
     */
    public void producer(List<GroceryItem> groceryItems) {
        groceryItems.forEach(aGroceryItem -> {
            aGroceryItem.setId(UUID.randomUUID().toString());
            String aGroceryItemStringify = Mapper.toString(aGroceryItem);
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TopicNames.GROCERY_TOPIC, aGroceryItem.getId(), aGroceryItemStringify);
            if (doProduceByKafkaTemplate) {
                log.info("[KAFKA] [PRODUCER] sending message using kafka-template");
                kafkaTemplate.send(producerRecord);
            } else {
                log.info("[KAFKA] [PRODUCER] sending message using apache-kafka org core impl");
                kafkaProducer.send(producerRecord);
            }
        });
    }

}
