package com.projedata.sms.entity.dto;

import com.projedata.sms.entity.Product;
import com.projedata.sms.entity.RawMaterial;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ProductDto {
    private String name;
    private BigDecimal price;
    private List<RawMaterial> rawMaterials;

    public Product transformEntity() {
        var product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setRawMaterials(rawMaterials);
        return product;
    }

    public ProductDto transformDto(Product product) {
        var productDto = new ProductDto();
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setRawMaterials(product.getRawMaterials());
        return productDto;
    }

    public List<ProductDto> transformListDtos(List<Product> products) {
        return products.stream().map(this::transformDto).collect(Collectors.toList());
    }
}
