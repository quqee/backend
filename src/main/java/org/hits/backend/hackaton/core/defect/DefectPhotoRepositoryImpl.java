package org.hits.backend.hackaton.core.defect;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.example.hackathon.public_.tables.ApplicationPhoto.APPLICATION_PHOTO;

@Repository
@RequiredArgsConstructor
public class DefectPhotoRepositoryImpl implements DefectPhotoRepository {
    private static final DefectPhotoEntityMapper DEFECT_PHOTO_ENTITY_MAPPER = new DefectPhotoEntityMapper();

    private final DSLContext create;


    @Override
    public void createPhoto(DefectPhotoEntity photo) {
        create.insertInto(APPLICATION_PHOTO)
                .set(APPLICATION_PHOTO.PHOTO_ID, photo.photoId())
                .set(APPLICATION_PHOTO.APPLICATION_ID, photo.defectId())
                .set(APPLICATION_PHOTO.IS_REVIEW, photo.isReview())
                .execute();
    }

    @Override
    public void deletePhoto(DefectPhotoEntity photo) {
        create.deleteFrom(APPLICATION_PHOTO)
                .where(APPLICATION_PHOTO.PHOTO_ID.eq(photo.photoId()))
                .execute();
    }

    @Override
    public List<DefectPhotoEntity> getPhotosByDefectId(UUID defectId) {
        return create.selectFrom(APPLICATION_PHOTO)
                .where(APPLICATION_PHOTO.APPLICATION_ID.eq(defectId))
                .fetch(DEFECT_PHOTO_ENTITY_MAPPER);
    }
}
