package org.hits.backend.hackaton.core.user.repository.mapper;

import com.example.hackathon.public_.tables.records.UserAuthorityRecord;
import org.hits.backend.hackaton.core.user.repository.entity.UserAuthoritiesEntity;
import org.jooq.RecordMapper;

public class UserAuthoritiesEntityMapper implements RecordMapper<UserAuthorityRecord, UserAuthoritiesEntity> {
    @Override
    public UserAuthoritiesEntity map(UserAuthorityRecord record) {
        return new UserAuthoritiesEntity(
                record.getId(),
                record.getUserId(),
                record.getAuthorities()
        );
    }
}
