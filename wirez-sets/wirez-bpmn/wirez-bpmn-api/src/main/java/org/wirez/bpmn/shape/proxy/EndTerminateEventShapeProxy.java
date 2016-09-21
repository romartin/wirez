package org.wirez.bpmn.shape.proxy;

import org.wirez.bpmn.definition.EndTerminateEvent;
import org.wirez.core.client.shape.HasChildren;
import org.wirez.shapes.proxy.*;

import java.util.HashMap;
import java.util.Map;

public final class EndTerminateEventShapeProxy
        extends AbstractBasicDynamicShapeProxy<EndTerminateEvent>
        implements CircleProxy<EndTerminateEvent>,
        HasChildProxies<EndTerminateEvent> {
    
    @Override
    public double getRadius( final EndTerminateEvent element ) {
        return element.getDimensionsSet().getRadius().getValue();
    }

    @Override
    public String getBackgroundColor( final EndTerminateEvent element ) {
        return element.getBackgroundSet().getBgColor().getValue();
    }

    @Override
    public String getBorderColor( final EndTerminateEvent element ) {
        return element.getBackgroundSet().getBorderColor().getValue();
    }

    @Override
    public double getBorderSize( final EndTerminateEvent element ) {
        return element.getBackgroundSet().getBorderSize().getValue();
    }

    @Override
    public String getFontFamily( final EndTerminateEvent element ) {
        return element.getFontSet().getFontFamily().getValue();
    }

    @Override
    public String getFontColor( final EndTerminateEvent element ) {
        return element.getFontSet().getFontColor().getValue();
    }

    @Override
    public double getFontSize( final EndTerminateEvent element ) {
        return element.getFontSet().getFontSize().getValue();
    }

    @Override
    public String getNamePropertyValue( final EndTerminateEvent element ) {
        return element.getGeneral().getName().getValue();
    }

    @Override
    public double getFontBorderSize( final EndTerminateEvent element ) {
        return element.getFontSet().getFontBorderSize().getValue();
    }

    @Override
    public String getGlyphBackgroundColor( final EndTerminateEvent element ) {
        return EndTerminateEvent.EndTerminateEventBuilder.COLOR;
    }

    @Override
    public String getGlyphDescription(final EndTerminateEvent element ) {
        return EndTerminateEvent.description;
    }

    @Override
    public Map<BasicShapeProxy<EndTerminateEvent>, HasChildren.Layout> getChildProxies() {
        return new HashMap<BasicShapeProxy<EndTerminateEvent>, HasChildren.Layout>() {{
            put( new EndNoneEventRingProxy( EndTerminateEventShapeProxy.this ), HasChildren.Layout.CENTER );
        }};
    }
    
    public final class EndNoneEventRingProxy extends WrappedBasicNamedShapeProxy<EndTerminateEvent> 
            implements RingProxy<EndTerminateEvent> {

        public EndNoneEventRingProxy(final BasicNamedShapeProxy<EndTerminateEvent> parent) {
            super( parent );
        }

        @Override
        public double getInnerRadius( final EndTerminateEvent element ) {
            return EndTerminateEventShapeProxy.this.getRadius( element ) * 0.7;
        }

        @Override
        public double getOuterRadius( final EndTerminateEvent element ) {
            return EndTerminateEventShapeProxy.this.getRadius( element ) * 0.9;
        }

        @Override
        public String getBackgroundColor( final EndTerminateEvent element ) {
            return EndTerminateEvent.EndTerminateEventBuilder.RING_COLOR;
        }

        @Override
        public String getBorderColor( final EndTerminateEvent element ) {
            return EndTerminateEvent.EndTerminateEventBuilder.RING_COLOR;
        }

        @Override
        public double getBorderSize( final EndTerminateEvent element ) {
            return 0;
        }

        @Override
        public String getGlyphBackgroundColor( final EndTerminateEvent element ) {
            return EndTerminateEvent.EndTerminateEventBuilder.RING_COLOR;
        }
        
    }
    
}
