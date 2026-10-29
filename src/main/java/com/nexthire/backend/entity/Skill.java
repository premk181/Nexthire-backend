package com.nexthire.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "skills")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String category;

    public Skill() {
    }

    public Skill(Long id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public static SkillBuilder builder() {
        return new SkillBuilder();
    }

    public static class SkillBuilder {
        private Long id;
        private String name;
        private String category;

        public SkillBuilder id(Long id) { this.id = id; return this; }
        public SkillBuilder name(String name) { this.name = name; return this; }
        public SkillBuilder category(String category) { this.category = category; return this; }

        public Skill build() {
            return new Skill(id, name, category);
        }
    }
}
