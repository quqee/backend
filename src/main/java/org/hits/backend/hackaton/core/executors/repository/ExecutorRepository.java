package org.hits.backend.hackaton.core.executors.repository;

import org.hits.backend.hackaton.core.executors.repository.entity.ExecutorEntity;

import java.util.UUID;

public interface ExecutorRepository {
    ExecutorEntity save(ExecutorEntity entity);
    boolean existsByStatementIdAndExecutorId(UUID statementId, UUID executorId);
}
