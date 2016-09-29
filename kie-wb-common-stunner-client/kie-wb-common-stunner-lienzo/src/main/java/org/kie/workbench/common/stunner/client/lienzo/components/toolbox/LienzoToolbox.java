package org.kie.workbench.common.stunner.client.lienzo.components.toolbox;

import org.kie.workbench.common.stunner.core.client.components.toolbox.Toolbox;
import org.kie.workbench.common.stunner.lienzo.toolbox.grid.GridToolbox;

public class LienzoToolbox implements Toolbox {
    
    private final GridToolbox toolbox;

    public LienzoToolbox( final GridToolbox toolbox ) {
        this.toolbox = toolbox;
    }

    @Override
    public void show() {
        toolbox.show();
    }

    @Override
    public void hide() {
        toolbox.hide();
    }

    @Override
    public void remove() {
        toolbox.remove();   
    }
    
}
