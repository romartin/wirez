package org.kie.workbench.common.stunner.client.widgets.palette;

import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.kie.workbench.common.stunner.core.client.ShapeManager;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;
import org.kie.workbench.common.stunner.core.client.canvas.controls.event.BuildCanvasShapeEvent;
import org.kie.workbench.common.stunner.core.client.components.palette.factory.AbstractPaletteFactory;
import org.kie.workbench.common.stunner.core.client.components.palette.factory.DefaultDefSetPaletteDefinitionFactory;
import org.kie.workbench.common.stunner.core.client.components.palette.model.PaletteDefinition;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;

public abstract class AbstractPaletteWidgetFactory<I extends PaletteDefinition, P extends PaletteWidget<I, ?>>
        extends AbstractPaletteFactory<I, P>
        implements PaletteWidgetFactory<I, P> {

    protected Event<BuildCanvasShapeEvent> buildCanvasShapeEvent;
    protected CanvasHandler canvasHandler;

    public AbstractPaletteWidgetFactory( final ShapeManager shapeManager,
                                         final SyncBeanManager beanManager,
                                         final Instance<DefaultDefSetPaletteDefinitionFactory> defaultPaletteDefinitionFactoryInstance,
                                         final P palette,
                                         final Event<BuildCanvasShapeEvent> buildCanvasShapeEvent ) {
        super( shapeManager, beanManager, defaultPaletteDefinitionFactoryInstance, palette );
        this.buildCanvasShapeEvent = buildCanvasShapeEvent;
    }

    @Override
    public PaletteWidgetFactory<I, P> forCanvasHandler( final CanvasHandler canvasHandler ) {
        this.canvasHandler = canvasHandler;
        return this;
    }

    @Override
    protected void beforeBindPalette( final I paletteDefinition ) {

        super.beforeBindPalette( paletteDefinition );

        if ( null != canvasHandler ) {

            palette.onItemDrop((definition, factory, x, y) ->
                    buildCanvasShapeEvent.fire( new BuildCanvasShapeEvent((AbstractCanvasHandler ) canvasHandler,
                            definition, factory, x, y ) ) );

        }

    }

}
