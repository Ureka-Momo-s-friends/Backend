package io.bootify.momo.domain.cat.service;

import io.bootify.momo.domain.cat.dto.request.StrayCatRequest;
import io.bootify.momo.domain.cat.dto.response.StrayCatResponse;
import io.bootify.momo.domain.cat.model.StrayCat;
import io.bootify.momo.domain.cat.repository.StrayCatRepository;
import io.bootify.momo.domain.member.model.Member;
import io.bootify.momo.domain.member.repository.MemberRepository;
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
    private final MemberRepository memberRepository;// 파일 저장 서비스 추가

    public List<StrayCatResponse> findAllByMember(final Long memberId) {
        return strayCatRepository.findAllByMemberId(memberId)
                .stream()
                .map(StrayCatResponse::of)
                .toList();
    }



    public void delete(final Long id) {
        strayCatRepository.deleteById(id);
    }
}
