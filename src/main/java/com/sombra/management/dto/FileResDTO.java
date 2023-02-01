package com.sombra.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileResDTO {

    private String id;
    private String fileName;
    private String fileType;
    private byte[] file;

}
