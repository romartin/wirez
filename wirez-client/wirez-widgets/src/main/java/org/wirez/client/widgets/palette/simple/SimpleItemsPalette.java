package org.wirez.client.widgets.palette.simple;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.ui.Widget;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.uberfire.client.mvp.UberView;
import org.wirez.client.widgets.palette.ItemsPalette;
import org.wirez.client.widgets.palette.PaletteItem;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.components.glyph.GlyphTooltip;
import org.wirez.core.client.components.glyph.ShapeGlyphDragHandler;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.factory.ShapeFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Dependent
@Simple
public class SimpleItemsPalette implements ItemsPalette {

    private static Logger LOGGER = Logger.getLogger(SimpleItemsPalette.class.getName());
    private static final int ICON_SIZE = 50;
    private static final int COLUMNS = 1;
    private static final int PADDING = 10;
    
    public interface View extends UberView<SimpleItemsPalette> {

        View setEmptyViewVisible( boolean isVisible );
        
        View show( double width, double height, Iterable<PaletteItem<Group>> items );

        // TODO: Should not be here. Refactor this when ShapeGlyphDragHandler refactored.
        LienzoPanel getLienzoPanel();

        View clear();

    }

    ShapeManager shapeManager;
    DefinitionManager definitionManager;
    ClientFactoryServices clientFactoryServices;
    SyncBeanManager beanManager;
    GlyphTooltip<Group> paletteTooltip;
    ShapeGlyphDragHandler<LienzoPanel, Group> shapeGlyphDragHandler;
    View view;

    private Callback callback;
    private int width;
    private final List<PaletteItem<Group>> items = new LinkedList<PaletteItem<Group>>();

    @Inject
    public SimpleItemsPalette(final View view,
                              final ShapeManager shapeManager,
                              final DefinitionManager definitionManager,
                              final ClientFactoryServices clientFactoryServices,
                              final SyncBeanManager beanManager,
                              final GlyphTooltip<Group> paletteTooltip,
                              final ShapeGlyphDragHandler<LienzoPanel, Group> shapeGlyphDragHandler) {
        this.view = view;
        this.shapeManager = shapeManager;
        this.definitionManager = definitionManager;
        this.clientFactoryServices = clientFactoryServices;
        this.beanManager = beanManager;
        this.paletteTooltip = paletteTooltip;
        this.shapeGlyphDragHandler = shapeGlyphDragHandler;
    }

    @PostConstruct
    public void init() {
        view.init(this);
        showEmpty();
    }

    @Override
    public void showEmpty() {
        showEmpty( true );
    }

    @Override
    public void show( final int width, 
                      final Iterable<PaletteItem<Group>> items, 
                      final Callback callback ) {
        
        this.width = width;
        this.callback = callback;
        
        final boolean empty = items == null || !items.iterator().hasNext();
        
        showEmpty( empty );

        if ( !empty ) {

            double height = getPadding();
            for ( final PaletteItem<Group> item : items ) {

                height += item.getGlyph().getHeight() + getPadding();
                this.items.add( item );

            }
            
            view.show( width, height, items );
            
        }
        
    }
    
    @Override
    public void clear() {
        view.clear();
        items.clear();
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }
    
    int getColumns() {
        return COLUMNS;
    }
    
    int getIconSize() {
        return ICON_SIZE;
    }
    
    int getPadding() {
        return PADDING;
    }
    
    void onItemHover(final int index,
                     final double x,
                     final double y) {
        
        final PaletteItem<Group> item = getItem( index );
        
        paletteTooltip.show( item.getGlyph(), item.getDescription(),  width + 5, y - 50 );
    }

    void onItemOut( final int index ) {
        
        paletteTooltip.hide();
        
    }

    @SuppressWarnings("unchecked")
    void onItemMouseDown(final int index,
                         final int x,
                         final int y) {

        final PaletteItem<Group> item = getItem( index );

        shapeGlyphDragHandler.show( view.getLienzoPanel(), item.getGlyph(), x, y, new ShapeGlyphDragHandler.Callback<LienzoPanel>() {
            @Override
            public void onMove(final LienzoPanel floatingPanel, final double x, final double y) {

            }

            @Override
            public void onComplete(final LienzoPanel floatingPanel, final double x, final double y) {
                if ( null != callback ) {
                    log(Level.FINE, "Palette: Adding " + item.getDescription() + " at " + x + "," + y);
                    
                    final String defId = item.getDefinitionId();
                    final Object definition = clientFactoryServices.newDomainObject( defId );
                    final ShapeFactory<?, ?, ? extends Shape> factory = shapeManager.getFactory( defId );
                    
                    // Fire the callback as shape dropped onto the target canvas.
                    callback.onAddShape( definition, factory, x, y );
                    
                }
            }
        });
        
    }

    void onItemClick(final int index,
                     final int x,
                     final int y) {
        
        // TODO
        
    }
    
    private PaletteItem<Group> getItem( final int index ) {
        return items.get( index );
    }
    
    protected void showEmpty( boolean empty ) {
        if ( empty ) {
            clear();
        }
        view.setEmptyViewVisible( empty );
    }
    
    private void log( final Level level,
                      final String message ) {
        
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
        
    }

}
