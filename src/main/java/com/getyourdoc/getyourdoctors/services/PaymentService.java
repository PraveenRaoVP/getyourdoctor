package com.getyourdoc.getyourdoctors.services;

import com.getyourdoc.getyourdoctors.models.Appointment;
import com.getyourdoc.getyourdoctors.models.Patient;
import com.getyourdoc.getyourdoctors.models.Payment;
import com.getyourdoc.getyourdoctors.models.Receipt;
import com.getyourdoc.getyourdoctors.repositories.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PatientService patientService;
    private final AppointmentService appointmentService;
    private final ReceiptService receiptService;

    public PaymentService(PaymentRepository paymentRepository,
                          PatientService patientService,
                          AppointmentService appointmentService,
                          ReceiptService receiptService) {
        this.paymentRepository = paymentRepository;
        this.patientService = patientService;
        this.appointmentService = appointmentService;
        this.receiptService = receiptService;
    }

    public Payment createPayment(Long receiptId, Payment payment) {
        Receipt receipt = receiptService.getReceiptById(receiptId);
        Patient patient = patientService.getPatientById(receipt.getPatient().getPatientId());
        Appointment appointment = appointmentService.getAppointmentById(receipt.getAppointment().getAppointmentId());
        if (patient == null) {
            throw new IllegalArgumentException("Invalid patient ID");
        }
        if (appointment == null) {
            throw new IllegalArgumentException("Invalid appointment ID");
        }

        // Create a new receipt and set the payment as its reference

        receipt.getPayments().add(payment);

        // Set the payment fields
        payment.setPatient(patient);
        payment.setAppointment(appointment);
        payment.setReceipt(receipt);
        payment.setPaymentDate(java.sql.Timestamp.valueOf(LocalDateTime.now()));

        return paymentRepository.save(payment);
    }

    public Payment getPaymentById(Long paymentId) {
        Optional<Payment> optionalPayment = paymentRepository.findByPaymentId(paymentId);
        return optionalPayment.orElse(null);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment updatePayment(Long paymentId, Payment updatedPayment) {
        Payment payment = getPaymentById(paymentId);
        if (payment == null) {
            throw new IllegalArgumentException("Payment not found with ID: " + paymentId);
        }

        if (updatedPayment.getPatient() != null) {
            Patient patient = patientService.getPatientById(updatedPayment.getPatient().getPatientId());
            if (patient == null) {
                throw new IllegalArgumentException("Invalid patient ID");
            }
            payment.setPatient(patient);
        }

        if (updatedPayment.getAppointment() != null) {
            Appointment appointment = appointmentService.getAppointmentById(updatedPayment.getAppointment().getAppointmentId());
            if (appointment == null) {
                throw new IllegalArgumentException("Invalid appointment ID");
            }
            payment.setAppointment(appointment);
        }

        if (updatedPayment.getReceipt() != null) {
            Receipt receipt = receiptService.getReceiptById(updatedPayment.getReceipt().getReceiptId());
            if (receipt == null) {
                throw new IllegalArgumentException("Invalid receipt ID");
            }
            payment.setReceipt(receipt);
        }

        payment.setAmount(updatedPayment.getAmount());
        payment.setPaymentDate(java.sql.Timestamp.valueOf(LocalDateTime.now()));

        return paymentRepository.save(payment);
    }

    public void deletePayment(Long paymentId) {
        Payment payment = getPaymentById(paymentId);
        if (payment == null) {
            throw new IllegalArgumentException("Payment not found with ID: " + paymentId);
        }
        paymentRepository.delete(payment);
    }
}
