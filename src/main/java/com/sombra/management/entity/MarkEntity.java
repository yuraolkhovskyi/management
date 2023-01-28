package com.sombra.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "marks")
@AllArgsConstructor
@NoArgsConstructor
public class MarkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "mark")
    private Integer mark;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name="lesson_id", nullable=false)
    private LessonEntity lesson;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name="instructor_id", nullable=false)
    private UserEntity instructor;


}
