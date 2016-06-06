package org.wirez.client.widgets.palette.simple;

import com.ait.lienzo.client.core.shape.Group;
import com.google.gwt.user.client.ui.Widget;
import org.uberfire.client.workbench.widgets.common.ErrorPopupPresenter;
import org.wirez.client.widgets.palette.PaletteItem;
import org.wirez.client.widgets.palette.PaletteItemImpl;
import org.wirez.client.widgets.palette.ShapeSetPalette;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.ShapeSet;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.client.service.ServiceCallback;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.factory.ShapeFactory;
import org.wirez.core.client.shape.view.ShapeGlyph;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.Collection;
import java.util.LinkedList;

@Dependent
@Simple
public class SimpleShapeSetPalette implements ShapeSetPalette {

    ShapeManager shapeManager;
    DefinitionManager definitionManager;
    ClientFactoryServices clientFactoryServices;
    ErrorPopupPresenter errorPopupPresenter;
    SimpleItemsPalette simpleItemsPalette;

    @Inject
    public SimpleShapeSetPalette(final ShapeManager shapeManager, 
                                 final DefinitionManager definitionManager, 
                                 final ClientFactoryServices clientFactoryServices, 
                                 final ErrorPopupPresenter errorPopupPresenter,
                                 final @Simple SimpleItemsPalette simpleItemsPalette) {
        this.shapeManager = shapeManager;
        this.definitionManager = definitionManager;
        this.clientFactoryServices = clientFactoryServices;
        this.errorPopupPresenter = errorPopupPresenter;
        this.simpleItemsPalette = simpleItemsPalette;
    }

    @Override
    public void showEmpty() {
        simpleItemsPalette.showEmpty();
    }

    @Override
    public void show( final int width, 
                      final String shapeSetId, 
                      final Callback callback) {

        clear();
        
        final ShapeSet wirezShapeSet = getShapeSet( shapeSetId );
        
        if ( null == wirezShapeSet ) {
            throw new IllegalArgumentException( "Shape Set not found for id [" + shapeSetId + "]" );
        }
        
        final String definitionSetId = wirezShapeSet.getDefinitionSetId();

        clientFactoryServices.newDomainObject(definitionSetId, new ServiceCallback<Object>() {
            
            @Override
            public void onSuccess( final Object definitionSet ) {
                
                doShow( width, definitionSet, callback );
                
            }

            @Override
            public void onError(final ClientRuntimeError error) {
                showError(error);
            }
            
        });
        
    }

    @SuppressWarnings("unchecked")
    private void doShow( final int width, 
                         final Object definitionSet, 
                         final Callback callback ) {

        final Collection<String> definitions = definitionManager.getDefinitionSetAdapter( definitionSet.getClass() ).getDefinitions( definitionSet );

        if ( null != definitions ) {

            final Collection<PaletteItem<Group>> items = new LinkedList<>();
            
            for( final String defId : definitions ) {

                final ShapeFactory<?, ?, ? extends Shape> factory = shapeManager.getFactory( defId );

                if ( null != factory ) {

                    // TODO: Avoid creating objects here.
                    final String description = factory.getDescription( defId );
                    final ShapeGlyph<Group> glyph = factory.glyph( defId, 50, 50 );

                    final PaletteItem<Group> item = new PaletteItemImpl( defId, description, glyph );
                    items.add( item );
                    
                }                    

            }
            
            simpleItemsPalette.show( width, items, callback );

        }

    }


    @Override
    public void clear() {
        simpleItemsPalette.clear();
    }

    @Override
    public Widget asWidget() {
        return simpleItemsPalette.asWidget();
    }

    private void showError( final ClientRuntimeError error ) {
        final String message = error.getCause() != null ? error.getCause() : error.getMessage();
        showError(message);
    }

    private void showError( final String message ) {
        errorPopupPresenter.showMessage(message);
    }


    private ShapeSet getShapeSet( final String shapeSetId ) {
        final Collection<ShapeSet> sets = shapeManager.getShapeSets();
        for (final ShapeSet set  : sets) {
            if (set.getId().equals(shapeSetId)) {
                return set;
            }
        }
        return null;
    }
    
    
}
