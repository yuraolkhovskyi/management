package com.sombra.management.integration.repository;

import com.sombra.management.entity.CourseEntity;
import com.sombra.management.entity.UserEntity;
import com.sombra.management.repository.CourseRepository;
import com.sombra.management.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@DataJpaTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository underTest;
    @Autowired
    private UserRepository userRepository;

    @Test
    void getCourseEntitiesByUserId__courseIsFoundByUserId() {
        //given
        final var courseEntity = new CourseEntity();
        courseEntity.setTitle("testTitle");
        courseEntity.setStartDate(LocalDate.now().plusMonths(3));
        courseEntity.setEndDate(LocalDate.now().plusMonths(5));

        final var userEntity = UserEntity.builder()
                .firstname("test")
                .lastname("test")
                .email("someEmail@gmail.com")
                .registrationDate(LocalDateTime.now())
                .password("somePass")
                .build();
        final UserEntity saveUserEntity = userRepository.save(userEntity);

        courseEntity.setPeople(Set.of(saveUserEntity));
        underTest.save(courseEntity);

        //when
        final Set<CourseEntity> courseEntitiesByUserId = underTest
                .getCourseEntitiesByUserId(userEntity.getId());
        final var expectedPerson = courseEntitiesByUserId.stream()
                .findFirst()
                .orElseThrow()
                .getPeople().stream()
                .findFirst().orElseThrow(() -> new RuntimeException("No user found"));


        //then
        Assertions.assertFalse(CollectionUtils.isEmpty(courseEntitiesByUserId));
        Assertions.assertEquals(1, courseEntitiesByUserId.size());
        Assertions.assertEquals(saveUserEntity, expectedPerson);
    }

    @Test
    void getCourseEntitiesByUserId__courseIsNotFoundByUserId() {
        //given
        final var courseEntity = new CourseEntity();
        courseEntity.setTitle("testTitle");
        courseEntity.setStartDate(LocalDate.now().plusMonths(3));
        courseEntity.setEndDate(LocalDate.now().plusMonths(5));

        final var userEntity = UserEntity.builder()
                .firstname("test")
                .lastname("test")
                .email("someEmail@gmail.com")
                .registrationDate(LocalDateTime.now())
                .password("somePass")
                .build();
        final UserEntity saveUserEntity = userRepository.save(userEntity);

        courseEntity.setPeople(Set.of(saveUserEntity));
        underTest.save(courseEntity);

        //when
        final Set<CourseEntity> courseEntitiesByUserId = underTest
                .getCourseEntitiesByUserId(Long.MAX_VALUE);

        //then
        Assertions.assertTrue(CollectionUtils.isEmpty(courseEntitiesByUserId));
        Assertions.assertEquals(0, courseEntitiesByUserId.size());
    }
}