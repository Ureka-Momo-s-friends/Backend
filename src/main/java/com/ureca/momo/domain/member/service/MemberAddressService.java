package com.ureca.momo.domain.member.service;

import com.ureca.momo.domain.member.dto.request.MemberAddressRequest;
import com.ureca.momo.domain.member.dto.response.MemberAddressResponse;
import com.ureca.momo.domain.member.model.Member;
import com.ureca.momo.domain.member.model.MemberAddress;
import com.ureca.momo.domain.member.repository.AddressRepository;
import com.ureca.momo.domain.member.repository.MemberRepository;
import com.ureca.momo.util.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class MemberAddressService {

    private final AddressRepository addressRepository;
    private final MemberRepository memberRepository;

    public List<MemberAddressResponse> getAddress(final Long memberId) {
        return addressRepository.findAllByMemberId(memberId)
                .stream()
                .map(MemberAddressResponse::of)
                .toList();
    }

    public Long create(final MemberAddressRequest request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundException::new);
        MemberAddress address = new MemberAddress(request, member);
        return addressRepository.save(address).getId();
    }

    public void update(final Long id, final MemberAddressRequest reqeust) {
        MemberAddress memberAddress = addressRepository.findById(id).orElseThrow(NotFoundException::new);
        memberAddress.update(reqeust);
    }

    public void delete(final Long id) {
        addressRepository.deleteById(id);
    }

}
