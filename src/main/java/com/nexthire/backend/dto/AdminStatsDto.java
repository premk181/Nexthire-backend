package com.nexthire.backend.dto;

public class AdminStatsDto {

    private long totalStudents;
    private long totalRecruiters;
    private long totalCompanies;
    private long totalJobs;
    private long activeJobs;
    private long totalApplications;

    public AdminStatsDto() {
    }

    public AdminStatsDto(long totalStudents, long totalRecruiters, long totalCompanies, long totalJobs, long activeJobs, long totalApplications) {
        this.totalStudents = totalStudents;
        this.totalRecruiters = totalRecruiters;
        this.totalCompanies = totalCompanies;
        this.totalJobs = totalJobs;
        this.activeJobs = activeJobs;
        this.totalApplications = totalApplications;
    }

    public long getTotalStudents() { return totalStudents; }
    public void setTotalStudents(long totalStudents) { this.totalStudents = totalStudents; }

    public long getTotalRecruiters() { return totalRecruiters; }
    public void setTotalRecruiters(long totalRecruiters) { this.totalRecruiters = totalRecruiters; }

    public long getTotalCompanies() { return totalCompanies; }
    public void setTotalCompanies(long totalCompanies) { this.totalCompanies = totalCompanies; }

    public long getTotalJobs() { return totalJobs; }
    public void setTotalJobs(long totalJobs) { this.totalJobs = totalJobs; }

    public long getActiveJobs() { return activeJobs; }
    public void setActiveJobs(long activeJobs) { this.activeJobs = activeJobs; }

    public long getTotalApplications() { return totalApplications; }
    public void setTotalApplications(long totalApplications) { this.totalApplications = totalApplications; }

    public static AdminStatsDtoBuilder builder() {
        return new AdminStatsDtoBuilder();
    }

    public static class AdminStatsDtoBuilder {
        private long totalStudents;
        private long totalRecruiters;
        private long totalCompanies;
        private long totalJobs;
        private long activeJobs;
        private long totalApplications;

        public AdminStatsDtoBuilder totalStudents(long totalStudents) { this.totalStudents = totalStudents; return this; }
        public AdminStatsDtoBuilder totalRecruiters(long totalRecruiters) { this.totalRecruiters = totalRecruiters; return this; }
        public AdminStatsDtoBuilder totalCompanies(long totalCompanies) { this.totalCompanies = totalCompanies; return this; }
        public AdminStatsDtoBuilder totalJobs(long totalJobs) { this.totalJobs = totalJobs; return this; }
        public AdminStatsDtoBuilder activeJobs(long activeJobs) { this.activeJobs = activeJobs; return this; }
        public AdminStatsDtoBuilder totalApplications(long totalApplications) { this.totalApplications = totalApplications; return this; }

        public AdminStatsDto build() {
            return new AdminStatsDto(totalStudents, totalRecruiters, totalCompanies, totalJobs, activeJobs, totalApplications);
        }
    }
}
