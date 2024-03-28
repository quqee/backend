package org.hits.backend.hackaton.core.user.repository.mapper;

import com.example.hackathon.public_.tables.records.UsersRecord;
import org.hits.backend.hackaton.core.user.repository.entity.UserEntity;
import org.jooq.RecordMapper;

public class UserEntityMapper implements RecordMapper<UsersRecord, UserEntity> {
    @Override
    public UserEntity map(UsersRecord record) {
        return new UserEntity(
                record.getUserId(),
                record.getUsername(),
                record.getPassword(),
                record.getEmail(),
                record.getFullName(),
                record.getOnlineStatus(),
                record.getOrganizationId()
        );
    }
}
