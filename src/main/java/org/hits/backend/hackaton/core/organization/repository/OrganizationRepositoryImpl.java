package org.hits.backend.hackaton.core.organization.repository;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.core.organization.repository.entity.OrganizationEntity;
import org.hits.backend.hackaton.core.organization.repository.mapper.OrganizationEntityMapper;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static com.example.hackathon.public_.tables.Organization.ORGANIZATION;

@Repository
@RequiredArgsConstructor
public class OrganizationRepositoryImpl implements OrganizationRepository {
    private static final OrganizationEntityMapper ORGANIZATION_ENTITY_MAPPER = new OrganizationEntityMapper();
    private final DSLContext dslContext;


    @Override
    public Optional<OrganizationEntity> findById(UUID organizationId) {
        return dslContext.selectFrom(ORGANIZATION)
                .where(ORGANIZATION.ORGANIZATION_ID.eq(organizationId))
                .fetchOptional(ORGANIZATION_ENTITY_MAPPER);
    }
}
