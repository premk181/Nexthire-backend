package com.nexthire.backend.util;

import com.nexthire.backend.entity.Skill;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SkillMatcher {

    public int calculateMatchPercentage(Set<Skill> studentSkills, Set<Skill> requiredSkills) {
        if (requiredSkills == null || requiredSkills.isEmpty()) {
            return 100;
        }

        if (studentSkills == null || studentSkills.isEmpty()) {
            return 0;
        }

        Set<String> studentSkillNames = studentSkills.stream()
                .map(s -> s.getName().toLowerCase().trim())
                .collect(Collectors.toSet());

        Set<String> requiredSkillNames = requiredSkills.stream()
                .map(s -> s.getName().toLowerCase().trim())
                .collect(Collectors.toSet());

        long matchingCount = requiredSkillNames.stream()
                .filter(studentSkillNames::contains)
                .count();

        return (int) Math.round(((double) matchingCount / requiredSkillNames.size()) * 100);
    }
}
