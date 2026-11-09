package com.acme.defectscafelab.defect.interfaces.rest.transform;

import com.acme.defectscafelab.defect.domain.model.aggregates.Defect;
import com.acme.defectscafelab.defect.interfaces.rest.resources.DefectResource;

public class DefectResourceFromEntityAssembler {

    public static DefectResource toResourceFromEntity(Defect entity) {
        return new DefectResource(
                entity.getId(),
                entity.getUserId().userId(),
                entity.getCoffeeDisplayName(),
                entity.getCoffeeRegion(),
                entity.getCoffeeVariety(),
                entity.getCoffeeTotalWeight(),
                entity.getName(),
                entity.getDefectType(),
                entity.getDefectWeight(),
                entity.getPercentage(),
                entity.getProbableCause(),
                entity.getSuggestedSolution()
        );
    }
}
