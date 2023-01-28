package com.sombra.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@Entity
@Table(name = "lessons")
@AllArgsConstructor
@NoArgsConstructor
public class LessonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name="course_id", nullable=false)
    private CourseEntity course;

    @OneToMany(mappedBy="lesson")
    private Set<HomeworkEntity> homeworks;

    @OneToMany(mappedBy="lesson")
    private Set<MarkEntity> marks;

}
