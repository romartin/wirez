package org.wirez.bpmn.client.controls.command;

import org.wirez.bpmn.api.BPMNDefinitionSet;
import org.wirez.bpmn.api.SequenceFlow;
import org.wirez.core.api.definition.adapter.binding.BindableAdapterUtils;
import org.wirez.core.api.graph.processing.index.bounds.GraphBoundsIndexer;
import org.wirez.core.api.lookup.util.CommonLookups;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.animation.Deselect;
import org.wirez.core.client.animation.Select;
import org.wirez.core.client.animation.ShapeAnimation;
import org.wirez.core.client.animation.ShapeDeSelectionAnimation;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.controls.builder.NodeBuilderControl;
import org.wirez.core.client.components.drag.NodeDragProxyFactory;
import org.wirez.core.client.components.glyph.GlyphTooltip;
import org.wirez.core.client.components.palette.Palette;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.client.shape.view.ShapeGlyph;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class NewSeqFlowNodeCommand extends org.wirez.client.lienzo.canvas.controls.toolbox.command.NewNodeCommand {

    private static final String DEF_SET_ID = BindableAdapterUtils.getDefinitionSetId( BPMNDefinitionSet.class );
    private static final String EDGE_ID = BindableAdapterUtils.getDefinitionId( SequenceFlow.class );

    @Inject
    public NewSeqFlowNodeCommand(final ClientFactoryServices clientFactoryServices,
                                 final CommonLookups commonLookups,
                                 final ShapeManager shapeManager,
                                 final GlyphTooltip glyphTooltip,
                                 final Palette<?, ShapeGlyph<?>> glyphMiniPalette,
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
