package org.hits.backend.hackaton.core.executors.service;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.core.executors.repository.ExecutorRepository;
import org.hits.backend.hackaton.core.executors.repository.entity.ExecutorEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExecutorService {
    private final ExecutorRepository executorRepository;

    public ExecutorEntity assignExecutor(UUID statementId, UUID userId) {
        var entity = new ExecutorEntity(userId, statementId);
        return executorRepository.save(entity);
    }
}
