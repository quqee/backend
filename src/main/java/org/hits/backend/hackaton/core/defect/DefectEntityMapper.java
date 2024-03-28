package org.hits.backend.hackaton.core.defect;

import com.example.hackathon.public_.tables.records.ApplicationRecord;
import org.hits.backend.hackaton.public_interface.defect.DefectStatus;
import org.jooq.RecordMapper;

public class DefectEntityMapper implements RecordMapper<ApplicationRecord, DefectEntity> {

    @Override
    public DefectEntity map(ApplicationRecord record) {
        return new DefectEntity(
                record.getApplicationId(),
                record.getStatementId(),
                record.getLongitude(),
                record.getLatitude(),
                DefectStatus.getStatusByName(record.getApplicationStatus()),
                record.getDefectStatusId(),
                record.getAddress(),
                record.getCreatedTime(),
                record.getDefectDistance()
        );
    }
}
