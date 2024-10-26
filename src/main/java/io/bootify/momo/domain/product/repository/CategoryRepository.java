package io.bootify.momo.domain.product.repository;

import io.bootify.momo.domain.product.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Long> {
}
