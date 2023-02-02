package com.sombra.management.repository;

import com.sombra.management.entity.MarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkRepository extends JpaRepository<MarkEntity, Long> {

}
