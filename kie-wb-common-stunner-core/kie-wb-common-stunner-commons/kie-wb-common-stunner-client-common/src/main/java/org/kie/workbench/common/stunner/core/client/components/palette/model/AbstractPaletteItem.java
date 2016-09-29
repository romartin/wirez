package org.kie.workbench.common.stunner.core.client.components.palette.model;

public abstract class AbstractPaletteItem implements PaletteItem {

    protected final String itemId;
    protected final String title;
    protected final String description;
    protected final String tooltip;

    public AbstractPaletteItem(final String itemId,
                               final String title,
                               final String description,
                               final String tooltip ) {
        this.itemId = itemId;
        this.title = title;
        this.description = description;
        this.tooltip = tooltip;
    }

    @Override
    public String getId() {
        return itemId;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getTooltip() {
        return tooltip;
    }

    @Override
    public boolean equals( final Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( !( o instanceof AbstractPaletteItem) ) {
            return false;
        }

        AbstractPaletteItem that = (AbstractPaletteItem) o;

        return itemId.equals( that.itemId );
    }

}
