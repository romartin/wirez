package org.wirez.client.widgets.palette.bs3.factory;

import org.jboss.errai.ioc.client.container.SyncBeanDef;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.wirez.client.widgets.palette.AbstractPaletteWidgetFactory;
import org.wirez.client.widgets.palette.bs3.BS3PaletteWidget;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.canvas.controls.event.BuildCanvasShapeEvent;
import org.wirez.core.client.components.palette.factory.DefSetPaletteDefinitionFactory;
import org.wirez.core.client.components.palette.factory.DefaultDefSetPaletteDefinitionFactory;
import org.wirez.core.client.components.palette.factory.PaletteDefinitionFactory;
import org.wirez.core.client.components.palette.model.definition.DefinitionSetPalette;

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

    SyncBeanManager beanManager;
    Instance<DefaultDefSetPaletteDefinitionFactory> defaultPaletteDefinitionFactoryInstance;

    private final List<BS3PaletteViewFactory> viewFactories = new LinkedList<>(  );
    private final List<DefSetPaletteDefinitionFactory> paletteDefinitionFactories = new LinkedList<>(  );

    @Inject
    public BS3PaletteFactoryImpl( final ShapeManager shapeManager,
                                  final SyncBeanManager beanManager,
                                  final Instance<DefaultDefSetPaletteDefinitionFactory> defaultPaletteDefinitionFactoryInstance,
                                  final BS3PaletteWidget palette,
                                  final Event<BuildCanvasShapeEvent> buildCanvasShapeEvent ) {
        super( shapeManager, palette, buildCanvasShapeEvent );
        this.beanManager = beanManager;
        this.defaultPaletteDefinitionFactoryInstance = defaultPaletteDefinitionFactoryInstance;
    }

    @PostConstruct
    @SuppressWarnings( "unchecked" )
    public void init() {

        Collection<SyncBeanDef<BS3PaletteViewFactory>> beanDefSets = beanManager.lookupBeans( BS3PaletteViewFactory.class );
        for ( SyncBeanDef<BS3PaletteViewFactory> defSet : beanDefSets ) {
            BS3PaletteViewFactory factory = defSet.getInstance();
            viewFactories.add( factory );
        }

        Collection<SyncBeanDef<DefSetPaletteDefinitionFactory>> factorySets = beanManager.lookupBeans( DefSetPaletteDefinitionFactory.class );
        for ( SyncBeanDef<DefSetPaletteDefinitionFactory> defSet : factorySets ) {
            DefSetPaletteDefinitionFactory factory = defSet.getInstance();
            paletteDefinitionFactories.add( factory );
        }

    }

    @Override
    protected PaletteDefinitionFactory getPaletteDefinitionFactory( final String defSetId ) {
        for ( final DefSetPaletteDefinitionFactory factory : paletteDefinitionFactories ) {
            if ( factory.accepts( defSetId ) ) {
                return factory;
            }
        }
        return defaultPaletteDefinitionFactoryInstance.get();
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
