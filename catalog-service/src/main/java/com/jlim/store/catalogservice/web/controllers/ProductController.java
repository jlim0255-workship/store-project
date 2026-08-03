package com.jlim.store.catalogservice.web.controllers;

import com.jlim.store.catalogservice.domain.PagedResult;
import com.jlim.store.catalogservice.domain.Product;
import com.jlim.store.catalogservice.domain.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
// MEAT: no need to set class or method to public, no one else except Spring Boot can call them!
class ProductController {

    private final ProductService productService;

    ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    PagedResult<Product> getProducts(@RequestParam(name="page", defaultValue = "1") int pageNo){
        return productService.getProducts(pageNo);
    }

}
