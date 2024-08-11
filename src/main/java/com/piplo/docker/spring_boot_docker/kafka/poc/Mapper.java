package com.piplo.docker.spring_boot_docker.kafka.poc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.piplo.docker.spring_boot_docker.groceries.GroceryItem;
import lombok.experimental.UtilityClass;
import org.apache.kafka.clients.consumer.ConsumerRecord;

@UtilityClass
public class Mapper {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static String toString(GroceryItem aGroceryItem) {
        try {
            return new ObjectMapper().writeValueAsString(aGroceryItem);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    public static GroceryItem toGroceryItem(String stringifyGroceryItem) {
        try {
            return OBJECT_MAPPER.readValue(stringifyGroceryItem, GroceryItem.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
