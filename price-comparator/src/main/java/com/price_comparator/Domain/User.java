package com.price_comparator.Domain;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public User() {
    }

    private String name;

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
