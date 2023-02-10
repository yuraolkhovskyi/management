package com.sombra.management.service.impl;

import com.sombra.management.dto.CourseGraduationDTO;
import com.sombra.management.dto.UserCourseDTO;
import com.sombra.management.entity.CourseEntity;
import com.sombra.management.entity.CourseGraduationEntity;
import com.sombra.management.entity.UserEntity;
import com.sombra.management.entity.enumeration.CourseGraduationStatus;
import com.sombra.management.exception.SystemException;
import com.sombra.management.repository.CourseGraduationRepository;
import com.sombra.management.service.CourseGraduationService;
import com.sombra.management.service.CourseService;
import com.sombra.management.service.MarkService;
import com.sombra.management.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.sombra.management.exception.ErrorMessageConstants.BAD_REQUEST_ERROR_MESSAGE;
import static com.sombra.management.exception.code.ServiceErrorCode.BAD_REQUEST;

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
                    log.error("Course Graduation with id {} doesn't exist", courseGraduationId);
                    throw new SystemException(BAD_REQUEST_ERROR_MESSAGE, BAD_REQUEST);
                });
    }

    @Override
    public CourseGraduationDTO graduateCourse(final UserCourseDTO userCourseDTO) {
        final CourseGraduationEntity courseGraduationEntity = getCourseGraduationEntity(userCourseDTO);
        final CourseGraduationEntity saved = courseGraduationRepository.save(courseGraduationEntity);
        return modelMapper.map(saved, CourseGraduationDTO.class);
    }

    private CourseGraduationEntity getCourseGraduationEntity(final UserCourseDTO userCourseDTO) {
        final CourseGraduationEntity courseGraduationEntity = new CourseGraduationEntity();

        final Integer finalMarkPerUserCourse = (int) Math.round(markService
                .calculateFinalMarkByStudentAndCourse(userCourseDTO));
        courseGraduationEntity.setFinalMark(finalMarkPerUserCourse);

        final UserEntity student = userService.findUserEntityByUserId(userCourseDTO.getUserId());
        courseGraduationEntity.setStudent(student);

        final CourseEntity course = courseService.findCourseEntityById(userCourseDTO.getCourseId());
        courseGraduationEntity.setCourse(course);
        courseGraduationEntity.setStatus(calculateGraduateCourseStatusByFinalMark(finalMarkPerUserCourse));
        return courseGraduationEntity;
    }

    CourseGraduationStatus calculateGraduateCourseStatusByFinalMark(final Integer finalMarkPerUserCourse) {
        final int finalGradePercentage = finalMarkPerUserCourse * 100 / MAX_MARK_PER_LESSON;
        return finalGradePercentage >= PERCENTAGE_TO_PASS_THE_COURSE
                ? CourseGraduationStatus.PASSED
                : CourseGraduationStatus.FAILED;
    }
}
