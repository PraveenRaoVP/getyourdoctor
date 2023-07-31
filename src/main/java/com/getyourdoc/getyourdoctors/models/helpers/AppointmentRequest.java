package com.getyourdoc.getyourdoctors.models.helpers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequest {

        private Long patientId;
        private Long clinicAreaId;
        private Long slotId;
        private LocalDate appointmentDate;
        private LocalTime startTime;
        private LocalTime endTime;

}
