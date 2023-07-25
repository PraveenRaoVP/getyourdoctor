package com.getyourdoc.getyourdoctors.controllers;

import com.getyourdoc.getyourdoctors.models.Payment;
import com.getyourdoc.getyourdoctors.services.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/{receiptId}")
    public ResponseEntity<Payment> createPayment(@PathVariable("receiptId") Long receiptId,
                                                 @RequestBody Payment payment) {
        Payment createdPayment = paymentService.createPayment(receiptId, payment);
        return new ResponseEntity<>(createdPayment, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable("id") Long paymentId) {
        Payment payment = paymentService.getPaymentById(paymentId);
        if (payment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable("id") Long paymentId, @RequestBody Payment updatedPayment) {
        Payment payment = paymentService.updatePayment(paymentId, updatedPayment);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable("id") Long paymentId) {
        paymentService.deletePayment(paymentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
