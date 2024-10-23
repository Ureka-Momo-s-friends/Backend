package io.bootify.momo.service;

import io.bootify.momo.domain.Cart;
import io.bootify.momo.domain.Category;
import io.bootify.momo.domain.OrderDetail;
import io.bootify.momo.domain.Product;
import io.bootify.momo.model.ProductDTO;
import io.bootify.momo.repos.CartRepository;
import io.bootify.momo.repos.CategoryRepository;
import io.bootify.momo.repos.OrderDetailRepository;
import io.bootify.momo.repos.ProductRepository;
import io.bootify.momo.util.NotFoundException;
import io.bootify.momo.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CartRepository cartRepository;
    private final OrderDetailRepository orderDetailRepository;

    public ProductService(final ProductRepository productRepository,
            final CategoryRepository categoryRepository, final CartRepository cartRepository,
            final OrderDetailRepository orderDetailRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.cartRepository = cartRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public List<ProductDTO> findAll() {
        final List<Product> products = productRepository.findAll(Sort.by("id"));
        return products.stream()
                .map(product -> mapToDTO(product, new ProductDTO()))
                .toList();
    }

    public ProductDTO get(final Long id) {
        return productRepository.findById(id)
                .map(product -> mapToDTO(product, new ProductDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ProductDTO productDTO) {
        final Product product = new Product();
        mapToEntity(productDTO, product);
        return productRepository.save(product).getId();
    }

    public void update(final Long id, final ProductDTO productDTO) {
        final Product product = productRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(productDTO, product);
        productRepository.save(product);
    }

    public void delete(final Long id) {
        productRepository.deleteById(id);
    }

    private ProductDTO mapToDTO(final Product product, final ProductDTO productDTO) {
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setSalePrice(product.getSalePrice());
        productDTO.setThumbnail(product.getThumbnail());
        productDTO.setDetail(product.getDetail());
        productDTO.setCategory(product.getCategory() == null ? null : product.getCategory().getId());
        return productDTO;
    }

    private Product mapToEntity(final ProductDTO productDTO, final Product product) {
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setSalePrice(productDTO.getSalePrice());
        product.setThumbnail(productDTO.getThumbnail());
        product.setDetail(productDTO.getDetail());
        final Category category = productDTO.getCategory() == null ? null : categoryRepository.findById(productDTO.getCategory())
                .orElseThrow(() -> new NotFoundException("category not found"));
        product.setCategory(category);
        return product;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Product product = productRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Cart productCart = cartRepository.findFirstByProduct(product);
        if (productCart != null) {
            referencedWarning.setKey("product.cart.product.referenced");
            referencedWarning.addParam(productCart.getId());
            return referencedWarning;
        }
        final OrderDetail prodcutOrderDetail = orderDetailRepository.findFirstByProdcut(product);
        if (prodcutOrderDetail != null) {
            referencedWarning.setKey("product.orderDetail.prodcut.referenced");
            referencedWarning.addParam(prodcutOrderDetail.getId());
            return referencedWarning;
        }
        return null;
    }

}
