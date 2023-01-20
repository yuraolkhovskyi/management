package com.sombra.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "marks")
@AllArgsConstructor
@NoArgsConstructor
public class Mark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "mark")
    private Integer mark;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name="lesson_id", nullable=false)
    private Lesson lesson;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

}
