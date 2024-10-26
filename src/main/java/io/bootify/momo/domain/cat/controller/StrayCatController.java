package io.bootify.momo.domain.cat.controller;

import io.bootify.momo.domain.cat.dto.response.StrayCatResponse;
import io.bootify.momo.model.StrayCatDTO;
import io.bootify.momo.domain.cat.service.StrayCatService;
import jakarta.validation.Valid;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/strayCats", produces = MediaType.APPLICATION_JSON_VALUE)
public class StrayCatController {

    private final StrayCatService strayCatService;

    // 회원의 모든 고양이 조회
    @GetMapping("/{id}")
    public ResponseEntity<List<StrayCatResponse>> getAllStrayCats(@RequestBody final Long memberId) {
        return ResponseEntity.ok(strayCatService.get(memberId));
    }

    // 특정 고양이 조회
    @GetMapping
    public ResponseEntity<StrayCatResponse> getStrayCat(@RequestBody final Long id) {
        return ResponseEntity.ok(strayCatService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createStrayCat(@RequestBody @Valid final StrayCatDTO strayCatDTO) {
        final Long createdId = strayCatService.create(strayCatDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateStrayCat(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final StrayCatDTO strayCatDTO) {
        strayCatService.update(id, strayCatDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStrayCat(@PathVariable(name = "id") final Long id) {
        strayCatService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
