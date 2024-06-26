package org.hits.backend.hackaton.core.user.repository;

import org.hits.backend.hackaton.core.user.repository.entity.UserAuthoritiesEntity;
import org.hits.backend.hackaton.core.user.repository.entity.UserAuthoritiesEnum;
import org.hits.backend.hackaton.core.user.repository.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findById(UUID userId);
    List<UserAuthoritiesEntity> findAuthoritiesByUserId(UUID userId);
    UserEntity createUser(UserEntity entity, List<UserAuthoritiesEnum> authorities);
    UserEntity updateUser(UserEntity entity);
    List<UserEntity> findConnectedUsers(Boolean onlineStatus);
    List<UserEntity> findAllEmployees(UUID organizationId);
    void deleteUser(UUID userId);
}
