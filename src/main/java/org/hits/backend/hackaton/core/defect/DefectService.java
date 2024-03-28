package org.hits.backend.hackaton.core.defect;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.core.file.FileMetadata;
import org.hits.backend.hackaton.core.file.StorageService;
import org.hits.backend.hackaton.core.user.repository.entity.UserEntity;
import org.hits.backend.hackaton.public_interface.defect.CreateDefectDto;
import org.hits.backend.hackaton.public_interface.defect.DefectFullDto;
import org.hits.backend.hackaton.public_interface.defect.DefectStatus;
import org.hits.backend.hackaton.public_interface.defect.DefectTypeDto;
import org.hits.backend.hackaton.public_interface.defect.UpdateDefectDto;
import org.hits.backend.hackaton.public_interface.exception.ExceptionInApplication;
import org.hits.backend.hackaton.public_interface.exception.ExceptionType;
import org.hits.backend.hackaton.public_interface.file.UploadFileDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefectService {
    private final DefectRepository defectRepository;
    private final StorageService storageService;
    private final DefectPhotoRepository defectPhotoRepository;

    public UUID createDefect(CreateDefectDto dto) {
        var defect = new DefectEntity(
                null,
                dto.statementId(),
                dto.longitude(),
                dto.latitude(),
                DefectStatus.OPEN,
                dto.defectTypeId(),
                dto.address(),
                OffsetDateTime.now(),
                dto.defectDistance()
        );

        var defectId = defectRepository.createDefect(defect);

        savePhotos(dto.photos(), defectId);

        return defectId;
    }

    public void reviewDefect(UUID defectId, MultipartFile file, UserEntity userEntity) {
        defectRepository.getDefectById(defectId)
                .orElseThrow(() -> new ExceptionInApplication("Defect not found", ExceptionType.NOT_FOUND));

        var fileMetadata = new FileMetadata(
                String.format("defect_%s_review_%s", defectId, UUID.randomUUID()),
                file.getContentType(),
                file.getSize()
        );
        var uploadFileDto = new UploadFileDto(
                fileMetadata,
                file
        );
        storageService.uploadFile(uploadFileDto)
                .doOnSuccess(aVoid -> defectPhotoRepository.createPhoto(
                        new DefectPhotoEntity(
                                fileMetadata.fileName(),
                                defectId,
                                true
                        )
                ))
                .subscribe();
    }

    public void updateDefect(UpdateDefectDto dto) {
        var oldDefect = defectRepository.getDefectById(dto.defectId())
                .orElseThrow(() -> new ExceptionInApplication("Defect not found", ExceptionType.NOT_FOUND));

        var newDefect = new DefectEntity(
                oldDefect.applicationId(),
                oldDefect.statementId(),
                dto.longitude(),
                dto.latitude(),
                dto.status(),
                dto.defectTypeId(),
                dto.address(),
                oldDefect.createdAt(),
                dto.defectDistance()
        );

        defectRepository.updateDefect(newDefect);

        var photos = defectPhotoRepository.getPhotosByDefectId(oldDefect.applicationId());
        for (var photo : photos) {
            defectPhotoRepository.deletePhoto(photo);
        }

        savePhotos(dto.photos(), oldDefect.applicationId());
    }

    public DefectFullDto getDefect(UUID defectId) {
        var defect = defectRepository.getDefectById(defectId)
            .orElseThrow(() -> new ExceptionInApplication("Defect not found", ExceptionType.NOT_FOUND));

        var defectType = defectRepository.getDefectTypeById(defect.defectStatusId())
                .orElseThrow(() -> new ExceptionInApplication("Defect type not found", ExceptionType.NOT_FOUND));

        var imagesBefore = defectPhotoRepository.getPhotosByDefectId(defectId)
                .stream()
                .filter(currentDefect -> !currentDefect.isReview())
                .map(photo -> storageService.getDownloadLinkByName(photo.photoId()))
                .toList();

        var imagesAfter = defectPhotoRepository.getPhotosByDefectId(defectId)
                .stream()
                .filter(DefectPhotoEntity::isReview)
                .map(photo -> storageService.getDownloadLinkByName(photo.photoId()))
                .toList();

        return new DefectFullDto(
                defect.applicationId(),
                defect.latitude(),
                defect.longitude(),
                defect.status(),
                defectType,
                defect.defectDistance(),
                defect.createdAt(),
                imagesBefore,
                imagesAfter
        );
    }

    public List<DefectTypeDto> getDefectTypes() {
        return defectRepository.getDefectTypes();
    }

    private void savePhotos(MultipartFile[] photos, UUID defectId) {
        for (var photo : photos) {
            var fileMetadata = new FileMetadata(
                    String.format("defect_%s_photo_%s", defectId, UUID.randomUUID()),
                    photo.getContentType(),
                    photo.getSize()
            );
            var uploadFileDto = new UploadFileDto(
                    fileMetadata,
                    photo
            );
            storageService.uploadFile(uploadFileDto)
                    .doOnSuccess(aVoid -> defectPhotoRepository.createPhoto(
                            new DefectPhotoEntity(
                                    fileMetadata.fileName(),
                                    defectId,
                                    false
                            )
                    ))
                    .subscribe();
        }
    }
}
