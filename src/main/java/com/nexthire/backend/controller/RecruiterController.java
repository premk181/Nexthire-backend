package com.nexthire.backend.controller;

import com.nexthire.backend.dto.RecruiterProfileDto;
import com.nexthire.backend.service.RecruiterService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/recruiters")
public class RecruiterController {

    private final RecruiterService recruiterService;

    public RecruiterController(RecruiterService recruiterService) {
        this.recruiterService = recruiterService;
    }

    @GetMapping("/profile")
    public ResponseEntity<RecruiterProfileDto> getMyProfile(Authentication authentication) {
        return ResponseEntity.ok(recruiterService.getProfileByEmail(authentication.getName()));
    }

    @PutMapping("/profile")
    public ResponseEntity<RecruiterProfileDto> updateProfile(Authentication authentication,
                                                             @RequestBody Map<String, String> request) {
        String designation = request.get("designation");
        String department = request.get("department");
        return ResponseEntity.ok(recruiterService.updateProfile(authentication.getName(), designation, department));
    }
}
