package org.hits.backend.hackaton.core.defect;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.public_interface.defect.DefectTypeDto;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.hackathon.public_.Tables.APPLICATION;
import static com.example.hackathon.public_.Tables.APPLICATION_AUDIO;
import static com.example.hackathon.public_.Tables.DEFECT_STATUS;

@Repository
@RequiredArgsConstructor
public class DefectRepositoryImpl implements DefectRepository {
    private static final DefectEntityMapper DEFECT_ENTITY_MAPPER = new DefectEntityMapper();

    private final DSLContext create;

    @Override
    public List<DefectEntity> getDefectsByStatementId(UUID statementId) {
        return create.selectFrom(APPLICATION)
                .where(APPLICATION.STATEMENT_ID.eq(statementId))
                .fetch(DEFECT_ENTITY_MAPPER);
    }

    @Override
    public Optional<DefectTypeDto> getDefectTypeById(int defectTypeId) {
        return create.selectFrom(DEFECT_STATUS)
                .where(DEFECT_STATUS.ID.eq(defectTypeId))
                .fetchOptional(row -> new DefectTypeDto(
                        row.get(DEFECT_STATUS.ID),
                        row.get(DEFECT_STATUS.NAME),
                        row.get(DEFECT_STATUS.HAS_DISTANCE)
                ));
    }

    @Override
    public Optional<DefectEntity> getDefectById(UUID defectId) {
        return create.selectFrom(APPLICATION)
                .where(APPLICATION.APPLICATION_ID.eq(defectId))
                .fetchOptional(DEFECT_ENTITY_MAPPER);
    }

    @Override
    public UUID createDefect(DefectEntity defect) {
        return create.insertInto(APPLICATION)
                .set(APPLICATION.STATEMENT_ID, defect.statementId())
                .set(APPLICATION.LONGITUDE, defect.longitude())
                .set(APPLICATION.LATITUDE, defect.latitude())
                .set(APPLICATION.APPLICATION_STATUS, defect.status().name())
                .set(APPLICATION.DEFECT_STATUS_ID, defect.defectStatusId())
                .set(APPLICATION.ADDRESS, defect.address())
                .set(APPLICATION.CREATED_TIME, defect.createdAt())
                .set(APPLICATION.DEFECT_DISTANCE, defect.defectDistance())
                .returning(APPLICATION.APPLICATION_ID)
                .fetchOne(APPLICATION.APPLICATION_ID);
    }

    @Override
    public void updateDefect(DefectEntity defect) {
        create.update(APPLICATION)
                .set(APPLICATION.LONGITUDE, defect.longitude())
                .set(APPLICATION.LATITUDE, defect.latitude())
                .set(APPLICATION.APPLICATION_STATUS, defect.status().name())
                .set(APPLICATION.DEFECT_STATUS_ID, defect.defectStatusId())
                .set(APPLICATION.ADDRESS, defect.address())
                .set(APPLICATION.DEFECT_DISTANCE, defect.defectDistance())
                .where(APPLICATION.APPLICATION_ID.eq(defect.applicationId()))
                .execute();
    }

    @Override
    public List<DefectTypeDto> getDefectTypes() {
        return create.selectFrom(DEFECT_STATUS)
                .fetch(row -> new DefectTypeDto(
                        row.get(DEFECT_STATUS.ID),
                        row.get(DEFECT_STATUS.NAME),
                        row.get(DEFECT_STATUS.HAS_DISTANCE)
                ));
    }
}
