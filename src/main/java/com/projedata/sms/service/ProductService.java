package com.projedata.sms.service;

import com.projedata.sms.entity.Product;
import com.projedata.sms.exception.ProductNotFoundException;
import com.projedata.sms.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product createNewProduct(Product product) {
        return repository.save(product);
    }

    public Product updateProduct(Long productId, Product product) {
        var newProduct = repository.findById(productId).orElseThrow(ProductNotFoundException::new);

        newProduct.setName(product.getName());
        newProduct.setPrice(product.getPrice());
        newProduct.setRawMaterials(product.getRawMaterials());
        return repository.save(newProduct);
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Product findById(Long productId) {
        return repository.findById(productId).orElseThrow(ProductNotFoundException::new);
    }

    public void deleteProduct(Long productId) {
        var product = repository.findById(productId).orElseThrow(ProductNotFoundException::new);
        repository.delete(product);
    }
}
