package com.sombra.management.service.impl;

import com.sombra.management.dto.CourseDTO;
import com.sombra.management.dto.CourseResDTO;
import com.sombra.management.dto.LessonDTO;
import com.sombra.management.dto.RegisterUserToCourseDTO;
import com.sombra.management.entity.CourseEntity;
import com.sombra.management.entity.UserEntity;
import com.sombra.management.repository.CourseRepository;
import com.sombra.management.service.CourseService;
import com.sombra.management.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.sombra.management.util.DateTimeUtil.isLocalDateBetweenDatesIncludingLimits;

@Slf4j
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Value(value = "${user.max-number-of-courses}")
    private Long NUMBER_OF_ACTIVE_COURSES_PER_USER;
    @Value(value = "${course.min-number-of-lessons}")
    private Integer MIN_NUMBER_OF_LESSONS;

    public CourseServiceImpl(final CourseRepository courseRepository,
                             final UserService userService,
                             final ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<CourseResDTO> getCoursesByUserId(final Long userId) {
        // FIXME: 30.01.2023 add validation if current user id from SCH is equal to userId
        return courseRepository.getCourseEntitiesByUserId(userId).stream()
                .map(e -> modelMapper.map(e, CourseResDTO.class))
                .collect(Collectors.toSet());
    }

    @Override
    public CourseDTO findById(final Long courseId) {
        final CourseEntity courseEntity = courseRepository.findById(courseId).orElseThrow();
        return modelMapper.map(courseEntity, CourseDTO.class);
    }

    @Override
    public CourseEntity findCourseEntityById(final Long courseId) {
        return courseRepository.findById(courseId).orElseThrow();
    }


    @Override
    public CourseDTO addNewCourse(final CourseDTO courseDto) {
        final Set<LessonDTO> lessons = courseDto.getLessons();

        if (Objects.isNull(lessons) || lessons.isEmpty() || lessons.size() < MIN_NUMBER_OF_LESSONS) {
            throw new RuntimeException("Invalid lessons data");
        }
        final CourseEntity courseEntity = modelMapper.map(courseDto, CourseEntity.class);
        Set<Long> instructorIds = courseDto.getInstructorIds();

        if (Objects.isNull(instructorIds) || instructorIds.isEmpty()) {
            throw new RuntimeException("Course cannot be created without any instructor assigned.");
        }

        final Set<UserEntity> instructors = userService.findUserEntityByUserIds(instructorIds);

        final Set<UserEntity> users = courseEntity.getUsers();

        if (Objects.isNull(users)) {
            courseEntity.setUsers(instructors);
        } else {
            courseEntity.getUsers().addAll(instructors);
        }

        courseRepository.save(courseEntity);

        return courseDto;
    }

    @Override
    public RegisterUserToCourseDTO registerUserToCourse(final RegisterUserToCourseDTO userToCourseDTO) {
        final Long userId = userToCourseDTO.getUserId();
        final Long courseId = userToCourseDTO.getCourseId();

        final Set<CourseResDTO> coursesByUserId = getCoursesByUserId(userId);

        long numberOfActiveCoursesPerUser = coursesByUserId.stream()
                .filter(course -> isLocalDateBetweenDatesIncludingLimits(course.getStartDate(), course.getEndDate()))
                .count();
        if (numberOfActiveCoursesPerUser > NUMBER_OF_ACTIVE_COURSES_PER_USER) {
            final String errorMessage = String.format("User with id %d can not take more than %d courses simultaneously.",
                    userId, NUMBER_OF_ACTIVE_COURSES_PER_USER);
            throw new RuntimeException(errorMessage);
        }

        final Set<Long> activeCourseIdsPerUser = coursesByUserId.stream()
                .map(CourseResDTO::getId)
                .collect(Collectors.toSet());
        if (activeCourseIdsPerUser.contains(courseId)) {
            final String errorMessage = String.format("User with id %d is already registered for the course.", userId);
            throw new RuntimeException(errorMessage);
        }

        final CourseEntity courseEntity = courseRepository.findById(courseId).orElseThrow();
        final UserEntity userEntityByUserId = userService.findUserEntityByUserId(userId);
        courseEntity.getUsers().add(userEntityByUserId);

        courseRepository.save(courseEntity);

        return userToCourseDTO;
    }
}
