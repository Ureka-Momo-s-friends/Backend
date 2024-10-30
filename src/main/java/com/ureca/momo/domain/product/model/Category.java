package com.ureca.momo.domain.product.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Category {
    사료("사료"),
    캔_간식("캔_간식"),
    모래_탈취제("모래_탈취제"),
    화장실_매트("화장실_매트");

    private final String value;

    Category(String value) {
        this.value = value;
    }

    @JsonValue  // JSON 직렬화 시 사용할 값
    public String getValue() {
        return value;
    }

    @JsonCreator  // JSON 역직렬화 시 사용할 메서드
    public static Category from(String value) {
        for (Category category : Category.values()) {
            if (category.getValue().equals(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid category value: " + value);
    }
}
