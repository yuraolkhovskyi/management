package com.sombra.management.entity;

import com.sombra.management.entity.converter.CourseGraduationStatusConverter;
import com.sombra.management.entity.enumeration.CourseGraduationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Entity
@Table(name = "course_graduations")
@AllArgsConstructor
@NoArgsConstructor
public class CourseGraduation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "final_mark")
    private BigDecimal finalMark;

    @Column(name = "instructor_feedback")
    private LocalDate instructorFeedback;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    @Convert(converter = CourseGraduationStatusConverter.class)
    private CourseGraduationStatus status;

    @ManyToOne
    @JoinColumn(name="student_id", nullable=false)
    private User student;

    @ManyToOne
    @JoinColumn(name="instructor_id", nullable=false)
    private User instructor;

    @ManyToOne
    @JoinColumn(name="course_id", nullable=false)
    private Course course;

}
