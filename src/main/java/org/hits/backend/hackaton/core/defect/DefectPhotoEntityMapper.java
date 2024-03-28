package org.hits.backend.hackaton.core.defect;

import com.example.hackathon.public_.tables.records.ApplicationPhotoRecord;
import org.jooq.RecordMapper;

public class DefectPhotoEntityMapper implements RecordMapper<ApplicationPhotoRecord, DefectPhotoEntity> {
    @Override
    public DefectPhotoEntity map(ApplicationPhotoRecord record) {
        return new DefectPhotoEntity(
                record.getPhotoId(),
                record.getApplicationId()
        );
    }
}
