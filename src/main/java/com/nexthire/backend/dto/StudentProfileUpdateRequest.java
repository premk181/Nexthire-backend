package com.nexthire.backend.dto;

import java.util.Set;

public class StudentProfileUpdateRequest {

    private String name;
    private String phone;
    private String headline;
    private String bio;
    private String educationJson;
    private String experienceJson;
    private String projectsJson;
    private String resumeUrl;
    private String githubUrl;
    private String linkedinUrl;
    private Set<Long> skillIds;

    public StudentProfileUpdateRequest() {
    }

    public StudentProfileUpdateRequest(String name, String phone, String headline, String bio, String educationJson, String experienceJson, String projectsJson, String resumeUrl, String githubUrl, String linkedinUrl, Set<Long> skillIds) {
        this.name = name;
        this.phone = phone;
        this.headline = headline;
        this.bio = bio;
        this.educationJson = educationJson;
        this.experienceJson = experienceJson;
        this.projectsJson = projectsJson;
        this.resumeUrl = resumeUrl;
        this.githubUrl = githubUrl;
        this.linkedinUrl = linkedinUrl;
        this.skillIds = skillIds;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getHeadline() { return headline; }
    public void setHeadline(String headline) { this.headline = headline; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getEducationJson() { return educationJson; }
    public void setEducationJson(String educationJson) { this.educationJson = educationJson; }

    public String getExperienceJson() { return experienceJson; }
    public void setExperienceJson(String experienceJson) { this.experienceJson = experienceJson; }

    public String getProjectsJson() { return projectsJson; }
    public void setProjectsJson(String projectsJson) { this.projectsJson = projectsJson; }

    public String getResumeUrl() { return resumeUrl; }
    public void setResumeUrl(String resumeUrl) { this.resumeUrl = resumeUrl; }

    public String getGithubUrl() { return githubUrl; }
    public void setGithubUrl(String githubUrl) { this.githubUrl = githubUrl; }

    public String getLinkedinUrl() { return linkedinUrl; }
    public void setLinkedinUrl(String linkedinUrl) { this.linkedinUrl = linkedinUrl; }

    public Set<Long> getSkillIds() { return skillIds; }
    public void setSkillIds(Set<Long> skillIds) { this.skillIds = skillIds; }
}
