package com.novaretail.backend.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.novaretail.backend.dto.SaleDetailCreateDTO;
import com.novaretail.backend.dto.SaleDetailUpdateDTO;

import com.novaretail.backend.entity.SaleDetail;
import com.novaretail.backend.entity.Products;

import com.novaretail.backend.repository.SaleDetailRepository;
import com.novaretail.backend.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleDetailService {

    private final SaleDetailRepository saleDetailRepository;
    private final ProductRepository productRepository;

    // GET ALL
    public List<SaleDetail> getAllSaleDetail() {
        return saleDetailRepository.findAll();
    }

    // GET BY ID
    public SaleDetail getSaleDetailById(Long id) {
        return saleDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle de venta no encontrado"));
    }

    // CREATE
    public SaleDetail createSaleDetail(SaleDetailCreateDTO dto) {

    if (dto.getProductId() == null || dto.getQuantity() == null) {
        throw new RuntimeException("productId y quantity son obligatorios");
    }

    Products product = productRepository.findById(dto.getProductId())
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

    SaleDetail saleDetail = new SaleDetail();

    saleDetail.setProduct(product);
    saleDetail.setQuantity(dto.getQuantity());

    BigDecimal quantity = BigDecimal.valueOf(dto.getQuantity());

    saleDetail.setSubtotal(
            product.getPrice().multiply(quantity)
    );

    return saleDetailRepository.save(saleDetail);
}

    // UPDATE
    public SaleDetail updateSaleDetail(Long id, SaleDetailUpdateDTO dto) {

        SaleDetail saleDetail = getSaleDetailById(id);

        // actualizar producto si viene
        if (dto.getProductId() != null) {
            Products product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            saleDetail.setProduct(product);
        }

        // actualizar quantity si viene
        if (dto.getQuantity() != null) {
            saleDetail.setQuantity(dto.getQuantity());
        }

        // recalcular subtotal SIEMPRE
        BigDecimal quantity = BigDecimal.valueOf(saleDetail.getQuantity());

        saleDetail.setSubtotal(
                saleDetail.getProduct().getPrice().multiply(quantity)
        );

        return saleDetailRepository.save(saleDetail);
    }

    // DELETE
    public void deleteSaleDetail(Long id) {
        SaleDetail saleDetail = getSaleDetailById(id);
        saleDetailRepository.delete(saleDetail);
    }
}