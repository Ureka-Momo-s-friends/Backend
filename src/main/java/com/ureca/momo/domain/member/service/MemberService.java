package com.ureca.momo.domain.member.service;

import com.ureca.momo.domain.member.dto.request.MemberRequest;
import com.ureca.momo.domain.member.dto.response.MemberResponse;
import com.ureca.momo.domain.member.model.Member;
import com.ureca.momo.domain.member.repository.MemberRepository;
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
    public void update(Long id, MemberRequest memberRequest) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + id));

        member.setUsername(memberRequest.username());
        member.setContact(memberRequest.contact());
        member.setGoogleId(memberRequest.googleId());
        member.setProfileImg(memberRequest.profileImg());

        memberRepository.save(member);
    }

    @Transactional
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }
}
