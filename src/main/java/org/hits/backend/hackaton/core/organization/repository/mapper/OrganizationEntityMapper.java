package org.hits.backend.hackaton.core.organization.repository.mapper;

import com.example.hackathon.public_.tables.records.OrganizationRecord;
import org.hits.backend.hackaton.core.organization.repository.entity.OrganizationEntity;
import org.jooq.RecordMapper;

public class OrganizationEntityMapper implements RecordMapper<OrganizationRecord, OrganizationEntity> {

    @Override
    public OrganizationEntity map(OrganizationRecord organizationRecord) {
        return new OrganizationEntity(
                organizationRecord.getOrganizationId(),
                organizationRecord.getName(),
                organizationRecord.getAddress(),
                organizationRecord.getPhoneNumber(),
                organizationRecord.getEmail(),
                organizationRecord.getOrganizationType()
        );
    }
}
