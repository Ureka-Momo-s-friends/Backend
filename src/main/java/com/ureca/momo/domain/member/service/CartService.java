package com.ureca.momo.domain.member.service;

import com.ureca.momo.domain.member.dto.request.CartRequest;
import com.ureca.momo.domain.member.dto.response.CartResponse;
import com.ureca.momo.domain.member.model.Cart;
import com.ureca.momo.domain.member.model.Member;
import com.ureca.momo.domain.member.repository.CartRepository;
import com.ureca.momo.domain.member.repository.MemberRepository;
import com.ureca.momo.domain.order.dto.request.OrderRequest;
import com.ureca.momo.domain.product.model.Product;
import com.ureca.momo.domain.product.repository.ProductRepository;
import com.ureca.momo.util.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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

    // OrderService에서 호출하는 주문 시 장바구니 수정 메소드
    public void updateByOrder(Long memberId, List<OrderRequest.OrderDetailRequest> orderDetailRequests) {
        // 주문된 상품들의 ID 리스트 추출
        List<Long> productIds = orderDetailRequests.stream()
                .map(OrderRequest.OrderDetailRequest::productId)
                .toList();

        // 해당 회원의 장바구니 중 주문된 상품들만 조회
        List<Cart> carts = cartRepository.findByMemberIdAndProductIdIn(memberId, productIds);

        // 주문 상품별 수량을 Map으로 변환하여 조회 효율성 높임
        Map<Long, Integer> productAmountMap = orderDetailRequests.stream()
                .collect(Collectors.toMap(
                        OrderRequest.OrderDetailRequest::productId,
                        OrderRequest.OrderDetailRequest::amount
                ));

        // 각 장바구니 아이템에 대해 수량 차감 또는 삭제 처리
        List<Cart> cartsToDelete = new ArrayList<>();
        for (Cart cart : carts) {
            Integer orderAmount = productAmountMap.get(cart.getProduct().getId());
            if (orderAmount == null) {
                continue; // 주문 목록에 없는 상품은 스킵
            }

            // 주문 수량만큼 장바구니 수량 차감
            int newAmount = cart.getAmount() - orderAmount;
            if (newAmount <= 0) {
                // 새로운 수량이 0 이하면 삭제 목록에 추가
                cartsToDelete.add(cart);
            } else {
                // 새로운 수량이 1 이상이면 수량 업데이트
                cart.updateAmount(-orderAmount);
            }
        }

        // 수량이 0 이하가 된 장바구니 아이템들 삭제
        if (!cartsToDelete.isEmpty()) {
            cartRepository.deleteAll(cartsToDelete);
        }
    }
}
