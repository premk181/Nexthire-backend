package com.nexthire.backend.service;

import com.nexthire.backend.dto.CompanyDto;
import com.nexthire.backend.dto.RecruiterProfileDto;
import com.nexthire.backend.dto.UserDto;
import com.nexthire.backend.entity.RecruiterProfile;
import com.nexthire.backend.entity.User;
import com.nexthire.backend.exception.ResourceNotFoundException;
import com.nexthire.backend.repository.RecruiterProfileRepository;
import com.nexthire.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecruiterService {

    private final RecruiterProfileRepository recruiterProfileRepository;
    private final UserRepository userRepository;
    private final CompanyService companyService;

    public RecruiterService(RecruiterProfileRepository recruiterProfileRepository,
                            UserRepository userRepository,
                            CompanyService companyService) {
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.userRepository = userRepository;
        this.companyService = companyService;
    }

    public RecruiterProfileDto getProfileByEmail(String email) {
        RecruiterProfile profile = recruiterProfileRepository.findByUserEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Recruiter profile not found for email: " + email));
        return mapToDto(profile);
    }

    @Transactional
    public RecruiterProfileDto updateProfile(String email, String designation, String department) {
        RecruiterProfile profile = recruiterProfileRepository.findByUserEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Recruiter profile not found for email: " + email));

        if (designation != null) profile.setDesignation(designation);
        if (department != null) profile.setDepartment(department);

        profile = recruiterProfileRepository.save(profile);
        return mapToDto(profile);
    }

    public RecruiterProfileDto mapToDto(RecruiterProfile profile) {
        User u = profile.getUser();
        UserDto userDto = UserDto.builder()
                .id(u.getId())
                .name(u.getName())
                .email(u.getEmail())
                .role(u.getRole())
                .phone(u.getPhone())
                .avatarUrl(u.getAvatarUrl())
                .enabled(u.isEnabled())
                .createdAt(u.getCreatedAt())
                .build();

        CompanyDto companyDto = profile.getCompany() != null ? companyService.mapToDto(profile.getCompany()) : null;

        return RecruiterProfileDto.builder()
                .id(profile.getId())
                .user(userDto)
                .designation(profile.getDesignation())
                .department(profile.getDepartment())
                .company(companyDto)
                .build();
    }
}
