package org.hits.backend.hackaton.core.defect;

import java.util.List;
import java.util.UUID;

public interface DefectPhotoRepository {
    void createPhoto(DefectPhotoEntity photo);
    void deletePhoto(DefectPhotoEntity photo);
    List<DefectPhotoEntity> getPhotosByDefectId(UUID defectId);
}
