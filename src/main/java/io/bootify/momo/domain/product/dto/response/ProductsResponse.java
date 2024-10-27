package io.bootify.momo.domain.product.dto.response;

import io.bootify.momo.domain.product.model.Product;

public record ProductsResponse(
        Long id,
        String thumbnail,
        String name,
        Integer price,
        Integer salePrice
) {
    public static ProductsResponse of(Product product) {
        return new ProductsResponse(
                product.getId(),
                product.getThumbnail(),
                product.getName(),
                product.getPrice(),
                product.getSalePrice()
        );
    }
}
