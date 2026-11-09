package com.acme.defectscafelab.defect.application.acl;

import com.acme.defectscafelab.defect.domain.model.commands.CreateDefectCommand;
import com.acme.defectscafelab.defect.domain.services.DefectCommandService;
import com.acme.defectscafelab.defect.interfaces.acl.DefectsContextFacade;
import org.springframework.stereotype.Service;

@Service
public class DefectsContextFacadeImpl implements DefectsContextFacade {
    private final DefectCommandService defectCommandService;

    public DefectsContextFacadeImpl(DefectCommandService defectCommandService) {
        this.defectCommandService = defectCommandService;
    }

    @Override
    public Long createDefect(CreateDefectCommand command) {
        var defect = defectCommandService.handle(command);
        return defect.isEmpty() ? 0L : defect.get().getId();
    }
}
