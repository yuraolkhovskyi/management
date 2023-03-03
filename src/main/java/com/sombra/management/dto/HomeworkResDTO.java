package com.sombra.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HomeworkResDTO {

    private Long homeworkId;
    private Long userId;
    private Long lessonId;
    private UUID fileId;

}
