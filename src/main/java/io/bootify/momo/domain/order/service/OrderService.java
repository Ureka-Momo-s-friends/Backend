package io.bootify.momo.domain.order.service;

import io.bootify.momo.domain.member.model.Member;
import io.bootify.momo.domain.member.repository.MemberRepository;
import io.bootify.momo.domain.member.service.CartService;
import io.bootify.momo.domain.order.dto.request.OrderRequest;
import io.bootify.momo.domain.order.dto.response.OrderDetailResponse;
import io.bootify.momo.domain.order.dto.response.OrdersResponse;
import io.bootify.momo.domain.order.model.Order;
import io.bootify.momo.domain.order.model.OrderDetail;
import io.bootify.momo.domain.order.model.OrderStatus;
import io.bootify.momo.domain.order.repository.OrderDetailRepository;
import io.bootify.momo.domain.order.repository.OrderRepository;
import io.bootify.momo.domain.product.model.Product;
import io.bootify.momo.domain.product.repository.ProductRepository;
import io.bootify.momo.util.NotFoundException;
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
    private final CartService cartService;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public List<OrdersResponse> findAll(Long memberId) {
        return orderRepository.findAllByMemberId(memberId)
                .stream()
                .map(OrdersResponse::of)
                .toList();
    }

    public Long create(final OrderRequest orderRequest, Long memberId) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(NotFoundException::new);

        // order 생성
        Product findProduct = productRepository.findById(orderRequest.orderDetailRequestList().get(0).productId()).orElseThrow(NotFoundException::new);

        Order order = new Order(
                findProduct.getThumbnail(),
                findProduct.getName() + " 등 " + orderRequest.orderDetailRequestList().size() + " 건",
                null, //pay 자리
                orderRequest.addressDetail(),
                orderRequest.address(),
                orderRequest.zonecode(),
                findMember,
                OrderStatus.상품준비중,
                LocalDateTime.now()
        );

        Order saveOrder = orderRepository.save(order);

        // orderDetail 생성
        List<OrderDetail> orderDetails = orderRequest.orderDetailRequestList().stream()
                .map(item -> {
                    Product product = productRepository.findById(item.productId())
                            .orElseThrow(NotFoundException::new);
                    return new OrderDetail(item.amount(), saveOrder, product);
                })
                .toList();

        orderDetailRepository.saveAll(orderDetails);

        int totalPrice = orderDetails.stream()
                .mapToInt(OrderDetail::calculatePrice)
                .sum();

        // TODO : pay생성



        // cart 업데이트
        cartService.updateByOrder(memberId, orderRequest.orderDetailRequestList() );

        return saveOrder.getId();
    }

    public void update(final Long id, final OrderStatus orderStaus) {
        Order findOrder = orderRepository.findById(id).orElseThrow(NotFoundException::new);
        findOrder.updateStatus(orderStaus);
    }

    public List<OrderDetailResponse> findAllDetails(Long orderId) {
        return orderDetailRepository.findAllByOrderId(orderId)
                .stream()
                .map(OrderDetailResponse::of)
                .toList();
    }
}
