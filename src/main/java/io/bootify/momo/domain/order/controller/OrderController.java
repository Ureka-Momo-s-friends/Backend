package io.bootify.momo.domain.order.controller;

import io.bootify.momo.domain.order.dto.request.OrderRequest;
import io.bootify.momo.domain.order.dto.response.OrderDetailResponse;
import io.bootify.momo.domain.order.dto.response.OrdersResponse;
import io.bootify.momo.domain.order.model.OrderStatus;
import io.bootify.momo.domain.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
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
