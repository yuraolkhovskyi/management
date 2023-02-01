package com.sombra.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HomeworkResDTO {

    private Long homeworkId;
    private Long userId;
    private Long lessonId;
    private String fileId;

}
