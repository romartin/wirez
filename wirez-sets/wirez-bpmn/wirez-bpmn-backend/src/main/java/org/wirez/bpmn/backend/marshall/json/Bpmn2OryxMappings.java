package org.wirez.bpmn.backend.marshall.json;

import org.wirez.bpmn.api.BPMNProperty;
import org.wirez.bpmn.api.property.diagram.DiagramSet;
import org.wirez.bpmn.api.property.general.*;
import org.wirez.bpmn.api.property.simulation.CatchEventAttributes;
import org.wirez.bpmn.api.property.simulation.ThrowEventAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class contains the mappings for the different stencil identifiers that are different from 
 * the patterns used in this tool.
 */
public class Bpmn2OryxMappings {
    
    private static final Map<Class<?>, String> mappings = new HashMap<Class<?>, String>() {{
        put( CatchEventAttributes.class, "catchEventAttributes" );
        put( ThrowEventAttributes.class, "throwEventAttributes" );
        put( BPMNGeneral.class, "bpmnGeneral" );
        put( BackgroundSet.class, "backgroundSet" );
        put( BgColor.class, "bgColor" );
        put( BorderColor.class, "borderColor" );
        put( BorderSize.class, "borderSize" );
        put( FontBorderSize.class, "fontBorderSize" );
        put( FontColor.class, "fontColor" );
        put( FontFamily.class, "fontFamily" );
        put( FontSet.class, "font" );
        put( FontSize.class, "fontSize" );
        put( DiagramSet.class, "diagramSet" );
        put( Name.class, "name" );
    }};
    
    public static String getOryxId(Class<?> clazz ) {
        
        String result = mappings.get( clazz );
        
        if ( null == result ) {
            result = clazz.getSimpleName();
            
            if ( clazz.isAssignableFrom(BPMNProperty.class) ) {
                result = result.toLowerCase();
            }
            
        }
        
        return result;
    }

    public static String getOryxPropertyId(String className ) {

        Set<Map.Entry<Class<?>, String>> entrySet = mappings.entrySet();
        for ( Map.Entry<Class<?>, String> entry : entrySet ) {
            String entryClassName = entry.getKey().getSimpleName();
            
            if ( className.equals( entryClassName )) {
                return entry.getValue();
            }
        }

        return className.toLowerCase();
    }

    public static String getId( String oryxId ) {

        Set<Map.Entry<Class<?>, String>> entrySet = mappings.entrySet();
        for ( Map.Entry<Class<?>, String> entry : entrySet ) {
            String entryClassName = entry.getKey().getSimpleName();
            String oId = entry.getValue();
            
            if ( oId.equals( oryxId )) {
                return entryClassName;
            }
        }
        
        return oryxId;
    }

}
