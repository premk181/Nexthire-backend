package com.nexthire.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recruiter_id", nullable = false)
    private User recruiter;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(columnDefinition = "TEXT")
    private String requirements;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobType jobType;

    private String location;

    private boolean isRemote = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExperienceLevel experienceLevel;

    private String salaryRange;

    private String deadline;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobStatus status = JobStatus.ACTIVE;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "job_skills",
        joinColumns = @JoinColumn(name = "job_id"),
        inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> requiredSkills = new HashSet<>();

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Job() {
    }

    public Job(Long id, String title, Company company, User recruiter, String description, String requirements, JobType jobType, String location, boolean isRemote, ExperienceLevel experienceLevel, String salaryRange, String deadline, JobStatus status, Set<Skill> requiredSkills, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.recruiter = recruiter;
        this.description = description;
        this.requirements = requirements;
        this.jobType = jobType;
        this.location = location;
        this.isRemote = isRemote;
        this.experienceLevel = experienceLevel;
        this.salaryRange = salaryRange;
        this.deadline = deadline;
        this.status = status != null ? status : JobStatus.ACTIVE;
        this.requiredSkills = requiredSkills != null ? requiredSkills : new HashSet<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }

    public User getRecruiter() { return recruiter; }
    public void setRecruiter(User recruiter) { this.recruiter = recruiter; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getRequirements() { return requirements; }
    public void setRequirements(String requirements) { this.requirements = requirements; }

    public JobType getJobType() { return jobType; }
    public void setJobType(JobType jobType) { this.jobType = jobType; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public boolean isRemote() { return isRemote; }
    public void setRemote(boolean remote) { isRemote = remote; }

    public ExperienceLevel getExperienceLevel() { return experienceLevel; }
    public void setExperienceLevel(ExperienceLevel experienceLevel) { this.experienceLevel = experienceLevel; }

    public String getSalaryRange() { return salaryRange; }
    public void setSalaryRange(String salaryRange) { this.salaryRange = salaryRange; }

    public String getDeadline() { return deadline; }
    public void setDeadline(String deadline) { this.deadline = deadline; }

    public JobStatus getStatus() { return status; }
    public void setStatus(JobStatus status) { this.status = status; }

    public Set<Skill> getRequiredSkills() { return requiredSkills; }
    public void setRequiredSkills(Set<Skill> requiredSkills) { this.requiredSkills = requiredSkills; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public static JobBuilder builder() {
        return new JobBuilder();
    }

    public static class JobBuilder {
        private Long id;
        private String title;
        private Company company;
        private User recruiter;
        private String description;
        private String requirements;
        private JobType jobType;
        private String location;
        private boolean isRemote = false;
        private ExperienceLevel experienceLevel;
        private String salaryRange;
        private String deadline;
        private JobStatus status = JobStatus.ACTIVE;
        private Set<Skill> requiredSkills = new HashSet<>();
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public JobBuilder id(Long id) { this.id = id; return this; }
        public JobBuilder title(String title) { this.title = title; return this; }
        public JobBuilder company(Company company) { this.company = company; return this; }
        public JobBuilder recruiter(User recruiter) { this.recruiter = recruiter; return this; }
        public JobBuilder description(String description) { this.description = description; return this; }
        public JobBuilder requirements(String requirements) { this.requirements = requirements; return this; }
        public JobBuilder jobType(JobType jobType) { this.jobType = jobType; return this; }
        public JobBuilder location(String location) { this.location = location; return this; }
        public JobBuilder isRemote(boolean isRemote) { this.isRemote = isRemote; return this; }
        public JobBuilder experienceLevel(ExperienceLevel experienceLevel) { this.experienceLevel = experienceLevel; return this; }
        public JobBuilder salaryRange(String salaryRange) { this.salaryRange = salaryRange; return this; }
        public JobBuilder deadline(String deadline) { this.deadline = deadline; return this; }
        public JobBuilder status(JobStatus status) { this.status = status; return this; }
        public JobBuilder requiredSkills(Set<Skill> requiredSkills) { this.requiredSkills = requiredSkills; return this; }
        public JobBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public JobBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Job build() {
            return new Job(id, title, company, recruiter, description, requirements, jobType, location, isRemote, experienceLevel, salaryRange, deadline, status, requiredSkills, createdAt, updatedAt);
        }
    }
}
