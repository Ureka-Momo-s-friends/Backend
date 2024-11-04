package com.ureca.momo.domain.product.dto.request;


import com.ureca.momo.domain.product.model.Category;

public record SearchConditionRequest(
        Category category
) {
}
