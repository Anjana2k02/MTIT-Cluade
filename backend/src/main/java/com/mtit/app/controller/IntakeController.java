package com.mtit.app.controller;

import com.mtit.app.dto.intake.IntakeFormRequest;
import com.mtit.app.dto.intake.IntakeFormResponse;
import com.mtit.app.service.IntakeFormService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/intake")
@RequiredArgsConstructor
@Tag(name = "Service Intake", description = "Full service intake form submission and retrieval")
public class IntakeController {

    private final IntakeFormService intakeFormService;

    @PostMapping
    @Operation(summary = "Submit a complete service intake form")
    public ResponseEntity<IntakeFormResponse> submitIntake(@Valid @RequestBody IntakeFormRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(intakeFormService.submitIntakeForm(request));
    }

    @GetMapping("/{serviceRecordId}")
    @Operation(summary = "Get intake form details by service record ID")
    public ResponseEntity<IntakeFormResponse> getIntake(@PathVariable Long serviceRecordId) {
        return ResponseEntity.ok(intakeFormService.getIntakeFormByServiceRecordId(serviceRecordId));
    }

    @GetMapping
    @Operation(summary = "Get all intake forms")
    public ResponseEntity<List<IntakeFormResponse>> getAllIntakes() {
        return ResponseEntity.ok(intakeFormService.getAllIntakeForms());
    }
}
