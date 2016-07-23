package org.wirez.client.widgets.palette.bs3.factory;

import org.jboss.errai.ioc.client.container.SyncBeanDef;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.wirez.client.widgets.palette.AbstractPaletteWidgetFactory;
import org.wirez.client.widgets.palette.bs3.BS3PaletteWidget;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.canvas.controls.event.BuildCanvasShapeEvent;
import org.wirez.core.client.components.palette.factory.DefaultDefSetPaletteDefinitionFactory;
import org.wirez.core.client.components.palette.model.definition.DefinitionSetPalette;
import org.wirez.core.client.components.palette.view.PaletteGrid;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Dependent
public class BS3PaletteFactoryImpl extends AbstractPaletteWidgetFactory<DefinitionSetPalette, BS3PaletteWidget>
        implements BS3PaletteFactory {

    private final List<BS3PaletteViewFactory> viewFactories = new LinkedList<>(  );

    @Inject
    public BS3PaletteFactoryImpl( final ShapeManager shapeManager,
                                  final SyncBeanManager beanManager,
                                  final Instance<DefaultDefSetPaletteDefinitionFactory> defaultPaletteDefinitionFactoryInstance,
                                  final BS3PaletteWidget palette,
                                  final Event<BuildCanvasShapeEvent> buildCanvasShapeEvent ) {
        super( shapeManager, beanManager, defaultPaletteDefinitionFactoryInstance, palette, buildCanvasShapeEvent );
    }

    @PostConstruct
    @SuppressWarnings( "unchecked" )
    public void init() {

        super.init();

        Collection<SyncBeanDef<BS3PaletteViewFactory>> beanDefSets = beanManager.lookupBeans( BS3PaletteViewFactory.class );
        for ( SyncBeanDef<BS3PaletteViewFactory> defSet : beanDefSets ) {
            BS3PaletteViewFactory factory = defSet.getInstance();
            viewFactories.add( factory );
        }

    }

    @Override
    protected void applyGrid( final PaletteGrid grid ) {
        // TODO: Currently grid is harcoded on the widget itself, refactor pending.
    }


    @Override
    protected void beforeBindPalette( final DefinitionSetPalette paletteDefinition ) {

        super.beforeBindPalette( paletteDefinition );

        final String defSetId = paletteDefinition.getDefinitionSetId();

        BS3PaletteViewFactory viewFactory = getViewFactory( defSetId );

        if ( null == viewFactory ) {

            viewFactory = new BS3PaletteGlyphViewFactory( shapeManager );

        }

        palette.setViewFactory( viewFactory );

    }

    private BS3PaletteViewFactory getViewFactory( final String defSetId) {

        for ( final BS3PaletteViewFactory factory : viewFactories ) {

            if ( factory.accepts( defSetId ) ) {

                return factory;

            }

        }

        return null;
    }

}
