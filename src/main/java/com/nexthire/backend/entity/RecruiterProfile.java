package com.nexthire.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "recruiter_profiles")
public class RecruiterProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String designation;

    private String department;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    public RecruiterProfile() {
    }

    public RecruiterProfile(Long id, User user, String designation, String department, Company company) {
        this.id = id;
        this.user = user;
        this.designation = designation;
        this.department = department;
        this.company = company;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }

    public static RecruiterProfileBuilder builder() {
        return new RecruiterProfileBuilder();
    }

    public static class RecruiterProfileBuilder {
        private Long id;
        private User user;
        private String designation;
        private String department;
        private Company company;

        public RecruiterProfileBuilder id(Long id) { this.id = id; return this; }
        public RecruiterProfileBuilder user(User user) { this.user = user; return this; }
        public RecruiterProfileBuilder designation(String designation) { this.designation = designation; return this; }
        public RecruiterProfileBuilder department(String department) { this.department = department; return this; }
        public RecruiterProfileBuilder company(Company company) { this.company = company; return this; }

        public RecruiterProfile build() {
            return new RecruiterProfile(id, user, designation, department, company);
        }
    }
}
