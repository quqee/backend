package org.hits.backend.hackaton.rest.statement.v1;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.core.StatementService;
import org.hits.backend.hackaton.public_interface.statement.CreateStatementDto;
import org.hits.backend.hackaton.rest.statement.v1.request.CreateStatementRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<UUID> createStatement(@RequestParam MultipartFile[] audio, @RequestParam OffsetDateTime deadlineTime) {
        var createStatementDto = new CreateStatementDto(
                deadlineTime,
                audio[0]
        );

        return ResponseEntity.ok(statementService.createStatement(createStatementDto));
    }
}
