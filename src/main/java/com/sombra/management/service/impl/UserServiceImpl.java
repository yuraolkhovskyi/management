package com.sombra.management.service.impl;

import com.sombra.management.repository.UserRepository;
import com.sombra.management.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public Set<Object> getCoursesByInstructorId(final Long instructorId) {
        return null;
    }

}
