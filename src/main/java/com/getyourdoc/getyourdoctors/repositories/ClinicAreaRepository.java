package com.getyourdoc.getyourdoctors.repositories;

import com.getyourdoc.getyourdoctors.models.ClinicArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClinicAreaRepository extends JpaRepository<ClinicArea, Long> {
    ClinicArea getClinicAreaByClinicAreaId(Long clinicAreaId);

    ClinicArea findClinicAreaByClinicAreaId(Long clinicAreaId);

    List<ClinicArea> findByClinicAreaNameContainingIgnoreCase(String clinicAreaName);

    List<ClinicArea> findByAddress(String address);

    List<ClinicArea> findByAddressIgnoreCase(String address);

    @Query("SELECT c FROM ClinicArea c WHERE LOWER(c.address) LIKE %:address% AND LOWER(c.keywords) LIKE %:keywords%")
    List<ClinicArea> findByAddressAndKeywordsContainingIgnoreCase(String address, String keywords);
}
