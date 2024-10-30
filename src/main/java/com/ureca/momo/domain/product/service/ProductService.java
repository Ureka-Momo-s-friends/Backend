package com.ureca.momo.domain.product.service;

import com.ureca.momo.domain.product.dto.request.SearchConditionRequest;
import com.ureca.momo.domain.product.dto.response.ProductDetailResponse;
import com.ureca.momo.domain.product.dto.response.ProductsResponse;
import com.ureca.momo.domain.product.repository.ProductRepository;
import com.ureca.momo.util.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductsResponse> findAll(SearchConditionRequest searchConditionRequest) {
        if (searchConditionRequest.category() == null) {
            return productRepository.findAll()
                    .stream()
                    .map(ProductsResponse::of)
                    .toList();
        }

        return productRepository.findAllByCategory(searchConditionRequest.category())
                .stream()
                .map(ProductsResponse::of)
                .toList();
    }

    public ProductDetailResponse get(final Long id) {
        return productRepository.findById(id)
                .map(ProductDetailResponse::of)
                .orElseThrow(NotFoundException::new);
    }

}
