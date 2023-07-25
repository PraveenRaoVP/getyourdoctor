package com.getyourdoc.getyourdoctors.repositories;

import com.getyourdoc.getyourdoctors.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByPaymentId(Long paymentId);
}
