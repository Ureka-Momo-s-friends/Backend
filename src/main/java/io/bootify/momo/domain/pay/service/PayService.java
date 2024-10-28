package io.bootify.momo.domain.pay.service;

import io.bootify.momo.domain.pay.dto.PayDTO;
import io.bootify.momo.domain.pay.model.Pay;
import io.bootify.momo.domain.pay.repository.PayRepository;

import io.bootify.momo.util.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PayService {

    private final PayRepository payRepository;

    public List<PayDTO> findAll() {
        return payRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public PayDTO get(final Long id) {
        return payRepository.findById(id)
                .map(this::mapToDTO)
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

    private PayDTO mapToDTO(final Pay pay) {
        PayDTO payDTO = new PayDTO();
        payDTO.setId(pay.getId());
        payDTO.setAmount(pay.getAmount());
        payDTO.setStatus(pay.getStatus());
        payDTO.setPaymentKey(pay.getPaymentKey());
        // Order 관련된 설정 제거
        return payDTO;
    }

    private void mapToEntity(final PayDTO payDTO, final Pay pay) {
        pay.setAmount(payDTO.getAmount());
        pay.setStatus(payDTO.getStatus());
        pay.setPaymentKey(payDTO.getPaymentKey());
        // Order 관련된 설정 제거
    }
}
