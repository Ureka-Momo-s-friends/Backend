package com.ureca.momo.domain.cat.controller;

import com.ureca.momo.domain.cat.dto.request.StrayCatRequest;
import com.ureca.momo.domain.cat.dto.response.StrayCatResponse;
import com.ureca.momo.domain.cat.service.StrayCatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/strayCats")
public class StrayCatController {

    private final StrayCatService strayCatService;

    // 특정 회원의 고양이 전체 조회
    @GetMapping
    public ResponseEntity<List<StrayCatResponse>> getAllStrayCats(@RequestParam("memberId") Long memberId) {
        return ResponseEntity.ok(strayCatService.findAll(memberId));
    }

    // 고양이 등록
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> createStrayCat(
            @RequestParam("catImg") MultipartFile catImg,
            @RequestParam("lat") Double lat,
            @RequestParam("lon") Double lon,
            @RequestParam("memberId") Long memberId
    ) {
        StrayCatRequest request = new StrayCatRequest(catImg, lat, lon);
        final Long createdId = strayCatService.create(request, memberId);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    // 고양이 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStrayCat(@PathVariable(name = "id") final Long id) {
        strayCatService.delete(id);
        return ResponseEntity.noContent().build();
    }

}