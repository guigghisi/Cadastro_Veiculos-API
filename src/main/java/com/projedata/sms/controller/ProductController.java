package com.projedata.sms.controller;

import com.projedata.sms.entity.dto.ProductDto;
import com.projedata.sms.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        var product = productDto.transformEntity();
        var savedProduct = productService.createNewProduct(product);

        return productDto.transformDto(savedProduct);
    }

    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto updateProduct(@PathVariable(value = "productId") Long productID, @RequestBody ProductDto productDto) {
        //TODO validar se o produto existe/fazer o usuario escolher entre uma lista de produtos
        var newProduct = productService.updateProduct(productID, productDto.transformEntity());
        return productDto.transformDto(newProduct);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> findProduct() {
        var products = productService.findAll();
        var productsDto = new ProductDto();
        return productsDto.transformListDtos(products);
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto findProduct(@PathVariable(value = "producId") Long productId) {
        var product = productService.findById(productId);
        var productsDto = new ProductDto();
        return productsDto.transformDto(product);
    }

    @GetMapping("/produce")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> produceProduct() {
        var products = productService.produceProduct();
        var productsDto = new ProductDto();
        return productsDto.transformListDtos(products);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteProduct(@PathVariable(value = "productId") Long productId) {
        productService.deleteProduct(productId);
    }
}
