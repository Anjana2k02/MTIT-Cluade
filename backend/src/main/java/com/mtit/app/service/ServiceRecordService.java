package com.mtit.app.service;

import com.mtit.app.dto.intake.InternalDetailsRequest;
import com.mtit.app.dto.intake.ServiceDetailsRequest;
import com.mtit.app.model.ServiceRecord;

import java.util.List;

public interface ServiceRecordService {

    ServiceRecord createServiceRecord(ServiceDetailsRequest serviceReq, InternalDetailsRequest internalReq, Long vehicleId);

    ServiceRecord getServiceRecordById(Long id);

    List<ServiceRecord> getByVehicleId(Long vehicleId);

    List<ServiceRecord> getAllServiceRecords();

    ServiceRecord updateServiceRecord(Long id, ServiceDetailsRequest serviceReq, InternalDetailsRequest internalReq);

    void softDeleteServiceRecord(Long id);
}
