package com.ureca.momo.domain.order.controller;

import com.ureca.momo.domain.order.dto.request.OrderRequest;
import com.ureca.momo.domain.order.dto.response.OrderDetailResponse;
import com.ureca.momo.domain.order.dto.response.OrdersResponse;
import com.ureca.momo.domain.order.model.OrderStatus;
import com.ureca.momo.domain.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrdersResponse>> getAllOrders(@RequestBody Long memberId) {
        return ResponseEntity.ok(orderService.findAll(memberId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<OrderDetailResponse>> getAllOrderDetails(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(orderService.findAllDetails(id));
    }

    @PostMapping
    public ResponseEntity<Long> createOrder(@RequestBody @Valid final OrderRequest request, @RequestBody Long memberId) {
        final Long createdId = orderService.create(request, memberId);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateOrder(@PathVariable(name = "id") final Long id,
                                            @RequestBody @Valid OrderStatus orderStatus) {
        orderService.update(id, orderStatus);
        return ResponseEntity.ok(id);
    }

}
