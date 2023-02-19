package org.sid.customerservice.entities;

import lombok.*;

import javax.persistence.*;

@Entity @AllArgsConstructor @NoArgsConstructor @Data @ToString
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
}
