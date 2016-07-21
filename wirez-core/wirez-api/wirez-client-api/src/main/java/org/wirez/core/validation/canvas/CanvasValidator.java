package org.wirez.core.validation.canvas;

import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.rule.graph.GraphRulesManager;
import org.wirez.core.validation.Validator;

public interface CanvasValidator extends Validator<CanvasHandler, CanvasValidatorCallback> {

    CanvasValidator withRulesManager( GraphRulesManager rulesManager );

}
