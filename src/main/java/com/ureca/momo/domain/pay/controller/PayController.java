package com.ureca.momo.domain.pay.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/pays", produces = MediaType.APPLICATION_JSON_VALUE)
public class PayController {
//
//    private final PayService payService;
//
//    public PayResource(final PayService payService) {
//        this.payService = payService;
//    }
//
//    @GetMapping
//    public ResponseEntity<List<PayDTO>> getAllPays() {
//        return ResponseEntity.ok(payService.findAll());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<PayDTO> getPay(@PathVariable(name = "id") final Long id) {
//        return ResponseEntity.ok(payService.get(id));
//    }
//
//    @PostMapping
//    public ResponseEntity<Long> createPay(@RequestBody @Valid final PayDTO payDTO) {
//        final Long createdId = payService.create(payDTO);
//        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Long> updatePay(@PathVariable(name = "id") final Long id,
//            @RequestBody @Valid final PayDTO payDTO) {
//        payService.update(id, payDTO);
//        return ResponseEntity.ok(id);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deletePay(@PathVariable(name = "id") final Long id) {
//        payService.delete(id);
//        return ResponseEntity.noContent().build();
//    }

}
