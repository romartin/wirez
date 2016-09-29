package org.kie.workbench.common.stunner.core.client.components.palette.view;

import org.kie.workbench.common.stunner.core.client.components.palette.view.PaletteElementView;
import org.kie.workbench.common.stunner.core.client.components.palette.view.PaletteView;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractPaletteView<T,  L, I extends PaletteElementView> implements PaletteView<T, L, I> {

    protected final List<I> items = new LinkedList<I>();
    protected double x = 0;
    protected double y = 0;

    protected abstract void doClear();

    @Override
    @SuppressWarnings("unchecked")
    public T setX(final double x) {
        this.x = x;
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T setY(final double y) {
        this.y = y;
        return (T) this;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T add(final I item) {
        items.add( item );
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T set(final int pos,
                 final I item) {
        items.set( pos, item );
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T remove(final int pos) {
        items.remove( pos );
        return (T) this;
    }

    @Override
    public T clear() {
        doClear();
        items.clear();
        return (T) this;
    }

}
