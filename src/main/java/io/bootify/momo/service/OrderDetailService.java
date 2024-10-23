package io.bootify.momo.service;

import io.bootify.momo.domain.Order;
import io.bootify.momo.domain.OrderDetail;
import io.bootify.momo.domain.Product;
import io.bootify.momo.model.OrderDetailDTO;
import io.bootify.momo.repos.OrderDetailRepository;
import io.bootify.momo.repos.OrderRepository;
import io.bootify.momo.repos.ProductRepository;
import io.bootify.momo.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderDetailService(final OrderDetailRepository orderDetailRepository,
            final OrderRepository orderRepository, final ProductRepository productRepository) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public List<OrderDetailDTO> findAll() {
        final List<OrderDetail> orderDetails = orderDetailRepository.findAll(Sort.by("id"));
        return orderDetails.stream()
                .map(orderDetail -> mapToDTO(orderDetail, new OrderDetailDTO()))
                .toList();
    }

    public OrderDetailDTO get(final Long id) {
        return orderDetailRepository.findById(id)
                .map(orderDetail -> mapToDTO(orderDetail, new OrderDetailDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final OrderDetailDTO orderDetailDTO) {
        final OrderDetail orderDetail = new OrderDetail();
        mapToEntity(orderDetailDTO, orderDetail);
        return orderDetailRepository.save(orderDetail).getId();
    }

    public void update(final Long id, final OrderDetailDTO orderDetailDTO) {
        final OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(orderDetailDTO, orderDetail);
        orderDetailRepository.save(orderDetail);
    }

    public void delete(final Long id) {
        orderDetailRepository.deleteById(id);
    }

    private OrderDetailDTO mapToDTO(final OrderDetail orderDetail,
            final OrderDetailDTO orderDetailDTO) {
        orderDetailDTO.setId(orderDetail.getId());
        orderDetailDTO.setAmount(orderDetail.getAmount());
        orderDetailDTO.setOrder(orderDetail.getOrder() == null ? null : orderDetail.getOrder().getId());
        orderDetailDTO.setProdcut(orderDetail.getProdcut() == null ? null : orderDetail.getProdcut().getId());
        return orderDetailDTO;
    }

    private OrderDetail mapToEntity(final OrderDetailDTO orderDetailDTO,
            final OrderDetail orderDetail) {
        orderDetail.setAmount(orderDetailDTO.getAmount());
        final Order order = orderDetailDTO.getOrder() == null ? null : orderRepository.findById(orderDetailDTO.getOrder())
                .orElseThrow(() -> new NotFoundException("order not found"));
        orderDetail.setOrder(order);
        final Product prodcut = orderDetailDTO.getProdcut() == null ? null : productRepository.findById(orderDetailDTO.getProdcut())
                .orElseThrow(() -> new NotFoundException("prodcut not found"));
        orderDetail.setProdcut(prodcut);
        return orderDetail;
    }

}
