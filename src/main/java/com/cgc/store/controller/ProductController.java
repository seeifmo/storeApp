package com.cgc.store.controller;

import com.cgc.store.dto.AppResponse;
import com.cgc.store.entity.Product;
import com.cgc.store.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<AppResponse> saveProduct(@RequestBody Product product) {
        return ResponseEntity.ok(new AppResponse(201, productService.saveProduct(product),"Product created successfully"));
    }
    @PutMapping
    public ResponseEntity<AppResponse> updateProduct(@RequestBody Product product) {
        return ResponseEntity.ok(new AppResponse(200,  productService.updateProduct(product), "Product updated successfully"));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<AppResponse> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok( new AppResponse(200, null, "Product deleted successfully"));
    }
    @GetMapping("/all")
    public ResponseEntity<AppResponse> getAllProducts() {
        return ResponseEntity.ok(new AppResponse(200, productService.getAllProducts(), "All products fetched successfully"));
    }

    @GetMapping("{id}")
    public ResponseEntity<AppResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(new AppResponse(200, productService.getProductByIdElseThrow(id), "Product fetched successfully"));
    }
}
