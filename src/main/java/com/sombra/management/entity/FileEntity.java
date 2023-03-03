package com.sombra.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;

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
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @JdbcTypeCode(Types.LONGVARBINARY)
    @Lob
    @Column(name = "file", columnDefinition = "bytea")
    private byte[] file;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "file", cascade = CascadeType.ALL)
    private HomeworkEntity homework;

}
