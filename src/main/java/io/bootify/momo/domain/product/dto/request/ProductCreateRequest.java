package io.bootify.momo.domain.product.dto.request;

import io.bootify.momo.domain.product.model.Category;
import org.springframework.web.multipart.MultipartFile;

public record ProductCreateRequest(
        String name,
        Integer price,
        Integer salePrice,
        MultipartFile thumbnail,
        MultipartFile detailImage,
        Category category
) {
}
