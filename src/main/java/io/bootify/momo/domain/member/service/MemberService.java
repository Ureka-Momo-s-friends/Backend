package io.bootify.momo.domain.member.service;

import io.bootify.momo.domain.member.model.Address;
import io.bootify.momo.domain.member.model.Cart;
import io.bootify.momo.domain.member.model.Member;
import io.bootify.momo.domain.order.model.Order;
import io.bootify.momo.domain.cat.model.Pet;
import io.bootify.momo.domain.cat.model.StrayCat;
import io.bootify.momo.model.MemberDTO;
import io.bootify.momo.domain.member.repository.AddressRepository;
import io.bootify.momo.domain.member.repository.CartRepository;
import io.bootify.momo.domain.member.repository.MemberRepository;
import io.bootify.momo.domain.order.repository.OrderRepository;
import io.bootify.momo.domain.cat.repository.PetRepository;
import io.bootify.momo.domain.cat.repository.StrayCatRepository;
import io.bootify.momo.util.NotFoundException;
import io.bootify.momo.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PetRepository petRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final StrayCatRepository strayCatRepository;
    private final AddressRepository addressRepository;

    public MemberService(final MemberRepository memberRepository, final PetRepository petRepository,
                         final CartRepository cartRepository, final OrderRepository orderRepository,
                         final StrayCatRepository strayCatRepository,
                         final AddressRepository addressRepository) {
        this.memberRepository = memberRepository;
        this.petRepository = petRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.strayCatRepository = strayCatRepository;
        this.addressRepository = addressRepository;
    }

    public List<MemberDTO> findAll() {
        final List<Member> members = memberRepository.findAll(Sort.by("id"));
        return members.stream()
                .map(member -> mapToDTO(member, new MemberDTO()))
                .toList();
    }

    public MemberDTO get(final Long id) {
        return memberRepository.findById(id)
                .map(member -> mapToDTO(member, new MemberDTO()))
                .orElseThrow(NotFoundException::new);
    }


    public MemberDTO findByGoogleId(String googleId) {
        return memberRepository.findByGoogleId(googleId)
                .map(member -> mapToDTO(member, new MemberDTO()))
                .orElse(null);
    }


    public Long create(final MemberDTO memberDTO) {
        final Member member = new Member();
        mapToEntity(memberDTO, member);
        return memberRepository.save(member).getId();
    }

    // 업데이트 메서드 추가 또는 수정
    // MemberService.java
    public void update(final Long id, final MemberDTO memberDTO) {
        final Member member = memberRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(memberDTO, member);

        // 프로필 이미지 경로가 존재하면 업데이트
        if (memberDTO.getProfileImgUrl() != null) {
            member.setProfileImgUrl(memberDTO.getProfileImgUrl());
        }

        memberRepository.save(member);
    }


    public void delete(final Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Member not found"));

        // Check if the member has associated pets (or any other related data)
        if (!member.getMemberPets().isEmpty()) {
            // Decide if you want to throw an exception or just disassociate pets
            throw new IllegalStateException("Member has associated pets");
        }

        // If no associated data, delete member
        memberRepository.deleteById(id);
    }

    private MemberDTO mapToDTO(final Member member, final MemberDTO memberDTO) {
        memberDTO.setId(member.getId());
        memberDTO.setUsername(member.getUsername());
        memberDTO.setContact(member.getContact());
        memberDTO.setGoogleId(member.getGoogleId());
        memberDTO.setProfileImgUrl(member.getProfileImgUrl()); // 프로필 이미지 URL 추가
        return memberDTO;
    }

    private Member mapToEntity(final MemberDTO memberDTO, final Member member) {
        member.setUsername(memberDTO.getUsername());
        member.setContact(memberDTO.getContact());
        member.setGoogleId(memberDTO.getGoogleId()); // 필드 추가
        member.setProfileImgUrl(memberDTO.getProfileImgUrl()); // 프로필 이미지 URL 추가
        return member;
    }



    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Member member = memberRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Pet memberPet = petRepository.findFirstByMember(member);
        if (memberPet != null) {
            referencedWarning.setKey("member.pet.member.referenced");
            referencedWarning.addParam(memberPet.getId());
            return referencedWarning;
        }
        final Cart memberCart = cartRepository.findFirstByMember(member);
        if (memberCart != null) {
            referencedWarning.setKey("member.cart.member.referenced");
            referencedWarning.addParam(memberCart.getId());
            return referencedWarning;
        }
        final Order memberOrder = orderRepository.findFirstByMember(member);
        if (memberOrder != null) {
            referencedWarning.setKey("member.order.member.referenced");
            referencedWarning.addParam(memberOrder.getId());
            return referencedWarning;
        }
        final StrayCat memberStrayCat = strayCatRepository.findFirstByMember(member);
        if (memberStrayCat != null) {
            referencedWarning.setKey("member.strayCat.member.referenced");
            referencedWarning.addParam(memberStrayCat.getId());
            return referencedWarning;
        }
        final Address memberAddress = addressRepository.findFirstByMember(member);
        if (memberAddress != null) {
            referencedWarning.setKey("member.address.member.referenced");
            referencedWarning.addParam(memberAddress.getId());
            return referencedWarning;
        }
        return null;
    }

}
