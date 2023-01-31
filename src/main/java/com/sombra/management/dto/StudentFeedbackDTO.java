package com.sombra.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentFeedbackDTO {

    private String feedbackMessage;
    private Long courseGraduationId;
    private Long instructorId;

}
