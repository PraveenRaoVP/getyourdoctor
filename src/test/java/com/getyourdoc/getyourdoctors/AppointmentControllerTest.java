package com.getyourdoc.getyourdoctors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getyourdoc.getyourdoctors.controllers.AppointmentController;
import com.getyourdoc.getyourdoctors.models.Appointment;
import com.getyourdoc.getyourdoctors.models.helpers.AppointmentRequest;
import com.getyourdoc.getyourdoctors.services.AppointmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AppointmentController.class)
public class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AppointmentService appointmentService;

    private List<Appointment> appointmentList;

    @BeforeEach
    void setUp() {
        appointmentList = new ArrayList<>();

        // Create mock appointments
        Appointment appointment1 = new Appointment();
        appointment1.setAppointmentId(1L);
        appointment1.setAppointmentDate(LocalDate.of(2023, 7, 28));
        appointment1.setStartTime(LocalTime.of(14, 0));
        appointment1.setEndTime(LocalTime.of(15, 0));
        // Set other properties

        Appointment appointment2 = new Appointment();
        appointment2.setAppointmentId(2L);
        appointment2.setAppointmentDate(LocalDate.of(2023, 7, 26));
        appointment2.setStartTime(LocalTime.of(14, 0));
        appointment2.setEndTime(LocalTime.of(15, 0));
        // Set other properties

        // Add mock appointments to the list
        appointmentList.add(appointment1);
        appointmentList.add(appointment2);

        // Mock the service methods
        when(appointmentService.bookAppointment(any(AppointmentRequest.class)))
                .thenReturn(appointmentList.get(0));

        when(appointmentService.getAllAppointments())
                .thenReturn(appointmentList);

        when(appointmentService.getAppointmentsByPatient(anyLong()))
                .thenReturn(appointmentList);

        when(appointmentService.getAppointmentsByClinicArea(anyLong()))
                .thenReturn(appointmentList);

        when(appointmentService.getAppointmentsByDate(any(LocalDate.class)))
                .thenReturn(appointmentList);

        when(appointmentService.getAppointmentsBetweenDates(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(appointmentList);

        when(appointmentService.getAppointmentById(anyLong()))
                .thenReturn(appointmentList.get(0));

        when(appointmentService.updateAppointment(anyLong(), any(Appointment.class)))
                .thenReturn(appointmentList.get(0));

//        when(appointmentService.cancelAppointment(anyLong()))
//                .then(invocation -> {
//                    Long appointmentId = invocation.getArgument(0);
//                    appointmentList.removeIf(appointment -> appointment.getAppointmentId().equals(appointmentId));
//                    return null;
//                });
    }

    @Test
    public void testBookAppointment() throws Exception {
        AppointmentRequest appointmentRequest = new AppointmentRequest();
        appointmentRequest.setPatientId(1L);
        appointmentRequest.setClinicAreaId(1L);
        appointmentRequest.setSlotId(1L);
        appointmentRequest.setDoctorId(1L);
        appointmentRequest.setSymptoms("Fever");
        appointmentRequest.setAppointmentDate((LocalDate.of(2023, 7, 28)));
        appointmentRequest.setStartTime(LocalTime.of(14, 0));
        appointmentRequest.setEndTime(LocalTime.of(15, 0));

        Appointment mockAppointment = appointmentList.get(0);

        when(appointmentService.bookAppointment(any(AppointmentRequest.class)))
                .thenReturn(mockAppointment);

        mockMvc.perform(post("/api/v1/appointments/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appointmentRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appointmentId").value(mockAppointment.getAppointmentId()))
                .andExpect(jsonPath("$.appointmentDate").value(mockAppointment.getAppointmentDate()))
                .andExpect(jsonPath("$.startTime").value(mockAppointment.getStartTime()))
                .andExpect(jsonPath("$.endTime").value(mockAppointment.getEndTime()))
                .andExpect(jsonPath("$.clinicArea.clinicAreaId").value(mockAppointment.getClinicArea().getClinicAreaId()));
    }

    @Test
    public void testGetAllAppointments() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/appointments/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(appointmentList.size()));
    }


}
