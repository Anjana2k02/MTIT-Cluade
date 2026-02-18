package com.mtit.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/test")
@Tag(name = "Test", description = "Protected test endpoints")
public class TestController {

    @GetMapping("/secure")
    @Operation(summary = "Protected endpoint - requires JWT", security = @SecurityRequirement(name = "Bearer Auth"))
    public ResponseEntity<Map<String, String>> secureEndpoint(Authentication authentication) {
        return ResponseEntity.ok(Map.of(
                "message", "You have accessed a protected endpoint!",
                "user", authentication.getName()
        ));
    }
}
