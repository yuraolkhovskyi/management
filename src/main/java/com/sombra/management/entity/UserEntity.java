package com.sombra.management.entity;

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
@Entity(name = "users")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;

    private String lastname;

    private String email;

    private String password;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    //    [SECURITY] There are three roles: admin, instructor, student;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "role")
    @Convert(converter = UserRoleConverter.class)
    private UserRole role;

    @ManyToMany(mappedBy = "people")
    private Set<CourseEntity> courses;

    @OneToMany(mappedBy="student")
    private Set<CourseGraduationEntity> studentCourseGraduations;

    @OneToMany(mappedBy="student")
    private Set<HomeworkEntity> homeworks;

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
