package com.sombra.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "homeworks")
@AllArgsConstructor
@NoArgsConstructor
public class HomeworkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "upload_date")
    private LocalDateTime uploadDate;

    @ManyToOne
    @JoinColumn(name="lesson_id", nullable=false)
    private LessonEntity lesson;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private UserEntity student;

    @OneToOne(fetch = FetchType.LAZY)
//    @MapsId best practice
    @JoinColumn(name = "file_id")
    private FileEntity file;


}
