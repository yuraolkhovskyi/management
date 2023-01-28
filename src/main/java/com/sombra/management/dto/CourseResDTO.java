package com.sombra.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseResDTO {

    private Long id;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;

}
