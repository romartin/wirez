package org.wirez.bpmn.shape.proxy;

import org.wirez.bpmn.definition.BaseTask;
import org.wirez.bpmn.definition.property.task.TaskType;
import org.wirez.core.client.shape.HasChildren;
import org.wirez.shapes.proxy.BasicShapeProxy;
import org.wirez.shapes.proxy.HasChildProxies;
import org.wirez.shapes.proxy.RectangleProxy;
import org.wirez.shapes.proxy.icon.statics.IconProxy;
import org.wirez.shapes.proxy.icon.statics.Icons;

import java.util.HashMap;
import java.util.Map;

public final class TaskShapeProxy implements
        RectangleProxy<BaseTask>,
        HasChildProxies<BaseTask> {
    
    @Override
    public String getBackgroundColor( final BaseTask element ) {
        return element.getBackgroundSet().getBgColor().getValue();
    }

    @Override
    public String getBorderColor( final BaseTask element ) {
        return element.getBackgroundSet().getBorderColor().getValue();
    }

    @Override
    public double getBorderSize( final BaseTask element ) {
        return element.getBackgroundSet().getBorderSize().getValue();
    }

    @Override
    public String getFontFamily( final BaseTask element ) {
        return element.getFontSet().getFontFamily().getValue();
    }

    @Override
    public String getFontColor( final BaseTask element ) {
        return element.getFontSet().getFontColor().getValue();
    }

    @Override
    public double getFontSize( final BaseTask element ) {
        return element.getFontSet().getFontSize().getValue();
    }

    @Override
    public String getNamePropertyValue( final BaseTask element ) {
        return element.getGeneral().getName().getValue();
    }

    @Override
    public double getFontBorderSize( final BaseTask element ) {
        return element.getFontSet().getFontBorderSize().getValue();
    }

    @Override
    public String getGlyphBackgroundColor( final BaseTask element ) {
        return element.getBackgroundSet().getBgColor().getValue();
    }

    @Override
    public String getDescription( final BaseTask element ) {
        return "A " + element.getTaskType().getValue().toString() + " Task";
    }

    @Override
    public Map<BasicShapeProxy<BaseTask>, HasChildren.Layout> getChildProxies() {
        
        return new HashMap<BasicShapeProxy<BaseTask>, HasChildren.Layout>() {{
            
            put( new TaskTypeProxy(), HasChildren.Layout.CENTER );
            
        }};
    }

    @Override
    public double getWidth( final BaseTask element ) {
        return element.getWidth().getValue();
    }

    @Override
    public double getHeight( final BaseTask element ) {
        return element.getHeight().getValue();
    }

    public final class TaskTypeProxy implements IconProxy<BaseTask> {

        private static final String BLACK = "#000000";

        @Override
        public Icons getIcon( final BaseTask element) {
            
            final TaskType taskType = element.getTaskType();
            
            switch (taskType.getValue()) {
            
                case USER:
                    
                    return Icons.USER;

                case SCRIPT:

                    return Icons.SCRIPT;

                case BUSINESS_RULE:

                    return Icons.BUSINESS_RULE;
                
            }
            
            return null;
            
        }

        @Override
        public String getGlyphBackgroundColor( final BaseTask element ) {
            return BLACK;
        }

        @Override
        public String getDescription( final BaseTask element ) {
            return "The task type";
        }
        
    }
    
}
