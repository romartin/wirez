package org.kie.workbench.common.stunner.bpmn.shape.proxy;

import org.kie.workbench.common.stunner.shapes.proxy.*;
import org.kie.workbench.common.stunner.bpmn.definition.IntermediateTimerEvent;
import org.kie.workbench.common.stunner.core.client.shape.HasChildren;

import org.kie.workbench.common.stunner.shapes.proxy.icon.statics.IconProxy;
import org.kie.workbench.common.stunner.shapes.proxy.icon.statics.Icons;

import java.util.HashMap;
import java.util.Map;

public final class IntermediateTimerEventShapeProxy
        extends AbstractBasicDynamicShapeProxy<IntermediateTimerEvent>
        implements
        CircleProxy<IntermediateTimerEvent>,
        HasChildProxies<IntermediateTimerEvent> {

    private static final String WHITE = "#FFFFFF";

    @Override
    public double getRadius( final IntermediateTimerEvent element ) {
        return element.getDimensionsSet().getRadius().getValue();
    }

    @Override
    public String getBackgroundColor( final IntermediateTimerEvent element ) {
        return element.getBackgroundSet().getBgColor().getValue();
    }

    @Override
    public String getBorderColor( final IntermediateTimerEvent element ) {
        return element.getBackgroundSet().getBorderColor().getValue();
    }

    @Override
    public double getBorderSize( final IntermediateTimerEvent element ) {
        return element.getBackgroundSet().getBorderSize().getValue();
    }

    @Override
    public String getFontFamily( final IntermediateTimerEvent element ) {
        return element.getFontSet().getFontFamily().getValue();
    }

    @Override
    public String getFontColor( final IntermediateTimerEvent element ) {
        return element.getFontSet().getFontColor().getValue();
    }

    @Override
    public double getFontSize( final IntermediateTimerEvent element ) {
        return element.getFontSet().getFontSize().getValue();
    }

    @Override
    public String getNamePropertyValue( final IntermediateTimerEvent element ) {
        return element.getGeneral().getName().getValue();
    }

    @Override
    public double getFontBorderSize( final IntermediateTimerEvent element ) {
        return element.getFontSet().getFontBorderSize().getValue();
    }

    @Override
    public String getGlyphBackgroundColor( final IntermediateTimerEvent element ) {
        return IntermediateTimerEvent.IntermediateTimerEventBuilder.COLOR;
    }

    @Override
    public String getGlyphDescription(final IntermediateTimerEvent element ) {
        return IntermediateTimerEvent.description;
    }

    @Override
    public Map<BasicShapeProxy<IntermediateTimerEvent>, HasChildren.Layout> getChildProxies() {
        
        return new HashMap<BasicShapeProxy<IntermediateTimerEvent>, HasChildren.Layout>() {{
            
            put( new Circle1Proxy( IntermediateTimerEventShapeProxy.this ), HasChildren.Layout.CENTER );
            put( new Circle2Proxy( IntermediateTimerEventShapeProxy.this ), HasChildren.Layout.CENTER );
            put( new Circle3Proxy( IntermediateTimerEventShapeProxy.this ), HasChildren.Layout.CENTER );
            put( new Circle4Proxy( IntermediateTimerEventShapeProxy.this ), HasChildren.Layout.CENTER );
            put( new Circle5Proxy( IntermediateTimerEventShapeProxy.this ), HasChildren.Layout.CENTER );
            put( new TimerIconProxy(), HasChildren.Layout.CENTER );
            
        }};
        
    }

    // The timer icon.
    public final class TimerIconProxy
            extends AbstractBasicGlyphProxy<IntermediateTimerEvent>
            implements IconProxy<IntermediateTimerEvent> {

        @Override
        public Icons getIcon(final IntermediateTimerEvent element) {
            return Icons.TIMER;
        }

        @Override
        public String getGlyphBackgroundColor( final IntermediateTimerEvent element ) {
            return WHITE;
        }

        @Override
        public String getGlyphDescription(final IntermediateTimerEvent element ) {
            return null;
        }

    }
    
    // Outer circle #1.
    public final class Circle1Proxy extends WrappedBasicNamedShapeProxy<IntermediateTimerEvent> 
            implements CircleProxy<IntermediateTimerEvent> {

        public Circle1Proxy( final BasicNamedShapeProxy<IntermediateTimerEvent> parent ) {
            super( parent );
        }

        @Override
        public double getRadius( final IntermediateTimerEvent element ) {
            return percent( element, 1 );
        }

        @Override
        public double getBorderSize( final IntermediateTimerEvent element ) {
            return 1;
        }
        
    }

    // Outer circle #2.
    public final class Circle2Proxy extends WrappedBasicNamedShapeProxy<IntermediateTimerEvent>
            implements CircleProxy<IntermediateTimerEvent> {

        public Circle2Proxy( final BasicNamedShapeProxy<IntermediateTimerEvent> parent ) {
            super( parent );
        }

        @Override
        public double getRadius( final IntermediateTimerEvent element ) {
            return percent( element, 0.8 );
        }

        @Override
        public double getBorderSize( final IntermediateTimerEvent element ) {
            return 1;
        }

    }

    // Outer circle #3.
    public final class Circle3Proxy extends WrappedBasicNamedShapeProxy<IntermediateTimerEvent>
            implements CircleProxy<IntermediateTimerEvent> {

        public Circle3Proxy( final BasicNamedShapeProxy<IntermediateTimerEvent> parent ) {
            super( parent );
        }

        @Override
        public double getRadius( final IntermediateTimerEvent element ) {
            return percent( element, 1 );
        }

        @Override
        public double getBorderSize( final IntermediateTimerEvent element ) {
            return 1;
        }

    }

    // Outer circle #4.
    public final class Circle4Proxy extends WrappedBasicNamedShapeProxy<IntermediateTimerEvent>
            implements CircleProxy<IntermediateTimerEvent> {

        public Circle4Proxy( final BasicNamedShapeProxy<IntermediateTimerEvent> parent ) {
            super( parent );
        }

        @Override
        public double getRadius( final IntermediateTimerEvent element ) {
            return percent( element, 0.8 );
        }

        @Override
        public double getBorderSize( final IntermediateTimerEvent element ) {
            return 1;
        }

    }

    // Outer circle #5.
    public final class Circle5Proxy extends WrappedBasicNamedShapeProxy<IntermediateTimerEvent>
            implements CircleProxy<IntermediateTimerEvent> {

        public Circle5Proxy( final BasicNamedShapeProxy<IntermediateTimerEvent> parent ) {
            super( parent );
        }

        @Override
        public double getRadius( final IntermediateTimerEvent element ) {
            return percent( element, 0.67 );
        }

        @Override
        public double getBorderSize( final IntermediateTimerEvent element ) {
            return 1;
        }

    }

    private double percent( final IntermediateTimerEvent element, final double pct ) {
        final double radius = IntermediateTimerEventShapeProxy.this.getRadius( element );
        return percent( radius, pct );
    }
    
    private static double percent( final double value, final double pct ) {
        return ( value * pct );
    }
    
}
