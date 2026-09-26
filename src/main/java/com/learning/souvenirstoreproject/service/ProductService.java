package com.learning.souvenirstoreproject.service;

import com.learning.souvenirstoreproject.dto.request.ProductCreationRequest;
import com.learning.souvenirstoreproject.dto.request.ProductUpdateRequest;
import com.learning.souvenirstoreproject.dto.response.ProductResponse;
import com.learning.souvenirstoreproject.entity.Category;
import com.learning.souvenirstoreproject.entity.Product;
import com.learning.souvenirstoreproject.enums.ProductStatus;
import com.learning.souvenirstoreproject.exception.AppException;
import com.learning.souvenirstoreproject.exception.ErrorCode;
import com.learning.souvenirstoreproject.mapper.ProductMapper;
import com.learning.souvenirstoreproject.repository.CategoryRepository;
import com.learning.souvenirstoreproject.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
public class ProductService {
    ProductRepository productRepository;
    ProductMapper productMapper;
    CategoryRepository categoryRepository;

    //create
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ProductResponse createProduct(ProductCreationRequest productCreationRequest) {
        Category category = categoryRepository.findById(productCreationRequest.getCategoryId()).
                orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        if (productRepository.existsByName(productCreationRequest.getName())) {
            throw new AppException(ErrorCode.PRODUCT_EXISTED);
        }
        Product product = productMapper.toProduct(productCreationRequest);

        product.setCategory(category);
        product.setThumbnail(productCreationRequest.getThumbnail());

        try {
            productRepository.save(product);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.PRODUCT_EXISTED);
        }

        return productMapper.toProductResponse(product);
    }

    //getProductById
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        return productMapper.toProductResponse(product);
    }

    //getAllProducts
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return productMapper.toProductResponseList(products);
    }

    //update
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ProductResponse updateProduct(Long id, ProductUpdateRequest productUpdateRequest) {
        Product product = productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        productMapper.updateProduct(product, productUpdateRequest);

        return productMapper.toProductResponse(productRepository.save(product));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse activateProductStatus(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        product.setStatus(ProductStatus.INACTIVE);

        return  productMapper.toProductResponse(productRepository.save(product));
    }

    //deactivateProduct
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse deactivateProductStatus(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        product.setStatus(ProductStatus.INACTIVE);

        return  productMapper.toProductResponse(productRepository.save(product));
    }

    //delete
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        productRepository.delete(product);
    }
}
