package com.nexthire.backend.dto;

import com.nexthire.backend.entity.ApplicationStatus;

import java.time.LocalDateTime;

public class ApplicationDto {

    private Long id;
    private JobDto job;
    private StudentProfileDto studentProfile;
    private String resumeUrl;
    private String coverLetter;
    private ApplicationStatus status;
    private LocalDateTime appliedAt;
    private LocalDateTime updatedAt;
    private String notes;
    private Integer matchPercentage;

    public ApplicationDto() {
    }

    public ApplicationDto(Long id, JobDto job, StudentProfileDto studentProfile, String resumeUrl, String coverLetter, ApplicationStatus status, LocalDateTime appliedAt, LocalDateTime updatedAt, String notes, Integer matchPercentage) {
        this.id = id;
        this.job = job;
        this.studentProfile = studentProfile;
        this.resumeUrl = resumeUrl;
        this.coverLetter = coverLetter;
        this.status = status;
        this.appliedAt = appliedAt;
        this.updatedAt = updatedAt;
        this.notes = notes;
        this.matchPercentage = matchPercentage;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public JobDto getJob() { return job; }
    public void setJob(JobDto job) { this.job = job; }

    public StudentProfileDto getStudentProfile() { return studentProfile; }
    public void setStudentProfile(StudentProfileDto studentProfile) { this.studentProfile = studentProfile; }

    public String getResumeUrl() { return resumeUrl; }
    public void setResumeUrl(String resumeUrl) { this.resumeUrl = resumeUrl; }

    public String getCoverLetter() { return coverLetter; }
    public void setCoverLetter(String coverLetter) { this.coverLetter = coverLetter; }

    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }

    public LocalDateTime getAppliedAt() { return appliedAt; }
    public void setAppliedAt(LocalDateTime appliedAt) { this.appliedAt = appliedAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Integer getMatchPercentage() { return matchPercentage; }
    public void setMatchPercentage(Integer matchPercentage) { this.matchPercentage = matchPercentage; }

    public static ApplicationDtoBuilder builder() {
        return new ApplicationDtoBuilder();
    }

    public static class ApplicationDtoBuilder {
        private Long id;
        private JobDto job;
        private StudentProfileDto studentProfile;
        private String resumeUrl;
        private String coverLetter;
        private ApplicationStatus status;
        private LocalDateTime appliedAt;
        private LocalDateTime updatedAt;
        private String notes;
        private Integer matchPercentage;

        public ApplicationDtoBuilder id(Long id) { this.id = id; return this; }
        public ApplicationDtoBuilder job(JobDto job) { this.job = job; return this; }
        public ApplicationDtoBuilder studentProfile(StudentProfileDto studentProfile) { this.studentProfile = studentProfile; return this; }
        public ApplicationDtoBuilder resumeUrl(String resumeUrl) { this.resumeUrl = resumeUrl; return this; }
        public ApplicationDtoBuilder coverLetter(String coverLetter) { this.coverLetter = coverLetter; return this; }
        public ApplicationDtoBuilder status(ApplicationStatus status) { this.status = status; return this; }
        public ApplicationDtoBuilder appliedAt(LocalDateTime appliedAt) { this.appliedAt = appliedAt; return this; }
        public ApplicationDtoBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
        public ApplicationDtoBuilder notes(String notes) { this.notes = notes; return this; }
        public ApplicationDtoBuilder matchPercentage(Integer matchPercentage) { this.matchPercentage = matchPercentage; return this; }

        public ApplicationDto build() {
            return new ApplicationDto(id, job, studentProfile, resumeUrl, coverLetter, status, appliedAt, updatedAt, notes, matchPercentage);
        }
    }
}
