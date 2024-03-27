package org.hits.backend.hackaton.core.user.repository.mapper;

import com.example.hackathon.public_.tables.records.UserAuthoritiesRecord;
import org.hits.backend.hackaton.core.user.repository.entity.UserAuthoritiesEntity;
import org.jooq.RecordMapper;

public class UserAuthoritiesEntityMapper implements RecordMapper<UserAuthoritiesRecord, UserAuthoritiesEntity> {
    @Override
    public UserAuthoritiesEntity map(UserAuthoritiesRecord record) {
        return new UserAuthoritiesEntity(
                record.getId(),
                record.getUserId(),
                record.getAuthorities()
        );
    }
}
