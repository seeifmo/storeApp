package com.cgc.store.service;

import com.cgc.store.entity.CartItem;
import com.cgc.store.entity.Purchase;
import com.cgc.store.repository.PurchaseRepo;
import com.cgc.store.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepo purchaseRepo;
    private final CartItemService cartItemService;
    private final ProductService productService;
    private final UserService userService;


    public List<Purchase> getPurchaseHistory(Long userId) {
        if(userId == null){
            throw new IllegalArgumentException("User id cannot be null");
        }
        userService.checkIfUserExistElseThrow(userId);
        return purchaseRepo.findByUserId(userId);
    }

    public String completePurchase(Long userId) {

        userService.checkIfUserExistElseThrow(userId);
        List<CartItem> cartItems = cartItemService.getCartItems(userId);

        if (cartItems == null || cartItems.isEmpty()) {
           throw new IllegalArgumentException("Cart is empty");
        }

        int totalPrice = 0;

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();

            if (cartItem.getQuantity() > product.getStock()) {
                throw new IllegalArgumentException("Not enough stock for product " + product.getName());
            }
            product.setStock(product.getStock() - cartItem.getQuantity());
            productService.updateProduct(product);

            purchaseRepo.save(new Purchase(cartItem.getUser(), product, cartItem.getQuantity(), cartItem.getQuantity() * product.getPrice()));

            totalPrice += (int) (cartItem.getQuantity() * product.getPrice());

            cartItemService.deleteCartItem(cartItem.getId());
        }

        return "Purchase completed successfully. Total price: " + totalPrice;
    }



}
