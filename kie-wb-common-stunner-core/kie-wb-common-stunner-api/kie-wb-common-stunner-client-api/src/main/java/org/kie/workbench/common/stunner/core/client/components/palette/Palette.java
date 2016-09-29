package org.kie.workbench.common.stunner.core.client.components.palette;

import org.kie.workbench.common.stunner.core.client.components.palette.model.HasPaletteItems;

public interface Palette<I extends HasPaletteItems> {

    interface CloseCallback {

        boolean onClose();

    }

    interface ItemHoverCallback {

        boolean onItemHover( String id, double mouseX, double mouseY, double itemX, double itemY );

    }

    interface ItemOutCallback {

        boolean onItemOut( String id );

    }

    interface ItemMouseDownCallback {

        boolean onItemMouseDown( String id, double mouseX, double mouseY, double itemX, double itemY );

    }

    interface ItemClickCallback {

        boolean onItemClick( String id, double mouseX, double mouseY, double itemX, double itemY );

    }

    Palette<I> onItemHover( ItemHoverCallback callback );

    Palette<I> onItemOut( ItemOutCallback callback );

    Palette<I> onItemMouseDown( ItemMouseDownCallback callback );

    Palette<I> onItemClick( ItemClickCallback callback );

    Palette<I> onClose( CloseCallback callback );

    Palette<I> bind( I paletteDefinition );

    I getDefinition();

    void destroy();

}
