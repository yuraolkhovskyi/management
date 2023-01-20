package com.sombra.management.entity;

import com.sombra.management.entity.converter.UserRoleConverter;
import com.sombra.management.entity.enumeration.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "role")
    @Convert(converter = UserRoleConverter.class)
    private UserRole role;

    @ManyToMany(mappedBy = "users")
    private Set<Course> courses;

    @OneToMany(mappedBy="student")
    private Set<CourseGraduation> studentCourseGraduations;

    @OneToMany(mappedBy="instructor")
    private Set<CourseGraduation> instructorCourseGraduations;

    @OneToMany(mappedBy="user")
    private Set<Mark> marks;

}
