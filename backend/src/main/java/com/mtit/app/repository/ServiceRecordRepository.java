package com.mtit.app.repository;

import com.mtit.app.model.ServiceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRecordRepository extends JpaRepository<ServiceRecord, Long> {

    @Query("SELECT sr FROM ServiceRecord sr LEFT JOIN FETCH sr.partsRequired LEFT JOIN FETCH sr.vehicle v LEFT JOIN FETCH v.customer WHERE sr.id = :id")
    Optional<ServiceRecord> findByIdWithDetails(@Param("id") Long id);

    @Query("SELECT DISTINCT sr FROM ServiceRecord sr LEFT JOIN FETCH sr.partsRequired LEFT JOIN FETCH sr.vehicle v LEFT JOIN FETCH v.customer")
    List<ServiceRecord> findAllWithDetails();

    @Query("SELECT DISTINCT sr FROM ServiceRecord sr LEFT JOIN FETCH sr.partsRequired LEFT JOIN FETCH sr.vehicle v LEFT JOIN FETCH v.customer WHERE v.id = :vehicleId")
    List<ServiceRecord> findByVehicleIdWithDetails(@Param("vehicleId") Long vehicleId);

    List<ServiceRecord> findByVehicleId(Long vehicleId);

    @Query(value = "SELECT COUNT(*) FROM service_records WHERE EXTRACT(YEAR FROM intake_timestamp) = :year AND deleted = false", nativeQuery = true)
    Long countByIntakeYear(@Param("year") int year);
}
