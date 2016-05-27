package org.wirez.bpmn.shape.proxy;

import org.wirez.bpmn.definition.Task;
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
        RectangleProxy<Task>,
        HasChildProxies<Task> {
    
    @Override
    public String getBackgroundColor( final Task element ) {
        return element.getBackgroundSet().getBgColor().getValue();
    }

    @Override
    public String getBorderColor( final Task element ) {
        return element.getBackgroundSet().getBorderColor().getValue();
    }

    @Override
    public double getBorderSize( final Task element ) {
        return element.getBackgroundSet().getBorderSize().getValue();
    }

    @Override
    public String getFontFamily( final Task element ) {
        return element.getFontSet().getFontFamily().getValue();
    }

    @Override
    public String getFontColor( final Task element ) {
        return element.getFontSet().getFontColor().getValue();
    }

    @Override
    public double getFontSize( final Task element ) {
        return element.getFontSet().getFontSize().getValue();
    }

    @Override
    public String getNamePropertyValue( final Task element ) {
        return element.getGeneral().getName().getValue();
    }

    @Override
    public double getFontBorderSize( final Task element ) {
        return element.getFontSet().getFontBorderSize().getValue();
    }

    @Override
    public String getGlyphBackgroundColor() {
        return Task.TaskBuilder.COLOR;
    }

    @Override
    public String getDescription() {
        return Task.description;
    }

    @Override
    public Map<BasicShapeProxy<Task>, HasChildren.Layout> getChildProxies() {
        
        return new HashMap<BasicShapeProxy<Task>, HasChildren.Layout>() {{
            
            put( new TaskTypeProxy(), HasChildren.Layout.CENTER );
            
        }};
    }

    @Override
    public double getWidth( final Task element ) {
        return element.getWidth().getValue();
    }

    @Override
    public double getHeight( final Task element ) {
        return element.getHeight().getValue();
    }

    public final class TaskTypeProxy implements IconProxy<Task> {

        private static final String BLACK = "#000000";

        @Override
        public Icons getIcon( final Task element) {
            
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
        public String getGlyphBackgroundColor() {
            return BLACK;
        }

        @Override
        public String getDescription() {
            return "The task type";
        }
        
    }
    
}
