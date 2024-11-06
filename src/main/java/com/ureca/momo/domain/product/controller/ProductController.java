package com.ureca.momo.domain.product.controller;

import com.ureca.momo.domain.product.dto.request.SearchConditionRequest;
import com.ureca.momo.domain.product.dto.response.ProductDetailResponse;
import com.ureca.momo.domain.product.dto.response.ProductsResponse;
import com.ureca.momo.domain.product.model.Product;
import com.ureca.momo.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
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

    @GetMapping("/search")
    public ResponseEntity<List<ProductsResponse>> searchProducts(@RequestParam("keyword") String keyword) {
        return ResponseEntity.ok(productService.searchByName(keyword));
    }

    @PostMapping("/batch")
    public String saveProducts(@RequestBody List<Product> products) {
        productService.saveProducts(products);
        return "Products saved successfully!";
    }
}
