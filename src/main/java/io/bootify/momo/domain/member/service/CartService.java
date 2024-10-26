package io.bootify.momo.domain.member.service;

import io.bootify.momo.domain.member.dto.request.CartRequest;
import io.bootify.momo.domain.member.dto.response.CartResponse;
import io.bootify.momo.domain.member.model.Cart;
import io.bootify.momo.domain.member.model.Member;
import io.bootify.momo.domain.product.model.Product;
import io.bootify.momo.domain.member.repository.CartRepository;
import io.bootify.momo.domain.member.repository.MemberRepository;
import io.bootify.momo.domain.product.repository.ProductRepository;
import io.bootify.momo.util.NotFoundException;
import java.util.List;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public List<CartResponse> get(final Long memberId) {
       return cartRepository.findByMemberId(memberId)
               .stream()
               .map(CartResponse::of)
               .toList();
    }

    public Long create(CartRequest request, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundException::new);
        Product product = productRepository.findById(request.productId()).orElseThrow(NotFoundException::new);
        Cart cart = new Cart(request.amount(), member, product);
        return cartRepository.save(cart).getId();
    }

    public void update(final Long id, final Integer diff) {
        Cart findCart = cartRepository.findById(id).orElseThrow(NotFoundException::new);
        findCart.updateAmount(diff);
    }

    public void delete(final Long id) {
        cartRepository.deleteById(id);
    }

}
