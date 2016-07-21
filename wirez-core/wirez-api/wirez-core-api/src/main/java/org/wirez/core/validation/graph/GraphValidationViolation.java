package org.wirez.core.validation.graph;

import org.wirez.core.graph.Element;
import org.wirez.core.rule.RuleViolation;
import org.wirez.core.validation.ValidationViolation;

public interface GraphValidationViolation extends ValidationViolation<Element<?>> {

    RuleViolation getRuleViolation();

}
