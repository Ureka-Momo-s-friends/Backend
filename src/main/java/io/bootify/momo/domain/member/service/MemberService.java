package io.bootify.momo.domain.member.service;

import io.bootify.momo.domain.member.dto.request.MemberRequest;
import io.bootify.momo.domain.member.dto.response.MemberResponse;
import io.bootify.momo.domain.member.model.Member;
import io.bootify.momo.domain.member.repository.MemberRepository;
import io.bootify.momo.util.NotFoundException;
import io.bootify.momo.util.ReferencedWarning;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<MemberResponse> findAll() {
        // 모든 멤버를 찾아서 응답 객체로 변환
        return memberRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public MemberResponse get(final Long id) {
        return memberRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final MemberRequest memberRequest) {
        final Member member = mapToEntity(memberRequest, new Member());
        return memberRepository.save(member).getId();
    }

    public void update(final Long id, final MemberRequest memberRequest, MultipartFile profileImg) throws IOException {
        final Member member = memberRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        // 필드를 직접 업데이트
        member.setUsername(memberRequest.getUsername());
        member.setContact(memberRequest.getContact());
        member.setGoogleId(memberRequest.getGoogleId());

        // 새 이미지 파일이 있으면 업데이트, 없으면 기존 이미지 유지
        if (profileImg != null && !profileImg.isEmpty()) {
            member.setProfileImg(profileImg.getBytes());
        }

        memberRepository.save(member);
    }
    
    public MemberResponse findByGoogleId(final String googleId) {
        return memberRepository.findByGoogleId(googleId)
                .map(this::mapToResponse)
                .orElse(null);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        return null;
    }

//    public void delete(final Long id) {
//        Member member = memberRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException("Member not found"));
//        memberRepository.deleteById(id);
//    }

    private MemberResponse mapToResponse(final Member member) {
        MemberResponse response = new MemberResponse();
        response.setId(member.getId());
        response.setUsername(member.getUsername());
        response.setContact(member.getContact());
        response.setGoogleId(member.getGoogleId());
        if (member.getProfileImg() != null) {
            response.setProfileImg(new String(java.util.Base64.getEncoder().encode(member.getProfileImg())));
        } else {
            response.setProfileImg(null);
        }
        return response;
    }

    private Member mapToEntity(final MemberRequest memberRequest, final Member member) {
        member.setUsername(memberRequest.getUsername());
        member.setContact(memberRequest.getContact());
        member.setGoogleId(memberRequest.getGoogleId());
        member.setProfileImg(memberRequest.getProfileImg());
        return member;
    }
}
