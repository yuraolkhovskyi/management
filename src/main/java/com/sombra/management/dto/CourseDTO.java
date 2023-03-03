package com.sombra.management.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseDTO {

    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<Long> instructorIds;
    private Set<LessonDTO> lessons;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<UserDTO> people;

}
