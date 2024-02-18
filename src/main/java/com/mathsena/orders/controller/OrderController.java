package com.mathsena.orders.controller;

import com.mathsena.orders.dto.OrderDto;
import com.mathsena.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping()
    public List<OrderDto> getAllOrders() {
        return service.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> listarPorId(@PathVariable @NotNull Long id) {
        OrderDto dto = service.getById(id);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/port")
    public String returnPort(@Value("${local.server.port}") String port) {
        return String.format("Requisition on port:  %s", port);
    }

    @PostMapping()
    public ResponseEntity<OrderDto> realizaPedido(@RequestBody @Valid OrderDto dto, UriComponentsBuilder uriBuilder) {
        OrderDto orderRealized = service.createOrder(dto);

        URI uri = uriBuilder.path("/orders/{id}")
                .buildAndExpand(orderRealized.getId())
                .toUri();

        return ResponseEntity.created(uri)
                .body(orderRealized);

    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderDto> atualizaStatus(@PathVariable Long id, @RequestBody OrderDto status) {
        OrderDto dto = service.updateStatus(id, status);

        return ResponseEntity.ok(dto);
    }


    @PutMapping("/{id}/paid")
    public ResponseEntity<Void> aprovePayment(@PathVariable @NotNull Long id) {
        service.aprovePayment(id);

        return ResponseEntity.ok()
                .build();

    }
}
