package org.wirez.bpmn.backend.marshall.json.oryx;

import org.apache.commons.lang3.StringUtils;
import org.wirez.bpmn.definition.*;
import org.wirez.bpmn.definition.property.dataio.AssignmentsInfo;
import org.wirez.bpmn.definition.property.general.Name;
import org.wirez.bpmn.definition.property.task.TaskType;
import org.wirez.bpmn.definition.property.variables.ProcessVariables;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.definition.adapter.binding.BindableAdapterUtils;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.*;

/**
 * This class contains the mappings for the different stencil identifiers that are different from 
 * the patterns used in this tool.
 */
@Dependent
public class Bpmn2OryxIdMappings {

    private final Map<Class<?>, String> defMappings = new HashMap<Class<?>, String>();
    
    DefinitionManager definitionManager;
    
    private final Map<Class<?>, String> globalMappings = new HashMap<Class<?>, String>() {{
        // Add here global class <-> oryxId mappings, if any.
        // No custom mappings, for now.
        put( Name.class, "name" );
        put( TaskType.class, "tasktype" );
        put( NoneTask.class, "Task" );
        put( UserTask.class, "Task" );
        put( ScriptTask.class, "Task" );
        put( BusinessRuleTask.class, "Task" );
    }};

    private final Map<Class<?>, Set<String>> skippedProperties = new HashMap<Class<?>, Set<String>>() {{
        // Add here global class <-> collection oryx prpoerty identifiers to skip processing, if any.
        put(BPMNDiagram.class, new HashSet<String>() {{
            add( "name" );
        }});
    }};
    
    private final Map<Class<?>, Map<Class<?>, String>> definitionMappings = new HashMap<Class<?>, Map<Class<?>, String>>() {{
        // Add here class <-> oryxId mappings just for a concrete definition (stencil), if any.
        
        Map<Class<?>, String> diagramPropertiesMap = new HashMap<Class<?>, String>();
        put(BPMNDiagram.class, diagramPropertiesMap);
        // The name property in the diagram stencil is "processn".
        diagramPropertiesMap.put(Name.class, "processn");
        // The process variables property in the diagram stencil is "vardefs".
        diagramPropertiesMap.put(ProcessVariables.class, "vardefs");

        Map<Class<?>, String> userTaskPropertiesMap = new HashMap<Class<?>, String>();
        put(UserTask.class, userTaskPropertiesMap);
        userTaskPropertiesMap.put(AssignmentsInfo.class, "assignmentsinfo");
    }};

    protected Bpmn2OryxIdMappings() {
        this( null );
    }

    @Inject
    public Bpmn2OryxIdMappings( DefinitionManager definitionManager ) {
        this.definitionManager = definitionManager;
    }

    void init( List<Class<?>> definitions ) {

        // Load default & custom mappings for BPMN definitions.
        for ( final Class<?> defClass : definitions ) {
            String customMapping = globalMappings.get( defClass );
            String orxId = customMapping != null ? customMapping : getDefaultOryxDefinitionId( defClass );
            defMappings.put( defClass, orxId );
        }

    }

    public String getOryxDefinitionId( Class<?> clazz ) {
        return defMappings.get( clazz );
    }

    public String getOryxPropertyId( Class<?> clazz ) {
        String customMapping = globalMappings.get( clazz );
        return customMapping != null ? customMapping : getDefaultOryxPropertyId( clazz );
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

    @SuppressWarnings("unchecked")
    public <T> Class<?> getProperty( T definition, String oryxId ) {


        Class<?> clazz = getKey( oryxId, globalMappings );
        if ( null != clazz ) {
            return clazz;
        }
        
        Set<Object> properties = (Set<Object>) definitionManager.adapters().forDefinition().getProperties( definition );
        
        if ( null != properties && !properties.isEmpty() ) {
            
            for ( Object property : properties ) {
                Class<?> pClass = property.getClass();
                String pId = getDefaultOryxPropertyId( pClass );
                if ( oryxId.equals( pId ) ) {
                    return pClass;
                }
            }
            
        }
        
        return null;
    }

    public Class<?> getDefinition( String oryxId ) {
        return get(oryxId, defMappings);
    }
    
    public <T> String getPropertyId( T definition,
                               String oryxId ) {
        
        Class<?> definitionClass = definition.getClass();
        
        Map<Class<?>, String> mappings = definitionMappings.get( definitionClass );
        
        if ( null != mappings ) {
            Class<?> p = get( oryxId, mappings );
            if ( null != p ) {
                return getPropertyId( p );
            }
        }

        Class<?> c = getProperty( definition, oryxId );
        return null != c ? getPropertyId( c ) : null;
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

        Class<?> r = getKey( oryxId, map );
        if ( null != r ) {
            return r;
        }

        return null;

    }
    
    private Class<?> getKey( String value, Map<Class<?>, String> map ) {
        Set<Map.Entry<Class<?>, String>> entrySet = map.entrySet();
        for ( Map.Entry<Class<?>, String> entry : entrySet ) {
            String oId = entry.getValue();

            if ( oId.equals( value ) ) {
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
