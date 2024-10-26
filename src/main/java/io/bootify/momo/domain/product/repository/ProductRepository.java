package io.bootify.momo.domain.product.repository;

import io.bootify.momo.domain.product.model.Category;
import io.bootify.momo.domain.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findFirstByCategory(Category category);

}
