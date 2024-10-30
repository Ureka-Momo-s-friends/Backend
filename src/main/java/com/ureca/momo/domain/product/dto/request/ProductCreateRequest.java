package com.ureca.momo.domain.product.dto.request;

import com.ureca.momo.domain.product.model.Category;
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
