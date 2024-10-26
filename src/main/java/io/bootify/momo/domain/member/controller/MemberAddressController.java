package io.bootify.momo.domain.member.controller;

import io.bootify.momo.domain.member.dto.request.MemberAddressRequest;
import io.bootify.momo.domain.member.dto.response.MemberAddressResponse;
import io.bootify.momo.domain.member.service.MemberAddressService;
import jakarta.validation.Valid;
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
import java.util.List;


@RestController
@RequestMapping(value = "/api/addresses", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MemberAddressController {

    private final MemberAddressService addressService;

    @GetMapping
    public ResponseEntity<List<MemberAddressResponse>> getAddress(@RequestBody final Long memberId) {
        return ResponseEntity.ok(addressService.getAddress(memberId));
    }

    @PostMapping
    public ResponseEntity<Long> createAddress(@RequestBody @Valid final MemberAddressRequest request, @RequestBody final Long memberId) {
        final Long createdId = addressService.create(request, memberId);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateAddress(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final MemberAddressRequest request) {
        addressService.update(id, request);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable(name = "id") final Long id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
