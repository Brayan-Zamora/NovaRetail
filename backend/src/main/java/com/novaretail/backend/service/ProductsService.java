package com.novaretail.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.novaretail.backend.dto.ProductsCreateDTO;
import com.novaretail.backend.dto.ProductsUpdateDTO;
import com.novaretail.backend.entity.Products;
import com.novaretail.backend.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductRepository productRepository;

    public List<Products> getAllProducts() {
        return productRepository.findAll();
    }

    public Products getProductById(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public Products createProducts(ProductsCreateDTO dto) {

        Products product = new Products();

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setCategory(dto.getCategory());

        return productRepository.save(product);
    }

    public Products updateProducts(Long id, ProductsUpdateDTO dto) {

        Products product = getProductById(id);

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setCategory(dto.getCategory());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}