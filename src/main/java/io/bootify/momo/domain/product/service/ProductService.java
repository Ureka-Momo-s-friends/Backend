package io.bootify.momo.domain.product.service;

import io.bootify.momo.domain.product.dto.request.SearchConditionRequest;
import io.bootify.momo.domain.product.dto.response.ProductDetailResponse;
import io.bootify.momo.domain.product.dto.response.ProductsResponse;
import io.bootify.momo.domain.product.repository.ProductRepository;
import io.bootify.momo.util.NotFoundException;
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
