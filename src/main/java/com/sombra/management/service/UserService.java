package com.sombra.management.service;

import java.util.Set;

public interface UserService {
    Set<Object> getCoursesByInstructorId(Long instructorId);
}