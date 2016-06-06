package org.wirez.client.widgets.palette.simple;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import org.wirez.client.widgets.palette.PaletteItem;
import org.wirez.lienzo.palette.AbstractPalette;
import org.wirez.lienzo.palette.Palette;

import java.util.LinkedList;
import java.util.List;

public class SimpleItemsPaletteView extends Composite implements SimpleItemsPalette.View {

    private final Palette miniPalette = new Palette();

    interface ViewBinder extends UiBinder<Widget, SimpleItemsPaletteView> {

    }

    private static ViewBinder uiBinder = GWT.create( ViewBinder.class );

    @UiField
    FlowPanel mainPanel;

    @UiField
    FlowPanel noCanvasPanel;

    @UiField
    SimplePanel palettePanel;

    private LienzoPanel lienzoPanel;
    private final Layer lienzoLayer = new Layer().setTransformable(true);
    private SimpleItemsPalette presenter;
    
    @Override
    public void init( final SimpleItemsPalette presenter ) {
        this.presenter = presenter;
        initWidget( uiBinder.createAndBindUi( this ) );

        lienzoLayer.add( miniPalette );
        
        miniPalette
                .setX( 0 )
                .setY( 0 )
                .setItemCallback(new AbstractPalette.Callback() {
        
                    @Override
                    public void onItemHover(final int index,
                                            final double x,
                                            final double y) {
        
                            presenter.onItemHover( index, x, y );
        
                    }
        
                    @Override
                    public void onItemOut(final int index) {
        
                        presenter.onItemOut(index);
        
                    }
        
                    @Override
                    public void onItemMouseDown(final int index,
                                                final int x,
                                                final int y) {
        
                        presenter.onItemMouseDown(index, x, y);
        
                    }
        
                    @Override
                    public void onItemClick(final int index,
                                            final int x,
                                            final int y) {
        
                        presenter.onItemClick(index, x, y);
        
                    }
        
                } )
            .setColumns( presenter.getColumns() )
            .setIconSize( presenter.getIconSize() )
            .setPadding( presenter.getPadding() );
        
    }

    private void initLienzoPanel( final int width, 
                                  final int height ) {
        
        if ( null != lienzoPanel ) {
        
            lienzoLayer.clear();
            lienzoLayer.removeFromParent();
            lienzoPanel.clear();
            palettePanel.clear();
            
        }
        
        lienzoPanel = new LienzoPanel( width, height );
        
        lienzoPanel.add( lienzoLayer );
        palettePanel.add( lienzoPanel );
        
    }

    @Override
    public SimpleItemsPalette.View show( final double width, 
                                        final double height,
                                         final Iterable<PaletteItem<Group>> items ) {
        initLienzoPanel( (int) width, (int) height );

        final List<IPrimitive<?>> primitives = new LinkedList<>();
        
        if ( null != items ) {
            
            for ( final PaletteItem<Group> item : items ) {
                
                final IPrimitive<?> group = item.getGlyph().getGroup();
                primitives.add( group );
                
            }
            
            final IPrimitive<?>[] array = primitives.toArray( new IPrimitive[ primitives.size() ] );
            
            miniPalette.build( array );

        }
        
        return this;
    }

    @Override
    public SimpleItemsPalette.View setEmptyViewVisible( final boolean isVisible ) {
        noCanvasPanel.setVisible( isVisible );
        return this;
    }

    @Override
    public LienzoPanel getLienzoPanel() {
        return lienzoPanel;
    }

    @Override
    public SimpleItemsPalette.View clear() {
        miniPalette.clear();
        lienzoLayer.clear();
        return this;
    }

}
