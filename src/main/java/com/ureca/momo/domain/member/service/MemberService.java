package com.ureca.momo.domain.member.service;

import com.ureca.momo.domain.member.dto.request.MemberRequest;
import com.ureca.momo.domain.member.dto.response.MemberResponse;
import com.ureca.momo.domain.member.model.Member;
import com.ureca.momo.domain.member.repository.MemberRepository;
import com.ureca.momo.util.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponse get(final Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + id));
        return MemberResponse.of(member);
    }


    public MemberResponse googleLogin(MemberRequest request) {
        // Google ID로 기존 사용자를 찾기
        return memberRepository.findByGoogleId(request.googleId())
                .map(MemberResponse::of) // 기존 사용자가 있으면 반환
                .orElseGet(() -> { // 없으면 새 사용자 생성 후 반환
                    Member newMember = new Member(
                            request.username(),
                            request.contact(),
                            request.googleId(),
                            request.profileImg()
                    );
                    memberRepository.save(newMember);
                    return MemberResponse.of(newMember);
                });
    }

    // 업데이트 메서드 추가 또는 수정
    public void update(final Long id, final MemberRequest memberRequest, MultipartFile profileImg) throws IOException {
        final Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Member not found"));

        // Update fields from MemberRequest
        member.setUsername(memberRequest.username());
        member.setContact(memberRequest.contact());
        member.setGoogleId(memberRequest.googleId());

        // Use profileImg MultipartFile for updating if it exists, otherwise retain existing image
        if (profileImg != null && !profileImg.isEmpty()) {
            member.setProfileImg(profileImg.getBytes());
        }

        memberRepository.save(member);
    }



    public void delete(final Long id) {
        memberRepository.deleteById(id);
    }

}
