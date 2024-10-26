package io.bootify.momo.domain.member.controller;

import io.bootify.momo.domain.member.dto.response.CartResponse;
import io.bootify.momo.domain.member.service.CartService;
import jakarta.validation.Valid;
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
@RequestMapping(value = "/api/carts", produces = MediaType.APPLICATION_JSON_VALUE)
public class CartController {

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartResponse> getCart(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(cartService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createCart(@RequestBody @Valid final CartDTO cartDTO) {
        final Long createdId = cartService.create(cartDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateCart(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final CartDTO cartDTO) {
        cartService.update(id, cartDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable(name = "id") final Long id) {
        cartService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
