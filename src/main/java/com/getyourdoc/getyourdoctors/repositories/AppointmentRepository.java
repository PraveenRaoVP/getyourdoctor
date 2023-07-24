package com.getyourdoc.getyourdoctors.repositories;

import com.getyourdoc.getyourdoctors.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("SELECT a FROM Appointment a WHERE a.clinicArea.clinicAreaId = :clinicAreaId " +
            "AND a.appointmentDate = :date AND a.startTime <= :endTime AND a.endTime >= :startTime")
    List<Appointment> findAppointmentsInSlot(@Param("clinicAreaId") Long clinicAreaId,
                                             @Param("date") LocalDate date,
                                             @Param("startTime") LocalTime startTime,
                                             @Param("endTime") LocalTime endTime);

    List<Appointment> findByPatient_PatientId(Long patientId);

    List<Appointment> findByClinicArea_ClinicAreaId(Long clinicAreaId);

    List<Appointment> findByAppointmentDate(LocalDate date);

    List<Appointment> findByAppointmentDateBetween(LocalDate startDate, LocalDate endDate);

    List<Appointment> findByPatient_PatientIdAndAppointmentDateBetween(Long patientId, LocalDate today, LocalDate futureDate);


    List<Appointment> findByClinicArea_ClinicAreaIdAndAppointmentDate(Long clinicAreaId, LocalDate date);

    Optional<Appointment> findBySlot_SlotIdAndPatient_PatientIdAndAppointmentDate(Long slotId, Long patientId, LocalDate appointmentDate);
}
