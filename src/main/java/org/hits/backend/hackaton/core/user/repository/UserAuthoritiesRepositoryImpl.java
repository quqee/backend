package org.hits.backend.hackaton.core.user.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static com.example.hackathon.public_.tables.UserAuthority.USER_AUTHORITY;

@Repository
@RequiredArgsConstructor
public class UserAuthoritiesRepositoryImpl implements UserAuthoritiesRepository {
    private final DSLContext create;

    @Override
    public void deleteByUserId(UUID userId) {
        create.deleteFrom(USER_AUTHORITY)
                .where(USER_AUTHORITY.USER_ID.eq(userId))
                .execute();
    }
}
