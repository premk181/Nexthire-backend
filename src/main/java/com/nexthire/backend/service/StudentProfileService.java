package com.nexthire.backend.service;

import com.nexthire.backend.dto.StudentProfileDto;
import com.nexthire.backend.dto.StudentProfileUpdateRequest;
import com.nexthire.backend.dto.UserDto;
import com.nexthire.backend.entity.Skill;
import com.nexthire.backend.entity.StudentProfile;
import com.nexthire.backend.entity.User;
import com.nexthire.backend.exception.ResourceNotFoundException;
import com.nexthire.backend.repository.SkillRepository;
import com.nexthire.backend.repository.StudentProfileRepository;
import com.nexthire.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class StudentProfileService {

    private final StudentProfileRepository studentProfileRepository;
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;

    public StudentProfileService(StudentProfileRepository studentProfileRepository,
                                 UserRepository userRepository,
                                 SkillRepository skillRepository) {
        this.studentProfileRepository = studentProfileRepository;
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
    }

    public StudentProfileDto getProfileByEmail(String email) {
        StudentProfile profile = studentProfileRepository.findByUserEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not found for email: " + email));
        return mapToDto(profile);
    }

    public StudentProfileDto getProfileById(Long id) {
        StudentProfile profile = studentProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not found with ID: " + id));
        return mapToDto(profile);
    }

    @Transactional
    public StudentProfileDto updateProfile(String email, StudentProfileUpdateRequest request) {
        StudentProfile profile = studentProfileRepository.findByUserEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not found for email: " + email));

        User user = profile.getUser();
        if (request.getName() != null && !request.getName().isBlank()) {
            user.setName(request.getName());
        }
        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }
        userRepository.save(user);

        if (request.getHeadline() != null) profile.setHeadline(request.getHeadline());
        if (request.getBio() != null) profile.setBio(request.getBio());
        if (request.getEducationJson() != null) profile.setEducationJson(request.getEducationJson());
        if (request.getExperienceJson() != null) profile.setExperienceJson(request.getExperienceJson());
        if (request.getProjectsJson() != null) profile.setProjectsJson(request.getProjectsJson());
        if (request.getResumeUrl() != null) profile.setResumeUrl(request.getResumeUrl());
        if (request.getGithubUrl() != null) profile.setGithubUrl(request.getGithubUrl());
        if (request.getLinkedinUrl() != null) profile.setLinkedinUrl(request.getLinkedinUrl());

        if (request.getSkillIds() != null) {
            Set<Skill> skills = new HashSet<>(skillRepository.findAllById(request.getSkillIds()));
            profile.setSkills(skills);
        }

        profile = studentProfileRepository.save(profile);
        return mapToDto(profile);
    }

    public StudentProfileDto mapToDto(StudentProfile profile) {
        User u = profile.getUser();
        UserDto userDto = UserDto.builder()
                .id(u.getId())
                .name(u.getName())
                .email(u.getEmail())
                .role(u.getRole())
                .phone(u.getPhone())
                .avatarUrl(u.getAvatarUrl())
                .enabled(u.isEnabled())
                .createdAt(u.getCreatedAt())
                .build();

        return StudentProfileDto.builder()
                .id(profile.getId())
                .user(userDto)
                .headline(profile.getHeadline())
                .bio(profile.getBio())
                .educationJson(profile.getEducationJson())
                .experienceJson(profile.getExperienceJson())
                .projectsJson(profile.getProjectsJson())
                .resumeUrl(profile.getResumeUrl())
                .githubUrl(profile.getGithubUrl())
                .linkedinUrl(profile.getLinkedinUrl())
                .skills(profile.getSkills())
                .build();
    }
}
