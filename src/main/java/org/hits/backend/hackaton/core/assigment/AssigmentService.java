package org.hits.backend.hackaton.core.assigment;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.core.defect.DefectService;
import org.hits.backend.hackaton.core.mail.MailFormatter;
import org.hits.backend.hackaton.core.mail.MailService;
import org.hits.backend.hackaton.core.statement.StatementEntity;
import org.hits.backend.hackaton.core.statement.StatementRepository;
import org.hits.backend.hackaton.core.statement.StatementService;
import org.hits.backend.hackaton.public_interface.assigment.CreateAssigmentDto;
import org.hits.backend.hackaton.public_interface.exception.ExceptionInApplication;
import org.hits.backend.hackaton.public_interface.exception.ExceptionType;
import org.hits.backend.hackaton.public_interface.statement.StatementFullDto;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssigmentService {
    private final StatementService statementService;
    private final StatementRepository statementRepository;
    private final MailFormatter mailFormatter;
    private final DefectService defectService;
    private final MailService mailService;

    public StatementFullDto createAssigment(CreateAssigmentDto dto) {
        var statement = statementRepository.getStatementById(dto.statementId())
                .orElseThrow(() -> new ExceptionInApplication("Statement not found", ExceptionType.NOT_FOUND));

        var newStatement = new StatementEntity(
                statement.statementId(),
                statement.organizationCreatorId(),
                statement.organizationCreatorId(),
                statement.creationDate(),
                statement.areaName(),
                statement.length(),
                statement.roadType(),
                statement.surfaceType(),
                statement.direction(),
                statement.deadline(),
                statement.description(),
                statement.status()
        );
        statementRepository.updateStatement(newStatement);

        var fullStatement = statementService.getFullDto(newStatement.statementId());
        var fullDefects = fullStatement.defects()
                .stream()
                .map(small -> defectService.getDefect(small.defectId()))
                .toList();

        var text = mailFormatter.formatFullStatement(fullStatement, fullDefects);
        sendEmails(dto.emails(), text);

        return fullStatement;
    }

    public void sendEmails(List<String> emails, String text) {
        emails.forEach(email -> mailService.sendMessage(text, email, "Новое поручение"));
    }
}
