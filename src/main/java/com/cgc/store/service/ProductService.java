package com.cgc.store.service;

import com.cgc.store.repository.ProductRepo;
import com.cgc.store.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductService {

    private final ProductRepo productRepo;

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Product getProductById(Long id) {
        if(id == null){
            throw new IllegalArgumentException("Product id cannot be null");
        }
        return productRepo.findById(id).orElse(null);
    }

    public Product getProductByIdElseThrow(Long id) {
        Product product = getProductById(id);
        if (product == null) {
            throw new IllegalArgumentException("Product with id " + id + " not found");
        }
        return product;
    }


    public Product saveProduct(Product product) {
        checkIfProductExistByNameElseThrow(product.getName());
        validateProduct(product, true);
        return productRepo.save(product);
    }

    public void deleteProduct(Long id) {
        checkIfProductExistElseThrow(id);
        productRepo.deleteById(id);
    }

    public Product updateProduct(Product product) {
        checkIfProductNotExistsElseThrow(product.getId());
        validateProduct(product, false);
       return productRepo.save(product);
    }

    private void validateProduct(Product product, boolean save) {

        if(save){
            if (product.getId() != null) {
                throw new IllegalArgumentException("Product id must be null");
            }
        }
        else {
            if (product.getId() == null) {
                throw new IllegalArgumentException("Product id cannot be null");
            }
        }
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (product.getPrice() == null || product.getPrice() <= 0) {
            throw new IllegalArgumentException("Product price must be greater than 0");
        }
        if (product.getStock() == null || product.getStock() <= 0) {
            throw new IllegalArgumentException("Product quantity must be greater than 0");
        }
    }

    public Product checkIfProductExistElseThrow(Long id) {
        Product existingProduct = getProductById(id);
        if (existingProduct == null) {
            throw new IllegalArgumentException("Product with id " + id + " not found");
        }
        return existingProduct;
    }

    public void checkIfProductNotExistsElseThrow(Long id) {
        Product existingProduct = getProductById(id);
        if (existingProduct == null) {
            throw new IllegalArgumentException( "Product with id " + id + " not found");
        }
    }

    public Product getProductByName(String name) {
        return productRepo.findByName(name).orElse(null);
    }

    public void checkIfProductExistByNameElseThrow(String name) {
        Product existingProduct = getProductByName(name);
        if (existingProduct != null) {
            throw new IllegalArgumentException("Product with name " + name + " already exists");
        }
    }

}
