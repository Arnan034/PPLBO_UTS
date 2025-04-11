package com.b8.order.service;

import com.b8.saga.commons.dto.OrderRequestDto;
import com.b8.saga.commons.event.OrderEvent;
import com.b8.saga.commons.event.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
public class OrderStatusPublisher {

    @Autowired
    private Sinks.Many<OrderEvent> orderSinks;


    public void publishOrderEvent(OrderRequestDto orderRequestDto, OrderStatus orderStatus){
        OrderEvent orderEvent=new OrderEvent(orderRequestDto,orderStatus);
        Sinks.EmitResult result = orderSinks.tryEmitNext(orderEvent);
        System.out.println("Emit result: " + result);
    }
}