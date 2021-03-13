package com.dgraysh.market.services;

import com.dgraysh.market.dto.OrderDto;
import com.dgraysh.market.entities.Order;
import com.dgraysh.market.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public List<OrderDto> findAllUsersOrderDtosByUsername(String username) {
        return orderRepository.findAllOrdersByUsername(username).stream().map(OrderDto::new).collect(Collectors.toList());
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }
}
