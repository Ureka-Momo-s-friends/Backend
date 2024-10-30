package com.ureca.momo.domain.cat.service;

import com.ureca.momo.domain.cat.dto.request.StrayCatRequest;
import com.ureca.momo.domain.cat.dto.response.StrayCatResponse;

import java.util.List;

public interface StrayCatService {
    List<StrayCatResponse> findAll(Long memberId);

    Long create(StrayCatRequest request, Long memberId);

    void delete(Long id);
}
