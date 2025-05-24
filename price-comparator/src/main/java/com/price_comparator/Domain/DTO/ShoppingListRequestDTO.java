package com.price_comparator.Domain.DTO;
import lombok.Data;
import java.util.List;
@Data
public class ShoppingListRequestDTO {
    private List<ShoppingListItemDTO> items;
}