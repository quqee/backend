package org.hits.backend.hackaton.core.organization.service;

import lombok.AllArgsConstructor;
import org.hits.backend.hackaton.core.organization.repository.OrganizationRepository;
import org.hits.backend.hackaton.core.user.repository.entity.UserEntity;
import org.hits.backend.hackaton.core.user.service.UserService;
import org.hits.backend.hackaton.public_interface.common.PageResponse;
import org.hits.backend.hackaton.public_interface.exception.ExceptionInApplication;
import org.hits.backend.hackaton.public_interface.exception.ExceptionType;
import org.hits.backend.hackaton.public_interface.organization.EmployeeRequest;
import org.hits.backend.hackaton.public_interface.user.CreateUserDto;
import org.hits.backend.hackaton.rest.organization.v1.response.EmployeeDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class OrganizationService {
    private OrganizationRepository organizationRepository;
    private UserService userService;

    public EmployeeDto addEmployee(UserEntity authentication, EmployeeRequest request) {
        checkIfOrganizationExists(authentication);

        var createUserDto = new CreateUserDto(
                authentication.organizationId(),
                request.username(),
                request.password(),
                request.email(),
                request.fullName()
        );

        var user = userService.createUser(createUserDto);

        return EmployeeDto.builder()
                .user_id(user.id())
                .username(user.username())
                .fullName(user.fullName())
                .email(user.email())
                .build();
    }

    public PageResponse<EmployeeDto> getAllEmployees(UserEntity authentication, int page, int size) {
        checkIfOrganizationExists(authentication);

        PageRequest pageable = PageRequest.of(page, size);
        return userService.getAllEmployees(authentication.organizationId(), pageable);
    }

    public EmployeeDto getEmployee(UUID employeeId, UserEntity authentication) {
        checkIfOrganizationExists(authentication);

        var user = userService.findUserById(employeeId);

        if (!user.organizationId().equals(authentication.organizationId())) {
            throw new ExceptionInApplication("Employee not found", ExceptionType.NOT_FOUND);
        }

        return EmployeeDto.builder()
                .user_id(user.id())
                .username(user.username())
                .fullName(user.fullName())
                .email(user.email())
                .build();
    }

    private void checkIfOrganizationExists(UserEntity authentication) {
        organizationRepository.findById(authentication.organizationId())
                .orElseThrow(() -> new ExceptionInApplication("Organization not found", ExceptionType.NOT_FOUND));
    }
}
