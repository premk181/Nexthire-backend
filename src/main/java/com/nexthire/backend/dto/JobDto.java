package com.nexthire.backend.dto;

import com.nexthire.backend.entity.ExperienceLevel;
import com.nexthire.backend.entity.JobStatus;
import com.nexthire.backend.entity.JobType;
import com.nexthire.backend.entity.Skill;

import java.time.LocalDateTime;
import java.util.Set;

public class JobDto {

    private Long id;
    private String title;
    private CompanyDto company;
    private Long recruiterId;
    private String recruiterName;
    private String description;
    private String requirements;
    private JobType jobType;
    private String location;
    private boolean isRemote;
    private ExperienceLevel experienceLevel;
    private String salaryRange;
    private String deadline;
    private JobStatus status;
    private Set<Skill> requiredSkills;
    private LocalDateTime createdAt;
    private Integer matchPercentage;
    private boolean isSaved;

    public JobDto() {
    }

    public JobDto(Long id, String title, CompanyDto company, Long recruiterId, String recruiterName, String description, String requirements, JobType jobType, String location, boolean isRemote, ExperienceLevel experienceLevel, String salaryRange, String deadline, JobStatus status, Set<Skill> requiredSkills, LocalDateTime createdAt, Integer matchPercentage, boolean isSaved) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.recruiterId = recruiterId;
        this.recruiterName = recruiterName;
        this.description = description;
        this.requirements = requirements;
        this.jobType = jobType;
        this.location = location;
        this.isRemote = isRemote;
        this.experienceLevel = experienceLevel;
        this.salaryRange = salaryRange;
        this.deadline = deadline;
        this.status = status;
        this.requiredSkills = requiredSkills;
        this.createdAt = createdAt;
        this.matchPercentage = matchPercentage;
        this.isSaved = isSaved;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public CompanyDto getCompany() { return company; }
    public void setCompany(CompanyDto company) { this.company = company; }

    public Long getRecruiterId() { return recruiterId; }
    public void setRecruiterId(Long recruiterId) { this.recruiterId = recruiterId; }

    public String getRecruiterName() { return recruiterName; }
    public void setRecruiterName(String recruiterName) { this.recruiterName = recruiterName; }

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

    public Integer getMatchPercentage() { return matchPercentage; }
    public void setMatchPercentage(Integer matchPercentage) { this.matchPercentage = matchPercentage; }

    public boolean isSaved() { return isSaved; }
    public void setSaved(boolean saved) { isSaved = saved; }

    public static JobDtoBuilder builder() {
        return new JobDtoBuilder();
    }

    public static class JobDtoBuilder {
        private Long id;
        private String title;
        private CompanyDto company;
        private Long recruiterId;
        private String recruiterName;
        private String description;
        private String requirements;
        private JobType jobType;
        private String location;
        private boolean isRemote;
        private ExperienceLevel experienceLevel;
        private String salaryRange;
        private String deadline;
        private JobStatus status;
        private Set<Skill> requiredSkills;
        private LocalDateTime createdAt;
        private Integer matchPercentage;
        private boolean isSaved;

        public JobDtoBuilder id(Long id) { this.id = id; return this; }
        public JobDtoBuilder title(String title) { this.title = title; return this; }
        public JobDtoBuilder company(CompanyDto company) { this.company = company; return this; }
        public JobDtoBuilder recruiterId(Long recruiterId) { this.recruiterId = recruiterId; return this; }
        public JobDtoBuilder recruiterName(String recruiterName) { this.recruiterName = recruiterName; return this; }
        public JobDtoBuilder description(String description) { this.description = description; return this; }
        public JobDtoBuilder requirements(String requirements) { this.requirements = requirements; return this; }
        public JobDtoBuilder jobType(JobType jobType) { this.jobType = jobType; return this; }
        public JobDtoBuilder location(String location) { this.location = location; return this; }
        public JobDtoBuilder isRemote(boolean isRemote) { this.isRemote = isRemote; return this; }
        public JobDtoBuilder experienceLevel(ExperienceLevel experienceLevel) { this.experienceLevel = experienceLevel; return this; }
        public JobDtoBuilder salaryRange(String salaryRange) { this.salaryRange = salaryRange; return this; }
        public JobDtoBuilder deadline(String deadline) { this.deadline = deadline; return this; }
        public JobDtoBuilder status(JobStatus status) { this.status = status; return this; }
        public JobDtoBuilder requiredSkills(Set<Skill> requiredSkills) { this.requiredSkills = requiredSkills; return this; }
        public JobDtoBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public JobDtoBuilder matchPercentage(Integer matchPercentage) { this.matchPercentage = matchPercentage; return this; }
        public JobDtoBuilder isSaved(boolean isSaved) { this.isSaved = isSaved; return this; }

        public JobDto build() {
            return new JobDto(id, title, company, recruiterId, recruiterName, description, requirements, jobType, location, isRemote, experienceLevel, salaryRange, deadline, status, requiredSkills, createdAt, matchPercentage, isSaved);
        }
    }
}
