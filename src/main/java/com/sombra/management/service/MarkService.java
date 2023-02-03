package com.sombra.management.service;

import com.sombra.management.dto.MarkDTO;
import com.sombra.management.dto.UserCourseDTO;

public interface MarkService {

    MarkDTO putMarkByLesson(final MarkDTO markDTO);

    Double calculateFinalMarkByStudentAndCourse(final UserCourseDTO userCourseDTO);
}
