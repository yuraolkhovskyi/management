package com.sombra.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class MarkDTO {

    private Integer mark;
    private Long studentId;
    private Long lessonId;
    private Long instructorId;

}
