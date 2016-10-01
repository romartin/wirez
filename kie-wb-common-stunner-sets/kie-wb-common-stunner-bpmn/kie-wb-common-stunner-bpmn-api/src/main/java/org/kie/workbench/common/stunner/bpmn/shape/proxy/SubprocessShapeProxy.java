package org.kie.workbench.common.stunner.bpmn.shape.proxy;

import org.kie.workbench.common.stunner.core.definition.shape.ShapeProxy;
import org.kie.workbench.common.stunner.shapes.proxy.AbstractBasicShapeProxy;
import org.kie.workbench.common.stunner.shapes.proxy.BasicShapeProxy;
import org.kie.workbench.common.stunner.shapes.proxy.HasChildProxies;
import org.kie.workbench.common.stunner.shapes.proxy.RectangleProxy;
import org.kie.workbench.common.stunner.bpmn.definition.ReusableSubprocess;
import org.kie.workbench.common.stunner.core.client.shape.HasChildren;

import org.kie.workbench.common.stunner.shapes.proxy.icon.dynamics.AbstractDynamicIconProxy;
import org.kie.workbench.common.stunner.shapes.proxy.icon.dynamics.DynamicIconProxy;
import org.kie.workbench.common.stunner.shapes.proxy.icon.dynamics.Icons;

import java.util.HashMap;
import java.util.Map;

public final class SubprocessShapeProxy
        extends AbstractBasicShapeProxy<ReusableSubprocess>
        implements
        RectangleProxy<ReusableSubprocess>,
        HasChildProxies<ReusableSubprocess> {
    
    @Override
    public String getBackgroundColor( final ReusableSubprocess element ) {
        return element.getBackgroundSet().getBgColor().getValue();
    }

    @Override
    public String getBorderColor( final ReusableSubprocess element ) {
        return element.getBackgroundSet().getBorderColor().getValue();
    }

    @Override
    public double getBorderSize( final ReusableSubprocess element ) {
        return element.getBackgroundSet().getBorderSize().getValue();
    }

    @Override
    public String getFontFamily( final ReusableSubprocess element ) {
        return element.getFontSet().getFontFamily().getValue();
    }

    @Override
    public String getFontColor( final ReusableSubprocess element ) {
        return element.getFontSet().getFontColor().getValue();
    }

    @Override
    public double getFontSize( final ReusableSubprocess element ) {
        return element.getFontSet().getFontSize().getValue();
    }

    @Override
    public String getNamePropertyValue( final ReusableSubprocess element ) {
        return element.getGeneral().getName().getValue();
    }

    @Override
    public double getFontBorderSize( final ReusableSubprocess element ) {
        return element.getFontSet().getFontBorderSize().getValue();
    }

    @Override
    public String getGlyphBackgroundColor( final ReusableSubprocess element ) {
        return element.getBackgroundSet().getBgColor().getValue();
    }

    @Override
    public String getGlyphDescription(final ReusableSubprocess element ) {
        return ReusableSubprocess.title;
    }

    @Override
    public Map<ShapeProxy<ReusableSubprocess>, HasChildren.Layout> getChildProxies() {
        
        return new HashMap<ShapeProxy<ReusableSubprocess>, HasChildren.Layout>() {{
            
            put( new ProcessIconProxy(), HasChildren.Layout.CENTER );
            
        }};
    }

    @Override
    public double getWidth( final ReusableSubprocess element ) {
        return element.getDimensionsSet().getWidth().getValue();
    }

    @Override
    public double getHeight( final ReusableSubprocess element ) {
        return element.getDimensionsSet().getHeight().getValue();
    }

    public final class ProcessIconProxy
            extends AbstractDynamicIconProxy<ReusableSubprocess>
            implements DynamicIconProxy<ReusableSubprocess> {

        private static final String BLACK = "#000000";

        @Override
        public String getGlyphBackgroundColor( final ReusableSubprocess element ) {
            return BLACK;
        }

        @Override
        public String getGlyphDescription(final ReusableSubprocess element ) {
            return null;
        }


        @Override
        public double getWidth( final ReusableSubprocess element ) {
            return element.getDimensionsSet().getWidth().getValue() / 2;
        }

        @Override
        public double getHeight( final ReusableSubprocess element ) {
            return element.getDimensionsSet().getHeight().getValue() / 2;
        }

        @Override
        public String getBackgroundColor( final ReusableSubprocess element ) {
            return BLACK;
        }

        @Override
        public String getBorderColor( final ReusableSubprocess element ) {
            return BLACK;
        }

        @Override
        public double getBorderSize( final ReusableSubprocess element ) {
            return 0;
        }

        @Override
        public Icons getIcon( final ReusableSubprocess definition ) {
            return Icons.PLUS;
        }

    }
    
}
