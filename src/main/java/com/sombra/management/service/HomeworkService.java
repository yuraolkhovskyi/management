package com.sombra.management.service;

import com.sombra.management.dto.HomeworkResDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface HomeworkService {

    HomeworkResDTO uploadHomework(final MultipartFile homeworkFile,
                                  final Long lessonId,
                                  final Long userId) throws IOException;

}
