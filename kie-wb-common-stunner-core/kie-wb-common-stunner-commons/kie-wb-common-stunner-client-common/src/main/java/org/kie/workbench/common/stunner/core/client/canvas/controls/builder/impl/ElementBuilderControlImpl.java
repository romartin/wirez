package org.kie.workbench.common.stunner.core.client.canvas.controls.builder.impl;

import org.kie.workbench.common.stunner.core.client.canvas.util.CanvasLayoutUtils;
import org.kie.workbench.common.stunner.core.client.command.Session;
import org.kie.workbench.common.stunner.core.graph.processing.index.bounds.GraphBoundsIndexer;
import org.kie.workbench.common.stunner.core.graph.util.GraphUtils;
import org.kie.workbench.common.stunner.core.rule.model.ModelCardinalityRuleManager;
import org.kie.workbench.common.stunner.core.rule.model.ModelContainmentRuleManager;
import org.kie.workbench.common.stunner.core.client.api.ClientDefinitionManager;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.client.command.CanvasCommandManager;
import org.kie.workbench.common.stunner.core.client.command.factory.CanvasCommandFactory;
import org.kie.workbench.common.stunner.core.client.service.ClientFactoryServices;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * Build element on the canvas, either a node or an edge.
 */
@Dependent
@Element
public class ElementBuilderControlImpl extends AbstractElementBuilderControl {

    private static Logger LOGGER = Logger.getLogger( ElementBuilderControlImpl.class.getName() );

    @Inject
    public ElementBuilderControlImpl( final ClientDefinitionManager clientDefinitionManager,
                                      final ClientFactoryServices clientFactoryServices,
                                      final @Session CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager,
                                      final GraphUtils graphUtils,
                                      final ModelContainmentRuleManager modelContainmentRuleManager,
                                      final ModelCardinalityRuleManager modelCardinalityRuleManager,
                                      final CanvasCommandFactory canvasCommandFactory,
                                      final GraphBoundsIndexer graphBoundsIndexer,
                                      final CanvasLayoutUtils canvasLayoutUtils ) {
        super( clientDefinitionManager, clientFactoryServices, canvasCommandManager, graphUtils,
                modelContainmentRuleManager, modelCardinalityRuleManager, canvasCommandFactory,
                graphBoundsIndexer, canvasLayoutUtils );

    }

}
