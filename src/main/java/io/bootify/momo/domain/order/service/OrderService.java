package io.bootify.momo.domain.order.service;

import io.bootify.momo.domain.order.model.Order;
import io.bootify.momo.domain.order.model.OrderDetail;
import io.bootify.momo.domain.pay.model.Pay;
import io.bootify.momo.domain.member.repository.AddressRepository;
import io.bootify.momo.domain.member.repository.MemberRepository;
import io.bootify.momo.domain.order.repository.OrderDetailRepository;
import io.bootify.momo.domain.order.repository.OrderRepository;
import io.bootify.momo.domain.pay.repository.PayRepository;
import io.bootify.momo.util.NotFoundException;
import io.bootify.momo.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final AddressRepository addressRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final PayRepository payRepository;

    public OrderService(final OrderRepository orderRepository,
            final MemberRepository memberRepository, final AddressRepository addressRepository,
            final OrderDetailRepository orderDetailRepository, final PayRepository payRepository) {
        this.orderRepository = orderRepository;
        this.memberRepository = memberRepository;
        this.addressRepository = addressRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.payRepository = payRepository;
    }

    public List<OrderDTO> findAll() {
        final List<Order> orders = orderRepository.findAll(Sort.by("id"));
        return orders.stream()
                .map(order -> mapToDTO(order, new OrderDTO()))
                .toList();
    }

    public OrderDTO get(final Long id) {
        return orderRepository.findById(id)
                .map(order -> mapToDTO(order, new OrderDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final OrderDTO orderDTO) {
        final Order order = new Order();
        mapToEntity(orderDTO, order);
        return orderRepository.save(order).getId();
    }

    public void update(final Long id, final OrderDTO orderDTO) {
        final Order order = orderRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(orderDTO, order);
        orderRepository.save(order);
    }

    public void delete(final Long id) {
        orderRepository.deleteById(id);
    }

    private OrderDTO mapToDTO(final Order order, final OrderDTO orderDTO) {
        orderDTO.setId(order.getId());
        orderDTO.setOrderTime(order.getOrderTime());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setMember(order.getMember() == null ? null : order.getMember().getId());
        orderDTO.setAddress(order.getAddress() == null ? null : order.getAddress().getId());
        return orderDTO;
    }

    private Order mapToEntity(final OrderDTO orderDTO, final Order order) {
        order.setOrderTime(orderDTO.getOrderTime());
        order.setStatus(orderDTO.getStatus());
        final Member member = orderDTO.getMember() == null ? null : memberRepository.findById(orderDTO.getMember())
                .orElseThrow(() -> new NotFoundException("member not found"));
        order.setMember(member);
        final Address address = orderDTO.getAddress() == null ? null : addressRepository.findById(orderDTO.getAddress())
                .orElseThrow(() -> new NotFoundException("address not found"));
        order.setAddress(address);
        return order;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Order order = orderRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final OrderDetail orderOrderDetail = orderDetailRepository.findFirstByOrder(order);
        if (orderOrderDetail != null) {
            referencedWarning.setKey("order.orderDetail.order.referenced");
            referencedWarning.addParam(orderOrderDetail.getId());
            return referencedWarning;
        }
        final Pay orderPay = payRepository.findFirstByOrder(order);
        if (orderPay != null) {
            referencedWarning.setKey("order.pay.order.referenced");
            referencedWarning.addParam(orderPay.getId());
            return referencedWarning;
        }
        return null;
    }

    public List<OrderDetailResponse> findAllDetails(Long orderId) {
        return orderDetailRepository.findAllByOrderId(orderId)
                .stream()
                .map(OrderDetailResponse::of)
                .toList();
    }
}
