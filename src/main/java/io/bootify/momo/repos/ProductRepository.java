package io.bootify.momo.repos;

import io.bootify.momo.domain.Category;
import io.bootify.momo.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findFirstByCategory(Category category);

}
