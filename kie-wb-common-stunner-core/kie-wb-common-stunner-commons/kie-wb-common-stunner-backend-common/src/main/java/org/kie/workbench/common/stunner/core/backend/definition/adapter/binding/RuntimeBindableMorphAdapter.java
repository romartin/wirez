package org.kie.workbench.common.stunner.core.backend.definition.adapter.binding;

import org.kie.workbench.common.stunner.core.api.FactoryManager;
import org.kie.workbench.common.stunner.core.definition.adapter.BindableMorphAdapter;
import org.kie.workbench.common.stunner.core.definition.morph.MorphDefinition;
import org.kie.workbench.common.stunner.core.definition.morph.MorphDefinitionProvider;
import org.kie.workbench.common.stunner.core.definition.util.DefinitionUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.Collection;

@Dependent
public class RuntimeBindableMorphAdapter<S> extends BindableMorphAdapter<S> {

    Instance<MorphDefinitionProvider> morphDefinitionInstances;

    @Inject
    public RuntimeBindableMorphAdapter(DefinitionUtils definitionUtils,
                                       FactoryManager factoryManager,
                                       Instance<MorphDefinitionProvider> morphDefinitionInstances) {
        super( definitionUtils, factoryManager );
        this.morphDefinitionInstances = morphDefinitionInstances;
    }

    public RuntimeBindableMorphAdapter(DefinitionUtils definitionUtils,
                                       FactoryManager factoryManager,
                                       Collection<MorphDefinition> morphDefinitions1 ) {
        super( definitionUtils, factoryManager );
        morphDefinitions.addAll( morphDefinitions1 );
    }

    @PostConstruct
    public void init() {
        initMorphDefinitions();
    }

    private void initMorphDefinitions() {
        if ( null != morphDefinitionInstances ) {
            for ( MorphDefinitionProvider morphDefinitionProvider : morphDefinitionInstances ) {
                morphDefinitions.addAll( morphDefinitionProvider.getMorphDefinitions() );
            }
        }
    }

    @Override
    protected <T> T doMerge( final S source,
                             final T result ) {

        return RuntimeBindingUtils.merge( source, result );

    }

    @Override
    public boolean accepts( Class<?> type ) {
        return true;
    }

}
