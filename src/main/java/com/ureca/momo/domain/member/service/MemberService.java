package com.ureca.momo.domain.member.service;

import com.ureca.momo.domain.member.dto.request.MemberRequest;
import com.ureca.momo.domain.member.dto.response.MemberResponse;
import com.ureca.momo.domain.member.model.Member;
import com.ureca.momo.domain.member.repository.MemberRepository;
import com.ureca.momo.util.ReferencedWarning;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public List<MemberResponse> findAll() {
        return memberRepository.findAll()
                .stream()
                .map(MemberResponse::of)
                .collect(Collectors.toList());
    }

    public MemberResponse get(Long id) {
        return memberRepository.findById(id)
                .map(MemberResponse::of)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + id));
    }

    public MemberResponse findByGoogleId(String googleId) {
        return memberRepository.findByGoogleId(googleId)
                .map(MemberResponse::of)
                .orElse(null);
    }

    public Long create(MemberRequest memberRequest) {
        Member member = new Member(
                memberRequest.username(),
                memberRequest.contact(),
                memberRequest.googleId(),
                memberRequest.profileImg()
        );
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public void update(Long id, MemberRequest memberRequest, MultipartFile profileImg) throws IOException {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + id));

        // 필드를 직접 업데이트
        member.setUsername(memberRequest.username());
        member.setContact(memberRequest.contact());
        member.setGoogleId(memberRequest.googleId());

        // 새 이미지 파일이 있으면 업데이트, 없으면 기존 이미지 유지
        if (profileImg != null && !profileImg.isEmpty()) {
            member.setProfileImg(profileImg.getBytes());
        }

        memberRepository.save(member);
    }

    @Transactional
    public void delete(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        memberRepository.delete(member);
    }

    // 참조 경고 확인 메서드 (필요 시 사용)
    public ReferencedWarning getReferencedWarning(Long id) {
        // 다른 참조와의 연결 확인 및 경고 메시지 반환 로직 추가
        // 예: 특정 조건에 따라 반환할 수 있는 경고 생성
        return null; // 경고가 필요 없을 경우 null 반환
    }
}
