package org.wirez.lienzo.palette;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.tooling.nativetools.client.event.HandlerRegistrationManager;
import org.wirez.lienzo.Decorator;
import org.wirez.lienzo.Decorator.ItemCallback;
import org.wirez.lienzo.grid.Grid;

import java.util.Iterator;

public abstract class AbstractPalette<T> extends Group {

    public static class Item {

        private final IPrimitive<?> primitive;
        private final ItemDecorator decorator;

        public Item( final IPrimitive<?> primitive,
                     final ItemDecorator decorator ) {
            this.primitive = primitive;
            this.decorator = decorator;
        }

        public IPrimitive<?> getPrimitive() {
            return primitive;
        }

        public ItemDecorator getDecorator() {
            return decorator;
        }
    }

    public enum ItemDecorator {

        DEFAULT;

    }

    public interface Callback {

        void onItemHover( int index, double eventX, double eventY, double itemX, double itemY );

        void onItemOut( int index );

        void onItemMouseDown( int index, double eventX, double eventY, double itemX, double itemY );

        void onItemClick( int index, double eventX, double eventY, double itemX, double itemY  );

    }

    protected int iconSize;
    protected int padding;
    protected int x;
    protected int y;
    protected int rows = -1;
    protected int cols = -1;
    protected Callback callback;
    protected final Group itemsGroup = new Group();
    protected final HandlerRegistrationManager handlerRegistrationManager = new HandlerRegistrationManager();

    public AbstractPalette() {

    }

    public T setItemCallback(final Callback callback) {
        this.callback = callback;
        return (T) this;
    }

    public T setIconSize(final int iconSize) {
        this.iconSize = iconSize;
        return (T) this;
    }

    public T setPadding(final int padding) {
        this.padding = padding;
        return (T) this;
    }

    public T setX(final int x) {
        this.x = x;
        return (T) this;
    }

    public T setY(final int y) {
        this.y = y;
        return (T) this;
    }

    public T setRows(final int rows) {
        this.rows = rows;
        return (T) this;
    }

    public T setColumns(final int cols) {
        this.cols = cols;
        return (T) this;
    }

    public T build( final Item... items ) {

        clear();

        this.add( itemsGroup );

        beforeBuild();

        final Grid grid = createGrid( items.length );

        final Iterator<Grid.Point> pointIterator = grid.iterator();

        for (int c = 0; c < items.length; c++) {
            final Grid.Point point = pointIterator.next();
            final Item item = items[c];
            final int index = c;

            final double px = x + point.getX();
            final double py = y + point.getY();

            final Decorator itemDecorator = item.getDecorator() != null ?
                    createDecorator( index, px, py ) : null;

            final IPrimitive<?> i = null != itemDecorator ?
                    itemDecorator.build( item.getPrimitive(), toDouble( iconSize ), toDouble( iconSize ) ) :
                    item.getPrimitive() ;

            this.itemsGroup.add( i
                    .setX( px )
                    .setY( py )
                    .moveToTop()
            );

            handlerRegistrationManager.register(
                    i.addNodeMouseDownHandler(event -> onItemMouseDown(index, event.getX(), event.getY(), px, py ))
            );

            handlerRegistrationManager.register(
                i.addNodeMouseClickHandler(event -> onItemClick(index, event.getX(), event.getY(), px, py ))
            );
        }

        afterBuild();

        return (T) this;
    }

    protected void doRedraw() {

    }

    public void redraw() {
        doRedraw();
        this.batch();
    }

    protected void beforeBuild() {

    }

    protected void afterBuild() {

    }

    protected Grid createGrid( final int itemsSize ) {
        final int r = rows > 0 ? rows : itemsSize;
        final int c = cols > 0 ? cols : itemsSize;
        return new Grid( padding, iconSize, r, c );
    }

    protected Decorator createDecorator( final int index,
                                         final double itemX,
                                         final double itemY ) {
        return new Decorator( createDecoratorCallback( index, itemX, itemY ) );
    }

    protected ItemCallback createDecoratorCallback( final int index,
                                                    final double itemX,
                                                    final double itemY ) {
        return new ItemCallback() {

            @Override
            public void onShow( final double x, final double y ) {
                doShowItem(index, x, y, itemX, itemY );
            }

            @Override
            public void onHide() {
                doItemOut(index);
            }
        };
    }

    public T clear() {
        this.handlerRegistrationManager.removeHandler();
        this.itemsGroup.removeAll();
        this.removeAll();

        return (T) this;
    }

    protected void doShowItem(final int index,
                              final double x,
                              final double y,
                              final double itemX,
                              final double itemY) {

        if (null != callback) {
            callback.onItemHover( index, x, y, itemX, itemY );
        }
    }

    protected void doItemOut(final int index) {

        if (null != callback) {
            callback.onItemOut(index);
        }
    }

    protected void onItemMouseDown(final int index, double eventX, double eventY, double itemX, double itemY ) {

        if (null != callback) {
            callback.onItemMouseDown(index, eventX, eventY, itemX, itemY );
        }
    }

    protected void onItemClick(final int index, double eventX, double eventY, double itemX, double itemY ) {

        if (null != callback) {
            callback.onItemClick(index, eventX, eventY, itemX, itemY );
        }
    }

    private double toDouble(final int i) {
        return (double) i;
    }
}
