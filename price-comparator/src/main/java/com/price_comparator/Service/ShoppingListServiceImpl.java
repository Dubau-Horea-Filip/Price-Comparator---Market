package com.price_comparator.Service;



import com.price_comparator.Domain.*;
import com.price_comparator.Repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public List<ShoppingCart> generateShoppingCarts(String userId) {
        ShoppingList list = getShoppingListByUser(userId);
        Map<String, ShoppingCart> carts = new HashMap<>();
        LocalDate today = LocalDate.now();

        for (String productId : list.getProductIds()) {
            // Find the cheapest product with this productId
            Optional<Product> cheapestOpt = productRepository.findTopByProductIdOrderByPriceAsc(productId);
            if (cheapestOpt.isEmpty()) continue;

            Product cheapest = cheapestOpt.get();
            String store = cheapest.getStore();

            // Check for an active discount
            Optional<Discount> discountOpt = discountRepository
                    .findByProductIdAndFromDateLessThanEqualAndToDateGreaterThanEqual(
                            productId, today, today);

            double originalPrice = cheapest.getPrice();
            double finalPrice = originalPrice;

            if (discountOpt.isPresent()) {
                int percentage = discountOpt.get().getPercentageOfDiscount();
                finalPrice = originalPrice * (1 - percentage / 100.0);
            }

            // Get or create the store-specific cart
            ShoppingCart cart = carts.computeIfAbsent(store, s -> {
                ShoppingCart newCart = new ShoppingCart();
                newCart.setStoreName(s);
                User u = userRepository.findById(Integer.valueOf(userId))
                        .orElseThrow(() -> new NoSuchElementException("User not found: " + userId));
                newCart.setUser(u); // TODO: Set the user
                newCart.setItems(new ArrayList<>());
                newCart.setTotal(0);
                return newCart;
            });

            // Create cart item
            ShoppingCartItem item = new ShoppingCartItem();
            item.setCart(cart);
            item.setProduct(cheapest);
            item.setQuantity(1); //TODO Extend later to support per-product quantity
            item.setUnitPrice(finalPrice);
            item.setTotalPrice(finalPrice);

            cart.getItems().add(item);
            cart.setTotal(cart.getTotal() + finalPrice);
        }

        // Persist all carts with their items
        List<ShoppingCart> savedCarts = shoppingCartRepository.saveAll(new ArrayList<>(carts.values()));
        return savedCarts;
    }

}
