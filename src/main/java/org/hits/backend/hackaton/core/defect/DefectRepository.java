package org.hits.backend.hackaton.core.defect;

import org.hits.backend.hackaton.public_interface.defect.DefectTypeDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DefectRepository {
    List<DefectEntity> getDefectsByStatementId(UUID statementId);
    Optional<DefectTypeDto> getDefectTypeById(int defectTypeId);
    Optional<DefectEntity> getDefectById(UUID defectId);
    UUID createDefect(DefectEntity defect);
    void updateDefect(DefectEntity defect);
}
