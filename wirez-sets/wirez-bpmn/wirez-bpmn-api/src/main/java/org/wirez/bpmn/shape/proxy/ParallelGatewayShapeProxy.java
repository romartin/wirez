package org.wirez.bpmn.shape.proxy;

import org.wirez.bpmn.definition.ParallelGateway;
import org.wirez.core.client.shape.HasChildren;
import org.wirez.shapes.proxy.BasicShapeProxy;
import org.wirez.shapes.proxy.HasChildProxies;
import org.wirez.shapes.proxy.PolygonProxy;
import org.wirez.shapes.proxy.icon.dynamics.DynamicIconProxy;
import org.wirez.shapes.proxy.icon.dynamics.Icons;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ParallelGatewayShapeProxy implements
        PolygonProxy<ParallelGateway>,
        HasChildProxies<ParallelGateway> {
    
    @Override
    public double getRadius( final ParallelGateway element ) {
        return element.getRadius().getValue();
    }

    @Override
    public String getBackgroundColor( final ParallelGateway element ) {
        return element.getBackgroundSet().getBgColor().getValue();
    }

    @Override
    public String getBorderColor( final ParallelGateway element ) {
        return element.getBackgroundSet().getBorderColor().getValue();
    }

    @Override
    public double getBorderSize( final ParallelGateway element ) {
        return element.getBackgroundSet().getBorderSize().getValue();
    }

    @Override
    public String getFontFamily( final ParallelGateway element ) {
        return element.getFontSet().getFontFamily().getValue();
    }

    @Override
    public String getFontColor( final ParallelGateway element ) {
        return element.getFontSet().getFontColor().getValue();
    }

    @Override
    public double getFontSize( final ParallelGateway element ) {
        return element.getFontSet().getFontSize().getValue();
    }

    @Override
    public String getNamePropertyValue( final ParallelGateway element ) {
        return element.getGeneral().getName().getValue();
    }

    @Override
    public double getFontBorderSize( final ParallelGateway element ) {
        return element.getFontSet().getFontBorderSize().getValue();
    }

    @Override
    public String getGlyphBackgroundColor() {
        return ParallelGateway.ParallelGatewayBuilder.COLOR;
    }

    @Override
    public String getDescription() {
        return ParallelGateway.description;
    }

    @Override
    public Map<BasicShapeProxy<ParallelGateway>, HasChildren.Layout> getChildProxies() {
        
        return new LinkedHashMap<BasicShapeProxy<ParallelGateway>, HasChildren.Layout>() {{
            
            put( new IconProxy(), HasChildren.Layout.CENTER );
            
        }};
        
    }

    public final class IconProxy implements DynamicIconProxy<ParallelGateway> {

        @Override
        public Icons getIcon(final ParallelGateway definition ) {
            return Icons.PLUS;
        }

        @Override
        public double getWidth( final ParallelGateway element ) {
            return element.getRadius().getValue() / 2;
        }

        @Override
        public double getHeight( final ParallelGateway element ) {
            return element.getRadius().getValue() / 2;
        }

        @Override
        public String getBackgroundColor( final ParallelGateway element ) {
            return ParallelGateway.ParallelGatewayBuilder.ICON_COLOR;
        }

        @Override
        public String getBorderColor( final ParallelGateway element ) {
            return ParallelGateway.ParallelGatewayBuilder.ICON_COLOR;
        }

        @Override
        public double getBorderSize( final ParallelGateway element ) {
            return 2;
        }
    
        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public String getGlyphBackgroundColor() {
            return null;
        }
    }
    
}
