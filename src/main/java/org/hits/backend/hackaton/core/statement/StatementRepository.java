package org.hits.backend.hackaton.core.statement;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface StatementRepository {
    UUID createStatement(StatementEntity entity);
    void updateStatement(StatementEntity entity);
    Optional<StatementEntity> getStatementById(UUID statementId);
    void deleteStatement(UUID statementId);
    List<StatementEntity> getStatements(String statementStatus);
    Stream<StatementEntity> getStatementsByOrganizationId(UUID organizationId);
    Stream<StatementEntity> getStatementsByExecutorId(UUID executorId);
    Stream<StatementEntity> getAssigmentsByOrganizationId(UUID organizationId);
}
