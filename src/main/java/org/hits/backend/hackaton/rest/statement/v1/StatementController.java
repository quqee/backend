package org.hits.backend.hackaton.rest.statement.v1;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.core.statement.StatementService;
import org.hits.backend.hackaton.core.statement.RoadType;
import org.hits.backend.hackaton.core.statement.SurfaceType;
import org.hits.backend.hackaton.core.user.repository.entity.UserEntity;
import org.hits.backend.hackaton.public_interface.defect.DefectSmallDto;
import org.hits.backend.hackaton.public_interface.statement.AssignmentEmployee;
import org.hits.backend.hackaton.public_interface.statement.CreateStatementDto;
import org.hits.backend.hackaton.public_interface.statement.StatementFullDto;
import org.hits.backend.hackaton.public_interface.statement.StatementSmallDto;
import org.hits.backend.hackaton.public_interface.statement.StatementStatus;
import org.hits.backend.hackaton.public_interface.statement.UpdateStatementDto;
import org.hits.backend.hackaton.rest.statement.v1.response.DefectSmallResponse;
import org.hits.backend.hackaton.rest.statement.v1.response.StatementFullResponse;
import org.hits.backend.hackaton.rest.statement.v1.response.StatementSmallResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/v1/statements")
public class StatementController {
    private final StatementService statementService;

    @PostMapping
    public ResponseEntity<StatementSmallDto> createStatement(
            @RequestParam("area_name") String areaName,
            @RequestParam Double length,
            @RequestParam("road_type") String roadType,
            @RequestParam("surface_type") String surfaceType,
            @RequestParam String direction,
            @RequestParam OffsetDateTime deadline,
            @AuthenticationPrincipal UserEntity userEntity
    ) {
        var createStatementDto = new CreateStatementDto(
                areaName,
                length,
                RoadType.getTypeByName(roadType),
                SurfaceType.getTypeByName(surfaceType),
                direction,
                deadline,
                userEntity.organizationId()
        );

        return ResponseEntity.ok(statementService.createStatement(createStatementDto));
    }

    @PutMapping
        public ResponseEntity<StatementSmallResponse> updateStatement(
            @RequestParam("file") MultipartFile audio,
            @RequestParam("area_name") String areaName,
            @RequestParam Double length,
            @RequestParam("road_type") String roadType,
            @RequestParam("surface_type") String surfaceType,
            @RequestParam String direction,
            @RequestParam OffsetDateTime deadline,
            @RequestParam("statement_id") UUID statementId,
            @RequestParam(value = "organization_performer_id", required = false) UUID organizationPerformerId,
            @RequestParam("status") String status,
            @AuthenticationPrincipal UserEntity userEntity
    ) {
        var updatedStatementDto = new UpdateStatementDto(
                audio,
                areaName,
                length,
                RoadType.getTypeByName(roadType),
                SurfaceType.getTypeByName(surfaceType),
                direction,
                deadline,
                userEntity.organizationId(),
                statementId,
                organizationPerformerId,
                StatementStatus.getStatusByName(status)
        );

        var updatedStatement = mapToResponse(statementService.updateStatement(updatedStatementDto));
        return ResponseEntity.ok(updatedStatement);
    }

    @GetMapping
    public ResponseEntity<List<StatementSmallResponse>> getAllStatements(
            @RequestParam(required = false) String status) {
        var statements = statementService.getStatements(status);
        var responseStatements = statements.stream().map(this::mapToResponse).toList();
        return ResponseEntity.ok(responseStatements);
    }

    @GetMapping("/{statementId}")
    public ResponseEntity<StatementFullResponse> getStatement(@PathVariable UUID statementId) {
        var response = statementService.getFullDto(statementId);

        return ResponseEntity.ok(mapToResponse(response));
    }

    @GetMapping("/my")
    public ResponseEntity<List<StatementSmallResponse>> getMyStatements(@AuthenticationPrincipal UserEntity userEntity) {
        var response = statementService.getMyStatements(userEntity)
                .stream()
                .map(this::mapToResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{statementId}")
    public ResponseEntity<Void> deleteStatement(@PathVariable UUID statementId) {
        statementService.deleteStatement(statementId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/employee")
    public ResponseEntity<Void> assignEmployeeToStatement(
            @AuthenticationPrincipal UserEntity authentication,
            @RequestBody AssignmentEmployee request) {
        statementService.assignEmployeeToStatement(authentication, request);
        return ResponseEntity.ok().build();
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
                dto.description()
        );
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
