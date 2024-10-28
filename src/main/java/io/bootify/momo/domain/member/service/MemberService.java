package io.bootify.momo.domain.member.service;

import io.bootify.momo.domain.member.model.Member;
import io.bootify.momo.domain.cat.model.Pet;
import io.bootify.momo.domain.cat.model.StrayCat;
import io.bootify.momo.model.MemberDTO;
import io.bootify.momo.domain.member.repository.MemberRepository;
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
    private final StrayCatRepository strayCatRepository;

    public MemberService(final MemberRepository memberRepository, final PetRepository petRepository,
                         final StrayCatRepository strayCatRepository) {
        this.memberRepository = memberRepository;
        this.petRepository = petRepository;
        this.strayCatRepository = strayCatRepository;
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

    public void update(final Long id, final MemberDTO memberDTO) {
        final Member member = memberRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(memberDTO, member);

        if (memberDTO.getProfileImgUrl() != null) {
            member.setProfileImgUrl(memberDTO.getProfileImgUrl());
        }

        memberRepository.save(member);
    }


    public void delete(final Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Member not found"));

        if (!member.getMemberPets().isEmpty()) {
            throw new IllegalStateException("Member has associated pets");
        }

        memberRepository.deleteById(id);
    }

    private MemberDTO mapToDTO(final Member member, final MemberDTO memberDTO) {
        memberDTO.setId(member.getId());
        memberDTO.setUsername(member.getUsername());
        memberDTO.setContact(member.getContact());
        memberDTO.setGoogleId(member.getGoogleId());
        memberDTO.setProfileImgUrl(member.getProfileImgUrl());
        return memberDTO;
    }

    private Member mapToEntity(final MemberDTO memberDTO, final Member member) {
        member.setUsername(memberDTO.getUsername());
        member.setContact(memberDTO.getContact());
        member.setGoogleId(memberDTO.getGoogleId());
        member.setProfileImgUrl(memberDTO.getProfileImgUrl());
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

        final StrayCat memberStrayCat = strayCatRepository.findFirstByMember(member);
        if (memberStrayCat != null) {
            referencedWarning.setKey("member.strayCat.member.referenced");
            referencedWarning.addParam(memberStrayCat.getId());
            return referencedWarning;
        }

        return null;
    }

}
