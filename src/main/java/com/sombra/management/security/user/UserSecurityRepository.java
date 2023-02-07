package com.sombra.management.security.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSecurityRepository extends JpaRepository<UserSecurityEntity, Integer> {

    Optional<UserSecurityEntity> findByEmail(final String email);

}
