package org.wirez.bpmn.definition.adapter.morph;

import org.wirez.bpmn.definition.property.task.TaskType;
import org.wirez.bpmn.definition.BaseTask;
import org.wirez.bpmn.definition.NoneTask;
import org.wirez.core.definition.adapter.MorphProperty;
import org.wirez.core.definition.adapter.binding.BindableMorphAdapterFactory;
import org.wirez.core.definition.adapter.binding.InheritanceMorphAdapterProxy;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

// TODO: This class has to be generated from the morphing annotations.

@Dependent
public class TaskMorphAdapter extends InheritanceMorphAdapterProxy<BaseTask, BaseTask, TaskType> {

    private final static Map<Class<?>, MorphProperty<TaskType>> PROPERTY_MORPH =
            new HashMap<Class<?>, MorphProperty<TaskType>>( 1 ) {{
                put( BaseTask.class, new TaskTypePropertyMorph() );
            }};


    protected TaskMorphAdapter() {
        this( null );
    }

    @Inject
    public TaskMorphAdapter(final BindableMorphAdapterFactory morphAdapterProducer ) {
        super( morphAdapterProducer );
    }

    @Override
    protected Map<Class<?>, MorphProperty<TaskType>> getPropertyMorphs() {
        return PROPERTY_MORPH;
    }

    @Override
    public Class<?> getDefaultDefinitionType() {
        return NoneTask.class;
    }


}
