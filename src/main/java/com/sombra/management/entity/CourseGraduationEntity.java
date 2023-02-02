package com.sombra.management.entity;

import com.sombra.management.entity.converter.CourseGraduationStatusConverter;
import com.sombra.management.entity.enumeration.CourseGraduationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "course_graduations")
@AllArgsConstructor
@NoArgsConstructor
public class CourseGraduationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "final_mark")
    private Integer finalMark;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    @Convert(converter = CourseGraduationStatusConverter.class)
    private CourseGraduationStatus status;

    @ManyToOne
    @JoinColumn(name="student_id", nullable=false)
    private UserEntity student;

    @ManyToOne
    @JoinColumn(name="course_id", nullable=false)
    private CourseEntity course;

    @OneToMany(mappedBy= "courseGraduation")
    private Set<FeedbackEntity> courseFeedbacks;


}
