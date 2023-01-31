package com.sombra.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "feedbacks")
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "feedback_message")
    private String feedbackMessage;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name="course_graduation_id", nullable=false)
    private CourseGraduationEntity courseGraduation;

    @ManyToOne
    @JoinColumn(name="instructor_id", nullable=false)
    private UserEntity instructor;


}
