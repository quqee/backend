package org.hits.backend.hackaton.core.statement;

import com.example.hackathon.public_.tables.records.StatementRecord;
import org.hits.backend.hackaton.public_interface.statement.StatementStatus;
import org.jooq.RecordMapper;

public class StatementEntityMapper implements RecordMapper<StatementRecord, StatementEntity> {

    @Override
    public StatementEntity map(StatementRecord statementRecord) {
        return new StatementEntity(
                statementRecord.getStatementId(),
                statementRecord.getOrganizationCreatorId(),
                statementRecord.getOrganizationPerformerId(),
                statementRecord.getCreateTime(),
                statementRecord.getAreaName(),
                statementRecord.getLength(),
                RoadType.getTypeByName(statementRecord.getRoadType()),
                SurfaceType.getTypeByName(statementRecord.getSurfaceType()),
                statementRecord.getDirection(),
                statementRecord.getDeadline(),
                statementRecord.getDescription(),
                StatementStatus.getStatusByName(statementRecord.getStatementStatus())
        );
    }
}
