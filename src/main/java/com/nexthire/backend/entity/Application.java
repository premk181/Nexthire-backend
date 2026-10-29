package com.nexthire.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "applications", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"job_id", "student_id"})
})
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    private StudentProfile studentProfile;

    private String resumeUrl;

    @Column(columnDefinition = "TEXT")
    private String coverLetter;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status = ApplicationStatus.APPLIED;

    private LocalDateTime appliedAt;

    private LocalDateTime updatedAt;

    private String notes;

    public Application() {
    }

    public Application(Long id, Job job, StudentProfile studentProfile, String resumeUrl, String coverLetter, ApplicationStatus status, LocalDateTime appliedAt, LocalDateTime updatedAt, String notes) {
        this.id = id;
        this.job = job;
        this.studentProfile = studentProfile;
        this.resumeUrl = resumeUrl;
        this.coverLetter = coverLetter;
        this.status = status != null ? status : ApplicationStatus.APPLIED;
        this.appliedAt = appliedAt;
        this.updatedAt = updatedAt;
        this.notes = notes;
    }

    @PrePersist
    protected void onCreate() {
        appliedAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Job getJob() { return job; }
    public void setJob(Job job) { this.job = job; }

    public StudentProfile getStudentProfile() { return studentProfile; }
    public void setStudentProfile(StudentProfile studentProfile) { this.studentProfile = studentProfile; }

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

    public static ApplicationBuilder builder() {
        return new ApplicationBuilder();
    }

    public static class ApplicationBuilder {
        private Long id;
        private Job job;
        private StudentProfile studentProfile;
        private String resumeUrl;
        private String coverLetter;
        private ApplicationStatus status = ApplicationStatus.APPLIED;
        private LocalDateTime appliedAt;
        private LocalDateTime updatedAt;
        private String notes;

        public ApplicationBuilder id(Long id) { this.id = id; return this; }
        public ApplicationBuilder job(Job job) { this.job = job; return this; }
        public ApplicationBuilder studentProfile(StudentProfile studentProfile) { this.studentProfile = studentProfile; return this; }
        public ApplicationBuilder resumeUrl(String resumeUrl) { this.resumeUrl = resumeUrl; return this; }
        public ApplicationBuilder coverLetter(String coverLetter) { this.coverLetter = coverLetter; return this; }
        public ApplicationBuilder status(ApplicationStatus status) { this.status = status; return this; }
        public ApplicationBuilder appliedAt(LocalDateTime appliedAt) { this.appliedAt = appliedAt; return this; }
        public ApplicationBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
        public ApplicationBuilder notes(String notes) { this.notes = notes; return this; }

        public Application build() {
            return new Application(id, job, studentProfile, resumeUrl, coverLetter, status, appliedAt, updatedAt, notes);
        }
    }
}
