package com.nexthire.backend.controller;

import com.nexthire.backend.dto.ApplicationCreateRequest;
import com.nexthire.backend.dto.ApplicationDto;
import com.nexthire.backend.dto.ApplicationStatusUpdateRequest;
import com.nexthire.backend.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<ApplicationDto> applyForJob(Authentication authentication,
                                                       @Valid @RequestBody ApplicationCreateRequest request) {
        return ResponseEntity.ok(applicationService.applyForJob(authentication.getName(), request));
    }

    @GetMapping("/my")
    public ResponseEntity<List<ApplicationDto>> getMyApplications(Authentication authentication) {
        return ResponseEntity.ok(applicationService.getStudentApplications(authentication.getName()));
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ApplicationDto>> getJobApplicants(@PathVariable Long jobId,
                                                                 Authentication authentication) {
        return ResponseEntity.ok(applicationService.getJobApplicants(jobId, authentication.getName()));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApplicationDto> updateStatus(@PathVariable Long id,
                                                        Authentication authentication,
                                                        @Valid @RequestBody ApplicationStatusUpdateRequest request) {
        return ResponseEntity.ok(applicationService.updateApplicationStatus(id, authentication.getName(), request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> withdrawApplication(@PathVariable Long id,
                                                    Authentication authentication) {
        applicationService.withdrawApplication(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
