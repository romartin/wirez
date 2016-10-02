package org.kie.workbench.common.stunner.bpmn.shape.def;

import org.kie.workbench.common.stunner.bpmn.definition.ReusableSubprocess;
import org.kie.workbench.common.stunner.core.client.shape.HasChildren;
import org.kie.workbench.common.stunner.core.definition.shape.AbstractShapeDef;
import org.kie.workbench.common.stunner.core.definition.shape.ShapeDef;
import org.kie.workbench.common.stunner.shapes.def.HasChildShapeDefs;
import org.kie.workbench.common.stunner.shapes.def.RectangleShapeDef;
import org.kie.workbench.common.stunner.shapes.def.icon.dynamics.DynamicIconShapeDef;
import org.kie.workbench.common.stunner.shapes.def.icon.dynamics.Icons;

import java.util.HashMap;
import java.util.Map;

public final class SubprocessShapeDef
        extends AbstractShapeDef<ReusableSubprocess>
        implements
        RectangleShapeDef<ReusableSubprocess>,
        HasChildShapeDefs<ReusableSubprocess> {
    
    @Override
    public String getBackgroundColor( final ReusableSubprocess element ) {
        return element.getBackgroundSet().getBgColor().getValue();
    }

    @Override
    public double getBackgroundAlpha( final ReusableSubprocess element ) {
        return 1;
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
    public double getBorderAlpha( final ReusableSubprocess element ) {
        return 1;
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
    public Map<ShapeDef<ReusableSubprocess>, HasChildren.Layout> getChildProxies() {
        
        return new HashMap<ShapeDef<ReusableSubprocess>, HasChildren.Layout>() {{
            
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
            extends AbstractShapeDef<ReusableSubprocess>
            implements DynamicIconShapeDef<ReusableSubprocess> {

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
        public double getBackgroundAlpha( final ReusableSubprocess element ) {
            return 1;
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
        public double getBorderAlpha( final ReusableSubprocess element ) {
            return 1;
        }

        @Override
        public Icons getIcon( final ReusableSubprocess definition ) {
            return Icons.PLUS;
        }

    }
    
}
