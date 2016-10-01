package org.kie.workbench.common.stunner.bpmn.shape.proxy;

import org.kie.workbench.common.stunner.core.definition.shape.ShapeProxy;
import org.kie.workbench.common.stunner.shapes.proxy.*;
import org.kie.workbench.common.stunner.basicset.definition.icon.statics.StaticIcons;
import org.kie.workbench.common.stunner.bpmn.definition.BaseTask;
import org.kie.workbench.common.stunner.bpmn.definition.BusinessRuleTask;
import org.kie.workbench.common.stunner.bpmn.definition.ScriptTask;
import org.kie.workbench.common.stunner.bpmn.definition.UserTask;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.TaskType;
import org.kie.workbench.common.stunner.core.client.shape.HasChildren;

import org.kie.workbench.common.stunner.shapes.proxy.icon.statics.IconProxy;
import org.kie.workbench.common.stunner.shapes.proxy.icon.statics.Icons;

import java.util.HashMap;
import java.util.Map;

public final class TaskShapeProxy
        extends AbstractBasicShapeProxy<BaseTask>
        implements RectangleProxy<BaseTask>, HasChildProxies<BaseTask> {
    
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
    public String getGlyphDescription(final BaseTask element ) {
        return "A " + element.getTaskType().getValue().toString() + " Task";
    }

    @Override
    public String getGlyphDefinitionId( final Class<?> clazz ) {

        Icons icon = null;

        if ( UserTask.class.equals( clazz ) ) {

            icon = Icons.USER;

        } else if ( ScriptTask.class.equals( clazz ) ) {

            icon = Icons.SCRIPT;

        } else if ( BusinessRuleTask.class.equals( clazz ) ) {

            icon = Icons.BUSINESS_RULE;

        }

        if ( null != icon ) {

            final String iconDefinitionId = StaticIcons.getIconDefinitionId( icon );

            return super.getGlyphDefinitionId( iconDefinitionId );

        }

        return super.getGlyphDefinitionId( clazz );
    }

    @Override
    public Map<ShapeProxy<BaseTask>, HasChildren.Layout> getChildProxies() {
        
        return new HashMap<ShapeProxy<BaseTask>, HasChildren.Layout>() {{
            
            put( new TaskTypeProxy(), HasChildren.Layout.CENTER );
            
        }};
    }

    @Override
    public double getWidth( final BaseTask element ) {
        return element.getDimensionsSet().getWidth().getValue();
    }

    @Override
    public double getHeight( final BaseTask element ) {
        return element.getDimensionsSet().getHeight().getValue();
    }

    public final class TaskTypeProxy extends AbstractBasicGlyphProxy<BaseTask> implements IconProxy<BaseTask> {

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

    }
    
}
