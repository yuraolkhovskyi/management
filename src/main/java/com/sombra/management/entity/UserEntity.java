package com.sombra.management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sombra.management.entity.converter.UserRoleConverter;
import com.sombra.management.entity.enumeration.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users", schema = "management")
@Entity
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

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

    @JsonIgnore
    @ManyToMany(mappedBy = "people")
    private Set<CourseEntity> courses;

    @JsonIgnore
    @OneToMany(mappedBy="student")
    private Set<CourseGraduationEntity> studentCourseGraduations;

    @JsonIgnore
    @OneToMany(mappedBy="student")
    private Set<HomeworkEntity> homeworks;

    @JsonIgnore
    @OneToMany(mappedBy="instructor")
    private Set<FeedbackEntity> courseFeedbacks;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
