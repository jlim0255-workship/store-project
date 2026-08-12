package com.jlim.store.webapp.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


// controller not rest controller as we are rendering thymeleaf template not json
@Controller
public class ProductController {

    @GetMapping
    String index(){
        return "redirect:/products";
    }

    @GetMapping("/products")
    String productsPage(@RequestParam(name="page", defaultValue = "1") int pageNo, Model model){
        model.addAttribute("pageNo", pageNo);
        return "products";
    }

    
}
