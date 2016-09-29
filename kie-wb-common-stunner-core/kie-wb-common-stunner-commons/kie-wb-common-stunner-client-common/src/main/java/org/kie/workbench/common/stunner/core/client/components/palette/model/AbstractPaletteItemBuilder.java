package org.kie.workbench.common.stunner.core.client.components.palette.model;

public abstract class AbstractPaletteItemBuilder<B, I> implements PaletteItemBuilder<B, I> {

    protected final String id;
    protected String title;
    protected String description;
    protected String tooltip;

    public AbstractPaletteItemBuilder(final String id ) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    @SuppressWarnings("unchecked")
    public B title( final String title ) {
        this.title = title;
        return (B) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public B description( final String description ) {
        this.description = description;
        return (B) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public B tooltip( final String tooltip ) {
        this.tooltip = tooltip;
        return (B) this;
    }

    @Override
    public boolean equals( final Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( !( o instanceof AbstractPaletteItemBuilder) ) {
            return false;
        }

        AbstractPaletteItemBuilder that = (AbstractPaletteItemBuilder) o;

        return id.equals( that.id );
    }

}
