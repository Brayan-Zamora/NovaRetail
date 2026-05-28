package com.novaretail.backend.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.novaretail.backend.dto.SaleCreateDTO;
import com.novaretail.backend.dto.SaleDetailCreateDTO;
import com.novaretail.backend.entity.Customer;
import com.novaretail.backend.entity.Products;
import com.novaretail.backend.entity.Sale;
import com.novaretail.backend.entity.SaleDetail;
import com.novaretail.backend.entity.User;
import com.novaretail.backend.repository.CustomerRepository;
import com.novaretail.backend.repository.ProductRepository;
import com.novaretail.backend.repository.SaleRepository;
import com.novaretail.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public List<Sale> getAllSale(){
        return saleRepository.findAll();
    }

    public Sale getSaleById(Long id){
        return saleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("venta no encontrada"));
    }

    public Sale createSale(SaleCreateDTO dto){

        Customer customer = customerRepository.findById(dto.getCustomerId())
                            .orElseThrow(() -> new RuntimeException("cliente no encontrado"));

        User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new RuntimeException("usuario no encontrado"));

        Sale sale = new Sale();

        sale.setCustomer(customer);
        sale.setUser(user);
        sale.setSaleDate(LocalDateTime.now());

        BigDecimal total = BigDecimal.ZERO;

        List<SaleDetail> saleDetails = new ArrayList<>();

        for (SaleDetailCreateDTO detailDTO: dto.getDetails()){

            Products product = productRepository.findById(detailDTO.getProductId())
                                    .orElseThrow(() -> new RuntimeException("producto no encontrado"));
            
            if (product.getStock() < detailDTO.getQuantity()){
                throw new RuntimeException(("Stock insuficiente"));
            }

            product.setStock(product.getStock() - detailDTO.getQuantity());
            
            SaleDetail detail = new SaleDetail();

            detail.setSale(sale);
            detail.setProduct(product);
            detail.setQuantity(detailDTO.getQuantity());

            BigDecimal quantity = BigDecimal.valueOf(detailDTO.getQuantity());

            BigDecimal subTotal = product.getPrice().multiply(quantity);

            detail.setSubtotal(subTotal);

            total = total.add(subTotal);

            saleDetails.add(detail);
        }

        sale.setTotal(total);

        sale.setSaleDetails(saleDetails);

        return saleRepository.save(sale);
    }

    public void deleteSale(Long id){
        saleRepository.deleteById(id);
    }

}
