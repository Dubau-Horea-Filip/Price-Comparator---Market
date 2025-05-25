package com.price_comparator.Controller;


import com.price_comparator.Domain.DTO.ShoppingCartDTO;
import com.price_comparator.Domain.ShoppingList;
import com.price_comparator.Domain.ShoppingCart;
import com.price_comparator.Service.ShoppingListService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopping-list")
@RequiredArgsConstructor
public class ShoppingListController {

    private final ShoppingListService shoppingListService;


    @Operation(
            summary = "Add a Product to Shopping List",
            description = "receives a userId and productId, adds the product to the user's shopping list."
    )
    @PostMapping("/{userId}/add")
    public ResponseEntity<Void> addToList(@PathVariable String userId, @RequestParam String productId) {
        shoppingListService.addToShoppingList(userId, productId);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "See user Shopping List",
            description = "Retrieves the shopping list for a specific user by userId."
    )
    @GetMapping("/{userId}")
    public ResponseEntity<ShoppingList> getList(@PathVariable String userId) {
        return ResponseEntity.ok(shoppingListService.getShoppingListByUser(userId));
    }

    @Operation(
            summary = "empty Shopping List",
            description = "empties the shopping list for a specific user by userId."
    )
    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<Void> clearList(@PathVariable String userId) {
        shoppingListService.clearList(userId);
        return ResponseEntity.ok().build();
    }


    @Operation(
            summary = "Split Shopping List",
            description = "Help users split their basket into shopping lists that optimise for cost saving"
    )
    @PostMapping("/{userId}/generate-carts")
    public ResponseEntity<List<ShoppingCartDTO>> generateCarts(@PathVariable String userId) {
        List<ShoppingCartDTO> carts = shoppingListService.generateShoppingCarts(userId);
        return ResponseEntity.ok(carts);
    }
}
