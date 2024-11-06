package com.ureca.momo.domain.member.controller;

import com.ureca.momo.domain.member.dto.request.CartRequest;
import com.ureca.momo.domain.member.dto.response.CartResponse;
import com.ureca.momo.domain.member.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/carts", produces = MediaType.APPLICATION_JSON_VALUE)
public class CartController {

    private final CartService cartService;

    // 특정 회원의 cart 전체 조회
    @GetMapping("/{memberId}")
    public ResponseEntity<List<CartResponse>> getCart(@PathVariable("memberId") final Long memberId) {
        return ResponseEntity.ok(cartService.get(memberId));
    }

    // 장바구니에 추가
    @PostMapping("/{memberId}")
    public ResponseEntity<Long> createCart(
            @RequestBody @Valid final CartRequest request,
            @PathVariable("memberId") final Long memberId
    ) {
        final Long createdId = cartService.create(request, memberId);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    // 장바구니 내 품목 수량 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCart(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid Integer number) {
        cartService.update(id, number);
        return ResponseEntity.noContent().build();
    }

    // 장바구니 내 품목 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable(name = "id") final Long id) {
        cartService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
