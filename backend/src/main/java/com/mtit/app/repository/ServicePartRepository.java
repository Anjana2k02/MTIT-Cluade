package com.mtit.app.repository;

import com.mtit.app.model.ServicePart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicePartRepository extends JpaRepository<ServicePart, Long> {
}
