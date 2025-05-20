package com.price_comparator.DTO;


import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String name;

    public UserDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
