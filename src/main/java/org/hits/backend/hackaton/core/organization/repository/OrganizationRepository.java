package org.hits.backend.hackaton.core.organization.repository;

import org.hits.backend.hackaton.core.organization.repository.entity.OrganizationEntity;

import java.util.Optional;
import java.util.UUID;

public interface OrganizationRepository {
    Optional<OrganizationEntity> findById(UUID organizationId);
}
