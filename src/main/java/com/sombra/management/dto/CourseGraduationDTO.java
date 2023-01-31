package com.sombra.management.dto;

import com.sombra.management.entity.enumeration.CourseGraduationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseGraduationDTO {

    private Long id;
    private BigDecimal finalMark;
    private CourseGraduationStatus status;

}
