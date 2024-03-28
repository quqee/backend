package org.hits.backend.hackaton.core.executors.repository;

import org.hits.backend.hackaton.core.executors.repository.entity.ExecutorEntity;

public interface ExecutorRepository {
    ExecutorEntity save(ExecutorEntity entity);
}
