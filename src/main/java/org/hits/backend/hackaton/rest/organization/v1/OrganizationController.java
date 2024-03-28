package org.hits.backend.hackaton.rest.organization.v1;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.core.organization.service.OrganizationService;
import org.hits.backend.hackaton.core.user.repository.entity.UserEntity;
import org.hits.backend.hackaton.public_interface.common.PageResponse;
import org.hits.backend.hackaton.public_interface.organization.EmployeeRequest;
import org.hits.backend.hackaton.rest.organization.v1.request.AddEmployeeRequest;
import org.hits.backend.hackaton.rest.organization.v1.response.EmployeeDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/v1/organizations")
public class OrganizationController {
    private final OrganizationService organizationService;

    @PostMapping()
    public EmployeeDto addEmployee(@AuthenticationPrincipal UserEntity authentication,
                                   @RequestBody AddEmployeeRequest request) {
        var employeeRequest = EmployeeRequest.builder()
                .username(request.username())
                .email(request.email())
                .fullName(request.fullName())
                .password(request.password())
                .build();

        return organizationService.addEmployee(authentication, employeeRequest);
    }

    @GetMapping("/employee/all")
    public PageResponse<EmployeeDto> getAllEmployees(@AuthenticationPrincipal UserEntity authentication,
                                                     @RequestParam(required = false, defaultValue = "0") int page,
                                                     @RequestParam(required = false, defaultValue = "10") int size) {
        return organizationService.getAllEmployees(authentication, page, size);
    }

    @GetMapping("/{employeeId}")
    public EmployeeDto getEmployee(@AuthenticationPrincipal UserEntity authentication,
                                   @PathVariable String employeeId) {
        return organizationService.getEmployee(UUID.fromString(employeeId), authentication);
    }

    @DeleteMapping("/{employeeId}")
    public void deleteEmployee(@AuthenticationPrincipal UserEntity authentication,
                               @PathVariable String employeeId) {
        organizationService.deleteEmployee(UUID.fromString(employeeId), authentication);
    }
}
