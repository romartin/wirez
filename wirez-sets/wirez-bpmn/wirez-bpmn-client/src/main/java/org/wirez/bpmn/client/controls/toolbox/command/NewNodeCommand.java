package org.wirez.bpmn.client.controls.toolbox.command;

import org.wirez.bpmn.BPMNDefinitionSet;
import org.wirez.bpmn.definition.SequenceFlow;
import org.wirez.client.lienzo.canvas.controls.toolbox.command.NewNodeToolboxCommand;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.animation.Deselect;
import org.wirez.core.client.animation.Select;
import org.wirez.core.client.animation.ShapeAnimation;
import org.wirez.core.client.animation.ShapeDeSelectionAnimation;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.controls.builder.NodeBuilderControl;
import org.wirez.core.client.components.drag.NodeDragProxyFactory;
import org.wirez.core.client.components.glyph.GlyphTooltip;
import org.wirez.core.client.components.palette.glyph.SimpleGlyphPalette;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.graph.processing.index.bounds.GraphBoundsIndexer;
import org.wirez.core.lookup.util.CommonLookups;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class NewNodeCommand extends NewNodeToolboxCommand {

    private static final String DEF_SET_ID = BindableAdapterUtils.getDefinitionSetId( BPMNDefinitionSet.class );
    private static final String EDGE_ID = BindableAdapterUtils.getDefinitionId( SequenceFlow.class );

    @Inject
    public NewNodeCommand(final ClientFactoryServices clientFactoryServices,
                          final CommonLookups commonLookups,
                          final ShapeManager shapeManager,
                          final GlyphTooltip glyphTooltip,
                          final SimpleGlyphPalette<?, ?> glyphMiniPalette,
                          final NodeDragProxyFactory<AbstractCanvasHandler> nodeDragProxyFactory,
                          final NodeBuilderControl<AbstractCanvasHandler> nodeBuilderControl,
                          final GraphBoundsIndexer graphBoundsIndexer,
                          final @Select  ShapeAnimation selectionAnimation,
                          final @Deselect ShapeDeSelectionAnimation deSelectionAnimation) {
        super(clientFactoryServices, commonLookups, shapeManager, glyphTooltip, glyphMiniPalette, 
                nodeDragProxyFactory, nodeBuilderControl, graphBoundsIndexer,
                selectionAnimation, deSelectionAnimation );
    }

    @PostConstruct
    public void init() {
        super.initialize();
    }
    
    @Override
    protected String getDefinitionSetIdentifier() {
        return DEF_SET_ID;
    }

    @Override
    protected String getEdgeIdentifier() {
        return EDGE_ID;
    }
    
}
