package com.acme.defectscafelab.defect.domain.services;

import com.acme.defectscafelab.defect.domain.model.aggregates.Defect;
import com.acme.defectscafelab.defect.domain.model.queries.GetDefectByIdForUserQuery;
import com.acme.defectscafelab.defect.domain.model.queries.GetDefectsByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface DefectQueryService {

    List<Defect> handle(GetDefectsByUserIdQuery query);

    Optional<Defect> handle(GetDefectByIdForUserQuery query);
}
