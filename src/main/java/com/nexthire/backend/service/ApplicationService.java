package com.nexthire.backend.service;

import com.nexthire.backend.dto.ApplicationCreateRequest;
import com.nexthire.backend.dto.ApplicationDto;
import com.nexthire.backend.dto.ApplicationStatusUpdateRequest;
import com.nexthire.backend.entity.*;
import com.nexthire.backend.exception.BadRequestException;
import com.nexthire.backend.exception.ResourceNotFoundException;
import com.nexthire.backend.exception.UnauthorizedException;
import com.nexthire.backend.repository.*;
import com.nexthire.backend.util.SkillMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final UserRepository userRepository;
    private final StudentProfileService studentProfileService;
    private final JobService jobService;
    private final SkillMatcher skillMatcher;

    public ApplicationService(ApplicationRepository applicationRepository,
                              JobRepository jobRepository,
                              StudentProfileRepository studentProfileRepository,
                              UserRepository userRepository,
                              StudentProfileService studentProfileService,
                              JobService jobService,
                              SkillMatcher skillMatcher) {
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.studentProfileRepository = studentProfileRepository;
        this.userRepository = userRepository;
        this.studentProfileService = studentProfileService;
        this.jobService = jobService;
        this.skillMatcher = skillMatcher;
    }

    @Transactional
    public ApplicationDto applyForJob(String studentEmail, ApplicationCreateRequest request) {
        StudentProfile student = studentProfileRepository.findByUserEmail(studentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not found for email: " + studentEmail));

        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with ID: " + request.getJobId()));

        if (job.getStatus() != JobStatus.ACTIVE) {
            throw new BadRequestException("Cannot apply to closed or archived job posting.");
        }

        if (applicationRepository.existsByJobIdAndStudentProfileId(job.getId(), student.getId())) {
            throw new BadRequestException("You have already submitted an application for this opportunity.");
        }

        String resumeUrl = request.getResumeUrl() != null && !request.getResumeUrl().isBlank()
                ? request.getResumeUrl()
                : student.getResumeUrl();

        Application application = Application.builder()
                .job(job)
                .studentProfile(student)
                .resumeUrl(resumeUrl)
                .coverLetter(request.getCoverLetter())
                .status(ApplicationStatus.APPLIED)
                .build();

        application = applicationRepository.save(application);
        return mapToDto(application);
    }

    public List<ApplicationDto> getStudentApplications(String studentEmail) {
        StudentProfile student = studentProfileRepository.findByUserEmail(studentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not found"));

        List<Application> applications = applicationRepository.findByStudentProfileIdOrderByAppliedAtDesc(student.getId());
        return applications.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<ApplicationDto> getJobApplicants(Long jobId, String recruiterEmail) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with ID: " + jobId));

        User recruiter = userRepository.findByEmail(recruiterEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!job.getRecruiter().getId().equals(recruiter.getId()) && recruiter.getRole() != Role.ROLE_ADMIN) {
            throw new UnauthorizedException("You are not authorized to view applicants for this job.");
        }

        List<Application> applications = applicationRepository.findByJobIdOrderByAppliedAtDesc(jobId);
        return applications.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ApplicationDto updateApplicationStatus(Long applicationId, String recruiterEmail, ApplicationStatusUpdateRequest request) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with ID: " + applicationId));

        User recruiter = userRepository.findByEmail(recruiterEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!application.getJob().getRecruiter().getId().equals(recruiter.getId()) && recruiter.getRole() != Role.ROLE_ADMIN) {
            throw new UnauthorizedException("You are not authorized to manage this application.");
        }

        application.setStatus(request.getStatus());
        if (request.getNotes() != null) {
            application.setNotes(request.getNotes());
        }

        application = applicationRepository.save(application);
        return mapToDto(application);
    }

    @Transactional
    public void withdrawApplication(Long applicationId, String studentEmail) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with ID: " + applicationId));

        StudentProfile student = studentProfileRepository.findByUserEmail(studentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not found"));

        if (!application.getStudentProfile().getId().equals(student.getId())) {
            throw new UnauthorizedException("You can only withdraw your own applications.");
        }

        applicationRepository.delete(application);
    }

    public ApplicationDto mapToDto(Application application) {
        StudentProfile student = application.getStudentProfile();
        Job job = application.getJob();

        int matchPercentage = skillMatcher.calculateMatchPercentage(student.getSkills(), job.getRequiredSkills());

        return ApplicationDto.builder()
                .id(application.getId())
                .job(jobService.mapToDto(job, student))
                .studentProfile(studentProfileService.mapToDto(student))
                .resumeUrl(application.getResumeUrl())
                .coverLetter(application.getCoverLetter())
                .status(application.getStatus())
                .appliedAt(application.getAppliedAt())
                .updatedAt(application.getUpdatedAt())
                .notes(application.getNotes())
                .matchPercentage(matchPercentage)
                .build();
    }
}
