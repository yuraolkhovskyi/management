package com.sombra.management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Entity
@Table(name = "courses")
@AllArgsConstructor
@NoArgsConstructor
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "person_course",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private Set<UserEntity> users;

    @JsonIgnore
    @OneToMany(mappedBy="course")
    private Set<CourseGraduationEntity> courseGraduations;

    @JsonIgnore
    @OneToMany(mappedBy="course")
    private Set<LessonEntity> lessons;


}
