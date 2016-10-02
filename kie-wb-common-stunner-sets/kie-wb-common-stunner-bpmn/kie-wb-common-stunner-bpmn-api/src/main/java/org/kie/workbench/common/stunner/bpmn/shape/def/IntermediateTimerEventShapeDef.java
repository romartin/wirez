package org.kie.workbench.common.stunner.bpmn.shape.def;

import org.kie.workbench.common.stunner.bpmn.definition.IntermediateTimerEvent;
import org.kie.workbench.common.stunner.core.client.shape.HasChildren;
import org.kie.workbench.common.stunner.core.definition.shape.AbstractShapeDef;
import org.kie.workbench.common.stunner.core.definition.shape.ShapeDef;
import org.kie.workbench.common.stunner.shapes.def.BasicShapeWithTitleDef;
import org.kie.workbench.common.stunner.shapes.def.CircleShapeDef;
import org.kie.workbench.common.stunner.shapes.def.HasChildShapeDefs;
import org.kie.workbench.common.stunner.shapes.def.WrappedBasicNamedShapeDef;
import org.kie.workbench.common.stunner.shapes.def.icon.statics.IconShapeDef;
import org.kie.workbench.common.stunner.shapes.def.icon.statics.Icons;

import java.util.HashMap;
import java.util.Map;

public final class IntermediateTimerEventShapeDef
        extends AbstractShapeDef<IntermediateTimerEvent>
        implements
        CircleShapeDef<IntermediateTimerEvent>,
        HasChildShapeDefs<IntermediateTimerEvent> {

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
    public double getBackgroundAlpha( final IntermediateTimerEvent element ) {
        return 1;
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
    public double getBorderAlpha( final IntermediateTimerEvent element ) {
        return 1;
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
    public Map<ShapeDef<IntermediateTimerEvent>, HasChildren.Layout> getChildProxies() {
        
        return new HashMap<ShapeDef<IntermediateTimerEvent>, HasChildren.Layout>() {{
            
            put( new Circle1Proxy( IntermediateTimerEventShapeDef.this ), HasChildren.Layout.CENTER );
            put( new Circle2Proxy( IntermediateTimerEventShapeDef.this ), HasChildren.Layout.CENTER );
            put( new Circle3Proxy( IntermediateTimerEventShapeDef.this ), HasChildren.Layout.CENTER );
            put( new Circle4Proxy( IntermediateTimerEventShapeDef.this ), HasChildren.Layout.CENTER );
            put( new Circle5Proxy( IntermediateTimerEventShapeDef.this ), HasChildren.Layout.CENTER );
            put( new TimerIconProxy(), HasChildren.Layout.CENTER );
            
        }};
        
    }

    // The timer icon.
    public final class TimerIconProxy
            extends AbstractShapeDef<IntermediateTimerEvent>
            implements IconShapeDef<IntermediateTimerEvent> {

        @Override
        public Icons getIcon(final IntermediateTimerEvent element) {
            return Icons.TIMER;
        }

        @Override
        public String getGlyphDescription(final IntermediateTimerEvent element ) {
            return null;
        }

    }
    
    // Outer circle #1.
    public final class Circle1Proxy extends WrappedBasicNamedShapeDef<IntermediateTimerEvent>
            implements CircleShapeDef<IntermediateTimerEvent> {

        public Circle1Proxy( final BasicShapeWithTitleDef<IntermediateTimerEvent> parent ) {
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
    public final class Circle2Proxy extends WrappedBasicNamedShapeDef<IntermediateTimerEvent>
            implements CircleShapeDef<IntermediateTimerEvent> {

        public Circle2Proxy( final BasicShapeWithTitleDef<IntermediateTimerEvent> parent ) {
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
    public final class Circle3Proxy extends WrappedBasicNamedShapeDef<IntermediateTimerEvent>
            implements CircleShapeDef<IntermediateTimerEvent> {

        public Circle3Proxy( final BasicShapeWithTitleDef<IntermediateTimerEvent> parent ) {
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
    public final class Circle4Proxy extends WrappedBasicNamedShapeDef<IntermediateTimerEvent>
            implements CircleShapeDef<IntermediateTimerEvent> {

        public Circle4Proxy( final BasicShapeWithTitleDef<IntermediateTimerEvent> parent ) {
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
    public final class Circle5Proxy extends WrappedBasicNamedShapeDef<IntermediateTimerEvent>
            implements CircleShapeDef<IntermediateTimerEvent> {

        public Circle5Proxy( final BasicShapeWithTitleDef<IntermediateTimerEvent> parent ) {
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
        final double radius = IntermediateTimerEventShapeDef.this.getRadius( element );
        return percent( radius, pct );
    }
    
    private static double percent( final double value, final double pct ) {
        return ( value * pct );
    }
    
}
