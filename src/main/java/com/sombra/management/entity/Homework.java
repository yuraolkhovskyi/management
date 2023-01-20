package com.sombra.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "homeworks")
@AllArgsConstructor
@NoArgsConstructor
public class Homework {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "file_homework")
    private String fileHomework; // FIXME: 18.01.2023 change to File type

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name="lesson_id", nullable=false)
    private Lesson lesson;

}
