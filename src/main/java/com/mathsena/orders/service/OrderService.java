package com.mathsena.orders.service;


import com.mathsena.orders.dto.OrderDto;
import com.mathsena.orders.model.Order;
import com.mathsena.orders.model.Status;
import com.mathsena.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private final ModelMapper modelMapper;
    @Autowired
    private OrderRepository repository;

    public List<OrderDto> getAllOrders() {
        return repository.findAll()
                .stream()
                .map(p -> modelMapper.map(p, OrderDto.class))
                .collect(Collectors.toList());
    }

    public OrderDto getById(Long id) {
        Order order = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(order, OrderDto.class);
    }

    public OrderDto createOrder(OrderDto dto) {
        Order order = modelMapper.map(dto, Order.class);

        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Status.COMPLETED);
        order.getItens()
                .forEach(item -> item.setOrder(order));
        Order saved = repository.save(order);

        return modelMapper.map(order, OrderDto.class);
    }

    public OrderDto updateStatus(Long id, OrderDto dto) {
        Order order = repository.findBYIdWithItems(id);

        if (order == null) {
            throw new EntityNotFoundException();
        }

        order.setStatus(dto.getStatus());
        repository.updateStatus(dto.getStatus(), order);
        return modelMapper.map(order, OrderDto.class);
    }

    public void aprovePayment(Long id) {
        Order order = repository.findBYIdWithItems(id);

        if (order == null) {
            throw new EntityNotFoundException();
        }

        order.setStatus(Status.PAID);
        repository.updateStatus(Status.PAID, order);
    }
}