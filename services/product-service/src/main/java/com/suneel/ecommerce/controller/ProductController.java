package com.suneel.ecommerce.controller;

import com.suneel.ecommerce.dto.ProductPurchaseRequest;
import com.suneel.ecommerce.dto.ProductPurchaseResponse;
import com.suneel.ecommerce.dto.ProductRequest;
import com.suneel.ecommerce.dto.ProductResponse;
import com.suneel.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Integer> createProduct(
            @RequestBody ProductRequest productRequest
            ){
        return ResponseEntity.ok(productService.createProduct(productRequest));
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>> purchaseProducts(
            @RequestBody List<ProductPurchaseRequest> productPurchaseRequest
    ){
        return ResponseEntity.ok(productService.purchaseProducts(productPurchaseRequest));
    }

    @GetMapping("/{product-id}")
    public ResponseEntity<ProductResponse> findProduct(
            @PathVariable("product-id") Integer id
    ){
        return ResponseEntity.ok(productService.findProduct(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAllProducts(){
        return ResponseEntity.ok(productService.findAllProducts());
    }
}
