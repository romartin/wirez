package org.wirez.basicset.shape.proxy;

import org.wirez.basicset.definition.PolygonWithIcon;
import org.wirez.core.client.shape.HasChildren;
import org.wirez.shapes.proxy.BasicShapeProxy;
import org.wirez.shapes.proxy.HasChildProxies;
import org.wirez.shapes.proxy.icon.dynamics.DynamicIconProxy;
import org.wirez.shapes.proxy.icon.dynamics.Icons;

import java.util.LinkedHashMap;
import java.util.Map;

public final class PolygonWithIconProxy implements
        org.wirez.shapes.proxy.PolygonProxy<PolygonWithIcon>,
        HasChildProxies<PolygonWithIcon> {
    
    @Override
    public double getRadius( final PolygonWithIcon element ) {
        return element.getRadius().getValue();
    }

    @Override
    public String getNamePropertyValue( final PolygonWithIcon element ) {
        return element.getName().getValue();
    }

    @Override
    public String getBackgroundColor( final PolygonWithIcon element ) {
        return element.getBackgroundSet().getBgColor().getValue();
    }

    @Override
    public String getBorderColor( final PolygonWithIcon element ) {
        return element.getBackgroundSet().getBorderColor().getValue();
    }

    @Override
    public double getBorderSize( final PolygonWithIcon element ) {
        return element.getBackgroundSet().getBorderSize().getValue();
    }

    @Override
    public String getFontFamily( final PolygonWithIcon element ) {
        return element.getFontSet().getFontFamily().getValue();
    }

    @Override
    public String getFontColor( final PolygonWithIcon element ) {
        return element.getFontSet().getFontColor().getValue();
    }

    @Override
    public double getFontSize( final PolygonWithIcon element ) {
        return element.getFontSet().getFontSize().getValue();
    }

    @Override
    public double getFontBorderSize( final PolygonWithIcon element ) {
        return element.getFontSet().getFontBorderSize().getValue();
    }

    @Override
    public String getDescription() {
        return PolygonWithIcon.title;
    }

    @Override
    public String getGlyphBackgroundColor() {
        return PolygonWithIcon.PolygonWithIconBuilder.COLOR;
    }

    @Override
    public Map<BasicShapeProxy<PolygonWithIcon>, HasChildren.Layout> getChildProxies() {
        return new LinkedHashMap<BasicShapeProxy<PolygonWithIcon>, HasChildren.Layout>() {{
            put( new IconProxy(), HasChildren.Layout.CENTER );
        }};
    }

    public final class IconProxy implements DynamicIconProxy<PolygonWithIcon> {

        private static final String COLOR = "#000000";
        
        @Override
        public Icons getIcon(final PolygonWithIcon definition ) {
            final String icon = definition.getIconType().getValue();
            return Icons.parse( icon );
        }

        @Override
        public double getWidth( final PolygonWithIcon element ) {
            return element.getRadius().getValue() / 2;
        }

        @Override
        public double getHeight( final PolygonWithIcon element ) {
            return element.getRadius().getValue() / 2;
        }

        @Override
        public String getBackgroundColor( final PolygonWithIcon element ) {
            return COLOR;
        }

        @Override
        public String getBorderColor( final PolygonWithIcon element ) {
            return COLOR;
        }

        @Override
        public double getBorderSize( final PolygonWithIcon element ) {
            return 5;
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
