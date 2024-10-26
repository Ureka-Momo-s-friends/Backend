package io.bootify.momo.domain.member.dto.response;

import io.bootify.momo.domain.member.model.Cart;
import io.bootify.momo.domain.product.model.Product;

public record CartResponse(
        Integer amount,
        Product product
) {
    public static CartResponse of(Cart cart) {
        return new CartResponse(cart.getAmount(), cart.getProduct());
    }
}
