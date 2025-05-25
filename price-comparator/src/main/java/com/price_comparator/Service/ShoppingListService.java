package com.price_comparator.Service;

import com.price_comparator.Domain.DTO.ShoppingCartDTO;
import com.price_comparator.Domain.ShoppingCart;
import com.price_comparator.Domain.ShoppingList;

import java.util.List;

public interface ShoppingListService {
    void addToShoppingList(String userId, String productId);
    ShoppingList getShoppingListByUser(String userId);
    void clearList(String userId);
    List<ShoppingCartDTO> generateShoppingCarts(String userId);
}
