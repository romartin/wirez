package org.wirez.core.client.definition.adapter.binding;

import org.jboss.errai.databinding.client.api.DataBinder;
import org.jboss.errai.ioc.client.container.SyncBeanDef;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.wirez.core.api.FactoryManager;
import org.wirez.core.definition.adapter.BindableMorphAdapter;
import org.wirez.core.definition.morph.MorphDefinitionProvider;
import org.wirez.core.definition.util.DefinitionUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.Collection;

@Dependent
public class ClientBindableMorphAdapter<S> extends BindableMorphAdapter<S> {

    SyncBeanManager beanManager;

    @Inject
    public ClientBindableMorphAdapter(final SyncBeanManager beanManager,
                                      final FactoryManager factoryManager,
                                      final DefinitionUtils definitionUtils) {
        super( definitionUtils, factoryManager );
        this.beanManager = beanManager;
    }

    @PostConstruct
    @SuppressWarnings("unchecked")
    public void init() {
        // Morph definitions.
        Collection<SyncBeanDef<MorphDefinitionProvider>> beanMorphAdapters = beanManager.lookupBeans( MorphDefinitionProvider.class );
        for ( SyncBeanDef<MorphDefinitionProvider> morphAdapter : beanMorphAdapters ) {
            MorphDefinitionProvider provider = morphAdapter.getInstance();
            morphDefinitions.addAll( provider.getMorphDefinitions() );
        }
    }

    @Override
    protected <T> T doMerge( final S source,
                             final T result ) {

        return ClientBindingUtils.merge( source, result );

    }

    @Override
    public boolean accepts( final Class<?> type ) {
        try {
            DataBinder.forType( type );
        } catch ( Exception e ) {
            return false;
        }
        return true;
    }


}
