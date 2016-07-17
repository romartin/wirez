package org.wirez.client.widgets.palette;

import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.components.palette.AbstractPalette;
import org.wirez.core.client.components.palette.model.PaletteDefinition;
import org.wirez.core.client.service.ClientFactoryServices;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.factory.ShapeFactory;
import org.wirez.core.client.shape.view.ShapeGlyph;

public abstract class AbstractPaletteWidget<D extends PaletteDefinition, V extends PaletteWidgetView>
        extends AbstractPalette<D>
        implements PaletteWidget<D, V> {

    protected final ClientFactoryServices clientFactoryServices;
    protected ItemDropCallback itemDropCallback;
    protected V view;
    protected int maxWidth;
    protected int maxHeight;

    public AbstractPaletteWidget(final ShapeManager shapeManager,
                                 final ClientFactoryServices clientFactoryServices,
                                 final V view) {
        super( shapeManager );
        this.clientFactoryServices = clientFactoryServices;
        this.view = view;
    }

    public abstract double getIconSize();

    @Override
    public PaletteWidget<D, V> onItemDrop( final ItemDropCallback callback ) {
        this.itemDropCallback = callback;
        return this;
    }

    @Override
    protected void beforeBind() {
        super.beforeBind();

        getView().clear();
        getView().showEmptyView( false );
    }

    @Override
    public void unbind() {

        // Only unbind if any definition is already bind.
        if ( null != paletteDefinition ) {

            getView().clear();
            getView().showEmptyView( true );

            this.paletteDefinition = null;

        }

    }

    @Override
    public PaletteWidget<D, V> setMaxWidth(final int maxWidth ) {
        this.maxWidth = maxWidth;
        return this;
    }

    @Override
    public PaletteWidget<D, V> setMaxHeight( final int maxHeight ) {
        this.maxHeight = maxHeight;
        return this;
    }

    public void onDragProxyMove( final String definitionId,
                          final double x,
                          final double y ) {

    }

    @SuppressWarnings("unchecked")
    public void onDragProxyComplete( final String definitionId,
                              final double x,
                              final double y ) {

        if ( null != itemDropCallback ) {

            final Object definition = clientFactoryServices.newDomainObject( definitionId );
            final ShapeFactory<?, ?, ? extends Shape> factory = getFactory( definitionId );

            // Fire the callback as shape dropped onto the target canvas.
            itemDropCallback.onDropItem( definition, factory, x, y );

        }

    }

    public ShapeGlyph<?> getShapeGlyph( final String definitionId ) {
        return getFactory( definitionId ).glyph( definitionId, getIconSize(), getIconSize() );
    }

    @Override
    protected void doDestroy() {

        getView().destroy();

        this.itemDropCallback = null;

    }

    @Override
    public V getView() {
        return view;
    }

}
