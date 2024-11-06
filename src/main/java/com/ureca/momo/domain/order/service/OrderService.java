package com.ureca.momo.domain.order.service;

import com.ureca.momo.domain.member.model.Member;
import com.ureca.momo.domain.member.repository.CartRepository;
import com.ureca.momo.domain.member.repository.MemberRepository;
import com.ureca.momo.domain.order.dto.request.OrderRequest;
import com.ureca.momo.domain.order.dto.response.OrderDetailResponse;
import com.ureca.momo.domain.order.dto.response.OrdersResponse;
import com.ureca.momo.domain.order.model.OrderDetail;
import com.ureca.momo.domain.order.model.OrderStatus;
import com.ureca.momo.domain.order.model.Orders;
import com.ureca.momo.domain.order.repository.OrderDetailRepository;
import com.ureca.momo.domain.order.repository.OrderRepository;
import com.ureca.momo.domain.pay.model.Pay;
import com.ureca.momo.domain.pay.service.PayService;
import com.ureca.momo.domain.product.model.Product;
import com.ureca.momo.domain.product.repository.ProductRepository;
import com.ureca.momo.util.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final PayService payService; // PayService 추가
    private final CartRepository cartRepository;

    public List<OrdersResponse> findAll(Long memberId) {
        return orderRepository.findAllByMemberId(memberId)
                .stream()
                .map(OrdersResponse::of)
                .toList();
    }

    public Long create(final OrderRequest orderRequest, Long memberId) {
        if (orderRequest.orderDetailRequestList() == null || orderRequest.orderDetailRequestList().isEmpty()) {
            throw new IllegalArgumentException("Order detail request list cannot be null or empty");
        }
        Member findMember = memberRepository.findById(memberId).orElseThrow(NotFoundException::new);

        // order 생성
        Product findProduct = productRepository.findById(orderRequest.orderDetailRequestList().get(0).productId())
                .orElseThrow(NotFoundException::new);

        Orders orders = new Orders(
                findProduct.getThumbnail(),
                findProduct.getName() + " 등 " + orderRequest.orderDetailRequestList().size() + "건",
                null, // Pay 자리
                orderRequest.addressDetail(),
                orderRequest.address(),
                orderRequest.zonecode(),
                findMember,
                OrderStatus.결제완료,
                LocalDateTime.now()
        );

        Orders savedOrder = orderRepository.save(orders);

        // orderDetail 생성
        List<OrderDetail> orderDetails = orderRequest.orderDetailRequestList().stream()
                .map(item -> {
                    Product product = productRepository.findById(item.productId())
                            .orElseThrow(NotFoundException::new);
                    return new OrderDetail(item.amount(), savedOrder, product);
                })
                .toList();

        orderDetailRepository.saveAll(orderDetails);

        int totalPrice = orderDetails.stream()
                .mapToInt(OrderDetail::calculatePrice)
                .sum();

        // Pay 엔티티 생성 및 저장
        Pay pay = new Pay();
        pay.setAmount(totalPrice);
        pay.setStatus("SUCCESS");
        pay.setPaymentKey(orderRequest.paymentKey());
        pay.setOrders(savedOrder);
        pay.setPaymentDate(LocalDateTime.now());

        // 결제 키는 외부 요청 후에 받아와야 하므로, 여기서 직접 설정할 필요가 없습니다.
        // orderRequest에 paymentKey 추가 필요 시 DTO에 추가 후 설정

        payService.savePayment(pay);

        // 주문에 결제 정보 추가 후 저장
        savedOrder.setPay(pay);
        orderRepository.save(savedOrder);

        // cart 업데이트
        cartRepository.deleteAll();

        return savedOrder.getId();
    }

    public void update(final Long id, final OrderStatus orderStatus) {
        Orders findOrders = orderRepository.findById(id).orElseThrow(NotFoundException::new);
        findOrders.updateStatus(orderStatus);
    }

    public List<OrderDetailResponse> findAllDetails(Long orderId) {
        return orderDetailRepository.findAllByOrdersId(orderId)
                .stream()
                .map(OrderDetailResponse::of)
                .toList();
    }


}
