package org.wirez.bpmn.shape.proxy;

import org.wirez.bpmn.definition.ExclusiveDatabasedGateway;
import org.wirez.core.client.shape.HasChildren;
import org.wirez.shapes.proxy.BasicShapeProxy;
import org.wirez.shapes.proxy.HasChildProxies;
import org.wirez.shapes.proxy.PolygonProxy;
import org.wirez.shapes.proxy.icon.dynamics.DynamicIconProxy;
import org.wirez.shapes.proxy.icon.dynamics.Icons;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ExclusiveDatabasedGatewayShapeProxy implements
        PolygonProxy<ExclusiveDatabasedGateway>,
        HasChildProxies<ExclusiveDatabasedGateway> {
    
    @Override
    public double getRadius( final ExclusiveDatabasedGateway element ) {
        return element.getRadius().getValue();
    }

    @Override
    public String getBackgroundColor( final ExclusiveDatabasedGateway element ) {
        return element.getBackgroundSet().getBgColor().getValue();
    }

    @Override
    public String getBorderColor( final ExclusiveDatabasedGateway element ) {
        return element.getBackgroundSet().getBorderColor().getValue();
    }

    @Override
    public double getBorderSize( final ExclusiveDatabasedGateway element ) {
        return element.getBackgroundSet().getBorderSize().getValue();
    }

    @Override
    public String getFontFamily( final ExclusiveDatabasedGateway element ) {
        return element.getFontSet().getFontFamily().getValue();
    }

    @Override
    public String getFontColor( final ExclusiveDatabasedGateway element ) {
        return element.getFontSet().getFontColor().getValue();
    }

    @Override
    public double getFontSize( final ExclusiveDatabasedGateway element ) {
        return element.getFontSet().getFontSize().getValue();
    }

    @Override
    public String getNamePropertyValue( final ExclusiveDatabasedGateway element ) {
        return element.getGeneral().getName().getValue();
    }

    @Override
    public double getFontBorderSize( final ExclusiveDatabasedGateway element ) {
        return element.getFontSet().getFontBorderSize().getValue();
    }

    @Override
    public String getGlyphBackgroundColor() {
        return ExclusiveDatabasedGateway.ExclusiveDatabasedGatewayBuilder.COLOR;
    }

    @Override
    public String getDescription() {
        return ExclusiveDatabasedGateway.description;
    }

    @Override
    public Map<BasicShapeProxy<ExclusiveDatabasedGateway>, HasChildren.Layout> getChildProxies() {
        
        return new LinkedHashMap<BasicShapeProxy<ExclusiveDatabasedGateway>, HasChildren.Layout>() {{
            
            put( new IconProxy(), HasChildren.Layout.CENTER );
            
        }};
        
    }

    public final class IconProxy implements DynamicIconProxy<ExclusiveDatabasedGateway> {

        @Override
        public Icons getIcon(final ExclusiveDatabasedGateway definition ) {
            return Icons.XOR;
        }

        @Override
        public double getWidth( final ExclusiveDatabasedGateway element ) {
            return element.getRadius().getValue() / 2;
        }

        @Override
        public double getHeight( final ExclusiveDatabasedGateway element ) {
            return element.getRadius().getValue() / 2;
        }

        @Override
        public String getBackgroundColor( final ExclusiveDatabasedGateway element ) {
            return ExclusiveDatabasedGateway.ExclusiveDatabasedGatewayBuilder.ICON_COLOR;
        }

        @Override
        public String getBorderColor( final ExclusiveDatabasedGateway element ) {
            return ExclusiveDatabasedGateway.ExclusiveDatabasedGatewayBuilder.ICON_COLOR;
        }

        @Override
        public double getBorderSize( final ExclusiveDatabasedGateway element ) {
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
