package com.b8.service;

import com.b8.dto.ProductEvent;
import com.b8.entity.Product;
import com.b8.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductQueryService {

    @Autowired
    private ProductRepository repository;

    public List<Product> getProducts() {
        return repository.findAll();
    }

    @KafkaListener(topics = "product-event-topic",groupId = "product-event-group")
    public void processProductEvents(ProductEvent productEvent) {
        Product product = productEvent.getProduct();
        if (productEvent.getEventType().equals("CreateProduct")) {
            repository.save(product);
        }
        if (productEvent.getEventType().equals("UpdateProduct")) {
            Product existingProduct = repository.findById(product.getId()).get();
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setDescription(product.getDescription());
            repository.save(existingProduct);
        }
    }
}
