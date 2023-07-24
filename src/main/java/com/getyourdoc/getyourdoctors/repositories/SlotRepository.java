package com.getyourdoc.getyourdoctors.repositories;

import com.getyourdoc.getyourdoctors.models.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {
    Slot findSlotBySlotId(Long slotId);

    List<Slot> findByAvailable(boolean b);

    List<Slot> findByClinicAreaClinicAreaId(Long clinicAreaId);

    List<Slot> findByAppointmentsAppointmentDateAndAvailable(LocalDate appointmentDate, boolean available);


    List<Slot> findByStartTimeBetween(Time startTime, Time endTime);
}
