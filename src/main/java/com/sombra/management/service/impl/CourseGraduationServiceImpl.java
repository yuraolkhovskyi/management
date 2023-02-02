package com.sombra.management.service.impl;

import com.sombra.management.dto.CourseGraduationDTO;
import com.sombra.management.dto.StudentCourseDTO;
import com.sombra.management.entity.CourseEntity;
import com.sombra.management.entity.CourseGraduationEntity;
import com.sombra.management.entity.UserEntity;
import com.sombra.management.entity.enumeration.CourseGraduationStatus;
import com.sombra.management.repository.CourseGraduationRepository;
import com.sombra.management.service.CourseGraduationService;
import com.sombra.management.service.CourseService;
import com.sombra.management.service.MarkService;
import com.sombra.management.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CourseGraduationServiceImpl implements CourseGraduationService {

    private final CourseGraduationRepository courseGraduationRepository;
    private final MarkService markService;
    private final UserService userService;
    private final CourseService courseService;

    private final ModelMapper modelMapper;

    private static final Integer MAX_MARK_PER_LESSON = 5;
    private static final Integer PERCENTAGE_TO_PASS_THE_COURSE = 80;

    @Override
    public CourseGraduationEntity getCourseGraduationById(final Long courseGraduationId) {
        return courseGraduationRepository.findById(courseGraduationId)
                .orElseThrow(() -> {
                    throw new RuntimeException(String.format("Course Graduation with id {%d} doesn't exist", courseGraduationId));
                });
    }

    @Override
    public CourseGraduationDTO graduateCourse(final StudentCourseDTO studentCourseDTO) {

        final CourseGraduationEntity courseGraduationEntity = new CourseGraduationEntity();

        final Integer finalMarkPerUserCourse = (int) Math.round(markService
                .calculateFinalMarkByStudentAndCourse(studentCourseDTO));
        courseGraduationEntity.setFinalMark(finalMarkPerUserCourse);

        final UserEntity student = userService.findUserEntityByUserId(studentCourseDTO.getStudentId());
        courseGraduationEntity.setStudent(student);

        final CourseEntity course = courseService.findCourseEntityById(studentCourseDTO.getCourseId());
        courseGraduationEntity.setCourse(course);
        courseGraduationEntity.setStatus(calculateGraduateCourseStatusByFinalMark(finalMarkPerUserCourse));

        final CourseGraduationEntity saved = courseGraduationRepository.save(courseGraduationEntity);

        return modelMapper.map(saved, CourseGraduationDTO.class);
    }

    private CourseGraduationStatus calculateGraduateCourseStatusByFinalMark(final Integer finalMarkPerUserCourse) {
        final int finalGradePercentage = finalMarkPerUserCourse * 100 / MAX_MARK_PER_LESSON;
        return finalGradePercentage >= PERCENTAGE_TO_PASS_THE_COURSE
                ? CourseGraduationStatus.PASSED
                : CourseGraduationStatus.FAILED;
    }
}
