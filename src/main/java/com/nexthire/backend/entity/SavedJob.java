package com.nexthire.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "saved_jobs", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"student_id", "job_id"})
})
public class SavedJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    private StudentProfile studentProfile;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    private LocalDateTime savedAt;

    public SavedJob() {
    }

    public SavedJob(Long id, StudentProfile studentProfile, Job job, LocalDateTime savedAt) {
        this.id = id;
        this.studentProfile = studentProfile;
        this.job = job;
        this.savedAt = savedAt;
    }

    @PrePersist
    protected void onCreate() {
        savedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public StudentProfile getStudentProfile() { return studentProfile; }
    public void setStudentProfile(StudentProfile studentProfile) { this.studentProfile = studentProfile; }

    public Job getJob() { return job; }
    public void setJob(Job job) { this.job = job; }

    public LocalDateTime getSavedAt() { return savedAt; }
    public void setSavedAt(LocalDateTime savedAt) { this.savedAt = savedAt; }

    public static SavedJobBuilder builder() {
        return new SavedJobBuilder();
    }

    public static class SavedJobBuilder {
        private Long id;
        private StudentProfile studentProfile;
        private Job job;
        private LocalDateTime savedAt;

        public SavedJobBuilder id(Long id) { this.id = id; return this; }
        public SavedJobBuilder studentProfile(StudentProfile studentProfile) { this.studentProfile = studentProfile; return this; }
        public SavedJobBuilder job(Job job) { this.job = job; return this; }
        public SavedJobBuilder savedAt(LocalDateTime savedAt) { this.savedAt = savedAt; return this; }

        public SavedJob build() {
            return new SavedJob(id, studentProfile, job, savedAt);
        }
    }
}
