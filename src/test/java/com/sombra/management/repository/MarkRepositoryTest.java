package com.sombra.management.repository;

import com.sombra.management.entity.CourseEntity;
import com.sombra.management.entity.LessonEntity;
import com.sombra.management.entity.MarkEntity;
import com.sombra.management.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@DataJpaTest
class MarkRepositoryTest {

    @Autowired
    private MarkRepository underTest;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private CourseRepository courseRepository;

    @Test
    void findAllByStudentIdAndLessonId_markIsPresent() {
        // given
        final var studentEntity = UserEntity.builder()
                .firstname("test")
                .lastname("test")
                .email("someEmail@gmail.com")
                .registrationDate(LocalDateTime.now())
                .password("somePass")
                .build();
        final UserEntity saveUserEntity = userRepository.save(studentEntity);

        final var courseEntity = new CourseEntity();
        courseEntity.setTitle("testTitle");
        courseEntity.setStartDate(LocalDate.now().plusMonths(3));
        courseEntity.setEndDate(LocalDate.now().plusMonths(5));
        final CourseEntity savedCourseEntity = courseRepository.save(courseEntity);

        final LessonEntity lessonEntity = new LessonEntity();
        lessonEntity.setTitle("someLesson");
        lessonEntity.setCourse(savedCourseEntity);
        final LessonEntity savedLessonEntity = lessonRepository.save(lessonEntity);

        final MarkEntity markEntity = new MarkEntity();
        markEntity.setMark(5);
        markEntity.setDate(LocalDateTime.now());
        markEntity.setLesson(lessonEntity);
        markEntity.setStudent(studentEntity);
        markEntity.setInstructor(studentEntity);
        underTest.save(markEntity);

        // when
        final Set<MarkEntity> marksByLessonAndStudentId = underTest
                .findAllByStudentIdAndLessonId(saveUserEntity.getId(), savedLessonEntity.getId());

        // then
        Assertions.assertFalse(CollectionUtils.isEmpty(marksByLessonAndStudentId));
        Assertions.assertEquals(1, marksByLessonAndStudentId.size());
    }

    @Test
    void findAllByStudentIdAndLessonId_markIsNotPresent() {
        // given
        final var studentEntity = UserEntity.builder()
                .firstname("test")
                .lastname("test")
                .email("someEmail@gmail.com")
                .registrationDate(LocalDateTime.now())
                .password("somePass")
                .build();
        final UserEntity savedUserEntity = userRepository.save(studentEntity);

        final var courseEntity = new CourseEntity();
        courseEntity.setTitle("testTitle");
        courseEntity.setStartDate(LocalDate.now().plusMonths(3));
        courseEntity.setEndDate(LocalDate.now().plusMonths(5));
        final CourseEntity savedCourseEntity = courseRepository.save(courseEntity);

        final LessonEntity lessonEntity = new LessonEntity();
        lessonEntity.setTitle("someLesson");
        lessonEntity.setCourse(savedCourseEntity);
        final LessonEntity savedLessonEntity = lessonRepository.save(lessonEntity);

        final MarkEntity markEntity = new MarkEntity();
        markEntity.setMark(5);
        markEntity.setDate(LocalDateTime.now());
        markEntity.setLesson(savedLessonEntity);
        markEntity.setStudent(savedUserEntity);
        markEntity.setInstructor(savedUserEntity);
        underTest.save(markEntity);

        // when
        final Set<MarkEntity> marksByLessonAndStudentId = underTest
                .findAllByStudentIdAndLessonId(Long.MAX_VALUE, Long.MAX_VALUE);

        // then
        Assertions.assertTrue(CollectionUtils.isEmpty(marksByLessonAndStudentId));
        Assertions.assertEquals(0, marksByLessonAndStudentId.size());
    }

}