package com.ureca.momo.domain.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ureca.momo.domain.member.dto.request.MemberRequest;
import com.ureca.momo.domain.member.dto.response.MemberResponse;
import com.ureca.momo.domain.member.service.MemberService;
import com.ureca.momo.util.ReferencedException;
import com.ureca.momo.util.ReferencedWarning;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/api/members", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getAllMembers() {
        return ResponseEntity.ok(memberService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(memberService.get(id));
    }

    @PostMapping("/google-login")
    public ResponseEntity<MemberResponse> googleLogin(@RequestBody @Valid final MemberRequest memberRequest) {
        try {
            String googleId = memberRequest.googleId();
            if (googleId == null || googleId.isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }

            MemberResponse existingMember = memberService.findByGoogleId(googleId);

            if (existingMember == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else {
                return ResponseEntity.ok(existingMember);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MemberResponse> register(
            @RequestPart("userData") @Valid final String userDataJson,
            @RequestPart(value = "profileImg", required = false) MultipartFile profileImg) {
        try {
            MemberRequest memberRequest = objectMapper.readValue(userDataJson, MemberRequest.class);

            if (profileImg != null && !profileImg.isEmpty()) {
                memberRequest = new MemberRequest(
                        memberRequest.username(),
                        memberRequest.contact(),
                        memberRequest.googleId(),
                        profileImg.getBytes()
                );
            }

            Long createdId = memberService.create(memberRequest);
            MemberResponse newMember = memberService.get(createdId);
            return new ResponseEntity<>(newMember, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateMember(
            @PathVariable(name = "id") final Long id,
            @RequestPart("userData") @Valid final String userDataJson,
            @RequestPart(value = "profileImg", required = false) MultipartFile profileImg) {
        try {
            // JSON을 MemberRequest 객체로 변환
            MemberRequest memberRequest = objectMapper.readValue(userDataJson, MemberRequest.class);

            // update 메서드 호출 시, profileImg가 있으면 전달
            memberService.update(id, memberRequest, profileImg);

            return ResponseEntity.ok(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable(name = "id") final Long id) {
        try {
            // 참조 경고가 있는지 확인
            final ReferencedWarning referencedWarning = memberService.getReferencedWarning(id);
            if (referencedWarning != null) {
                throw new ReferencedException(referencedWarning);
            }

            memberService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (ReferencedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 참조 오류 시 409 반환
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
