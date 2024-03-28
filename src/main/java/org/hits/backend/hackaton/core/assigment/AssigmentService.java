package org.hits.backend.hackaton.core.assigment;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.core.statement.StatementEntity;
import org.hits.backend.hackaton.core.statement.StatementRepository;
import org.hits.backend.hackaton.core.user.repository.entity.UserEntity;
import org.hits.backend.hackaton.public_interface.statement.StatementSmallDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssigmentService {
    private final StatementRepository statementRepository;

    public List<StatementSmallDto> getMyAssigments(UserEntity userEntity) {
        return statementRepository.getAssigmentsByOrganizationId(userEntity.organizationId())
                .map(this::mapToDto)
                .toList();
    }

    private StatementSmallDto mapToDto(StatementEntity entity) {
        return new StatementSmallDto(
                entity.statementId(),
                entity.areaName(),
                entity.length(),
                entity.roadType(),
                entity.surfaceType(),
                entity.direction(),
                entity.deadline(),
                entity.creationDate(),
                entity.description(),
                entity.status(),
                entity.organizationPerformerId(),
                entity.organizationCreatorId()
        );
    }
}
