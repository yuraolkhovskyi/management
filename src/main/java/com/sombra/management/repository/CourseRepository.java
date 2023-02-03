package com.sombra.management.repository;

import com.sombra.management.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {

    @Query(value = "select ce from CourseEntity ce " + "JOIN ce.people uss WHERE uss.id = :userId")
    Set<CourseEntity> getCourseEntitiesByUserId(@RequestParam Long userId);

}
