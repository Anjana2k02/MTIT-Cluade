package com.mtit.app.repository;

import com.mtit.app.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Optional<Vehicle> findByRegistrationNumber(String registrationNumber);

    boolean existsByRegistrationNumber(String registrationNumber);

    boolean existsByChassisNumber(String chassisNumber);

    List<Vehicle> findByCustomerId(Long customerId);
}
