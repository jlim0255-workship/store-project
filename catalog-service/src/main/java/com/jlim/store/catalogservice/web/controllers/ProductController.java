package com.jlim.store.catalogservice.web.controllers;

import com.jlim.store.catalogservice.domain.PagedResult;
import com.jlim.store.catalogservice.domain.Product;
import com.jlim.store.catalogservice.domain.ProductNotFoundException;
import com.jlim.store.catalogservice.domain.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
// MEAT: no need to set class or method to public, no one else except Spring Boot can call them!
class ProductController {

    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    PagedResult<Product> getProducts(@RequestParam(name = "page", defaultValue = "1") int pageNo) {
        return productService.getProducts(pageNo);
    }

    @GetMapping("/{code}")
    ResponseEntity<Product> getProductByCode(@PathVariable String code) {
        return productService
                .getProductByCode(code)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> ProductNotFoundException.forCode(code));
    }
}
