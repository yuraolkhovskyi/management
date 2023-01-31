package com.sombra.management.repository;

import com.sombra.management.entity.CourseGraduationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseGraduationRepository extends JpaRepository<CourseGraduationEntity, Long> {

}
