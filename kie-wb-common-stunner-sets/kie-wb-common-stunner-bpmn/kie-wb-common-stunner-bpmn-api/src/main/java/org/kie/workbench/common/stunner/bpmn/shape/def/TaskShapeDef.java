package org.kie.workbench.common.stunner.bpmn.shape.def;

import org.kie.workbench.common.stunner.basicset.definition.icon.statics.StaticIcons;
import org.kie.workbench.common.stunner.bpmn.definition.BaseTask;
import org.kie.workbench.common.stunner.bpmn.definition.BusinessRuleTask;
import org.kie.workbench.common.stunner.bpmn.definition.ScriptTask;
import org.kie.workbench.common.stunner.bpmn.definition.UserTask;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.TaskType;
import org.kie.workbench.common.stunner.core.client.shape.HasChildren;
import org.kie.workbench.common.stunner.core.definition.shape.AbstractShapeDef;
import org.kie.workbench.common.stunner.core.definition.shape.ShapeDef;
import org.kie.workbench.common.stunner.shapes.def.HasChildShapeDefs;
import org.kie.workbench.common.stunner.shapes.def.RectangleShapeDef;
import org.kie.workbench.common.stunner.shapes.def.icon.statics.IconShapeDef;
import org.kie.workbench.common.stunner.shapes.def.icon.statics.Icons;

import java.util.HashMap;
import java.util.Map;

public final class TaskShapeDef
        extends AbstractShapeDef<BaseTask>
        implements RectangleShapeDef<BaseTask>, HasChildShapeDefs<BaseTask> {
    
    @Override
    public String getBackgroundColor( final BaseTask element ) {
        return element.getBackgroundSet().getBgColor().getValue();
    }

    @Override
    public double getBackgroundAlpha( final BaseTask element ) {
        return 1;
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
    public double getBorderAlpha( final BaseTask element ) {
        return 1;
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
    public Map<ShapeDef<BaseTask>, HasChildren.Layout> getChildShapeDefs() {
        
        return new HashMap<ShapeDef<BaseTask>, HasChildren.Layout>() {{
            
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

    public final class TaskTypeProxy extends AbstractShapeDef<BaseTask> implements IconShapeDef<BaseTask> {

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
