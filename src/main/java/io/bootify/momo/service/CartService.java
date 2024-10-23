package io.bootify.momo.service;

import io.bootify.momo.domain.Cart;
import io.bootify.momo.domain.Member;
import io.bootify.momo.domain.Product;
import io.bootify.momo.model.CartDTO;
import io.bootify.momo.repos.CartRepository;
import io.bootify.momo.repos.MemberRepository;
import io.bootify.momo.repos.ProductRepository;
import io.bootify.momo.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CartService {

    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public CartService(final CartRepository cartRepository, final MemberRepository memberRepository,
            final ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    public List<CartDTO> findAll() {
        final List<Cart> carts = cartRepository.findAll(Sort.by("id"));
        return carts.stream()
                .map(cart -> mapToDTO(cart, new CartDTO()))
                .toList();
    }

    public CartDTO get(final Long id) {
        return cartRepository.findById(id)
                .map(cart -> mapToDTO(cart, new CartDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CartDTO cartDTO) {
        final Cart cart = new Cart();
        mapToEntity(cartDTO, cart);
        return cartRepository.save(cart).getId();
    }

    public void update(final Long id, final CartDTO cartDTO) {
        final Cart cart = cartRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(cartDTO, cart);
        cartRepository.save(cart);
    }

    public void delete(final Long id) {
        cartRepository.deleteById(id);
    }

    private CartDTO mapToDTO(final Cart cart, final CartDTO cartDTO) {
        cartDTO.setId(cart.getId());
        cartDTO.setAmount(cart.getAmount());
        cartDTO.setMember(cart.getMember() == null ? null : cart.getMember().getId());
        cartDTO.setProduct(cart.getProduct() == null ? null : cart.getProduct().getId());
        return cartDTO;
    }

    private Cart mapToEntity(final CartDTO cartDTO, final Cart cart) {
        cart.setAmount(cartDTO.getAmount());
        final Member member = cartDTO.getMember() == null ? null : memberRepository.findById(cartDTO.getMember())
                .orElseThrow(() -> new NotFoundException("member not found"));
        cart.setMember(member);
        final Product product = cartDTO.getProduct() == null ? null : productRepository.findById(cartDTO.getProduct())
                .orElseThrow(() -> new NotFoundException("product not found"));
        cart.setProduct(product);
        return cart;
    }

}
