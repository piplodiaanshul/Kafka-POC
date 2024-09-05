package com.piplo.docker.spring_boot_docker.kafka.poc;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class MyKafkaConfigBeans {

    //Note: Used this as singleton bean of Kafka-producer will be created.
    //Note: Implemented using only org.apache.kafka.clients.producer | Spring has also build wrapper around for ease of use with Producer/Consumer-Factories, kafkaTemplate and Annotations
    @Bean
    @Deprecated
    public KafkaProducer<String, String> initKafkaProducer() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9093"); // bootstrap server config //any kafka broker server/servers
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName()); //key serializer
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName()); //value serializer
        // initialize kafka producer with config
        return new KafkaProducer<>(properties);
    }

    /* Note: Used this as singleton bean of Kafka-consumer will be created.
     Not and idle method to crate bean, as its singleton and consumer require to subscribe to multiple topics
     Note: WRONG - single instance of consumer is good idea, BUT each kind of consumer subscribes to a specific set of Topic,
     MUST create separate consumer bean for consuming particular group/single topic subscription
     */
    @Bean
    @Deprecated
    public KafkaConsumer<String, String> initKafkaConsumer() {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9093"); // bootstrap server config //any kafka broker server/servers
        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", StringDeserializer.class.getName());
        // below is hard coupling - to singleton instance but in real world it can vary
        properties.setProperty("group.id", "GROCERY_ITEM_CONSUMING_GROUP");
        // initialize kafka consumer with config
        return new KafkaConsumer<>(properties);
    }

    //Note: before linger.ms time if batch is full the messages will be sent out
    @Bean
    public ProducerFactory<String, String> initProducer() {
        Map<String, Object> producerConfig = new HashMap<>();
        producerConfig.put("bootstrap.servers", "localhost:9093"); // bootstrap server config //any kafka broker server/servers
        producerConfig.put("key.serializer", StringSerializer.class.getName());
        producerConfig.put("value.serializer", StringSerializer.class.getName());
        // Set batch size
        producerConfig.put("batch.size", 32 * 1024);  // 32 KB batch size
        // Set linger.ms to 5 second
        producerConfig.put("linger.ms", 5 * 1000);
        return new DefaultKafkaProducerFactory<>(producerConfig);
    }

    @Bean
    public KafkaTemplate<String, String> initKafkaTemplate() {
        return new KafkaTemplate<>(initProducer());
    }

    @Bean
    public ConsumerFactory<String, String> groceryConsumerFactory() {
        Map<String, Object> consumerConfig = new HashMap<>();
        consumerConfig.put("key.deserializer", StringDeserializer.class.getName());
        consumerConfig.put("value.deserializer", StringDeserializer.class.getName());
        consumerConfig.put("bootstrap.servers", "localhost:9093");
        return new DefaultKafkaConsumerFactory<>(consumerConfig);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> newGroceryConsumer() {
        ConcurrentKafkaListenerContainerFactory<String, String> consumerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        consumerFactory.setConsumerFactory(this.groceryConsumerFactory());
        return consumerFactory;
    }

}
