package org.hits.backend.hackaton.rest.statement.v1;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.core.statement.StatementService;
import org.hits.backend.hackaton.core.statement.RoadType;
import org.hits.backend.hackaton.core.statement.SurfaceType;
import org.hits.backend.hackaton.core.user.repository.entity.UserEntity;
import org.hits.backend.hackaton.public_interface.statement.CreateStatementDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/v1/statement")
public class StatementController {
    private final StatementService statementService;

    @PostMapping
    public ResponseEntity<UUID> createStatement(
            @RequestParam MultipartFile audio,
            @RequestParam String areaName,
            @RequestParam Double length,
            @RequestParam String roadType,
            @RequestParam String surfaceType,
            @RequestParam String direction,
            @RequestParam OffsetDateTime deadline,
            @AuthenticationPrincipal UserEntity userEntity
            ) {
        var createStatementDto = new CreateStatementDto(
                audio,
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
}
