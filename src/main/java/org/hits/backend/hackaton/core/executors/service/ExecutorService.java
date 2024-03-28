package org.hits.backend.hackaton.core.executors.service;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.core.executors.repository.ExecutorRepository;
import org.hits.backend.hackaton.core.executors.repository.entity.ExecutorEntity;
import org.hits.backend.hackaton.public_interface.exception.ExceptionInApplication;
import org.hits.backend.hackaton.public_interface.exception.ExceptionType;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExecutorService {
    private final ExecutorRepository executorRepository;

    public void assignExecutor(UUID statementId, UUID userId) {
        if (executorRepository.existsByStatementIdAndExecutorId(statementId, userId)) {
            throw new ExceptionInApplication("Executor already assigned", ExceptionType.INVALID);
        }

        var entity = new ExecutorEntity(userId, statementId);
        executorRepository.save(entity);
    }
}
