package org.wirez.bpmn.backend.marshall.json.oryx;

import org.apache.commons.lang3.StringUtils;
import org.wirez.bpmn.definition.BPMNDiagram;
import org.wirez.bpmn.definition.property.general.Name;
import org.wirez.core.definition.adapter.binding.BindableAdapterUtils;

import javax.enterprise.context.Dependent;
import java.util.*;

/**
 * This class contains the mappings for the different stencil identifiers that are different from 
 * the patterns used in this tool.
 */
@Dependent
public class Bpmn2OryxIdMappings {

    private final Map<Class<?>, String> defMappings = new HashMap<Class<?>, String>();
    private final Map<Class<?>, String> propMappings = new HashMap<Class<?>, String>();
    
    private final Map<Class<?>, String> globalMappings = new HashMap<Class<?>, String>() {{
        // Add here global class <-> oryxId mappings, if any.
        // No custom mappings, for now.
        put( Name.class, "name" );
    }};

    private final Map<Class<?>, Set<String>> skippedProperties = new HashMap<Class<?>, Set<String>>() {{
        // Add here global class <-> collection oryx prpoerty identifiers to skip processing, if any.
        put(BPMNDiagram.class, new HashSet<String>() {{
            add( "name" );
        }});
    }};
    
    private final Map<Class<?>, Map<Class<?>, String>> definitionMappings = new HashMap<Class<?>, Map<Class<?>, String>>() {{
        // Add here class <-> oryxId mappings just for a concrete definition (stencil), if any.
        
        // The name property the diagram stencil is "processn".
        put(BPMNDiagram.class, 
                new HashMap<Class<?>, String>() {{ 
                    put(Name.class, "processn");
            }});
    }};

    public Bpmn2OryxIdMappings() {
    }

    void init( List<Class<?>> definitions, List<Class<?>> properties ) {

        // Load default & custom mappings for BPMN definitions.
        for ( final Class<?> defClass : definitions ) {
            String customMapping = globalMappings.get( defClass );
            String orxId = customMapping != null ? customMapping : getDefaultOryxDefinitionId( defClass );
            defMappings.put( defClass, orxId );
        }

        // Load default & custom mappings for BPMN properties.
        for ( final Class<?> propClass : properties ) {
            String customMapping = globalMappings.get( propClass );
            String orxId = customMapping != null ? customMapping : getDefaultOryxPropertyId( propClass );
            propMappings.put( propClass, orxId );
        }

    }

    public String getOryxDefinitionId( Class<?> clazz ) {
        return defMappings.get( clazz );
    }

    public String getOryxPropertyId( Class<?> clazz ) {
        return propMappings.get( clazz );
    }

    public String getOryxPropertyId( Class<?> definitionClass,
                                     Class<?> clazz ) {
        Map<Class<?>, String> mappings = definitionMappings.get( definitionClass );
        if ( null != mappings ) {
            String r = mappings.get( clazz );
            if ( null != r) {
                return r;
            }
        }

        return getOryxPropertyId( clazz );
    }
    
    public boolean isSkipProperty( Class<?> definitionClass, String oryxPropertyId ) {
        Set<String> toSkip = skippedProperties.get( definitionClass );
        return toSkip != null && toSkip.contains( oryxPropertyId );
    }

    public Class<?> getProperty( String oryxId ) {
        return get(oryxId, propMappings);
    }

    public Class<?> getDefinition( String oryxId ) {
        return get(oryxId, defMappings);
    }
    
    public String getPropertyId( String oryxId ) {
        Class<?> c = getProperty( oryxId );
        return null != c ? getPropertyId( c ) : null;
    }
    
    public String getPropertyId( Class<?> definitionClass,
                               String oryxId ) {
        Map<Class<?>, String> mappings = definitionMappings.get( definitionClass );
        if ( null != mappings ) {
            Class<?> p = get( oryxId, mappings );
            if ( null != p ) {
                return getPropertyId( p );
            }
        }

        return getPropertyId( oryxId );
    }

    public String getDefinitionId( String oryxId ) {
        Class<?> c = getDefinition( oryxId );
        return null != c ? getDefinitionId( c ) : null;
    }

    public String getPropertyId( Class<?> clazz ) {
        return BindableAdapterUtils.getPropertyId( clazz );
    }

    public String getDefinitionId( Class<?> clazz ) {
        return BindableAdapterUtils.getDefinitionId( clazz );
    }

    private Class<?> get( String oryxId, Map<Class<?>, String> map ) {

        Set<Map.Entry<Class<?>, String>> entrySet = map.entrySet();
        for ( Map.Entry<Class<?>, String> entry : entrySet ) {
            String oId = entry.getValue();

            if ( oId.equals( oryxId ) ) {
                return entry.getKey();
            }
        }

        return null;

    }

    private String getDefaultOryxDefinitionId( Class<?> clazz ) {
        return clazz.getSimpleName();
    }

    private String getDefaultOryxPropertyId( Class<?> clazz ) {
        return StringUtils.uncapitalize( clazz.getSimpleName() );
    }

}
