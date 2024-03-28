package org.hits.backend.hackaton.core.statement;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static com.example.hackathon.public_.tables.Statement.STATEMENT;

@Repository
@RequiredArgsConstructor
public class StatementRepositoryImpl implements StatementRepository {
    private static final StatementEntityMapper STATEMENT_ENTITY_MAPPER = new StatementEntityMapper();
    private final DSLContext create;

    @Override
    public UUID createStatement(StatementEntity entity) {
        return create.insertInto(STATEMENT)
                .set(STATEMENT.ORGANIZATION_CREATOR_ID, entity.organizationCreatorId())
                .set(STATEMENT.ORGANIZATION_PERFORMER_ID, entity.organizationPerformerId())
                .set(STATEMENT.CREATE_TIME, entity.creationDate())
                .set(STATEMENT.AREA_NAME, entity.areaName())
                .set(STATEMENT.LENGTH, entity.length())
                .set(STATEMENT.ROAD_TYPE, entity.roadType().name())
                .set(STATEMENT.SURFACE_TYPE, entity.surfaceType().name())
                .set(STATEMENT.DIRECTION, entity.direction())
                .set(STATEMENT.DEADLINE, entity.deadline())
                .set(STATEMENT.DESCRIPTION, entity.description())
                .set(STATEMENT.STATEMENT_STATUS, entity.status().name())
                .returning(STATEMENT.STATEMENT_ID)
                .fetchOne(STATEMENT.STATEMENT_ID);
    }

    @Override
    public void updateStatement(StatementEntity entity) {
        create.update(STATEMENT)
                .set(STATEMENT.ORGANIZATION_CREATOR_ID, entity.organizationCreatorId())
                .set(STATEMENT.ORGANIZATION_PERFORMER_ID, entity.organizationPerformerId())
                .set(STATEMENT.CREATE_TIME, entity.creationDate())
                .set(STATEMENT.AREA_NAME, entity.areaName())
                .set(STATEMENT.LENGTH, entity.length())
                .set(STATEMENT.ROAD_TYPE, entity.roadType().name())
                .set(STATEMENT.SURFACE_TYPE, entity.surfaceType().name())
                .set(STATEMENT.DIRECTION, entity.direction())
                .set(STATEMENT.DEADLINE, entity.deadline())
                .set(STATEMENT.DESCRIPTION, entity.description())
                .set(STATEMENT.STATEMENT_STATUS, entity.status().name())
                .where(STATEMENT.STATEMENT_ID.eq(entity.statementId()))
                .execute();
    }

    @Override
    public Optional<StatementEntity> getStatementById(UUID statementId) {
        return create.selectFrom(STATEMENT)
                .where(STATEMENT.STATEMENT_ID.eq(statementId))
                .fetchOptional(STATEMENT_ENTITY_MAPPER);
    }

}
