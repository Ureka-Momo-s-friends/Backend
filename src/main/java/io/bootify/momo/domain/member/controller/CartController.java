package io.bootify.momo.domain.member.controller;

import io.bootify.momo.domain.member.dto.request.CartRequest;
import io.bootify.momo.domain.member.dto.response.CartResponse;
import io.bootify.momo.domain.member.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/carts", produces = MediaType.APPLICATION_JSON_VALUE)
public class CartController {

    private final CartService cartService;

    // 특정 회원의 cart 전체 조회
    @GetMapping
        public ResponseEntity<List<CartResponse>> getCart(@RequestBody final Long memberId) {
        return ResponseEntity.ok(cartService.get(memberId));
    }

    // 장바구니에 추가
    @PostMapping
    public ResponseEntity<Long> createCart(@RequestBody @Valid final CartRequest request, @RequestBody final Long memberId) {
        final Long createdId = cartService.create(request, memberId);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    // 장바구니 내 품목 수량 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<Long> updateCart(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid Integer number) {
        cartService.update(id, number);
        return ResponseEntity.ok(id);
    }

    // 장바구니 내 품목 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable(name = "id") final Long id) {
        cartService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
