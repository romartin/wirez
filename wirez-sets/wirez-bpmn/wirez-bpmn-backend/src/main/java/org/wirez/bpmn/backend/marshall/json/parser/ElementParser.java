package org.wirez.bpmn.backend.marshall.json.parser;

import org.wirez.bpmn.backend.marshall.json.Bpmn2OryxMappings;
import org.wirez.bpmn.backend.marshall.json.parser.common.IntegerFieldParser;
import org.wirez.bpmn.backend.marshall.json.parser.common.ObjectParser;
import org.wirez.bpmn.backend.marshall.json.parser.common.StringFieldParser;
import org.wirez.core.api.definition.adapter.DefinitionAdapter;
import org.wirez.core.api.definition.adapter.PropertyAdapter;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.content.view.Bounds;
import org.wirez.core.api.graph.content.view.View;

import java.util.Set;

public abstract class ElementParser<T extends Element<View>> extends ObjectParser implements ContextualParser {
    
    protected final T element;
    private Context context;

    public ElementParser(String name, T element) {
        super(name);
        this.element = element;
    }
    
    @Override
    public void initialize(Context context) {

        this.context = context;
        Object definition = element.getContent().getDefinition();

        // Resource id field.
        super.addParser(new StringFieldParser("resourceId", element.getUUID()));

        
        // Properties array.
        Object def = element.getContent().getDefinition();
        Set<?> properties = context.getGraphUtils().getAllProperties( def );
        ObjectParser propertiesParser = new ObjectParser("properties");
        super.addParser( propertiesParser );
        if ( null != properties && !properties.isEmpty() ) {
            for ( Object property : properties ) {
                final PropertyAdapter propertyAdapter = context.getDefinitionManager().getPropertyAdapter(property.getClass()); 
                final String pId = propertyAdapter.getId( property );
                final String oryxPropId = Bpmn2OryxMappings.getOryxPropertyId( pId );
                Object value = propertyAdapter.getValue(property);
                // TODO: Use property specific formatting/parsing
                String valueStr = value != null ? value.toString() : "";
                
                propertiesParser.addParser( new StringFieldParser( oryxPropId, valueStr ) );
            }
        }

        // Stencil id field.
        String defId = Bpmn2OryxMappings.getOryxId( definition.getClass() );
        super.addParser( new ObjectParser( "stencil" ).addParser( new StringFieldParser( "id", defId ) ) );

        // Bounds.
        Bounds.Bound ul = element.getContent().getBounds().getUpperLeft();
        ObjectParser ulBoundParser = new ObjectParser( "upperLeft" )
                .addParser( new IntegerFieldParser( "x", ul.getX().intValue() ))
                .addParser( new IntegerFieldParser( "y", ul.getY().intValue() ));

        Bounds.Bound lr = element.getContent().getBounds().getLowerRight();
        ObjectParser lrBoundParser = new ObjectParser( "lowerRight" )
                .addParser( new IntegerFieldParser( "x", lr.getX().intValue() ))
                .addParser( new IntegerFieldParser( "y", lr.getY().intValue() ));


        ObjectParser boundsParser = new ObjectParser( "bounds" )
                .addParser( ulBoundParser )
                .addParser( lrBoundParser );
        super.addParser( boundsParser );

    }

    @Override
    protected void setCurrentParser(Parser p) {
        super.setCurrentParser(p);
        
        if ( current instanceof ContextualParser ) {
            ( (ContextualParser) current).initialize(context);
        }
        
    }
}
