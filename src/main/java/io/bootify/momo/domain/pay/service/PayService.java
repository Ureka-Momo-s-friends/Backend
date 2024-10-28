package io.bootify.momo.domain.pay.service;

import io.bootify.momo.domain.order.model.Order;
import io.bootify.momo.domain.pay.model.Pay;
import io.bootify.momo.model.PayDTO;
import io.bootify.momo.domain.order.repository.OrderRepository;
import io.bootify.momo.domain.pay.repository.PayRepository;
import io.bootify.momo.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PayService {

    private final PayRepository payRepository;
    private final OrderRepository orderRepository;

    public PayService(final PayRepository payRepository, final OrderRepository orderRepository) {
        this.payRepository = payRepository;
        this.orderRepository = orderRepository;
    }

    public List<PayDTO> findAll() {
        final List<Pay> pays = payRepository.findAll(Sort.by("id"));
        return pays.stream()
                .map(pay -> mapToDTO(pay, new PayDTO()))
                .toList();
    }

    public PayDTO get(final Long id) {
        return payRepository.findById(id)
                .map(pay -> mapToDTO(pay, new PayDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PayDTO payDTO) {
        final Pay pay = new Pay();
        mapToEntity(payDTO, pay);
        return payRepository.save(pay).getId();
    }

    public void update(final Long id, final PayDTO payDTO) {
        final Pay pay = payRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(payDTO, pay);
        payRepository.save(pay);
    }

    public void delete(final Long id) {
        payRepository.deleteById(id);
    }

    private PayDTO mapToDTO(final Pay pay, final PayDTO payDTO) {
        payDTO.setId(pay.getId());
        payDTO.setAmount(pay.getAmount());
        payDTO.setStatus(pay.getStatus());
        payDTO.setPaymentKey(pay.getPaymentKey());
        payDTO.setOrder(pay.getOrder() == null ? null : pay.getOrder().getId());
        return payDTO;
    }

    private Pay mapToEntity(final PayDTO payDTO, final Pay pay) {
        pay.setAmount(payDTO.getAmount());
        pay.setStatus(payDTO.getStatus());
        pay.setPaymentKey(payDTO.getPaymentKey());
        final Order order = payDTO.getOrder() == null ? null : orderRepository.findById(payDTO.getOrder())
                .orElseThrow(() -> new NotFoundException("order not found"));
        pay.setOrder(order);
        return pay;
    }

}
