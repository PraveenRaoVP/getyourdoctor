package com.getyourdoc.getyourdoctors.repositories;

import com.getyourdoc.getyourdoctors.models.ClinicArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicAreaRepository extends JpaRepository<ClinicArea, Long> {
    ClinicArea getClinicAreaByClinicAreaId(Long clinicAreaId);

    ClinicArea findClinicAreaByClinicAreaId(Long clinicAreaId);
}
