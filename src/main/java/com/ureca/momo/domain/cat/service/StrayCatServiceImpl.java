package com.ureca.momo.domain.cat.service;

import com.ureca.momo.domain.cat.dto.request.StrayCatRequest;
import com.ureca.momo.domain.cat.dto.response.StrayCatResponse;
import com.ureca.momo.domain.cat.model.StrayCat;
import com.ureca.momo.domain.cat.repository.StrayCatRepository;
import com.ureca.momo.domain.member.model.Member;
import com.ureca.momo.domain.member.repository.MemberRepository;
import com.ureca.momo.service.S3Service;
import com.ureca.momo.util.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StrayCatServiceImpl implements StrayCatService {

    private final StrayCatRepository strayCatRepository;
    private final MemberRepository memberRepository;
    private final S3Service s3Service;

    @Override
    public List<StrayCatResponse> findAll(Long memberId) {
        return strayCatRepository.findAllByMemberId(memberId)
                .stream()
                .map(StrayCatResponse::of)
                .toList();
    }

    @Override
    public Long create(StrayCatRequest request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundException::new);

        // S3에 이미지 업로드 후 URL 받기
        String imageUrl = s3Service.uploadFile(request.catImg());

        // 이미지 URL을 포함하여 StrayCat 엔티티 생성
        StrayCat strayCat = new StrayCat(imageUrl, request.lat(), request.lon(), member);
        return strayCatRepository.save(strayCat).getId();
    }

    @Override
    public void delete(Long id) {
        strayCatRepository.deleteById(id);
    }
}
