package io.bootify.momo.domain.member.service;

import io.bootify.momo.domain.member.dto.request.MemberAddressRequest;
import io.bootify.momo.domain.member.dto.response.MemberAddressResponse;
import io.bootify.momo.domain.member.model.Member;
import io.bootify.momo.domain.member.model.MemberAddress;
import io.bootify.momo.domain.member.repository.AddressRepository;
import io.bootify.momo.domain.member.repository.MemberRepository;
import io.bootify.momo.util.NotFoundException;
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
        return addressRepository.findByMemberId(memberId)
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
