package io.bootify.momo.domain.order.controller;

import io.bootify.momo.model.OrderDetailDTO;
import io.bootify.momo.domain.order.service.OrderDetailService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/orderDetails", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    public OrderDetailController(final OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDetailDTO>> getAllOrderDetails() {
        return ResponseEntity.ok(orderDetailService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailDTO> getOrderDetail(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(orderDetailService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createOrderDetail(
            @RequestBody @Valid final OrderDetailDTO orderDetailDTO) {
        final Long createdId = orderDetailService.create(orderDetailDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateOrderDetail(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final OrderDetailDTO orderDetailDTO) {
        orderDetailService.update(id, orderDetailDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable(name = "id") final Long id) {
        orderDetailService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
