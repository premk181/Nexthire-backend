package com.nexthire.backend.service;

import com.nexthire.backend.dto.AuthResponse;
import com.nexthire.backend.dto.LoginRequest;
import com.nexthire.backend.dto.RegisterRequest;
import com.nexthire.backend.entity.*;
import com.nexthire.backend.exception.BadRequestException;
import com.nexthire.backend.exception.ResourceNotFoundException;
import com.nexthire.backend.exception.UnauthorizedException;
import com.nexthire.backend.repository.*;
import com.nexthire.backend.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       StudentProfileRepository studentProfileRepository,
                       RecruiterProfileRepository recruiterProfileRepository,
                       CompanyRepository companyRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.studentProfileRepository = studentProfileRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email is already registered!");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail().toLowerCase().trim())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .phone(request.getPhone())
                .enabled(true)
                .build();

        user = userRepository.save(user);

        Long studentProfileId = null;
        Long companyId = null;

        if (request.getRole() == Role.ROLE_STUDENT) {
            StudentProfile profile = StudentProfile.builder()
                    .user(user)
                    .headline("Student / Job Seeker")
                    .build();
            profile = studentProfileRepository.save(profile);
            studentProfileId = profile.getId();
        } else if (request.getRole() == Role.ROLE_RECRUITER) {
            Company company = null;
            if (request.getCompanyName() != null && !request.getCompanyName().isBlank()) {
                company = companyRepository.findByName(request.getCompanyName())
                        .orElseGet(() -> companyRepository.save(Company.builder()
                                .name(request.getCompanyName())
                                .description("Company profile for " + request.getCompanyName())
                                .isVerified(true)
                                .build()));
                companyId = company.getId();
            }

            RecruiterProfile recruiterProfile = RecruiterProfile.builder()
                    .user(user)
                    .designation(request.getDesignation() != null ? request.getDesignation() : "Recruiter")
                    .company(company)
                    .build();
            recruiterProfileRepository.save(recruiterProfile);
        }

        String token = jwtUtil.generateTokenFromEmail(user.getEmail());

        return AuthResponse.builder()
                .token(token)
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .studentProfileId(studentProfileId)
                .companyId(companyId)
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail().toLowerCase().trim())
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));

        if (!user.isEnabled()) {
            throw new UnauthorizedException("Account is disabled. Please contact administrator.");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid email or password");
        }

        Long studentProfileId = null;
        Long companyId = null;

        if (user.getRole() == Role.ROLE_STUDENT) {
            studentProfileId = studentProfileRepository.findByUserId(user.getId())
                    .map(StudentProfile::getId)
                    .orElse(null);
        } else if (user.getRole() == Role.ROLE_RECRUITER) {
            companyId = recruiterProfileRepository.findByUserId(user.getId())
                    .map(rp -> rp.getCompany() != null ? rp.getCompany().getId() : null)
                    .orElse(null);
        }

        String token = jwtUtil.generateTokenFromEmail(user.getEmail());

        return AuthResponse.builder()
                .token(token)
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .studentProfileId(studentProfileId)
                .companyId(companyId)
                .build();
    }

    public AuthResponse getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        Long studentProfileId = null;
        Long companyId = null;

        if (user.getRole() == Role.ROLE_STUDENT) {
            studentProfileId = studentProfileRepository.findByUserId(user.getId())
                    .map(StudentProfile::getId)
                    .orElse(null);
        } else if (user.getRole() == Role.ROLE_RECRUITER) {
            companyId = recruiterProfileRepository.findByUserId(user.getId())
                    .map(rp -> rp.getCompany() != null ? rp.getCompany().getId() : null)
                    .orElse(null);
        }

        return AuthResponse.builder()
                .token(null)
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .studentProfileId(studentProfileId)
                .companyId(companyId)
                .build();
    }
}
