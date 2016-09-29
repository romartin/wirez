package org.kie.workbench.common.stunner.bpmn.backend.service.diagram;

import org.kie.workbench.common.stunner.bpmn.definition.BaseTask;
import org.kie.workbench.common.stunner.bpmn.definition.NoneTask;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.TaskType;
import org.kie.workbench.common.stunner.core.definition.morph.BindableMorphProperty;
import org.kie.workbench.common.stunner.core.definition.morph.BindablePropertyMorphDefinition;
import org.kie.workbench.common.stunner.core.definition.morph.MorphProperty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// TODO: This class describes the morphing for Task types. Morphing definitions are generated at compile time by
// annotation processing, so until not introspecting this info via runtime annotation processing on test scope, this
// morphing property adapter is necessary to make the marshallers work on test scope.
public class TaskTypeMorphDefinition extends BindablePropertyMorphDefinition {

    private static final Map<Class<?>, Collection<MorphProperty>> PROPERTY_MORPH_DEFINITIONS =
            new HashMap<Class<?>, Collection<MorphProperty>>( 1 ) {{
                put( BaseTask.class, new ArrayList<MorphProperty>( 1 ) {{
                    add( new TaskTypeMorphProperty() );
                }} );
            }};

    @Override
    protected Map<Class<?>, Collection<MorphProperty>> getBindableMorphProperties() {
        return PROPERTY_MORPH_DEFINITIONS;
    }

    @Override
    protected Class<?> getDefaultType() {
        return NoneTask.class;
    }

    private static class TaskTypeMorphProperty extends BindableMorphProperty<TaskType, Object> {

        private final static BaseTask.TaskTypeMorphPropertyBinding BINDER = new BaseTask.TaskTypeMorphPropertyBinding();

        @Override
        public Class<?> getPropertyClass() {
            return TaskType.class;
        }

        @Override
        public Map getMorphTargetClasses() {
            return BINDER.getMorphTargets();
        }

        @Override
        public Object getValue( final TaskType property ) {
            return BINDER.getValue( property );
        }

    }


}
