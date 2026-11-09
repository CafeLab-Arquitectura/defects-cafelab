package com.acme.defectscafelab.defect.interfaces.acl;

import com.acme.defectscafelab.defect.domain.model.commands.CreateDefectCommand;

public interface DefectsContextFacade {

    Long createDefect(CreateDefectCommand command);
}
