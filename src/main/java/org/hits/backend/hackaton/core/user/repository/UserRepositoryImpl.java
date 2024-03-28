package org.hits.backend.hackaton.core.user.repository;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.core.user.repository.entity.UserAuthoritiesEntity;
import org.hits.backend.hackaton.core.user.repository.entity.UserAuthoritiesEnum;
import org.hits.backend.hackaton.core.user.repository.entity.UserEntity;
import org.hits.backend.hackaton.core.user.repository.mapper.UserAuthoritiesEntityMapper;
import org.hits.backend.hackaton.core.user.repository.mapper.UserEntityMapper;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.InsertValuesStepN;
import org.jooq.impl.DSL;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.hackathon.public_.tables.Users.USERS;
import static com.example.hackathon.public_.tables.UserAuthority.USER_AUTHORITY;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private static final UserEntityMapper USER_ENTITY_MAPPER = new UserEntityMapper();
    private static final UserAuthoritiesEntityMapper USER_AUTHORITIES_ENTITY_MAPPER = new UserAuthoritiesEntityMapper();

    private final DSLContext create;

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return create.selectFrom(USERS)
                .where(USERS.USERNAME.eq(username))
                .fetchOptional(USER_ENTITY_MAPPER);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return create.selectFrom(USERS)
                .where(USERS.EMAIL.eq(email))
                .fetchOptional(USER_ENTITY_MAPPER);
    }

    @Override
    public Optional<UserEntity> findById(UUID userId) {
        return create.selectFrom(USERS)
                .where(USERS.USER_ID.eq(userId))
                .fetchOptional(USER_ENTITY_MAPPER);
    }

    @Override
    public List<UserAuthoritiesEntity> findAuthoritiesByUserId(UUID userId) {
        return create.selectFrom(USER_AUTHORITY)
                .where(USER_AUTHORITY.USER_ID.eq(userId))
                .fetch(USER_AUTHORITIES_ENTITY_MAPPER);
    }

    @Override
    @Transactional
    public UserEntity createUser(UserEntity entity, List<UserAuthoritiesEnum> authorities) {
        var user = create.insertInto(USERS)
                .set(USERS.USERNAME, entity.username())
                .set(USERS.EMAIL, entity.email())
                .set(USERS.ORGANIZATION_ID, entity.organizationId())
                .set(USERS.PASSWORD, entity.password())
                .set(USERS.FULL_NAME, entity.fullName())
                .set(USERS.ONLINE_STATUS, entity.onlineStatus())
                .returning(USERS.USER_ID, USERS.USERNAME, USERS.EMAIL, USERS.PASSWORD, USERS.FULL_NAME, USERS.ONLINE_STATUS)
                .fetchOne(USER_ENTITY_MAPPER);

        List<InsertValuesStepN<?>> insert = new ArrayList<>();
        authorities.forEach(a -> insert.add((InsertValuesStepN<?>) create.insertInto(USER_AUTHORITY)
                .set(USER_AUTHORITY.USER_ID, user.id())
                .set(USER_AUTHORITY.AUTHORITIES, a.name())));

        create.batch(insert).execute();

        return user;
    }

    @Override
    public UserEntity updateUser(UserEntity entity) {
        return create.update(USERS)
                .set(USERS.USERNAME, entity.username())
                .set(USERS.EMAIL, entity.email())
                .set(USERS.PASSWORD, entity.password())
                .set(USERS.FULL_NAME, entity.fullName())
                .set(USERS.ONLINE_STATUS, entity.onlineStatus())
                .where(USERS.USER_ID.eq(entity.id()))
                .returning(USERS.USER_ID, USERS.USERNAME, USERS.EMAIL, USERS.PASSWORD, USERS.FULL_NAME, USERS.ONLINE_STATUS)
                .fetchOne(USER_ENTITY_MAPPER);
    }

    @Override
    public List<UserEntity> findConnectedUsers(Boolean onlineStatus) {
        var query = create.selectFrom(USERS);

        Condition condition = DSL.trueCondition();

        if (onlineStatus != null) {
            condition = condition.and(USERS.ONLINE_STATUS.eq(onlineStatus));
        }

        return query.where(condition)
                .fetch(USER_ENTITY_MAPPER);
    }

    @Override
    public List<UserEntity> findAllEmployees(UUID organizationId, PageRequest pageable) {
        var offset = pageable.getPageNumber() * pageable.getPageSize();
        var limit = pageable.getPageSize();

        return create.selectFrom(USERS)
                .where(USERS.ORGANIZATION_ID.eq(organizationId))
                .limit(limit)
                .offset(offset)
                .fetch(USER_ENTITY_MAPPER);
    }

    @Override
    public int countAllEmployees(UUID organizationId) {
        return create.fetchCount(USERS, USERS.ORGANIZATION_ID.eq(organizationId));
    }

    @Override
    public void deleteUser(UUID userId) {
        create.deleteFrom(USERS)
                .where(USERS.USER_ID.eq(userId))
                .execute();
    }
}
