package com.sombra.management.service.impl;

import com.sombra.management.dto.CourseDTO;
import com.sombra.management.dto.UserDTO;
import com.sombra.management.dto.CourseResDTO;
import com.sombra.management.dto.LessonDTO;
import com.sombra.management.dto.RegisterUserToCourseDTO;
import com.sombra.management.dto.UserCourseDTO;
import com.sombra.management.entity.CourseEntity;
import com.sombra.management.entity.UserEntity;
import com.sombra.management.entity.enumeration.UserRole;
import com.sombra.management.exception.SystemException;
import com.sombra.management.repository.CourseRepository;
import com.sombra.management.service.CourseService;
import com.sombra.management.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.sombra.management.exception.ErrorMessageConstants.*;
import static com.sombra.management.exception.code.ServiceErrorCode.*;
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
        SecurityContext context = SecurityContextHolder.getContext();
        final UserEntity user = (UserEntity) context.getAuthentication().getPrincipal();
        if (user.getRole() != UserRole.ADMIN || !Objects.equals(user.getId(), userId)) {
            throw new SystemException(ACCESS_DENIED_ERROR_MESSAGE, ACCESS_DENIED);
        }
        return courseRepository.getCourseEntitiesByUserId(userId).stream()
                .map(e -> modelMapper.map(e, CourseResDTO.class))
                .collect(Collectors.toSet());
    }

    @Override
    public CourseDTO findById(final Long courseId) {
        final CourseEntity courseEntity = findCourseEntityById(courseId);
        return modelMapper.map(courseEntity, CourseDTO.class);
    }

    @Override
    public CourseEntity findCourseEntityById(final Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> {
                    log.error("Course with id {} doesn't exist", courseId);
                    throw new SystemException(BAD_REQUEST_ERROR_MESSAGE, BAD_REQUEST);
                });
    }

    @Override
    public UserCourseDTO assignInstructorToCourse(final UserCourseDTO userCourseDTO) {
        final CourseEntity courseEntity = findCourseEntityById(userCourseDTO.getCourseId());
        final Set<UserEntity> instructors = userService.findUserEntitiesByUserIds(Set.of(userCourseDTO.getUserId()));

        final Set<UserEntity> coursePeople = courseEntity.getPeople();

        if (CollectionUtils.isEmpty(coursePeople)) {
            courseEntity.setPeople(instructors);
        } else {
            courseEntity.getPeople().addAll(instructors);
        }

        courseRepository.save(courseEntity);
        return userCourseDTO;
    }

    @Override
    public Set<UserDTO> getStudentsByCourseId(final Long courseId) {
        final CourseDTO courseDTO = findById(courseId);

        final Set<Long> studentIds = courseDTO.getPeople().stream()
                .filter(e -> e.getRole() == UserRole.STUDENT)
                .map(UserDTO::getId)
                .collect(Collectors.toSet());
        return userService.findUserDtosByUserIds(studentIds);
    }

    @Override
    public CourseDTO addNewCourse(final CourseDTO courseDto) {
        final Set<LessonDTO> lessons = courseDto.getLessons();
        validateCourseCreation(courseDto, lessons);

        final CourseEntity courseEntity = modelMapper.map(courseDto, CourseEntity.class);
        final Set<UserEntity> instructors = userService.findUserEntitiesByUserIds(courseDto.getInstructorIds());
        final Set<UserEntity> people = courseEntity.getPeople();

        if (Objects.isNull(people)) {
            courseEntity.setPeople(instructors);
        } else {
            courseEntity.getPeople().addAll(instructors);
        }

        courseRepository.save(courseEntity);
        return courseDto;
    }

    void validateCourseCreation(final CourseDTO courseDto,
                                final Set<LessonDTO> lessons) {
        if (CollectionUtils.isEmpty(lessons) || lessons.size() < MIN_NUMBER_OF_LESSONS) {
            log.error("Invalid lessons data");
            throw new SystemException(BAD_REQUEST_ERROR_MESSAGE, BAD_REQUEST);
        }

        final Set<Long> instructorIds = courseDto.getInstructorIds();
        if (Objects.isNull(instructorIds) || instructorIds.isEmpty()) {
            log.error("Course cannot be created without any instructor assigned.");
            throw new SystemException(BAD_REQUEST_ERROR_MESSAGE, BAD_REQUEST);
        }
    }

    @Override
    public RegisterUserToCourseDTO registerUserToCourse(final RegisterUserToCourseDTO userToCourseDTO) {
        final Long userId = userToCourseDTO.getUserId();
        final Long courseId = userToCourseDTO.getCourseId();
        validateUserCourseRegistration(userId);

        final CourseEntity courseEntity = findCourseEntityById(courseId);
        final UserEntity userEntityByUserId = userService.findUserEntityByUserId(userId);
        courseEntity.getPeople().add(userEntityByUserId);
        courseRepository.save(courseEntity);

        return userToCourseDTO;
    }

    void validateUserCourseRegistration(final Long userId) {
        final Set<CourseResDTO> coursesByUserId = getCoursesByUserId(userId);
        long numberOfActiveCoursesPerUser = coursesByUserId.stream()
                .filter(course -> isLocalDateBetweenDatesIncludingLimits(course.getStartDate(), course.getEndDate()))
                .count();
        if (numberOfActiveCoursesPerUser > NUMBER_OF_ACTIVE_COURSES_PER_USER) {
            log.error("User with id {} can not take more than {} courses simultaneously.", userId,  NUMBER_OF_ACTIVE_COURSES_PER_USER);
            throw new SystemException(BAD_REQUEST_ERROR_MESSAGE, BAD_REQUEST);
        }

        final Set<Long> activeCourseIdsPerUser = coursesByUserId.stream()
                .map(CourseResDTO::getId)
                .collect(Collectors.toSet());
        if (activeCourseIdsPerUser.contains(userId)) {
            log.error("User with id {} is already registered for the course.", userId);
            throw new SystemException(BAD_REQUEST_ERROR_MESSAGE, BAD_REQUEST);

        }
    }
}
