package com.sombra.management.integration.repository;

import com.sombra.management.entity.CourseEntity;
import com.sombra.management.entity.LessonEntity;
import com.sombra.management.repository.CourseRepository;
import com.sombra.management.repository.LessonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.Set;

@DataJpaTest
class LessonRepositoryTest {

    @Autowired
    private LessonRepository underTest;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private CourseRepository courseRepository;

    @Test
    void getLessonEntitiesByCourse_Id__lessonsArePresent() {
        // given
        final var courseEntity = new CourseEntity();
        courseEntity.setTitle("testTitle");
        courseEntity.setStartDate(LocalDate.now().plusMonths(3));
        courseEntity.setEndDate(LocalDate.now().plusMonths(5));
        final CourseEntity savedCourseEntity = courseRepository.save(courseEntity);

        final var lessonEntity = new LessonEntity();
        lessonEntity.setTitle("someLesson");
        lessonEntity.setCourse(savedCourseEntity);
        lessonRepository.save(lessonEntity);

        // when
        final Set<LessonEntity> actual = underTest.getLessonEntitiesByCourse_Id(savedCourseEntity.getId());

        // then
        Assertions.assertFalse(CollectionUtils.isEmpty(actual));
        Assertions.assertEquals(1, actual.size());
    }

    @Test
    void getLessonEntitiesByCourse_Id__lessonsAreNotPresent() {
        // given
        final var courseEntity = new CourseEntity();
        courseEntity.setTitle("testTitle");
        courseEntity.setStartDate(LocalDate.now().plusMonths(3));
        courseEntity.setEndDate(LocalDate.now().plusMonths(5));
        final CourseEntity savedCourseEntity = courseRepository.save(courseEntity);

        final var lessonEntity = new LessonEntity();
        lessonEntity.setTitle("someLesson");
        lessonEntity.setCourse(savedCourseEntity);
        lessonRepository.save(lessonEntity);

        // when
        final Set<LessonEntity> actual = underTest.getLessonEntitiesByCourse_Id(Long.MAX_VALUE);

        // then
        Assertions.assertTrue(CollectionUtils.isEmpty(actual));
    }
}