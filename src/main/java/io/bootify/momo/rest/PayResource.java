package io.bootify.momo.rest;

import io.bootify.momo.model.PayDTO;
import io.bootify.momo.service.PayService;
import jakarta.validation.Valid;
import java.util.List;
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
@RequestMapping(value = "/api/pays", produces = MediaType.APPLICATION_JSON_VALUE)
public class PayResource {

    private final PayService payService;

    public PayResource(final PayService payService) {
        this.payService = payService;
    }

    @GetMapping
    public ResponseEntity<List<PayDTO>> getAllPays() {
        return ResponseEntity.ok(payService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PayDTO> getPay(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(payService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createPay(@RequestBody @Valid final PayDTO payDTO) {
        final Long createdId = payService.create(payDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePay(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final PayDTO payDTO) {
        payService.update(id, payDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePay(@PathVariable(name = "id") final Long id) {
        payService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
