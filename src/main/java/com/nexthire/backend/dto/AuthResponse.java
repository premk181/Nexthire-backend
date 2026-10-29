package com.nexthire.backend.dto;

import com.nexthire.backend.entity.Role;

public class AuthResponse {

    private String token;
    private String tokenType = "Bearer";
    private Long id;
    private String name;
    private String email;
    private Role role;
    private Long studentProfileId;
    private Long companyId;

    public AuthResponse() {
    }

    public AuthResponse(String token, String tokenType, Long id, String name, String email, Role role, Long studentProfileId, Long companyId) {
        this.token = token;
        this.tokenType = tokenType != null ? tokenType : "Bearer";
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.studentProfileId = studentProfileId;
        this.companyId = companyId;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public Long getStudentProfileId() { return studentProfileId; }
    public void setStudentProfileId(Long studentProfileId) { this.studentProfileId = studentProfileId; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public static AuthResponseBuilder builder() {
        return new AuthResponseBuilder();
    }

    public static class AuthResponseBuilder {
        private String token;
        private String tokenType = "Bearer";
        private Long id;
        private String name;
        private String email;
        private Role role;
        private Long studentProfileId;
        private Long companyId;

        public AuthResponseBuilder token(String token) { this.token = token; return this; }
        public AuthResponseBuilder tokenType(String tokenType) { this.tokenType = tokenType; return this; }
        public AuthResponseBuilder id(Long id) { this.id = id; return this; }
        public AuthResponseBuilder name(String name) { this.name = name; return this; }
        public AuthResponseBuilder email(String email) { this.email = email; return this; }
        public AuthResponseBuilder role(Role role) { this.role = role; return this; }
        public AuthResponseBuilder studentProfileId(Long studentProfileId) { this.studentProfileId = studentProfileId; return this; }
        public AuthResponseBuilder companyId(Long companyId) { this.companyId = companyId; return this; }

        public AuthResponse build() {
            return new AuthResponse(token, tokenType, id, name, email, role, studentProfileId, companyId);
        }
    }
}
