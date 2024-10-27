package io.bootify.momo.domain.product.dto.response;

import io.bootify.momo.domain.product.model.Product;

public record ProductDetailResponse(
        Long id,
        String name,
        Integer price,
        Integer salePrice,
        String thumbnail,
        String detailImage
) {
    public static ProductDetailResponse of(Product product) {
        return new ProductDetailResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getSalePrice(),
                product.getThumbnail(),
                product.getDetailImage()
        );
    }
}
