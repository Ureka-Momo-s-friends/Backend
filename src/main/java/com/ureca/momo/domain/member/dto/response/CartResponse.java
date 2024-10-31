package com.ureca.momo.domain.member.dto.response;


import com.ureca.momo.domain.member.model.Cart;
import com.ureca.momo.domain.product.model.Product;

public record CartResponse(
        Long id,
        Integer amount,
        Product product
) {
    public static CartResponse of(Cart cart) {
        return new CartResponse(
                cart.getId(),
                cart.getAmount(),
                cart.getProduct());
    }
}
