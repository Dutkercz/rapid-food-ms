package com.db.ar.controller;


import com.db.ar.dto.CallbackPaymentDto;
import com.db.ar.dto.CreatePaymentRequest;
import com.db.ar.dto.PaymentResponse;
import com.db.ar.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Void> bankCallback(@RequestBody @Valid CallbackPaymentDto request) {
        paymentService.bankCallback(request);
        return ResponseEntity.ok().build();
    }
}
