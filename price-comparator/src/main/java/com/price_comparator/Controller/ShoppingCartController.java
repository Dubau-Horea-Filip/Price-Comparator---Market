package com.price_comparator.Controller;

import com.price_comparator.Domain.ShoppingCart;
import com.price_comparator.Domain.User;
import com.price_comparator.Repository.ShoppingCartRepository;
import com.price_comparator.Repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shopping-cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    private UserRepository userRepository;

//    @Operation(summary = "getShoppingCartByUserId", description = "")
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<ShoppingCart> getShoppingCartByUserId(@PathVariable Integer userId) {
//        ResponseEntity<User> res = userRepository.findByUserId(userId)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//        return res;
//    }
}
