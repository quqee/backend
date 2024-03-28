package org.hits.backend.hackaton.core.organization;

import java.util.UUID;

public record OrganizationEntity(
        UUID organizationId,
        String name,
        String address,
        String phoneNumber,
        String email,
        OrganizationType organizationType
) {
}
