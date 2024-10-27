package io.bootify.momo.domain.product.controller;

import io.bootify.momo.domain.product.dto.request.SearchConditionRequest;
import io.bootify.momo.domain.product.dto.response.ProductDetailResponse;
import io.bootify.momo.domain.product.dto.response.ProductsResponse;
import io.bootify.momo.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/products", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductsResponse>> getAllProducts(SearchConditionRequest searchConditionRequest) {
        return ResponseEntity.ok(productService.findAll(searchConditionRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailResponse> getProduct(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(productService.get(id));
    }

}
