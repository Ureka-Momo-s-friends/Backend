package io.bootify.momo.domain.cat.service;

import io.bootify.momo.domain.cat.dto.request.StrayCatRequest;
import io.bootify.momo.domain.cat.dto.response.StrayCatResponse;
import io.bootify.momo.domain.cat.model.StrayCat;
import io.bootify.momo.domain.cat.repository.StrayCatRepository;
import io.bootify.momo.domain.member.model.Member;
import io.bootify.momo.domain.member.repository.MemberRepository;
import io.bootify.momo.service.FileStorageService;
import io.bootify.momo.util.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StrayCatService {

    private final StrayCatRepository strayCatRepository;
    private final MemberRepository memberRepository;
    private final FileStorageService fileStorageService; // 파일 저장 서비스 추가

    public List<StrayCatResponse> findAllByMember(final Long memberId) {
        return strayCatRepository.findAllByMemberId(memberId)
                .stream()
                .map(StrayCatResponse::of)
                .toList();
    }

    public Long create(final StrayCatRequest request, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundException::new);

        // 파일 경로 처리
        String catImgUrl = null;
        if (request.catImg() != null && !request.catImg().isEmpty()) {
            catImgUrl = fileStorageService.storeStrayCatFile(request.catImg()); // 파일 저장 후 URL 반환
        }

        // StrayCat 생성
        StrayCat strayCat = new StrayCat(catImgUrl, request.lat(), request.lon(), member);

        return strayCatRepository.save(strayCat).getId();
    }

    public void delete(final Long id) {
        strayCatRepository.deleteById(id);
    }
}
