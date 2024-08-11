package com.piplo.docker.spring_boot_docker.groceries;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("groceryitems")
public class GroceryItem {
    @Id
    private String id;
    private String name;
    private int quantity;
    private String category;
}