package com.cgc.store.controller;


import com.cgc.store.dto.AppResponse;
import com.cgc.store.entity.CartItem;
import com.cgc.store.dto.CartItemDto;
import com.cgc.store.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartItemService cartItemService;

    @GetMapping("/userId/{userId}")
    public ResponseEntity<AppResponse> getCartItems(@PathVariable Long userId) {
        return ResponseEntity.ok(new AppResponse(200, cartItemService.getCartItemsElseThrow(userId),"Cart items retrieved successfully"));
    }
    @PostMapping
    public ResponseEntity<AppResponse> addToCart(@RequestBody CartItemDto cartItem) {
        return ResponseEntity.ok(new AppResponse(201, cartItemService.addItemToCart(cartItem),"Item added to cart successfully"));
    }

    @PutMapping
    public ResponseEntity<AppResponse> updateCartItem(@RequestBody CartItemDto cartItem) {
        return ResponseEntity.ok( new AppResponse(200, cartItemService.updateCartItem(cartItem),"Cart item updated successfully"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<AppResponse> deleteCartItem(@PathVariable Long id) {
        cartItemService.deleteCartItem(id);
        return ResponseEntity.ok( new AppResponse(200, null,"Cart item deleted successfully"));
    }

}
