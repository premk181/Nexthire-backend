package com.nexthire.backend.dto;

import com.nexthire.backend.entity.ExperienceLevel;
import com.nexthire.backend.entity.JobType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public class JobCreateRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    private String requirements;

    @NotNull(message = "Job type is required")
    private JobType jobType;

    private String location;

    private boolean isRemote;

    @NotNull(message = "Experience level is required")
    private ExperienceLevel experienceLevel;

    private String salaryRange;

    private String deadline;

    private Set<Long> skillIds;

    public JobCreateRequest() {
    }

    public JobCreateRequest(String title, String description, String requirements, JobType jobType, String location, boolean isRemote, ExperienceLevel experienceLevel, String salaryRange, String deadline, Set<Long> skillIds) {
        this.title = title;
        this.description = description;
        this.requirements = requirements;
        this.jobType = jobType;
        this.location = location;
        this.isRemote = isRemote;
        this.experienceLevel = experienceLevel;
        this.salaryRange = salaryRange;
        this.deadline = deadline;
        this.skillIds = skillIds;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

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

    public Set<Long> getSkillIds() { return skillIds; }
    public void setSkillIds(Set<Long> skillIds) { this.skillIds = skillIds; }
}
