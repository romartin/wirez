package org.wirez.bpmn.backend.marshall.json.parser;

import org.wirez.bpmn.backend.marshall.json.parser.common.IntegerFieldParser;
import org.wirez.bpmn.backend.marshall.json.parser.common.ObjectParser;
import org.wirez.bpmn.backend.marshall.json.parser.common.StringFieldParser;
import org.wirez.core.definition.adapter.DefinitionAdapter;
import org.wirez.core.definition.adapter.PropertyAdapter;
import org.wirez.core.definition.property.PropertyType;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.content.view.Bounds;
import org.wirez.core.graph.content.view.View;

import java.util.Set;

public abstract class ElementParser<T extends Element<View>> extends ObjectParser implements ContextualParser {
    
    protected final T element;
    private Context context;

    public ElementParser(String name, T element) {
        super(name);
        this.element = element;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public void initialize(Context context) {

        this.context = context;
        Object definition = element.getContent().getDefinition();

        // Resource id field.
        super.addParser(new StringFieldParser("resourceId", element.getUUID()));

        
        // Properties array.
        Object def = element.getContent().getDefinition();
        DefinitionAdapter<Object> adapter = context.getDefinitionManager().getDefinitionAdapter( def.getClass() );
        Set<?> properties = adapter.getProperties( def );
        ObjectParser propertiesParser = new ObjectParser("properties");
        super.addParser( propertiesParser );
        if ( null != properties && !properties.isEmpty() ) {
            for ( Object property : properties ) {
                PropertyAdapter propertyAdapter = context.getDefinitionManager().getPropertyAdapter(property.getClass()); 
                PropertyType propertyType = propertyAdapter.getType( property );
                String oryxPropId = 
                        context.getOryxManager().getMappingsManager().getOryxPropertyId( def.getClass(), property.getClass() );
                Object value = propertyAdapter.getValue(property);
                String valueStr = value != null ?
                        context.getOryxManager().getPropertyManager().serialize( property, propertyType, value ) : "";
                propertiesParser.addParser( new StringFieldParser( oryxPropId, valueStr ) );
            }
        }

        // Stencil id field.
        String defId = context.getOryxManager().getMappingsManager().getOryxDefinitionId(definition.getClass());
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
