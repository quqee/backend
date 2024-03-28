package org.hits.backend.hackaton.public_interface.file;

import org.hits.backend.hackaton.core.file.FileMetadata;
import org.springframework.web.multipart.MultipartFile;

public record UploadFileDto(
        FileMetadata metadata,
        MultipartFile file
) {
}
