package com.b8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.b8.dto.ProductEvent;
import com.b8.entity.Product;
import com.b8.service.ProductCommandService;

@RestController
@RequestMapping("/products")
public class ProductCommandController {

    @Autowired
    private ProductCommandService commandService;

    @PostMapping
    public Product createProduct(@RequestBody ProductEvent productEvent) {
        return commandService.createProduct(productEvent);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable long id, @RequestBody ProductEvent productEvent) {
        return commandService.updateProduct(id, productEvent);
    }
}
