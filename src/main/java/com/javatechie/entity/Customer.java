package com.javatechie.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    private Long id;

    private String name;
    private String email;
    private String gender;
    private String region;
}
