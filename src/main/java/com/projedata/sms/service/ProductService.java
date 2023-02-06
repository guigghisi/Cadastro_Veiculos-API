package com.projedata.sms.service;

import com.projedata.sms.entity.Product;
import com.projedata.sms.entity.RawMaterial;
import com.projedata.sms.exception.ProductNotFoundException;
import com.projedata.sms.repository.ProductRepository;
import com.projedata.sms.repository.RawMaterialRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository repository;
    private final RawMaterialRepository rawMaterialRepository;

    public ProductService(ProductRepository repository, RawMaterialRepository rawMaterialRepository) {
        this.repository = repository;
        this.rawMaterialRepository = rawMaterialRepository;
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

    public List produceProduct() {
        var products = repository.findAllOrderByPriceDesc();
        var manufacturedProducts = new ArrayList<Product>();
        var rawMaterials = rawMaterialRepository.findAll();
        Map<RawMaterial, Integer> availableRawMaterials = rawMaterials.stream().collect(Collectors.toMap(rawMaterial -> rawMaterial, RawMaterial::getStocked));
        var totalValue = BigDecimal.ZERO;
        var productRawMaterialCount = repository.findProductRawMaterialCount();
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            boolean canProduce = true;
            for (Object[] productRawMaterial : productRawMaterialCount) {
                if (productRawMaterial[0].equals(product.getId())) {
                    RawMaterial rawMaterial = rawMaterialRepository.findById((Long) productRawMaterial[1]).orElse(null);
                    if (availableRawMaterials.get(rawMaterial) == null || availableRawMaterials.get(rawMaterial) < ((Long) productRawMaterial[2]).intValue()) {
                        canProduce = false;
                        break;
                    }
                }
            }
            if (canProduce) {
                manufacturedProducts.add(product);
                totalValue = totalValue.add(product.getPrice());
                for (Object[] productRawMaterial : productRawMaterialCount) {
                    if (productRawMaterial[0].equals(product.getId())) {
                        RawMaterial rawMaterial = rawMaterialRepository.findById((Long) productRawMaterial[1]).orElse(null);
                        availableRawMaterials.put(rawMaterial, availableRawMaterials.get(rawMaterial) - ((Long) productRawMaterial[2]).intValue());
                    }
                }
                i--;
            }
        }
        return manufacturedProducts;
    }


}

