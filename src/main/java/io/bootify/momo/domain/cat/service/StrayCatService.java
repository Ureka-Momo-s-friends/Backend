package io.bootify.momo.domain.cat.service;

import io.bootify.momo.domain.member.model.Member;
import io.bootify.momo.domain.cat.model.StrayCat;
import io.bootify.momo.model.StrayCatDTO;
import io.bootify.momo.domain.member.repository.MemberRepository;
import io.bootify.momo.domain.cat.repository.StrayCatRepository;
import io.bootify.momo.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class StrayCatService {

    private final StrayCatRepository strayCatRepository;
    private final MemberRepository memberRepository;

    public StrayCatService(final StrayCatRepository strayCatRepository,
            final MemberRepository memberRepository) {
        this.strayCatRepository = strayCatRepository;
        this.memberRepository = memberRepository;
    }

    public List<StrayCatDTO> findAll() {
        final List<StrayCat> strayCats = strayCatRepository.findAll(Sort.by("id"));
        return strayCats.stream()
                .map(strayCat -> mapToDTO(strayCat, new StrayCatDTO()))
                .toList();
    }

    public StrayCatDTO get(final Long id) {
        return strayCatRepository.findById(id)
                .map(strayCat -> mapToDTO(strayCat, new StrayCatDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final StrayCatDTO strayCatDTO) {
        final StrayCat strayCat = new StrayCat();
        mapToEntity(strayCatDTO, strayCat);
        return strayCatRepository.save(strayCat).getId();
    }

    public void update(final Long id, final StrayCatDTO strayCatDTO) {
        final StrayCat strayCat = strayCatRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(strayCatDTO, strayCat);
        strayCatRepository.save(strayCat);
    }

    public void delete(final Long id) {
        strayCatRepository.deleteById(id);
    }

    private StrayCatDTO mapToDTO(final StrayCat strayCat, final StrayCatDTO strayCatDTO) {
        strayCatDTO.setId(strayCat.getId());
        strayCatDTO.setCatImgUrl(strayCat.getCatImgUrl());
        strayCatDTO.setLat(strayCat.getLat());
        strayCatDTO.setLon(strayCat.getLon());
        strayCatDTO.setMember(strayCat.getMember() == null ? null : strayCat.getMember().getId());
        return strayCatDTO;
    }

    private StrayCat mapToEntity(final StrayCatDTO strayCatDTO, final StrayCat strayCat) {
        strayCat.setCatImgUrl(strayCatDTO.getCatImgUrl());
        strayCat.setLat(strayCatDTO.getLat());
        strayCat.setLon(strayCatDTO.getLon());
        final Member member = strayCatDTO.getMember() == null ? null : memberRepository.findById(strayCatDTO.getMember())
                .orElseThrow(() -> new NotFoundException("member not found"));
        strayCat.setMember(member);
        return strayCat;
    }

}
