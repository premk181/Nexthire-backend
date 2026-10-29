package com.nexthire.backend.controller;

import com.nexthire.backend.dto.StudentProfileDto;
import com.nexthire.backend.dto.StudentProfileUpdateRequest;
import com.nexthire.backend.service.StudentProfileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentProfileController {

    private final StudentProfileService studentProfileService;

    public StudentProfileController(StudentProfileService studentProfileService) {
        this.studentProfileService = studentProfileService;
    }

    @GetMapping("/profile")
    public ResponseEntity<StudentProfileDto> getMyProfile(Authentication authentication) {
        return ResponseEntity.ok(studentProfileService.getProfileByEmail(authentication.getName()));
    }

    @PutMapping("/profile")
    public ResponseEntity<StudentProfileDto> updateProfile(Authentication authentication,
                                                           @Valid @RequestBody StudentProfileUpdateRequest request) {
        return ResponseEntity.ok(studentProfileService.updateProfile(authentication.getName(), request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentProfileDto> getProfileById(@PathVariable Long id) {
        return ResponseEntity.ok(studentProfileService.getProfileById(id));
    }
}
