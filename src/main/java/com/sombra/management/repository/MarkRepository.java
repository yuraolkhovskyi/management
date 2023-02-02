package com.sombra.management.repository;

import com.sombra.management.entity.MarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface MarkRepository extends JpaRepository<MarkEntity, Long> {

    Set<MarkEntity> findAllByUserIdAndLessonId(final Long userId, final Long lessonId);

}
