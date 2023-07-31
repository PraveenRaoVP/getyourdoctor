package com.getyourdoc.getyourdoctors.models.helpers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FeedbackRequest {
    private Long patientId;
    private Long clinicAreaId;
    private int rating;
    private String comment;
}
