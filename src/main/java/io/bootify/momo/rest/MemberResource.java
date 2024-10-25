package io.bootify.momo.rest;

import io.bootify.momo.model.MemberDTO;
import io.bootify.momo.service.MemberService;
import io.bootify.momo.util.ReferencedException;
import io.bootify.momo.util.ReferencedWarning;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000") // React 개발 서버 주소
@RestController
@RequestMapping(value = "/api/members", produces = MediaType.APPLICATION_JSON_VALUE)
public class MemberResource {

    private final MemberService memberService;

    public MemberResource(final MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<MemberDTO>> getAllMembers() {
        return ResponseEntity.ok(memberService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDTO> getMember(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(memberService.get(id));
    }

    @PostMapping("/google-login")
    public ResponseEntity<MemberDTO> googleLogin(@RequestBody @Valid final MemberDTO memberDTO) {
        // Google ID를 기준으로 기존 사용자가 있는지 확인
        MemberDTO existingMember = memberService.findByGoogleId(memberDTO.getGoogleId());

        if (existingMember == null) {
            // 기존 사용자가 없다면 새로 생성
            final Long createdId = memberService.create(memberDTO);
            MemberDTO newMember = memberService.get(createdId); // 생성된 멤버 정보 가져오기
            return new ResponseEntity<>(newMember, HttpStatus.CREATED);
        } else {
            // 기존 사용자가 있다면 기존 사용자 정보 반환
            return new ResponseEntity<>(existingMember, HttpStatus.OK);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateMember(@PathVariable(name = "id") final Long id,
                                             @RequestBody @Valid final MemberDTO memberDTO) {
        memberService.update(id, memberDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = memberService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        memberService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
