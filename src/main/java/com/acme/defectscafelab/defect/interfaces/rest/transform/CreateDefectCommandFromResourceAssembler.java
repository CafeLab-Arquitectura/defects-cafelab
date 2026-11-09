package com.acme.defectscafelab.defect.interfaces.rest.transform;

import com.acme.defectscafelab.defect.domain.model.commands.CreateDefectCommand;
import com.acme.defectscafelab.defect.interfaces.rest.resources.CreateDefectResource;

public class CreateDefectCommandFromResourceAssembler {

    public static CreateDefectCommand toCommandFromResource(CreateDefectResource resource) {
        return new CreateDefectCommand(
                resource.userId(),
                resource.coffeeDisplayName(),
                blankToNull(resource.coffeeRegion()),
                blankToNull(resource.coffeeVariety()),
                resource.coffeeTotalWeight(),
                resource.name(),
                resource.defectType(),
                resource.defectWeight(),
                resource.percentage(),
                resource.probableCause(),
                resource.suggestedSolution()
        );
    }

    private static String blankToNull(String s) {
        if (s == null || s.isBlank()) {
            return null;
        }
        return s.trim();
    }
}
