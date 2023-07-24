package com.getyourdoc.getyourdoctors.repositories;

import com.getyourdoc.getyourdoctors.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
