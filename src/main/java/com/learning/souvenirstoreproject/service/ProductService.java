package com.learning.souvenirstoreproject.service;

import com.learning.souvenirstoreproject.dto.request.ProductCreationRequest;
import com.learning.souvenirstoreproject.dto.request.ProductUpdateRequest;
import com.learning.souvenirstoreproject.dto.response.ProductResponse;
import com.learning.souvenirstoreproject.entity.Product;
import com.learning.souvenirstoreproject.exception.AppException;
import com.learning.souvenirstoreproject.exception.ErrorCode;
import com.learning.souvenirstoreproject.mapper.ProductMapper;
import com.learning.souvenirstoreproject.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
public class ProductService {
    ProductRepository productRepository;
    ProductMapper productMapper;

    //create
    public ProductResponse createProduct(ProductCreationRequest productCreationRequest) {
        if (productRepository.existsByName(productCreationRequest.getName())) {
            throw new AppException(ErrorCode.PRODUCT_EXISTED);
        }
        Product product = productMapper.toProduct(productCreationRequest);

        return productMapper.toProductResponse(productRepository.save(product));
    }

    //getProductById
    public ProductResponse getProductById(Long id){
        Product product = productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        return productMapper.toProductResponse(product);
    }

    //update
    public ProductResponse updateProduct(Long id, ProductUpdateRequest productUpdateRequest) {
        Product product = productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        productMapper.updateProduct(product, productUpdateRequest);

        return productMapper.toProductResponse(productRepository.save(product));
    }

    //delete
    public void deleteProductById(Long id){
        productRepository.deleteById(id);
    }

    //getAllProducts
    public List<ProductResponse> getAllProducts(){
        List<Product> products = productRepository.findAll();

        return productMapper.toProductResponseList(products);
    }
}
