package com.sombra.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@Entity
@Table(name = "files", schema = "management")
@AllArgsConstructor
@NoArgsConstructor
public class FileEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Lob
    @Column(name = "file")
    private byte[] file;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "file", cascade = CascadeType.ALL)
    private HomeworkEntity homework;

}
