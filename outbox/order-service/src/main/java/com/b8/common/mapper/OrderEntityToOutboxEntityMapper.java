package com.b8.common.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.b8.entity.Order;
import com.b8.entity.Outbox;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class OrderEntityToOutboxEntityMapper {


    @SneakyThrows
    public Outbox map(Order order) {
        return
                Outbox.builder()
                        .aggregateId(order.getId().toString())
                        .payload(new ObjectMapper().writeValueAsString(order))
                        .createdAt(new Date())
                        .processed(false)
                        .build();
    }
}