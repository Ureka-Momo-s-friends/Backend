package io.bootify.momo.domain.cat.controller;

import io.bootify.momo.domain.cat.dto.request.StrayCatRequest;
import io.bootify.momo.domain.cat.dto.response.StrayCatResponse;
import io.bootify.momo.domain.cat.service.StrayCatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/strayCats", produces = MediaType.APPLICATION_JSON_VALUE)
public class StrayCatController {

    private final StrayCatService strayCatService;

    // 특정 회원의 고양이 전체 조회
    @GetMapping
    public ResponseEntity<List<StrayCatResponse>> getAllStrayCats(@RequestBody final Long memberId) {
        return ResponseEntity.ok(strayCatService.findAllByMember(memberId));
    }



    // 고양이 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStrayCat(@PathVariable(name = "id") final Long id) {
        strayCatService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
