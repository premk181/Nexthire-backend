package com.nexthire.backend.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "student_profiles")
public class StudentProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String headline;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(columnDefinition = "TEXT")
    private String educationJson;

    @Column(columnDefinition = "TEXT")
    private String experienceJson;

    @Column(columnDefinition = "TEXT")
    private String projectsJson;

    private String resumeUrl;

    private String githubUrl;

    private String linkedinUrl;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "student_skills",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills = new HashSet<>();

    public StudentProfile() {
    }

    public StudentProfile(Long id, User user, String headline, String bio, String educationJson, String experienceJson, String projectsJson, String resumeUrl, String githubUrl, String linkedinUrl, Set<Skill> skills) {
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
        this.skills = skills != null ? skills : new HashSet<>();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

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

    public static StudentProfileBuilder builder() {
        return new StudentProfileBuilder();
    }

    public static class StudentProfileBuilder {
        private Long id;
        private User user;
        private String headline;
        private String bio;
        private String educationJson;
        private String experienceJson;
        private String projectsJson;
        private String resumeUrl;
        private String githubUrl;
        private String linkedinUrl;
        private Set<Skill> skills = new HashSet<>();

        public StudentProfileBuilder id(Long id) { this.id = id; return this; }
        public StudentProfileBuilder user(User user) { this.user = user; return this; }
        public StudentProfileBuilder headline(String headline) { this.headline = headline; return this; }
        public StudentProfileBuilder bio(String bio) { this.bio = bio; return this; }
        public StudentProfileBuilder educationJson(String educationJson) { this.educationJson = educationJson; return this; }
        public StudentProfileBuilder experienceJson(String experienceJson) { this.experienceJson = experienceJson; return this; }
        public StudentProfileBuilder projectsJson(String projectsJson) { this.projectsJson = projectsJson; return this; }
        public StudentProfileBuilder resumeUrl(String resumeUrl) { this.resumeUrl = resumeUrl; return this; }
        public StudentProfileBuilder githubUrl(String githubUrl) { this.githubUrl = githubUrl; return this; }
        public StudentProfileBuilder linkedinUrl(String linkedinUrl) { this.linkedinUrl = linkedinUrl; return this; }
        public StudentProfileBuilder skills(Set<Skill> skills) { this.skills = skills; return this; }

        public StudentProfile build() {
            return new StudentProfile(id, user, headline, bio, educationJson, experienceJson, projectsJson, resumeUrl, githubUrl, linkedinUrl, skills);
        }
    }
}
