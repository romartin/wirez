package org.wirez.core.client.components.palette;

public abstract class AbstractPalette<T, V> implements Palette<T, V> {

    protected CloseCallback closeCallback;
    protected ItemHoverCallback itemHoverCallback;
    protected ItemOutCallback itemOutCallback;
    protected ItemMouseDownCallback itemMouseDownCallback;
    protected ItemClickCallback itemClickCallback;
    protected double x;
    protected double y;

    protected abstract void doClear();

    protected abstract void doDestroy();

    @Override
    @SuppressWarnings("unchecked")
    public T setCloseCallback(final CloseCallback callback) {
        this.closeCallback = callback;
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T setItemHoverCallback(final ItemHoverCallback callback) {
        this.itemHoverCallback = callback;
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T setItemOutCallback(final ItemOutCallback callback) {
        this.itemOutCallback = callback;
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T setItemMouseDownCallback(ItemMouseDownCallback callback) {
        this.itemMouseDownCallback = callback;
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T setItemClickCallback(ItemClickCallback callback) {
        this.itemClickCallback = callback;
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T setX(final int x) {
        this.x = x;
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T setY(final int y) {
        this.y = y;
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T clear() {
        doClear();
        this.x = 0;
        this.y = 0;
        return (T) this;
    }

    @Override
    public void destroy() {
        doDestroy();
        this.closeCallback = null;
        this.itemHoverCallback = null;
        this.itemOutCallback = null;
        this.itemMouseDownCallback = null;
        this.itemClickCallback = null;
    }

}
