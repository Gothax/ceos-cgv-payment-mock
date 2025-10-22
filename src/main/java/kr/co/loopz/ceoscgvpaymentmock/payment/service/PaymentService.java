package kr.co.loopz.ceoscgvpaymentmock.payment.service;


import jakarta.validation.constraints.NotBlank;
import kr.co.loopz.ceoscgvpaymentmock.client.AuthService;
import kr.co.loopz.ceoscgvpaymentmock.client.domain.Client;
import kr.co.loopz.ceoscgvpaymentmock.client.exception.ClientErrorCode;
import kr.co.loopz.ceoscgvpaymentmock.client.exception.ClientException;
import kr.co.loopz.ceoscgvpaymentmock.client.repository.ClientRepository;
import kr.co.loopz.ceoscgvpaymentmock.payment.domain.Payment;
import kr.co.loopz.ceoscgvpaymentmock.payment.domain.enums.PaymentStatus;
import kr.co.loopz.ceoscgvpaymentmock.payment.dto.request.InstantPaymentRequest;
import kr.co.loopz.ceoscgvpaymentmock.payment.dto.response.PaymentCompleteResponse;
import kr.co.loopz.ceoscgvpaymentmock.payment.dto.response.PaymentResponse;
import kr.co.loopz.ceoscgvpaymentmock.payment.exception.PaymentErrorCode;
import kr.co.loopz.ceoscgvpaymentmock.payment.exception.PaymentException;
import kr.co.loopz.ceoscgvpaymentmock.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ClientRepository clientRepository;

    @Transactional
    public PaymentCompleteResponse processPayment(
            String clientId,
            String paymentId,
            InstantPaymentRequest request
    ) {

        makePaymentFail();
        checkDuplicate(clientId, paymentId);
        checkStoreId(clientId, request.storeId());

        Payment payment = Payment.createPayment(clientId, paymentId, request);
        paymentRepository.save(payment);

        return PaymentCompleteResponse.of(paymentId, payment.getCreatedAt());
    }

    public PaymentResponse getPaymentDetails(
            String clientId,
            String paymentId
    ) {
        Payment payment = findPaymentOrThrow(clientId, paymentId);

        return PaymentResponse.from(payment);
    }

    @Transactional
    public PaymentResponse cancelPayment(
            String clientId,
            String paymentId
    ) {
        Payment payment = findPaymentOrThrow(clientId, paymentId);
        payment.updateStatus(PaymentStatus.CANCELLED);

        return PaymentResponse.from(payment);
    }


    private void makePaymentFail() {
        // 10% 확률로 결제 실패 시뮬레이션
        if (Math.random() < 0.1) throw new PaymentException(PaymentErrorCode.PAYMENT_PROCESSING_FAILED);
    }

    private void checkDuplicate(String clientId, String paymentId) {
        if (paymentRepository.existsByClientIdAndPaymentId(clientId, paymentId)) throw new PaymentException(PaymentErrorCode.PAYMENT_ALREADY_EXISTS);
    }


    private Payment findPaymentOrThrow(String clientId, String paymentId) {
        return paymentRepository.findByClientIdAndPaymentId(clientId, paymentId)
                .orElseThrow(() -> new PaymentException(PaymentErrorCode.PAYMENT_NOT_FOUND));
    }


    private void checkStoreId(String clientId, String storeId) {
        Client client = clientRepository.findClientByClientId(clientId)
                .orElseThrow(() -> new ClientException(ClientErrorCode.CLIENT_NOT_FOUND));
        if (!client.getStoreId().equals(storeId)) {
            throw new PaymentException(PaymentErrorCode.STORE_NOT_FOUND);
        }
    }



}
