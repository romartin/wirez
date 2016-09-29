package org.kie.workbench.common.stunner.core.client.canvas;

public final class DefaultCanvasGrid extends AbstractCanvasGrid {

    public static final DefaultCanvasGrid INSTANCE = new DefaultCanvasGrid();

    private DefaultCanvasGrid() {
        super( 100, 0.2, "#0000FF", 25, 0.2, "#00FF00" );
    }

}
