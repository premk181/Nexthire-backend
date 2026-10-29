package com.nexthire.backend.service;

import com.nexthire.backend.dto.JobCreateRequest;
import com.nexthire.backend.dto.JobDto;
import com.nexthire.backend.entity.*;
import com.nexthire.backend.exception.BadRequestException;
import com.nexthire.backend.exception.ResourceNotFoundException;
import com.nexthire.backend.exception.UnauthorizedException;
import com.nexthire.backend.repository.*;
import com.nexthire.backend.util.SkillMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final SavedJobRepository savedJobRepository;
    private final SkillRepository skillRepository;
    private final CompanyService companyService;
    private final SkillMatcher skillMatcher;

    public JobService(JobRepository jobRepository,
                      UserRepository userRepository,
                      RecruiterProfileRepository recruiterProfileRepository,
                      StudentProfileRepository studentProfileRepository,
                      SavedJobRepository savedJobRepository,
                      SkillRepository skillRepository,
                      CompanyService companyService,
                      SkillMatcher skillMatcher) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.studentProfileRepository = studentProfileRepository;
        this.savedJobRepository = savedJobRepository;
        this.skillRepository = skillRepository;
        this.companyService = companyService;
        this.skillMatcher = skillMatcher;
    }

    public List<JobDto> getAllJobs(String search, String location, JobType jobType, ExperienceLevel experienceLevel, String userEmail) {
        List<Job> jobs = jobRepository.searchJobs(search, location, jobType, experienceLevel);

        StudentProfile studentProfile = null;
        if (userEmail != null) {
            studentProfile = studentProfileRepository.findByUserEmail(userEmail).orElse(null);
        }

        final StudentProfile currentStudent = studentProfile;

        return jobs.stream()
                .map(job -> mapToDto(job, currentStudent))
                .collect(Collectors.toList());
    }

    public JobDto getJobById(Long id, String userEmail) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with ID: " + id));

        StudentProfile studentProfile = null;
        if (userEmail != null) {
            studentProfile = studentProfileRepository.findByUserEmail(userEmail).orElse(null);
        }

        return mapToDto(job, studentProfile);
    }

    @Transactional
    public JobDto createJob(String recruiterEmail, JobCreateRequest request) {
        RecruiterProfile recruiterProfile = recruiterProfileRepository.findByUserEmail(recruiterEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Recruiter profile not found for email: " + recruiterEmail));

        if (recruiterProfile.getCompany() == null) {
            throw new BadRequestException("You must create or associate a Company Profile before posting jobs.");
        }

        Set<Skill> requiredSkills = new HashSet<>();
        if (request.getSkillIds() != null && !request.getSkillIds().isEmpty()) {
            requiredSkills.addAll(skillRepository.findAllById(request.getSkillIds()));
        }

        Job job = Job.builder()
                .title(request.getTitle())
                .company(recruiterProfile.getCompany())
                .recruiter(recruiterProfile.getUser())
                .description(request.getDescription())
                .requirements(request.getRequirements())
                .jobType(request.getJobType())
                .location(request.getLocation())
                .isRemote(request.isRemote())
                .experienceLevel(request.getExperienceLevel())
                .salaryRange(request.getSalaryRange())
                .deadline(request.getDeadline())
                .status(JobStatus.ACTIVE)
                .requiredSkills(requiredSkills)
                .build();

        job = jobRepository.save(job);
        return mapToDto(job, null);
    }

    @Transactional
    public JobDto updateJob(Long id, String recruiterEmail, JobCreateRequest request) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with ID: " + id));

        User recruiter = userRepository.findByEmail(recruiterEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!job.getRecruiter().getId().equals(recruiter.getId()) && recruiter.getRole() != Role.ROLE_ADMIN) {
            throw new UnauthorizedException("You are not authorized to edit this job posting.");
        }

        if (request.getTitle() != null) job.setTitle(request.getTitle());
        if (request.getDescription() != null) job.setDescription(request.getDescription());
        if (request.getRequirements() != null) job.setRequirements(request.getRequirements());
        if (request.getJobType() != null) job.setJobType(request.getJobType());
        if (request.getLocation() != null) job.setLocation(request.getLocation());
        job.setRemote(request.isRemote());
        if (request.getExperienceLevel() != null) job.setExperienceLevel(request.getExperienceLevel());
        if (request.getSalaryRange() != null) job.setSalaryRange(request.getSalaryRange());
        if (request.getDeadline() != null) job.setDeadline(request.getDeadline());

        if (request.getSkillIds() != null) {
            Set<Skill> requiredSkills = new HashSet<>(skillRepository.findAllById(request.getSkillIds()));
            job.setRequiredSkills(requiredSkills);
        }

        job = jobRepository.save(job);
        return mapToDto(job, null);
    }

    @Transactional
    public void deleteJob(Long id, String recruiterEmail) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with ID: " + id));

        User user = userRepository.findByEmail(recruiterEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!job.getRecruiter().getId().equals(user.getId()) && user.getRole() != Role.ROLE_ADMIN) {
            throw new UnauthorizedException("You are not authorized to delete this job posting.");
        }

        jobRepository.delete(job);
    }

    public List<JobDto> getJobsByRecruiter(String recruiterEmail) {
        User recruiter = userRepository.findByEmail(recruiterEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Recruiter not found"));

        List<Job> jobs = jobRepository.findByRecruiterId(recruiter.getId());
        return jobs.stream()
                .map(job -> mapToDto(job, null))
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveJob(String studentEmail, Long jobId) {
        StudentProfile student = studentProfileRepository.findByUserEmail(studentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not found"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with ID: " + jobId));

        if (!savedJobRepository.existsByStudentProfileIdAndJobId(student.getId(), jobId)) {
            SavedJob savedJob = SavedJob.builder()
                    .studentProfile(student)
                    .job(job)
                    .build();
            savedJobRepository.save(savedJob);
        }
    }

    @Transactional
    public void unsaveJob(String studentEmail, Long jobId) {
        StudentProfile student = studentProfileRepository.findByUserEmail(studentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not found"));

        savedJobRepository.deleteByStudentProfileIdAndJobId(student.getId(), jobId);
    }

    public List<JobDto> getSavedJobs(String studentEmail) {
        StudentProfile student = studentProfileRepository.findByUserEmail(studentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not found"));

        List<SavedJob> savedJobs = savedJobRepository.findByStudentProfileIdOrderBySavedAtDesc(student.getId());
        return savedJobs.stream()
                .map(sj -> {
                    JobDto dto = mapToDto(sj.getJob(), student);
                    dto.setSaved(true);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public JobDto mapToDto(Job job, StudentProfile studentProfile) {
        Integer matchPercentage = null;
        boolean isSaved = false;

        if (studentProfile != null) {
            matchPercentage = skillMatcher.calculateMatchPercentage(studentProfile.getSkills(), job.getRequiredSkills());
            isSaved = savedJobRepository.existsByStudentProfileIdAndJobId(studentProfile.getId(), job.getId());
        }

        return JobDto.builder()
                .id(job.getId())
                .title(job.getTitle())
                .company(companyService.mapToDto(job.getCompany()))
                .recruiterId(job.getRecruiter().getId())
                .recruiterName(job.getRecruiter().getName())
                .description(job.getDescription())
                .requirements(job.getRequirements())
                .jobType(job.getJobType())
                .location(job.getLocation())
                .isRemote(job.isRemote())
                .experienceLevel(job.getExperienceLevel())
                .salaryRange(job.getSalaryRange())
                .deadline(job.getDeadline())
                .status(job.getStatus())
                .requiredSkills(job.getRequiredSkills())
                .createdAt(job.getCreatedAt())
                .matchPercentage(matchPercentage)
                .isSaved(isSaved)
                .build();
    }
}
