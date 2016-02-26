package org.wirez.bpmn.backend.marshall.json.parser;

import org.wirez.bpmn.backend.marshall.json.parser.common.ArrayParser;
import org.wirez.bpmn.backend.marshall.json.parser.common.IntegerFieldParser;
import org.wirez.bpmn.backend.marshall.json.parser.common.ObjectParser;
import org.wirez.bpmn.backend.marshall.json.parser.common.StringFieldParser;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.property.Property;
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
        Definition definition = element.getContent().getDefinition();

        // Resource id field.
        super.addParser(new StringFieldParser("resourceId", element.getUUID()));

        // Properties array.
        Set<Property> properties = element.getProperties();
        ObjectParser propertiesParser = new ObjectParser("properties");
        super.addParser( propertiesParser );
        if ( null != properties && !properties.isEmpty() ) {
            for ( Property property : properties ) {
                Object value = context.getDefinitionManager().getPropertyAdapter(property).getValue(property);
                // TODO: Use property specific formatting/parsing
                String valueStr = value != null ? value.toString() : "";
                propertiesParser.addParser( new StringFieldParser( property.getId(), valueStr ) );
            }
        }

        // Stencil id field.
        super.addParser( new ObjectParser( "stencil" ).addParser( new StringFieldParser( "id", definition.getId() ) ) );

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

        // Dockers.
        // TODO: Empty array for now.
        // super.addParser( new ArrayParser( "dockers") );

    }

    @Override
    protected void setCurrentParser(Parser p) {
        super.setCurrentParser(p);
        
        if ( current instanceof ContextualParser ) {
            ( (ContextualParser) current).initialize(context);
        }
        
    }
}
