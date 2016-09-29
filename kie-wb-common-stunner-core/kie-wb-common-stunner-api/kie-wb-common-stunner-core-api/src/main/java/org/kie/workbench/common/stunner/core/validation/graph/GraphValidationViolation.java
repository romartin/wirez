package org.kie.workbench.common.stunner.core.validation.graph;

import org.kie.workbench.common.stunner.core.rule.RuleViolation;
import org.kie.workbench.common.stunner.core.validation.ValidationViolation;
import org.kie.workbench.common.stunner.core.graph.Element;

public interface GraphValidationViolation extends ValidationViolation<Element<?>> {

    RuleViolation getRuleViolation();

}
