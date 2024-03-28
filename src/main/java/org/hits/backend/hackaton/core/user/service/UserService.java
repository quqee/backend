package org.hits.backend.hackaton.core.user.service;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.core.user.repository.UserAuthoritiesRepository;
import org.hits.backend.hackaton.core.user.repository.UserRepository;
import org.hits.backend.hackaton.core.user.repository.entity.UserAuthoritiesEntity;
import org.hits.backend.hackaton.core.user.repository.entity.UserAuthoritiesEnum;
import org.hits.backend.hackaton.core.user.repository.entity.UserEntity;
import org.hits.backend.hackaton.public_interface.common.PageResponse;
import org.hits.backend.hackaton.public_interface.exception.ExceptionInApplication;
import org.hits.backend.hackaton.public_interface.exception.ExceptionType;
import org.hits.backend.hackaton.public_interface.user.CreateUserDto;
import org.hits.backend.hackaton.public_interface.user.UserDto;
import org.hits.backend.hackaton.rest.organization.v1.response.EmployeeDto;
import org.hits.backend.hackaton.rest.user.v1.request.UpdateUserProfileRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserAuthoritiesRepository userAuthoritiesRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserEntity createUser(CreateUserDto createUserDto) {
        checkIfUsernameExists(createUserDto.username());
        checkIfEmailExists(createUserDto.email());

        var userEntity = new UserEntity(
                UUID.randomUUID(),
                createUserDto.organizationId(),
                createUserDto.username(),
                passwordEncoder.encode(createUserDto.password()),
                createUserDto.email(),
                createUserDto.fullName(),
                false
        );

        return userRepository.createUser(userEntity, List.of(UserAuthoritiesEnum.DEFAULT));
    }

    public void deleteUser(UUID userId) {
        userAuthoritiesRepository.deleteByUserId(userId);
        userRepository.deleteUser(userId);
    }

    public UserEntity findUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ExceptionInApplication("User not found", ExceptionType.NOT_FOUND));
    }

    public List<UserDto> findConnectedUsers(Boolean onlineStatus) {
        var connectedUsers = userRepository.findConnectedUsers(onlineStatus);
        return connectedUsers.stream()
                .map(this::mapEntityToDto)
                .toList();
    }

    public UserDto setUserOnline(UsernamePasswordAuthenticationToken authenticationToken) {
        var userEntity = getUserEntity(authenticationToken);
        return updateUserStatus(userEntity, true);
    }

    public UserDto setUserOffline(UsernamePasswordAuthenticationToken authenticationToken) {
        var userEntity = getUserEntity(authenticationToken);
        return updateUserStatus(userEntity, false);
    }

    private UserDto updateUserStatus(UserEntity user, boolean status) {

        var updatedUserEntity = new UserEntity(
                user.id(),
                user.organizationId(),
                user.username(),
                user.password(),
                user.email(),
                user.fullName(),
                status
        );

        return mapEntityToDto(userRepository.updateUser(updatedUserEntity));
    }

    public List<UserAuthoritiesEntity> findAuthoritiesByUserId(UUID userId) {
        return userRepository.findAuthoritiesByUserId(userId);
    }

    public UserDto getMyProfile(UUID id) {
        var userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ExceptionInApplication("User not found", ExceptionType.NOT_FOUND));
        return mapEntityToDto(userEntity);
    }

    public PageResponse<EmployeeDto> getAllEmployees(UUID organizationId, PageRequest pageable) {
        var employeesEntity = userRepository.findAllEmployees(organizationId, pageable);
        var totalElements = userRepository.countAllEmployees(organizationId);
        var employeesDto = employeesEntity.stream()
                .map(entity -> EmployeeDto.builder()
                        .user_id(entity.id())
                        .username(entity.username())
                        .fullName(entity.fullName())
                        .email(entity.email())
                        .build())
                .toList();

        var metadata = new PageResponse.Metadata(pageable.getPageNumber(), pageable.getPageSize(), totalElements);
        return new PageResponse<>(employeesDto, metadata);
    }

    public UserDto updateMyProfile(UUID id, UpdateUserProfileRequest request) {
        var userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ExceptionInApplication("User not found", ExceptionType.NOT_FOUND));

        var updatedUserEntity = new UserEntity(
                userEntity.id(),
                userEntity.organizationId(),
                userEntity.username(),
                userEntity.password(),
                request.email(),
                request.fullName(),
                false
        );

        var fullUserEntity = userRepository.updateUser(updatedUserEntity);
        return mapEntityToDto(fullUserEntity);
    }

    private UserEntity getUserEntity(UsernamePasswordAuthenticationToken authenticationToken) {
        var principal = authenticationToken.getPrincipal();

        if (!(principal instanceof UserEntity)) {
            throw new ExceptionInApplication("Principal isn't valid", ExceptionType.NOT_FOUND);
        }
        return (UserEntity) authenticationToken.getPrincipal();
    }

    private UserDto mapEntityToDto(UserEntity entity) {
        return new UserDto(
                entity.id(),
                entity.username(),
                entity.email(),
                entity.fullName(),
                entity.onlineStatus()
        );
    }

    private void checkIfUsernameExists(String username) {
        var userEntityOptionalByUsername = userRepository.findByUsername(username);

        if (userEntityOptionalByUsername.isPresent()) {
            throw new ExceptionInApplication("User already exists", ExceptionType.ALREADY_EXISTS);
        }
    }

    private void checkIfEmailExists(String email) {
        var userEntityOptionalByEmail = userRepository.findByEmail(email);

        if (userEntityOptionalByEmail.isPresent()) {
            throw new ExceptionInApplication("User already exists", ExceptionType.ALREADY_EXISTS);
        }
    }

    public boolean checkPassword(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }
}
