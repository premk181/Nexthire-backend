package com.nexthire.backend.service;

import com.nexthire.backend.dto.AdminStatsDto;
import com.nexthire.backend.dto.CompanyDto;
import com.nexthire.backend.dto.JobDto;
import com.nexthire.backend.dto.UserDto;
import com.nexthire.backend.entity.JobStatus;
import com.nexthire.backend.entity.Role;
import com.nexthire.backend.entity.User;
import com.nexthire.backend.exception.ResourceNotFoundException;
import com.nexthire.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final CompanyRepository companyRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final CompanyService companyService;
    private final JobService jobService;

    public AdminService(UserRepository userRepository,
                        StudentProfileRepository studentProfileRepository,
                        RecruiterProfileRepository recruiterProfileRepository,
                        CompanyRepository companyRepository,
                        JobRepository jobRepository,
                        ApplicationRepository applicationRepository,
                        CompanyService companyService,
                        JobService jobService) {
        this.userRepository = userRepository;
        this.studentProfileRepository = studentProfileRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.companyRepository = companyRepository;
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
        this.companyService = companyService;
        this.jobService = jobService;
    }

    public AdminStatsDto getPlatformStats() {
        long totalStudents = userRepository.findByRole(Role.ROLE_STUDENT).size();
        long totalRecruiters = userRepository.findByRole(Role.ROLE_RECRUITER).size();
        long totalCompanies = companyRepository.count();
        long totalJobs = jobRepository.count();
        long activeJobs = jobRepository.findByStatus(JobStatus.ACTIVE).size();
        long totalApplications = applicationRepository.count();

        return AdminStatsDto.builder()
                .totalStudents(totalStudents)
                .totalRecruiters(totalRecruiters)
                .totalCompanies(totalCompanies)
                .totalJobs(totalJobs)
                .activeJobs(activeJobs)
                .totalApplications(totalApplications)
                .build();
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(u -> UserDto.builder()
                        .id(u.getId())
                        .name(u.getName())
                        .email(u.getEmail())
                        .role(u.getRole())
                        .phone(u.getPhone())
                        .avatarUrl(u.getAvatarUrl())
                        .enabled(u.isEnabled())
                        .createdAt(u.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDto toggleUserStatus(Long userId, boolean enabled) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        user.setEnabled(enabled);
        user = userRepository.save(user);

        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .phone(user.getPhone())
                .avatarUrl(user.getAvatarUrl())
                .enabled(user.isEnabled())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public List<CompanyDto> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    public List<JobDto> getAllJobs() {
        return jobRepository.findAll().stream()
                .map(j -> jobService.mapToDto(j, null))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteJob(Long jobId) {
        jobRepository.deleteById(jobId);
    }
}
