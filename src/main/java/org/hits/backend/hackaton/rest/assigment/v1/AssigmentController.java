package org.hits.backend.hackaton.rest.assigment.v1;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.core.assigment.AssigmentService;
import org.hits.backend.hackaton.core.user.repository.entity.UserEntity;
import org.hits.backend.hackaton.public_interface.statement.StatementSmallDto;
import org.hits.backend.hackaton.rest.statement.v1.response.StatementSmallResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/v1/assignents")
public class AssigmentController {
    private final AssigmentService assigmentService;

    @GetMapping("/my")
    public ResponseEntity<List<StatementSmallResponse>> getMyAssigments(@AuthenticationPrincipal UserEntity userEntity) {
        var response = assigmentService.getMyAssigments(userEntity)
                .stream()
                .map(this::mapToResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    private StatementSmallResponse mapToResponse(StatementSmallDto dto) {
        return new StatementSmallResponse(
                dto.statementId(),
                dto.areaName(),
                dto.length(),
                dto.roadType().name(),
                dto.surfaceType().name(),
                dto.direction(),
                dto.deadline(),
                dto.createTime(),
                dto.description(),
                dto.statementStatus().name(),
                dto.organizationPerformerId(),
                dto.organizationCreatorId()
        );
    }
}
