package com.b8.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.b8.common.dto.OrderRequestDTO;
import com.b8.common.mapper.OrderDTOtoEntityMapper;
import com.b8.common.mapper.OrderEntityToOutboxEntityMapper;
import com.b8.entity.Order;
import com.b8.entity.Outbox;
import com.b8.repository.OrderRepository;
import com.b8.repository.OutboxRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderDTOtoEntityMapper orderDTOtoEntityMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OutboxRepository outboxRepository;

    @Autowired
    private OrderEntityToOutboxEntityMapper orderEntityToOutboxEntityMapper;

    @Transactional
    public Order createOrder(OrderRequestDTO orderRequestDTO) {
        // Order Repository Table
        Order order = orderDTOtoEntityMapper.map(orderRequestDTO);
        order = orderRepository.save(order);

        Outbox outbox = orderEntityToOutboxEntityMapper.map(order);
        outboxRepository.save(outbox);

        return order;
    }
}
