package org.kie.workbench.common.stunner.core.client.validation.canvas;

import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;
import org.kie.workbench.common.stunner.core.rule.graph.GraphRulesManager;
import org.kie.workbench.common.stunner.core.validation.Validator;

public interface CanvasValidator extends Validator<CanvasHandler, CanvasValidatorCallback> {

    CanvasValidator withRulesManager( GraphRulesManager rulesManager );

}
