package com.nexthire.backend.dto;

import com.nexthire.backend.entity.Skill;

import java.util.Set;

public class StudentProfileDto {

    private Long id;
    private UserDto user;
    private String headline;
    private String bio;
    private String educationJson;
    private String experienceJson;
    private String projectsJson;
    private String resumeUrl;
    private String githubUrl;
    private String linkedinUrl;
    private Set<Skill> skills;

    public StudentProfileDto() {
    }

    public StudentProfileDto(Long id, UserDto user, String headline, String bio, String educationJson, String experienceJson, String projectsJson, String resumeUrl, String githubUrl, String linkedinUrl, Set<Skill> skills) {
        this.id = id;
        this.user = user;
        this.headline = headline;
        this.bio = bio;
        this.educationJson = educationJson;
        this.experienceJson = experienceJson;
        this.projectsJson = projectsJson;
        this.resumeUrl = resumeUrl;
        this.githubUrl = githubUrl;
        this.linkedinUrl = linkedinUrl;
        this.skills = skills;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UserDto getUser() { return user; }
    public void setUser(UserDto user) { this.user = user; }

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

    public Set<Skill> getSkills() { return skills; }
    public void setSkills(Set<Skill> skills) { this.skills = skills; }

    public static StudentProfileDtoBuilder builder() {
        return new StudentProfileDtoBuilder();
    }

    public static class StudentProfileDtoBuilder {
        private Long id;
        private UserDto user;
        private String headline;
        private String bio;
        private String educationJson;
        private String experienceJson;
        private String projectsJson;
        private String resumeUrl;
        private String githubUrl;
        private String linkedinUrl;
        private Set<Skill> skills;

        public StudentProfileDtoBuilder id(Long id) { this.id = id; return this; }
        public StudentProfileDtoBuilder user(UserDto user) { this.user = user; return this; }
        public StudentProfileDtoBuilder headline(String headline) { this.headline = headline; return this; }
        public StudentProfileDtoBuilder bio(String bio) { this.bio = bio; return this; }
        public StudentProfileDtoBuilder educationJson(String educationJson) { this.educationJson = educationJson; return this; }
        public StudentProfileDtoBuilder experienceJson(String experienceJson) { this.experienceJson = experienceJson; return this; }
        public StudentProfileDtoBuilder projectsJson(String projectsJson) { this.projectsJson = projectsJson; return this; }
        public StudentProfileDtoBuilder resumeUrl(String resumeUrl) { this.resumeUrl = resumeUrl; return this; }
        public StudentProfileDtoBuilder githubUrl(String githubUrl) { this.githubUrl = githubUrl; return this; }
        public StudentProfileDtoBuilder linkedinUrl(String linkedinUrl) { this.linkedinUrl = linkedinUrl; return this; }
        public StudentProfileDtoBuilder skills(Set<Skill> skills) { this.skills = skills; return this; }

        public StudentProfileDto build() {
            return new StudentProfileDto(id, user, headline, bio, educationJson, experienceJson, projectsJson, resumeUrl, githubUrl, linkedinUrl, skills);
        }
    }
}
