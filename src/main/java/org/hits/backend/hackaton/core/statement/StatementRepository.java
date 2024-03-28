package org.hits.backend.hackaton.core.statement;

import java.util.Optional;
import java.util.UUID;

public interface StatementRepository {
    UUID createStatement(StatementEntity entity);
    void updateStatement(StatementEntity entity);
    Optional<StatementEntity> getStatementById(UUID statementId);
    void deleteStatement(UUID statementId);
}
