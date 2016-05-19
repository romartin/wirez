package org.wirez.basicset.definition.factory;

import org.wirez.basicset.BasicSet;
import org.wirez.core.definition.factory.BindableModelFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.LinkedHashSet;
import java.util.Set;

@ApplicationScoped
public class BasicDefinitionSetFactory extends BindableModelFactory<BasicSet> {

    BasicDefinitionFactory basicSetDefinitionFactory;

    protected BasicDefinitionSetFactory() {
    }

    @Inject
    public BasicDefinitionSetFactory(BasicDefinitionFactory basicSetDefinitionFactory) {
        this.basicSetDefinitionFactory = basicSetDefinitionFactory;
    }

    private static final Set<Class<?>>  SUPPORTED_CLASSES = new LinkedHashSet<Class<?>>() {{
        add(BasicSet.class);
    }};
    @Override
    public Set<Class<?>> getAcceptedClasses() {
        return SUPPORTED_CLASSES;
    }

    @Override
    public BasicSet build(final Class<?> clazz) {
        return new BasicSet();
    }


}
