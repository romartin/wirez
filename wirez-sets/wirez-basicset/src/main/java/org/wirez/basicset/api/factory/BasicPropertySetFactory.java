package org.wirez.basicset.api.factory;

import org.wirez.basicset.api.property.background.BackgroundAndBorderSet;
import org.wirez.basicset.api.property.font.FontSet;
import org.wirez.core.api.definition.factory.BindableModelFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.LinkedHashSet;
import java.util.Set;

@ApplicationScoped
public class BasicPropertySetFactory extends BindableModelFactory<Object> {

    BasicPropertyFactory basicSetPropertyFactory;

    protected BasicPropertySetFactory() {
    }

    @Inject
    public BasicPropertySetFactory(BasicPropertyFactory basicSetPropertyFactory) {
        this.basicSetPropertyFactory = basicSetPropertyFactory;
    }

    private static final Set<Class<?>> SUPPORTED_PROP_CLASSES = new LinkedHashSet<Class<?>>() {{
        add(BackgroundAndBorderSet.class);
        add(FontSet.class);
    }};

    @Override
    public Set<Class<?>> getAcceptedClasses() {
        return SUPPORTED_PROP_CLASSES;
    }

    @Override
    public Object build(final Class<?> clazz) {
        if (BackgroundAndBorderSet.class.equals(clazz)) {
            return buildBackgroundSet();
        }
        if (FontSet.class.equals(clazz)) {
            return buildFontSet();
        }

        throw new RuntimeException( "This factory should provide a property set for [" + clazz + "]" );
    }


    public BackgroundAndBorderSet buildBackgroundSet() {
        return new BackgroundAndBorderSet(basicSetPropertyFactory.buildBgColor(), 
                basicSetPropertyFactory.buildBorderColor(), 
                basicSetPropertyFactory.buildBorderSize());
    }

    public FontSet buildFontSet() {
        return new FontSet(basicSetPropertyFactory.buildFontFamily(), 
                basicSetPropertyFactory.buildFontColor(),
                basicSetPropertyFactory.buildFontSize(), 
                basicSetPropertyFactory.buildFontBorderSize());
    }
    
}
