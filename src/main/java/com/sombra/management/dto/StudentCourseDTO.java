package com.sombra.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentCourseDTO {

    private Long studentId;
    private Long courseId;

}
