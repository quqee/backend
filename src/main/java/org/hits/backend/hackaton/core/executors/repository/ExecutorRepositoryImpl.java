package org.hits.backend.hackaton.core.executors.repository;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.core.executors.repository.entity.ExecutorEntity;
import org.hits.backend.hackaton.core.executors.repository.mapper.ExecutorEntityMapper;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static com.example.hackathon.public_.tables.Executor.EXECUTOR;

@Repository
@RequiredArgsConstructor
public class ExecutorRepositoryImpl implements ExecutorRepository {
    private static final ExecutorEntityMapper EXECUTOR_ENTITY_MAPPER = new ExecutorEntityMapper();
    private final DSLContext dslContext;

    @Override
    public ExecutorEntity save(ExecutorEntity entity) {
        return dslContext.insertInto(EXECUTOR)
                .set(EXECUTOR.USER_ID, entity.userId())
                .set(EXECUTOR.STATEMENT_ID, entity.statementId())
                .returning(EXECUTOR.USER_ID, EXECUTOR.STATEMENT_ID)
                .fetchOne(EXECUTOR_ENTITY_MAPPER);
    }

    @Override
    public boolean existsByStatementIdAndExecutorId(UUID statementId, UUID executorId) {
        return dslContext.fetchExists(
                dslContext.selectOne()
                        .from(EXECUTOR)
                        .where(EXECUTOR.STATEMENT_ID.eq(statementId))
                        .and(EXECUTOR.USER_ID.eq(executorId))
        );
    }
}
