package com.jlim.store.webapp.web.controllers;

import com.jlim.store.webapp.web.clients.catalog.CatalogServiceClient;
import com.jlim.store.webapp.web.clients.catalog.PagedResult;
import com.jlim.store.webapp.web.clients.catalog.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

// controller not rest controller as we are rendering thymeleaf template not json
@Controller
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final CatalogServiceClient catalogServiceClient;

    ProductController(CatalogServiceClient catalogServiceClient) {
        this.catalogServiceClient = catalogServiceClient;
    }

    @GetMapping
    String index() {
        return "redirect:/products";
    }

    @GetMapping("/products")
    String productsPage(@RequestParam(name = "page", defaultValue = "1") int pageNo, Model model) {
        model.addAttribute("pageNo", pageNo);
        return "products";
    }

    @GetMapping("/api/products")
    @ResponseBody
    PagedResult<Product> products(@RequestParam(name = "page", defaultValue = "1") int pageNo, Model model) {
        log.info("Fetching products for page: {}", pageNo);
        return catalogServiceClient.getProducts(pageNo);
    }
}
