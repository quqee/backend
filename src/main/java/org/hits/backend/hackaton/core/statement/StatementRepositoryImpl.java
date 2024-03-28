package org.hits.backend.hackaton.core.statement;

import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static com.example.hackathon.public_.tables.Statement.STATEMENT;
import static com.example.hackathon.public_.tables.Executor.EXECUTOR;

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

    @Override
    public void deleteStatement(UUID statementId) {
        create.deleteFrom(STATEMENT)
                .where(STATEMENT.STATEMENT_ID.eq(statementId))
                .execute();
    }

    @Override
    public List<StatementEntity> getStatements(String statementStatus) {
        var query = create.selectFrom(STATEMENT);

        Condition condition = DSL.trueCondition();

        if (statementStatus != null && !statementStatus.isEmpty()) {
            condition = condition.and(STATEMENT.STATEMENT_STATUS.eq(statementStatus));
        }

        return query.where(condition)
                .fetch()
                .map(STATEMENT_ENTITY_MAPPER);
    }

    @Override
    public Stream<StatementEntity> getStatementsByOrganizationId(UUID organizationId) {
        return create.selectFrom(STATEMENT)
                .where(STATEMENT.ORGANIZATION_CREATOR_ID.eq(organizationId))
                .or(STATEMENT.ORGANIZATION_PERFORMER_ID.eq(organizationId))
                .fetchStream()
                .map(STATEMENT_ENTITY_MAPPER);
    }

    @Override
    public Stream<StatementEntity> getStatementsByExecutorId(UUID executorId) {
        return create.select(STATEMENT.fields())
                .from(STATEMENT)
                .join(EXECUTOR)
                .on(STATEMENT.STATEMENT_ID.eq(EXECUTOR.STATEMENT_ID))
                .and(EXECUTOR.USER_ID.eq(executorId))
                .fetchStream()
                .map(row -> STATEMENT_ENTITY_MAPPER.map(row.into(STATEMENT)));
    }

    @Override
    public Stream<StatementEntity> getAssigmentsByOrganizationId(UUID organizationId) {
        return create.selectFrom(STATEMENT)
                .where(STATEMENT.ORGANIZATION_CREATOR_ID.eq(organizationId))
                .and(STATEMENT.ORGANIZATION_PERFORMER_ID.isNull())
                .fetchStream()
                .map(STATEMENT_ENTITY_MAPPER);
    }

}
