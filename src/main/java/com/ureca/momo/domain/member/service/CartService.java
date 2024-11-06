package com.ureca.momo.domain.member.service;

import com.ureca.momo.domain.member.dto.request.CartRequest;
import com.ureca.momo.domain.member.dto.response.CartResponse;
import com.ureca.momo.domain.member.model.Cart;
import com.ureca.momo.domain.member.model.Member;
import com.ureca.momo.domain.member.repository.CartRepository;
import com.ureca.momo.domain.member.repository.MemberRepository;
import com.ureca.momo.domain.product.model.Product;
import com.ureca.momo.domain.product.repository.ProductRepository;
import com.ureca.momo.util.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public List<CartResponse> get(final Long memberId) {
       return cartRepository.findAllByMemberId(memberId)
               .stream()
               .map(CartResponse::of)
               .toList();
    }

    public Long create(CartRequest request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("회원을 찾을 수 없습니다."));
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new NotFoundException("상품을 찾을 수 없습니다."));

        Cart cart = cartRepository.findByMemberIdAndProductId(memberId, request.productId())
                .map(existingCart -> {
                    existingCart.updateAmount(existingCart.getAmount() + request.amount());
                    return existingCart;
                })
                .orElse(new Cart(request.amount(), member, product));
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
