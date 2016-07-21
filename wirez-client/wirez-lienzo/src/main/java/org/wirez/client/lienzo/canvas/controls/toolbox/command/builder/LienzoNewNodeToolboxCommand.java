package org.wirez.client.lienzo.canvas.controls.toolbox.command.builder;

import com.ait.lienzo.client.core.shape.IPrimitive;
import org.wirez.core.client.ClientDefinitionManager;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.animation.AnimationFactory;
import org.wirez.core.client.animation.ShapeAnimation;
import org.wirez.core.client.animation.ShapeDeSelectionAnimation;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.controls.builder.NodeBuilderControl;
import org.wirez.core.client.canvas.controls.toolbox.command.builder.NewNodeCommand;
import org.wirez.core.client.components.drag.NodeDragProxyFactory;
import org.wirez.core.client.components.glyph.DefinitionGlyphTooltip;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.definition.util.DefinitionUtils;
import org.wirez.core.graph.processing.index.bounds.GraphBoundsIndexer;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class LienzoNewNodeToolboxCommand extends NewNodeCommand<IPrimitive<?>> {

    protected LienzoNewNodeToolboxCommand() {
        this( null, null, null, null, null, null, null, null, null );
    }

    @Inject
    public LienzoNewNodeToolboxCommand(final ClientDefinitionManager clientDefinitionManager,
                                       final ClientFactoryServices clientFactoryServices,
                                       final ShapeManager shapeManager,
                                       final DefinitionGlyphTooltip<?> glyphTooltip,
                                       final GraphBoundsIndexer graphBoundsIndexer,
                                       final NodeDragProxyFactory<AbstractCanvasHandler> nodeDragProxyFactory,
                                       final NodeBuilderControl<AbstractCanvasHandler> nodeBuilderControl,
                                       final AnimationFactory animationFactory,
                                       final DefinitionUtils definitionUtils) {
        super( clientDefinitionManager, clientFactoryServices, shapeManager, glyphTooltip, graphBoundsIndexer,
                nodeDragProxyFactory, nodeBuilderControl, animationFactory,
                definitionUtils );
    }

    @PostConstruct
    public void init() {
        glyphTooltip.setPrefix( "Create a new " );
    }


}
