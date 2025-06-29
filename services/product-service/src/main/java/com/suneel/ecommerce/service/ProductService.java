package com.suneel.ecommerce.service;

import com.suneel.ecommerce.dto.ProductPurchaseRequest;
import com.suneel.ecommerce.dto.ProductPurchaseResponse;
import com.suneel.ecommerce.dto.ProductRequest;
import com.suneel.ecommerce.dto.ProductResponse;
import com.suneel.ecommerce.exception.ProductPurchaseException;
import com.suneel.ecommerce.mapper.ProductMapper;
import com.suneel.ecommerce.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    public Integer createProduct(
            @RequestBody ProductRequest productRequest
            ){
        var product =  productMapper.toProductEntity(productRequest);
        return productRepository.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProducts(
            List<ProductPurchaseRequest> request
    ){
                // store the productIds from the productRequest
        var productIds = request
                .stream()
                .map(ProductPurchaseRequest::productId)
                .toList();

                // get the list of stored products with the requestedIds
        var storedProducts = productRepository.findAllByIdInOrderById(productIds);
        if(productIds.size() != storedProducts.size()){
            throw new ProductPurchaseException("One or more products does not exist");
        }
                // sort the request with the productId
        var sortedRequest = request
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();
                // create an empty list to store the response and return
        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();
                // looping thru the stored products and compare the quantities
        for (int i=0 ; i<storedProducts.size(); i++){
            var existingProduct = storedProducts.get(i);
            var productRequest = sortedRequest.get(i);
            if(existingProduct.getAvailableQuantity() < productRequest.quantity()){
                throw new ProductPurchaseException("Insufficient stock quantity for this product with ID: "+ productRequest.productId());
            }
                // calculate the remaining quantity
            var updatedQuantity = existingProduct.getAvailableQuantity() - productRequest.quantity();
                // update the quantity for that product
            existingProduct.setAvailableQuantity(updatedQuantity);
                // save the product details after updating the quantity
            productRepository.save(existingProduct);
                // add the response object to the list
            purchasedProducts.add(productMapper.toProductPurchaseResponse(existingProduct, productRequest.quantity()));
        }
        return purchasedProducts;
    }


    public ProductResponse findProduct(Integer id){
        return productRepository.findById(id)
                .map(productMapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
    }


    public List<ProductResponse> findAllProducts(){
        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }



}
