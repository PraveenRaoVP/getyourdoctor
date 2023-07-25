package com.getyourdoc.getyourdoctors.controllers;

import com.getyourdoc.getyourdoctors.models.Feedback;
import com.getyourdoc.getyourdoctors.services.FeedbackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/feedbacks")
public class FeedbackController {
    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping("/{patientId}/{clinicAreaId}")
    public ResponseEntity<Feedback> createFeedback(@PathVariable Long patientId,
                                                   @PathVariable Long clinicAreaId,
                                                   @RequestBody Feedback feedback) {
        Feedback createdFeedback = feedbackService.createFeedback(patientId, clinicAreaId, feedback);
        return new ResponseEntity<>(createdFeedback, HttpStatus.CREATED);
    }

    @GetMapping("/{feedbackId}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable Long feedbackId) {
        Feedback feedback = feedbackService.getFeedbackById(feedbackId);
        if (feedback != null) {
            return new ResponseEntity<>(feedback, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    @PutMapping("/{feedbackId}")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable Long feedbackId,
                                                   @RequestBody Feedback updatedFeedback) {
        Feedback feedback = feedbackService.updateFeedback(feedbackId, updatedFeedback);
        return new ResponseEntity<>(feedback, HttpStatus.OK);
    }

    @DeleteMapping("/{feedbackId}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long feedbackId) {
        feedbackService.deleteFeedback(feedbackId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
