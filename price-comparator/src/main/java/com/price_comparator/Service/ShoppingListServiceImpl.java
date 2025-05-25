package com.price_comparator.Service;


import com.price_comparator.Domain.*;
import com.price_comparator.Domain.DTO.FinalPriceDTO;
import com.price_comparator.Domain.DTO.ItemDTO;
import com.price_comparator.Domain.DTO.ShoppingCartDTO;
import com.price_comparator.Repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ShoppingListServiceImpl implements ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final DiscountRepository discountRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final PriceCalculationService priceCalculationService;

    @Override
    public void addToShoppingList(String userId, String productId) {
        ShoppingList list = shoppingListRepository.findByUserId(userId)
                .orElseGet(() -> {
                    ShoppingList newList = new ShoppingList();
                    newList.setUserId(userId);
                    newList.setProductIds(new ArrayList<>());
                    return newList;
                });

        if (!list.getProductIds().contains(productId)) {
            list.getProductIds().add(productId);
            shoppingListRepository.save(list);
        }
    }

    @Override
    public ShoppingList getShoppingListByUser(String userId) {
        return shoppingListRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("No shopping list found for user " + userId));
    }

    @Override
    public void clearList(String userId) {
        shoppingListRepository.findByUserId(userId).ifPresent(list -> {
            list.getProductIds().clear();
            shoppingListRepository.save(list);
        });
    }

    @Override
    @Transactional
    public List<ShoppingCartDTO> generateShoppingCarts(String userId) {
        Integer userIdInt = Integer.valueOf(userId);
        shoppingCartRepository.deleteByUserId(userIdInt);

        ShoppingList list = getShoppingListByUser(userId);
        Map<String, ShoppingCart> carts = new HashMap<>();
        LocalDate today = LocalDate.of(2025, 5, 5); // For testing purposes

        for (String productId : list.getProductIds()) {
            List<FinalPriceDTO> bestPrices = priceCalculationService.getLowestFinalPrice(productId);
            if (bestPrices.isEmpty()) continue;

            FinalPriceDTO best = bestPrices.get(0);

            Product product = productRepository.findByProductIdAndStore(productId, best.getStoreName());
            if (product == null) continue;

            ShoppingCart cart = carts.computeIfAbsent(best.getStoreName(), store -> {
                ShoppingCart newCart = new ShoppingCart();
                newCart.setStoreName(store);
                User user = userRepository.findById(userIdInt)
                        .orElseThrow(() -> new NoSuchElementException("User not found: " + userId));
                newCart.setUser(user);
                newCart.setItems(new ArrayList<>());
                newCart.setTotal(0);
                return newCart;
            });

            ShoppingCartItem item = new ShoppingCartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(1); // TODO: pull from ShoppingList in the future
            item.setUnitPrice(best.getFinalPrice());
            item.setTotalPrice(best.getFinalPrice());

            cart.getItems().add(item);
            cart.setTotal(cart.getTotal() + best.getFinalPrice());
        }

        List<ShoppingCart> savedCarts = shoppingCartRepository.saveAll(new ArrayList<>(carts.values()));

        List<ShoppingCartDTO> res = savedCarts.stream().map(cart -> {
            ShoppingCartDTO cartDTO = new ShoppingCartDTO();
            cartDTO.setStore(cart.getStoreName());
            cartDTO.setTotalPrice(
                    BigDecimal.valueOf(cart.getTotal())
                            .setScale(2, RoundingMode.HALF_UP)
                            .doubleValue()
            );

            List<ItemDTO> items = cart.getItems().stream().map(item -> {
                ItemDTO dto = new ItemDTO();
                dto.setId(item.getProduct().getId());
                dto.setProductName(item.getProduct().getProductName());
                dto.setQuantity(item.getQuantity());
                return dto;
            }).toList();

            cartDTO.setItems(items);
            return cartDTO;
        }).toList();
        return res;
    }
}