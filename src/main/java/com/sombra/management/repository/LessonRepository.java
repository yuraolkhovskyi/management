package com.sombra.management.repository;

import com.sombra.management.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, Long> {

    Set<LessonEntity> getLessonEntitiesByCourse_Id(final Long courseId);

}
