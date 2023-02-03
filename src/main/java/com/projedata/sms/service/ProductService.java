package com.projedata.sms.service;

import com.projedata.sms.entity.Product;
import com.projedata.sms.entity.RawMaterial;
import com.projedata.sms.exception.ProductNotFoundException;
import com.projedata.sms.repository.ProductRepository;
import com.projedata.sms.repository.RawMaterialRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository repository;
    private final RawMaterialRepository rawMaterialRepositoryRepository;

    public ProductService(ProductRepository repository, RawMaterialRepository rawMaterialRepositoryRepository) {
        this.repository = repository;
        this.rawMaterialRepositoryRepository = rawMaterialRepositoryRepository;
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
        var products = repository.findProductByPriceOrderByPriceDesc();
        var manufacturedProducts = List.of();
        var rawMaterials = rawMaterialRepositoryRepository.findAll();
        Map<RawMaterial, Integer> availableRawMaterials = rawMaterials.stream().collect(Collectors.toMap(rawMaterial -> rawMaterial, RawMaterial::getStocked));
        var totalValue = BigDecimal.ZERO;


        for (var currentProduct = 0; currentProduct < products.size(); ) {

            boolean canProduce = true;
            for (var rawMaterial : products.get(currentProduct).getRawMaterials()) {
                if (availableRawMaterials.get(rawMaterial) == null || availableRawMaterials.get(rawMaterial) < rawMaterial.getStocked()) {
                    canProduce = false;
                    break;
                }
            }
            if (canProduce) {
                manufacturedProducts.add(products.get(currentProduct));
                totalValue = totalValue.add(products.get(currentProduct).getPrice());
                for (var rawMaterial : products.get(currentProduct).getRawMaterials()) {
                    availableRawMaterials.put(rawMaterial, availableRawMaterials.get(rawMaterial) - rawMaterial.getStocked());
                }
            } else {
                products.remove(currentProduct);
                currentProduct++;
            }
        }
        return List.of(products, totalValue);
    }

}
