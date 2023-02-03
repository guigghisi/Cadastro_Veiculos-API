package com.projedata.sms.service;

import com.projedata.sms.entity.Product;
import com.projedata.sms.entity.RawMaterial;
import com.projedata.sms.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;


    @Test
    void createNewProduct() {
        var product = new Product(1L, "teste", BigDecimal.valueOf(10), List.of(new RawMaterial(1L, "teste", 1)));
        when(productRepository.save(product)).thenAnswer(i -> i.getArgument(0));
        var savedProduct = productService.createNewProduct(product);

        assertEquals(product, savedProduct);
    }

    @Test
    void updateProduct() {
    }

    @Test
    void produceProduct() {
    }
}