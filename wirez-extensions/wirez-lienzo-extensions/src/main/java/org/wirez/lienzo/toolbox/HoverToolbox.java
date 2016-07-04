package org.wirez.lienzo.toolbox;

import com.ait.lienzo.client.core.shape.Node;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.lienzo.shared.core.types.Direction;
import com.google.gwt.event.shared.HandlerRegistration;
import org.wirez.lienzo.toolbox.builder.AbstractBuilder;
import org.wirez.lienzo.toolbox.grid.GridToolbox;

import java.util.List;

public class HoverToolbox extends AbstractToolbox {

    private final HoverTimer hoverTimer = new HoverTimer(new HoverTimer.Actions() {
        @Override
        public void onMouseEnter() {
            HoverToolbox.this.show();
        }

        @Override
        public void onMouseExit() {
            HoverToolbox.this.hide();
        }

        @Override
        public boolean isReadyToHide() {
            return HoverToolbox.this.showing;
        }
    });

    private boolean showing;

    @Override
    protected void registerButton( final ToolboxButton button ) {

        super.registerButton( button );

        HandlerRegistration hr1 = button.getDecorator().addNodeMouseEnterHandler( hoverTimer );
        HandlerRegistration hr2 = button.getDecorator().addNodeMouseExitHandler( hoverTimer );
        handlerRegistrationManager.register(hr1);
        handlerRegistrationManager.register(hr2);

    }

    @Override
    public void show() {
        
        if (!showing) {

           super.show();

            showing = true;
        }
        
    }


    @Override
    public void hide() {
        
        if (showing) {

            super.hide();
            
            showing = false;
        }
        
    }
    
    private HoverToolbox(final WiresShape shape,
                         final Shape<?> attachTo,
                         final Direction anchor,
                         final Direction towards,
                         final int rows,
                         final int cols,
                         final int padding,
                         final int iconSize,
                         final List<ToolboxButton> buttons) {
        super( shape, attachTo, anchor, towards, rows, cols, padding, iconSize, buttons );
    }

    @Override
    protected void registerHandlers( final Node<?> node ) {

        super.registerHandlers( node );

        HandlerRegistration hr1 = node.addNodeMouseEnterHandler( this.hoverTimer );
        HandlerRegistration hr2 = node.addNodeMouseExitHandler( this.hoverTimer );
        handlerRegistrationManager.register( hr1 );
        handlerRegistrationManager.register( hr2 );
    }

    public static class HoverToolboxBuilder extends AbstractBuilder {

        public HoverToolboxBuilder(WiresShape shape) {
            super(shape);
        }

        @Override
        public GridToolbox register() {
            return new HoverToolbox(this.shape, this.attachTo, this.anchor, this.towards, this.rows, this.cols, 
                    this.padding, this.iconSize, this.buttons);
        }

    }

}
