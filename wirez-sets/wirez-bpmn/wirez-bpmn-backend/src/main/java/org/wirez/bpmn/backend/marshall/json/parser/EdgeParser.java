package org.wirez.bpmn.backend.marshall.json.parser;

import org.wirez.bpmn.backend.marshall.json.parser.common.ArrayParser;
import org.wirez.bpmn.backend.marshall.json.parser.common.IntegerFieldParser;
import org.wirez.bpmn.backend.marshall.json.parser.common.ObjectParser;
import org.wirez.bpmn.backend.marshall.json.parser.common.StringFieldParser;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;

public class EdgeParser extends ElementParser<Edge<View, Node>> {
    
    public EdgeParser(String name, Edge<View, Node> element) {
        super(name, element);
    }

    @Override
    public void initialize(Context context) {
        super.initialize(context);

        String outNodeId = element.getTargetNode() != null ? element.getTargetNode().getUUID() : null;
        
        // Outgoing.
        if ( null != outNodeId ) {
            ArrayParser outgoingParser = new ArrayParser( "outgoing" );
            outgoingParser.addParser( new ObjectParser( "" ).addParser( new StringFieldParser( "resourceId", outNodeId ) ) );
            super.addParser( outgoingParser );
        }

        // TODO: Dockers -> BPMN2 marshaller expects dockers present for sequence flows, 
        // so using dummy 0,0 coordinates for now - Use magnets index or whatever can fit here.
        ObjectParser docker1ObjParser = new ObjectParser( "" )
                .addParser( new IntegerFieldParser( "x", 0 ))
                .addParser( new IntegerFieldParser( "y", 0 ));
        ObjectParser docker2ObjParser = new ObjectParser( "" )
                .addParser( new IntegerFieldParser( "x", 0 ))
                .addParser( new IntegerFieldParser( "y", 0 ));
        ArrayParser dockersParser = new ArrayParser( "dockers" )
                .addParser( docker1ObjParser )
                .addParser( docker2ObjParser );
        super.addParser( dockersParser );
        
    }
}
