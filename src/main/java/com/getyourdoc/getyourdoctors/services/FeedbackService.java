package com.getyourdoc.getyourdoctors.services;

import com.getyourdoc.getyourdoctors.exceptions.ClinicAreaNotFoundException;
import com.getyourdoc.getyourdoctors.exceptions.FeedbackNotFoundException;
import com.getyourdoc.getyourdoctors.exceptions.PatientNotFoundException;
import com.getyourdoc.getyourdoctors.models.ClinicArea;
import com.getyourdoc.getyourdoctors.models.Feedback;
import com.getyourdoc.getyourdoctors.models.Patient;
import com.getyourdoc.getyourdoctors.repositories.FeedbackRepository;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final PatientService patientService;
    private final ClinicAreaService clinicAreaService;

    public FeedbackService(FeedbackRepository feedbackRepository, PatientService patientService, ClinicAreaService clinicAreaService) {
        this.feedbackRepository = feedbackRepository;
        this.patientService = patientService;
        this.clinicAreaService = clinicAreaService;
    }
    public Feedback createFeedback(Long patientId, Long clinicAreaId, Feedback feedback) {
        Patient patient = patientService.getPatientById(patientId);
        if (patient == null) {
            throw new PatientNotFoundException("Invalid patient ID");
        }

        ClinicArea clinicArea = clinicAreaService.getClinicAreaById(clinicAreaId);
        if (clinicArea == null) {
            throw new ClinicAreaNotFoundException("Invalid clinic area ID");
        }
//        if (!Hibernate.isInitialized(clinicArea)) {
//            Hibernate.initialize(clinicArea);
//        }

        feedback.setPatient(patient);
        feedback.setClinicArea(clinicArea);
        return feedbackRepository.save(feedback);
    }

    public Feedback getFeedbackById(Long feedbackId) {
        Optional<Feedback> optionalFeedback = feedbackRepository.findById(feedbackId);
        return optionalFeedback.orElse(null);
    }

    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    public Feedback updateFeedback(Long feedbackId, Feedback updatedFeedback) {
        Feedback feedback = getFeedbackById(feedbackId);
        if (feedback == null) {
            throw new FeedbackNotFoundException("Feedback not found with ID: " + feedbackId);
        }

        if (updatedFeedback.getPatient() != null) {
            Patient patient = patientService.getPatientById(updatedFeedback.getPatient().getPatientId());
            if (patient == null) {
                throw new PatientNotFoundException("Invalid patient ID");
            }
            feedback.setPatient(patient);
        }

        if (updatedFeedback.getClinicArea() != null) {
            ClinicArea clinicArea = clinicAreaService.getClinicAreaById(updatedFeedback.getClinicArea().getClinicAreaId());
            if (clinicArea == null) {
                throw new ClinicAreaNotFoundException("Invalid clinic area ID");
            }
            feedback.setClinicArea(clinicArea);
        }

        feedback.setRating(updatedFeedback.getRating());
        feedback.setComment(updatedFeedback.getComment());

        return feedbackRepository.save(feedback);
    }

    public void deleteFeedback(Long feedbackId) {
        Feedback feedback = getFeedbackById(feedbackId);
        if (feedback == null) {
            throw new FeedbackNotFoundException("Feedback not found with ID: " + feedbackId);
        }
        feedbackRepository.delete(feedback);
    }
}
