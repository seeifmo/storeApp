package com.cgc.store.service;

import com.cgc.store.entity.CartItem;
import com.cgc.store.entity.User;
import com.cgc.store.repository.CartItemRepo;
import com.cgc.store.dto.CartItemDto;
import com.cgc.store.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepo cartItemRepo;
    private final ProductService productService;
    private final UserService userService;

    public List<CartItem> getCartItems(Long userId) {
        if(userId == null){
            throw new IllegalArgumentException("User id cannot be null");
        }

        return cartItemRepo.findByUserId(userId);
    }

    public List<CartItem> getCartItemsElseThrow(Long userId) {
        List<CartItem> cartItems = getCartItems(userId);
        userService.checkIfUserExistElseThrow(userId);
        return cartItems;
    }

    public CartItem addItemToCart(CartItemDto cartItemDto) {
        getCartIfExistThrow(cartItemDto);

        Product product = productService.checkIfProductExistElseThrow(cartItemDto.getProductId());

        User user = userService.checkIfUserExistElseThrow(cartItemDto.getUserId());

        validateCartItemQuantity(cartItemDto, product);

        CartItem newCartItem = new CartItem(user,product,cartItemDto.getQuantity());

        return cartItemRepo.save(newCartItem);
    }

    public CartItem updateCartItem(CartItemDto cartItemDto) {
        CartItem existingCartItem = getCartItemElseThrow(cartItemDto.getId());

        Product product = productService.checkIfProductExistElseThrow(cartItemDto.getProductId());

        validateCartItemQuantity(cartItemDto, product);

        existingCartItem.setQuantity(cartItemDto.getQuantity());

        return cartItemRepo.save(existingCartItem);
    }
    public void deleteCartItem(Long id) {
        getCartItemElseThrow(id);
        cartItemRepo.deleteById(id);
    }


    private CartItem getCartItemElseThrow(Long id) {
        if(id == null){
            throw new IllegalArgumentException("Cart item id cannot be null");
        }
        CartItem existingCartItem = cartItemRepo.findById(id).orElse(null);
        if (existingCartItem == null) {
            throw new IllegalArgumentException("Cart item with id " + id + " not found");
        }
        return existingCartItem;
    }
    private void getCartIfExistThrow(CartItemDto cartItem) {
        CartItem existingCartItem = cartItemRepo.findByUserIdAndProductId(cartItem.getUserId(), cartItem.getProductId());
        if (existingCartItem != null) {
            throw new IllegalArgumentException("Product with id " + cartItem.getProductId() + " already exists in cart");
        }
    }
    private void validateCartItemQuantity(CartItemDto cartItem, Product product) {
        if(cartItem.getQuantity() == null || cartItem.getQuantity() <= 0){
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        if(cartItem.getQuantity() > product.getStock()){
            throw new IllegalArgumentException("Not enough stock for product with id " + cartItem.getProductId());
        }
    }

}
