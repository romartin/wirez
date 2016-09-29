package org.kie.workbench.common.stunner.client.lienzo.components.palette;

import org.kie.workbench.common.stunner.client.lienzo.components.palette.view.LienzoPaletteView;
import org.kie.workbench.common.stunner.core.client.ShapeManager;
import org.kie.workbench.common.stunner.core.client.components.palette.AbstractPalette;
import org.kie.workbench.common.stunner.core.client.components.palette.model.HasPaletteItems;
import org.kie.workbench.common.stunner.core.client.components.palette.view.PaletteGrid;
import org.kie.workbench.common.stunner.core.client.components.palette.view.PaletteGridLayoutBuilder;

public abstract class AbstractLienzoPalette<D extends HasPaletteItems, V extends LienzoPaletteView> extends AbstractPalette<D>
        implements LienzoPalette<D, V> {

    protected boolean expanded;
    protected int iconSize;
    protected int padding;
    protected Layout layout;
    protected V view;
    protected boolean isExpandable;

    protected AbstractLienzoPalette() {
        this( null, null );
    }

    public AbstractLienzoPalette( final ShapeManager shapeManager,
                                  final V view ) {
        super( shapeManager );
        this.view = view;
        this.expanded = false;
        this.iconSize = 50;
        this.padding = 10;
        this.isExpandable = true;
        this.layout = Layout.VERTICAL;
    }

    protected abstract void doBind();

    protected abstract void doExpandCollapse();

    public abstract double[] computePaletteSize();

    protected void doInit() {
        view.setPresenter( this );
    }

    @Override
    @SuppressWarnings("unchecked")
    public AbstractLienzoPalette bind() {

        view.clear();

        beforeBind();

        doBind();

        afterBind();

        view.draw();

        return this;
    }

    protected void beforeBind() {

    }

    protected void afterBind() {

    }

    @Override
    public LienzoPalette<D, V> setExpandable( final boolean canExpand ) {
        this.isExpandable = canExpand;
        return this;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    @Override
    public void setLayout( final Layout layout) {
        this.layout = layout;
    }

    @Override
    public LienzoPalette<D, V> setIconSize(final int iconSize) {
        this.iconSize = iconSize;
        return this;
    }

    @Override
    public LienzoPalette<D, V> setPadding(final int padding) {
        this.padding = padding;
        return this;
    }

    @Override
    public LienzoPalette<D, V> expand() {

        if ( !isExpandable ) {
            throw new IllegalStateException( "Palette is not expandable" );
        }

        this.expanded = true;

        doExpandCollapse();

        return this;
    }

    @Override
    public LienzoPalette<D, V> collapse() {

        if ( !isExpandable ) {
            throw new IllegalStateException( "Palette is not expandable" );
        }

        this.expanded = false;

        doExpandCollapse();

        return this;
    }

    @Override
    protected void doDestroy() {
        getView().destroy();
    }

    public PaletteGrid getGrid() {
        final PaletteGridLayoutBuilder gridLayoutBuilder = isHorizontalLayout() ?
                PaletteGridLayoutBuilder.HORIZONTAL :
                PaletteGridLayoutBuilder.VERTICAL;

        gridLayoutBuilder.setIconSize( iconSize );
        gridLayoutBuilder.setPadding( padding );

        return gridLayoutBuilder.build();
    }


    public boolean isExpanded() {
        return expanded;
    }

    protected boolean isHorizontalLayout() {
        return layout.equals( Layout.HORIZONTAL );
    }

    @Override
    public V getView() {
        return view;
    }

}
