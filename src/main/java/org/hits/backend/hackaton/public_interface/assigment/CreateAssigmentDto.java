package org.hits.backend.hackaton.public_interface.assigment;

import java.util.List;
import java.util.UUID;

public record CreateAssigmentDto(
        UUID statementId,
        List<String> emails
) {
}
