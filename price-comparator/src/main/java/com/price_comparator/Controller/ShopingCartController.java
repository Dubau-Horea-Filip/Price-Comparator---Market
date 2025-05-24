package com.price_comparator.Controller;


import com.price_comparator.Domain.DTO.ItemDTO;
import com.price_comparator.Domain.DTO.ShoppingCartDTO;
import com.price_comparator.Domain.Product;
import com.price_comparator.Domain.ShoppingCart;
import com.price_comparator.Repository.ProductRepository;
import com.price_comparator.Repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ShopingCart")
public class ShopingCartController {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @GetMapping
    public ResponseEntity<List<ShoppingCartDTO>> getAllShoppingCarts() {
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findAll();

        List<ShoppingCartDTO> shoppingCartDTOs = shoppingCarts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(shoppingCartDTOs);
    }

    // Mapper method converting entity to DTO
    private ShoppingCartDTO convertToDTO(ShoppingCart shoppingCart) {
        ShoppingCartDTO dto = new ShoppingCartDTO();
        dto.setId(shoppingCart.getId());
        dto.setUserName(shoppingCart.getUser().getName());

        List<ItemDTO> items = shoppingCart.getItems().stream().map(item -> {
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setId(item.getId());
            itemDTO.setProductName(item.getProduct().getProductName());
            itemDTO.setQuantity(item.getQuantity());
            return itemDTO;
        }).collect(Collectors.toList());

        dto.setItems(items);
        return dto;
    }
}