package org.hits.backend.hackaton.core.executors.repository.mapper;


import com.example.hackathon.public_.tables.records.ExecutorRecord;
import org.hits.backend.hackaton.core.executors.repository.entity.ExecutorEntity;
import org.jooq.RecordMapper;

public class ExecutorEntityMapper implements RecordMapper<ExecutorRecord, ExecutorEntity> {

    @Override
    public ExecutorEntity map(ExecutorRecord executorRecord) {
        return new ExecutorEntity(
                executorRecord.getUserId(),
                executorRecord.getStatementId()
        );
    }
}
