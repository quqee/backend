package org.hits.backend.hackaton.core.file;

import org.hits.backend.hackaton.public_interface.file.UploadFileDto;
import reactor.core.publisher.Mono;

public interface StorageService {
    Mono<Void> uploadFile(UploadFileDto dto);
    String getDownloadLinkByName(String name);
}
