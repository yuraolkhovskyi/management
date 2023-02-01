package com.sombra.management.service.impl;

import com.sombra.management.dto.HomeworkResDTO;
import com.sombra.management.entity.FileEntity;
import com.sombra.management.entity.HomeworkEntity;
import com.sombra.management.entity.LessonEntity;
import com.sombra.management.entity.UserEntity;
import com.sombra.management.repository.HomeworkRepository;
import com.sombra.management.service.FileStorageService;
import com.sombra.management.service.HomeworkService;
import com.sombra.management.service.LessonService;
import com.sombra.management.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class HomeworkServiceImpl implements HomeworkService {

    private final HomeworkRepository homeworkRepository;
    private final UserService userService;
    private final LessonService lessonService;
    private final FileStorageService fileStorageService;

    @Override
    public HomeworkResDTO uploadHomework(final MultipartFile homeworkFile,
                                         final Long lessonId,
                                         final Long userId) throws IOException {
        final HomeworkEntity homeworkEntity = new HomeworkEntity();

        final LessonEntity lessonEntity = lessonService.findLessonEntityById(lessonId);
        final UserEntity userEntity = userService.findUserEntityByUserId(userId);
        final FileEntity fileEntity = fileStorageService.save(homeworkFile);

        homeworkEntity.setUploadDate(LocalDateTime.now());
        homeworkEntity.setFile(fileEntity);
        homeworkEntity.setUser(userEntity);
        homeworkEntity.setLesson(lessonEntity);

        HomeworkEntity homework = homeworkRepository.save(homeworkEntity);

        return new HomeworkResDTO(homework.getId(), userEntity.getId(), lessonEntity.getId(), fileEntity.getId());
    }

}
