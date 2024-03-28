package org.hits.backend.hackaton.rest.defect.v1;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.core.defect.DefectService;
import org.hits.backend.hackaton.core.user.repository.entity.UserEntity;
import org.hits.backend.hackaton.public_interface.defect.CreateDefectDto;
import org.hits.backend.hackaton.public_interface.defect.DefectFullDto;
import org.hits.backend.hackaton.public_interface.defect.DefectStatus;
import org.hits.backend.hackaton.public_interface.defect.DefectTypeDto;
import org.hits.backend.hackaton.public_interface.defect.UpdateDefectDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/v1/defects")
public class DefectController {
    private final DefectService defectService;

    @PostMapping
    public ResponseEntity<UUID> createDefect(
            @RequestParam("photos") MultipartFile[] photos,
            @RequestParam("longitude") Double longitude,
            @RequestParam("latitude") Double latitude,
            @RequestParam("defect_type_id") Integer defectTypeId,
            @RequestParam(value = "defect_distance", required = false) Double defectDistance,
            @RequestParam("statement_id") UUID statementId,
            @RequestParam("address") String address,
            @AuthenticationPrincipal UserEntity userEntity) {
        var dto = new CreateDefectDto(
                longitude,
                latitude,
                defectTypeId,
                defectDistance,
                photos,
                statementId,
                userEntity.id(),
                address
        );

        return ResponseEntity.ok(defectService.createDefect(dto));
    }

    @PutMapping
    public ResponseEntity<Void> updateDefect(
            @RequestParam("defect_id") UUID defectId,
            @RequestParam("photos") MultipartFile[] photos,
            @RequestParam("longitude") Double longitude,
            @RequestParam("latitude") Double latitude,
            @RequestParam("defect_type_id") Integer defectTypeId,
            @RequestParam(value = "defect_distance", required = false) Double defectDistance,
            @RequestParam("statement_id") UUID statementId,
            @RequestParam("address") String address,
            @RequestParam("defect_status") String defectStatus,
            @AuthenticationPrincipal UserEntity userEntity
    ) {
        var dto = new UpdateDefectDto(
                longitude,
                latitude,
                defectTypeId,
                defectDistance,
                photos,
                statementId,
                userEntity.id(),
                address,
                defectId,
                DefectStatus.getStatusByName(defectStatus)
        );

        defectService.updateDefect(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{defectId}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable UUID defectId,
            @RequestBody UpdateDefectStatusRequest request
    ) {
        defectService.updateStatus(defectId, DefectStatus.getStatusByName(request.status()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{defectId}/review")
    public ResponseEntity<Void> reviewDefect(
            @PathVariable UUID defectId,
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal UserEntity userEntity
    ) {
        defectService.reviewDefect(defectId, file, userEntity);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{defectId}")
    public ResponseEntity<DefectFullResponse> getDefect(@PathVariable UUID defectId) {
        var response = mapToResponse(defectService.getDefect(defectId));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/types")
    public ResponseEntity<List<DefectTypeResponse>> getDefectTypes() {
        var response = defectService.getDefectTypes()
                .stream()
                .map(this::mapToResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    private DefectFullResponse mapToResponse(DefectFullDto dto) {
        return new DefectFullResponse(
                dto.defectId(),
                dto.latitude(),
                dto.longitude(),
                dto.status().name(),
                dto.type().name(),
                dto.defectDistance(),
                dto.creationDate(),
                dto.imagesBefore(),
                dto.imagesAfter()
        );
    }

    private DefectTypeResponse mapToResponse(DefectTypeDto dto) {
        return new DefectTypeResponse(
                dto.id(),
                dto.name(),
                dto.hasDistance()
        );
    }
}
