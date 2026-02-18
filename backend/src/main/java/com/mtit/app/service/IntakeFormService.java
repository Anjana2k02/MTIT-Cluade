package com.mtit.app.service;

import com.mtit.app.dto.intake.IntakeFormRequest;
import com.mtit.app.dto.intake.IntakeFormResponse;

import java.util.List;

public interface IntakeFormService {

    IntakeFormResponse submitIntakeForm(IntakeFormRequest request);

    IntakeFormResponse getIntakeFormByServiceRecordId(Long serviceRecordId);

    List<IntakeFormResponse> getAllIntakeForms();
}
