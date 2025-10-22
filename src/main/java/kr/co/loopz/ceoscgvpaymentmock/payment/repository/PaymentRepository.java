package kr.co.loopz.ceoscgvpaymentmock.payment.repository;

import kr.co.loopz.ceoscgvpaymentmock.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    boolean existsByClientIdAndPaymentId(String clientId, String paymentId);

    Optional<Payment> findByClientIdAndPaymentId(String clientId, String paymentId);
}
