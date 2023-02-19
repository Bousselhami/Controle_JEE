package org.sid.inventoryservice.entities;

import lombok.*;
import javax.persistence.*;

@Entity @Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    private double price;
    private double quantity;
}
