package org.wirez.client.lienzo.canvas.controls.toolbox.command.builder;

import com.ait.lienzo.client.core.shape.IPrimitive;
import org.wirez.core.client.api.ClientDefinitionManager;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.animation.AnimationFactory;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.controls.builder.NodeBuilderControl;
import org.wirez.core.client.canvas.controls.toolbox.command.builder.NewNodeCommand;
import org.wirez.core.client.canvas.event.selection.CanvasElementSelectedEvent;
import org.wirez.core.client.canvas.util.CanvasLayoutUtils;
import org.wirez.core.client.components.drag.NodeDragProxy;
import org.wirez.core.client.components.glyph.DefinitionGlyphTooltip;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.definition.util.DefinitionUtils;
import org.wirez.core.graph.processing.index.bounds.GraphBoundsIndexer;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Dependent
public class LienzoNewNodeToolboxCommand extends NewNodeCommand<IPrimitive<?>> {

    protected LienzoNewNodeToolboxCommand() {
        this( null, null, null, null, null, null, null, null, null, null );
    }

    @Inject
    public LienzoNewNodeToolboxCommand(final ClientDefinitionManager clientDefinitionManager,
                                       final ClientFactoryServices clientFactoryServices,
                                       final ShapeManager shapeManager,
                                       final DefinitionGlyphTooltip<?> glyphTooltip,
                                       final GraphBoundsIndexer graphBoundsIndexer,
                                       final NodeDragProxy<AbstractCanvasHandler> nodeDragProxyFactory,
                                       final NodeBuilderControl<AbstractCanvasHandler> nodeBuilderControl,
                                       final DefinitionUtils definitionUtils,
                                       final CanvasLayoutUtils canvasLayoutUtils,
                                       final Event<CanvasElementSelectedEvent> elementSelectedEvent ) {
        super( clientDefinitionManager, clientFactoryServices, shapeManager, glyphTooltip, graphBoundsIndexer,
                nodeDragProxyFactory, nodeBuilderControl,  definitionUtils, canvasLayoutUtils, elementSelectedEvent );
    }

    @PostConstruct
    public void init() {
        glyphTooltip.setPrefix( "Create a new " );
    }


}
