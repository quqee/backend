package org.hits.backend.hackaton.core.organization;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static com.example.hackathon.public_.tables.Organization.ORGANIZATION;

@Repository
@RequiredArgsConstructor
public class OrganizationRepositoryImpl implements OrganizationRepository {
    private static final OrganizationEntityMapper ORGANIZATION_ENTITY_MAPPER = new OrganizationEntityMapper();

    private final DSLContext create;


}
