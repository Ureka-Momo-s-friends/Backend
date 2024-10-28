package io.bootify.momo.domain.product.dto.request;

import io.bootify.momo.domain.product.model.Category;

public record SearchConditionRequest(
        Category category
) {
}
