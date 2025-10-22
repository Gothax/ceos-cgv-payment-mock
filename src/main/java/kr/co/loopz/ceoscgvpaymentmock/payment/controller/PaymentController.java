package kr.co.loopz.ceoscgvpaymentmock.payment.controller;

import jakarta.validation.Valid;
import kr.co.loopz.ceoscgvpaymentmock.payment.dto.request.InstantPaymentRequest;
import kr.co.loopz.ceoscgvpaymentmock.payment.dto.response.PaymentCompleteResponse;
import kr.co.loopz.ceoscgvpaymentmock.payment.dto.response.PaymentResponse;
import kr.co.loopz.ceoscgvpaymentmock.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> getPayment(
            @AuthenticationPrincipal User client,
            @PathVariable String paymentId
    ) {
        String clientId = client.getUsername();

        PaymentResponse paymentResponse = paymentService.getPaymentDetails(clientId, paymentId);
        return ResponseEntity.ok(paymentResponse);
    }

    @PostMapping("/{paymentId}/instant")
    public ResponseEntity<PaymentCompleteResponse> instantPayment(
            @AuthenticationPrincipal User client,
            @PathVariable String paymentId,
            @Valid @RequestBody InstantPaymentRequest request
    ) {
        String clientId = client.getUsername();

        PaymentCompleteResponse paymentResponse = paymentService.processPayment(clientId, paymentId, request);
        return ResponseEntity.ok(paymentResponse);
    }

    @PostMapping("/{paymentId}/cancel")
    public ResponseEntity<PaymentResponse> cancelPayment(
            @AuthenticationPrincipal User client,
            @PathVariable String paymentId
    ) {
        String clientId = client.getUsername();

        PaymentResponse paymentResponse = paymentService.cancelPayment(clientId, paymentId);
        return ResponseEntity.ok(paymentResponse);
    }


}
