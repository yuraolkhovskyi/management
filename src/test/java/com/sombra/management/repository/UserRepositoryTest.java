package com.sombra.management.repository;

import com.sombra.management.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @Test
    void findByEmail__userIsPresent() {
        // given
        final String userEmail = "someEmail@gmail.com";
        final var userEntity = UserEntity.builder()
                .firstname("test")
                .lastname("test")
                .email(userEmail)
                .registrationDate(LocalDateTime.now())
                .password("somePass")
                .build();
        underTest.save(userEntity);

        // when
        final Optional<UserEntity> user = underTest.findByEmail(userEmail);

        // then
        Assertions.assertTrue(user.isPresent());
    }

    @Test
    void findByEmail__userIsNotPresent() {
        // given
        final String userEmail = "someEmail@gmail.com";
        final var userEntity = UserEntity.builder()
                .firstname("test")
                .lastname("test")
                .email(userEmail)
                .registrationDate(LocalDateTime.now())
                .password("somePass")
                .build();
        underTest.save(userEntity);

        // when
        final String emailToTest = "someOtherUserEmail@gmail.com";
        final Optional<UserEntity> user = underTest.findByEmail(emailToTest);

        // then
        Assertions.assertTrue(user.isEmpty());
    }
}