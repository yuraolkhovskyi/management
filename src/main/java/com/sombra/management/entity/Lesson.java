package com.sombra.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Entity
@Table(name = "lessons")
@AllArgsConstructor
@NoArgsConstructor
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name="course_id", nullable=false)
    private Course course;

    @OneToMany(mappedBy="lesson")
    private Set<Homework> homeworks;

    @OneToMany(mappedBy="lesson")
    private Set<Mark> marks;



}
