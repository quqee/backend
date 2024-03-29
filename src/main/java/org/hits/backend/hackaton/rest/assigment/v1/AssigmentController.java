package org.hits.backend.hackaton.rest.assigment.v1;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.core.assigment.AssigmentService;
import org.hits.backend.hackaton.public_interface.assigment.CreateAssigmentDto;
import org.hits.backend.hackaton.public_interface.defect.DefectSmallDto;
import org.hits.backend.hackaton.public_interface.statement.StatementFullDto;
import org.hits.backend.hackaton.rest.statement.v1.response.DefectSmallResponse;
import org.hits.backend.hackaton.rest.statement.v1.response.StatementFullResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/v1/assigment")
public class AssigmentController {
    private final AssigmentService assigmentService;

    @PostMapping
    public ResponseEntity<StatementFullResponse> createAssigment(
            @RequestBody CreateAssigmentRequest request
    ) {
        var dto = new CreateAssigmentDto(
                request.statementId(),
                request.emails()
        );
        var response = mapToResponse(assigmentService.createAssigment(dto));
        return ResponseEntity.ok(response);
    }

    private StatementFullResponse mapToResponse(StatementFullDto dto) {
        return new StatementFullResponse(
                dto.statementId(),
                dto.areaName(),
                dto.length(),
                dto.roadType().name(),
                dto.surfaceType().name(),
                dto.direction(),
                dto.deadline(),
                dto.createdAt(),
                dto.description(),
                dto.status().name(),
                dto.organizationCreatorId(),
                dto.organizationPerformerId(),
                dto.defects().stream().map(this::mapToResponse).toList()
        );
    }

    private DefectSmallResponse mapToResponse(DefectSmallDto dto) {
        return new DefectSmallResponse(
                dto.defectId(),
                dto.type().name(),
                dto.status().name(),
                dto.description(),
                dto.latitude(),
                dto.longitude()
        );
    }
}
