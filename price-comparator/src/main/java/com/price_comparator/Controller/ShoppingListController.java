package com.price_comparator.Controller;


import com.price_comparator.Domain.ShoppingList;
import com.price_comparator.Domain.ShoppingCart;
import com.price_comparator.Service.ShoppingListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopping-list")
@RequiredArgsConstructor
public class ShoppingListController {

    private final ShoppingListService shoppingListService;

    @PostMapping("/{userId}/add")
    public ResponseEntity<Void> addToList(@PathVariable String userId, @RequestParam String productId) {
        shoppingListService.addToShoppingList(userId, productId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ShoppingList> getList(@PathVariable String userId) {
        return ResponseEntity.ok(shoppingListService.getShoppingListByUser(userId));
    }

    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<Void> clearList(@PathVariable String userId) {
        shoppingListService.clearList(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/generate-carts")
    public ResponseEntity<List<ShoppingCart>> generateCarts(@PathVariable String userId) {
        List<ShoppingCart> carts = shoppingListService.generateShoppingCarts(userId);
        return ResponseEntity.ok(carts);
    }
}
