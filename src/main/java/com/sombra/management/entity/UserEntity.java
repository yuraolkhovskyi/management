package com.sombra.management.entity;

import com.sombra.management.entity.converter.UserRoleConverter;
import com.sombra.management.entity.enumeration.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

//    [SECURITY] There are three roles: admin, instructor, student;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "role")
    @Convert(converter = UserRoleConverter.class)
    private UserRole role;

    @ManyToMany(mappedBy = "users")
    private Set<CourseEntity> courses;

    @OneToMany(mappedBy="student")
    private Set<CourseGraduationEntity> studentCourseGraduations;

    @OneToMany(mappedBy="student")
    private Set<HomeworkEntity> homeworks;

    @OneToMany(mappedBy="instructor")
    private Set<FeedbackEntity> courseFeedbacks;


}
