package org.wirez.client.lienzo.components.toolbox;

import org.wirez.core.client.components.toolbox.Toolbox;
import org.wirez.lienzo.toolbox.HoverToolbox;

public class LienzoToolbox implements Toolbox {
    
    private final HoverToolbox toolbox;

    public LienzoToolbox( final HoverToolbox toolbox ) {
        this.toolbox = toolbox;
    }

    @Override
    public void remove() {
        toolbox.remove();   
    }
    
}
