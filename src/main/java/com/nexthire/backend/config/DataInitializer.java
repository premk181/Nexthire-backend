package com.nexthire.backend.config;

import com.nexthire.backend.entity.*;
import com.nexthire.backend.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final SkillRepository skillRepository;
    private final CompanyRepository companyRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           SkillRepository skillRepository,
                           CompanyRepository companyRepository,
                           StudentProfileRepository studentProfileRepository,
                           RecruiterProfileRepository recruiterProfileRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
        this.companyRepository = companyRepository;
        this.studentProfileRepository = studentProfileRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        seedSkills();
        seedAdminUser();
        seedSampleCompaniesAndRecruiters();
        seedSampleStudent();
    }

    private void seedSkills() {
        List<String[]> defaultSkills = Arrays.asList(
            new String[]{"Java", "Programming Languages"},
            new String[]{"Spring Boot", "Frameworks"},
            new String[]{"React", "Frontend Frameworks"},
            new String[]{"MySQL", "Databases"},
            new String[]{"JavaScript", "Programming Languages"},
            new String[]{"TypeScript", "Programming Languages"},
            new String[]{"Python", "Programming Languages"},
            new String[]{"Node.js", "Backend"},
            new String[]{"Docker", "DevOps & Cloud"},
            new String[]{"AWS", "DevOps & Cloud"},
            new String[]{"HTML5 & CSS3", "Frontend"},
            new String[]{"RESTful APIs", "Architecture"},
            new String[]{"Git & GitHub", "Tools"}
        );

        for (String[] skillInfo : defaultSkills) {
            if (!skillRepository.existsByName(skillInfo[0])) {
                skillRepository.save(Skill.builder()
                        .name(skillInfo[0])
                        .category(skillInfo[1])
                        .build());
            }
        }
    }

    private void seedAdminUser() {
        if (!userRepository.existsByEmail("admin@nexthire.com")) {
            User admin = User.builder()
                    .name("System Administrator")
                    .email("admin@nexthire.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ROLE_ADMIN)
                    .phone("+1 800-555-0199")
                    .enabled(true)
                    .build();
            userRepository.save(admin);
        }
    }

    private void seedSampleCompaniesAndRecruiters() {
        Company techCorp = companyRepository.findByName("TechCorp Global")
                .orElseGet(() -> companyRepository.save(Company.builder()
                        .name("TechCorp Global")
                        .description("Leading innovator in enterprise cloud software and AI solutions.")
                        .website("https://techcorp.example.com")
                        .location("San Francisco, CA")
                        .industry("Software & Technology")
                        .companySize("500-1000 employees")
                        .isVerified(true)
                        .build()));

        if (!userRepository.existsByEmail("recruiter@techcorp.com")) {
            User recruiterUser = User.builder()
                    .name("Sarah Jenkins")
                    .email("recruiter@techcorp.com")
                    .password(passwordEncoder.encode("recruiter123"))
                    .role(Role.ROLE_RECRUITER)
                    .phone("+1 415-555-0123")
                    .enabled(true)
                    .build();
            recruiterUser = userRepository.save(recruiterUser);

            RecruiterProfile recruiterProfile = RecruiterProfile.builder()
                    .user(recruiterUser)
                    .designation("Senior Talent Acquisition Lead")
                    .department("Human Resources")
                    .company(techCorp)
                    .build();
            recruiterProfileRepository.save(recruiterProfile);
        }
    }

    private void seedSampleStudent() {
        if (!userRepository.existsByEmail("student@nexthire.com")) {
            User studentUser = User.builder()
                    .name("Alex Morgan")
                    .email("student@nexthire.com")
                    .password(passwordEncoder.encode("student123"))
                    .role(Role.ROLE_STUDENT)
                    .phone("+1 650-555-0144")
                    .enabled(true)
                    .build();
            studentUser = userRepository.save(studentUser);

            Set<Skill> studentSkills = new HashSet<>();
            skillRepository.findByName("Java").ifPresent(studentSkills::add);
            skillRepository.findByName("Spring Boot").ifPresent(studentSkills::add);
            skillRepository.findByName("React").ifPresent(studentSkills::add);
            skillRepository.findByName("MySQL").ifPresent(studentSkills::add);

            StudentProfile studentProfile = StudentProfile.builder()
                    .user(studentUser)
                    .headline("Aspiring Full-Stack Software Engineer | Java & React Enthusiast")
                    .bio("Passionate Computer Science graduate skilled in building scalable web applications with Spring Boot and React.")
                    .educationJson("[{\"degree\":\"B.S. Computer Science\",\"institution\":\"State University\",\"passYear\":\"2025\",\"gpa\":\"3.8/4.0\"}]")
                    .skills(studentSkills)
                    .githubUrl("https://github.com/alexmorgan")
                    .linkedinUrl("https://linkedin.com/in/alexmorgan")
                    .build();
            studentProfileRepository.save(studentProfile);
        }
    }
}
