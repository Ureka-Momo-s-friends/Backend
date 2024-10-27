package io.bootify.momo.domain.product.repository;

import io.bootify.momo.domain.product.model.Category;
import io.bootify.momo.domain.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByCategory(Category category);

}
