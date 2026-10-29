package com.nexthire.backend.controller;

import com.nexthire.backend.dto.JobCreateRequest;
import com.nexthire.backend.dto.JobDto;
import com.nexthire.backend.entity.ExperienceLevel;
import com.nexthire.backend.entity.JobType;
import com.nexthire.backend.service.JobService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<JobDto>> getAllJobs(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) JobType jobType,
            @RequestParam(required = false) ExperienceLevel experienceLevel,
            Authentication authentication) {
        String email = authentication != null ? authentication.getName() : null;
        return ResponseEntity.ok(jobService.getAllJobs(search, location, jobType, experienceLevel, email));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDto> getJobById(@PathVariable Long id, Authentication authentication) {
        String email = authentication != null ? authentication.getName() : null;
        return ResponseEntity.ok(jobService.getJobById(id, email));
    }

    @PostMapping
    public ResponseEntity<JobDto> createJob(Authentication authentication,
                                           @Valid @RequestBody JobCreateRequest request) {
        return ResponseEntity.ok(jobService.createJob(authentication.getName(), request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobDto> updateJob(@PathVariable Long id,
                                           Authentication authentication,
                                           @Valid @RequestBody JobCreateRequest request) {
        return ResponseEntity.ok(jobService.updateJob(id, authentication.getName(), request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id, Authentication authentication) {
        jobService.deleteJob(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/recruiter/my")
    public ResponseEntity<List<JobDto>> getMyPostedJobs(Authentication authentication) {
        return ResponseEntity.ok(jobService.getJobsByRecruiter(authentication.getName()));
    }

    @PostMapping("/{id}/save")
    public ResponseEntity<Void> saveJob(@PathVariable Long id, Authentication authentication) {
        jobService.saveJob(authentication.getName(), id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/save")
    public ResponseEntity<Void> unsaveJob(@PathVariable Long id, Authentication authentication) {
        jobService.unsaveJob(authentication.getName(), id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/saved")
    public ResponseEntity<List<JobDto>> getSavedJobs(Authentication authentication) {
        return ResponseEntity.ok(jobService.getSavedJobs(authentication.getName()));
    }
}
