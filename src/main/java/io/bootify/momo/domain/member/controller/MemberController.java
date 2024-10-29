package io.bootify.momo.domain.member.controller;

import io.bootify.momo.domain.member.dto.request.MemberRequest;
import io.bootify.momo.domain.member.dto.response.MemberResponse;
import io.bootify.momo.domain.member.service.MemberService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:3000") // React 개발 서버 주소
@RestController
@RequestMapping(value = "/api/members", produces = MediaType.APPLICATION_JSON_VALUE)
public class MemberController {

    private final MemberService memberService;

    public MemberController(final MemberService memberService) {
        this.memberService = memberService;
    }

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
        // Google ID를 기준으로 기존 사용자가 있는지 확인
        MemberResponse existingMember = memberService.findByGoogleId(memberRequest.getGoogleId());

        if (existingMember == null) {
            // 기존 사용자가 없다면 새로 생성
            final Long createdId = memberService.create(memberRequest);
            MemberResponse newMember = memberService.get(createdId); // 생성된 멤버 정보 가져오기
            return new ResponseEntity<>(newMember, HttpStatus.CREATED);
        } else {
            // 기존 사용자가 있다면 기존 사용자 정보 반환
            return new ResponseEntity<>(existingMember, HttpStatus.OK);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateMember(@PathVariable(name = "id") final Long id,
                                             @RequestPart("userData") @Valid final MemberRequest memberRequest,
                                             @RequestPart(value = "profileImg", required = false) MultipartFile profileImg) {
        try {
            // 프로필 이미지 파일이 있는 경우에만 전달
            memberService.update(id, memberRequest, profileImg);
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }




//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteMember(@PathVariable(name = "id") final Long id) {
//        final ReferencedWarning referencedWarning = memberService.getReferencedWarning(id);
//        if (referencedWarning != null) {
//            throw new ReferencedException(referencedWarning);
//        }
//        memberService.delete(id);
//        return ResponseEntity.noContent().build();
//    }
}
