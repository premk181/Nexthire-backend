package com.nexthire.backend.dto;

public class RecruiterProfileDto {

    private Long id;
    private UserDto user;
    private String designation;
    private String department;
    private CompanyDto company;

    public RecruiterProfileDto() {
    }

    public RecruiterProfileDto(Long id, UserDto user, String designation, String department, CompanyDto company) {
        this.id = id;
        this.user = user;
        this.designation = designation;
        this.department = department;
        this.company = company;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UserDto getUser() { return user; }
    public void setUser(UserDto user) { this.user = user; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public CompanyDto getCompany() { return company; }
    public void setCompany(CompanyDto company) { this.company = company; }

    public static RecruiterProfileDtoBuilder builder() {
        return new RecruiterProfileDtoBuilder();
    }

    public static class RecruiterProfileDtoBuilder {
        private Long id;
        private UserDto user;
        private String designation;
        private String department;
        private CompanyDto company;

        public RecruiterProfileDtoBuilder id(Long id) { this.id = id; return this; }
        public RecruiterProfileDtoBuilder user(UserDto user) { this.user = user; return this; }
        public RecruiterProfileDtoBuilder designation(String designation) { this.designation = designation; return this; }
        public RecruiterProfileDtoBuilder department(String department) { this.department = department; return this; }
        public RecruiterProfileDtoBuilder company(CompanyDto company) { this.company = company; return this; }

        public RecruiterProfileDto build() {
            return new RecruiterProfileDto(id, user, designation, department, company);
        }
    }
}
