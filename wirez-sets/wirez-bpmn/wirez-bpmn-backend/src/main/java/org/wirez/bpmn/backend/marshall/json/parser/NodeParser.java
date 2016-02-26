package org.wirez.bpmn.backend.marshall.json.parser;

import org.wirez.bpmn.backend.marshall.json.parser.common.ArrayParser;
import org.wirez.bpmn.backend.marshall.json.parser.common.ObjectParser;
import org.wirez.bpmn.backend.marshall.json.parser.common.StringFieldParser;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.view.View;

import java.util.LinkedList;
import java.util.List;

public class NodeParser extends ElementParser<Node<View, Edge>> {
    
    private final List<Parser> children = new LinkedList<>();
    private final ArrayParser childrenParser = new ArrayParser( "childShapes" );
    
    public NodeParser(String name, Node<View, Edge> element) {
        super(name, element);
    }
    
    public NodeParser addChild( Parser parser ) {
        childrenParser.addParser( parser );
        return this;
    }

    @Override
    public void initialize(Context context) {
        super.initialize(context);

        // Children.
        super.addParser( childrenParser );
        
        // Outgoing.
        ArrayParser outgoingParser = new ArrayParser( "outgoing" );
        super.addParser( outgoingParser );
        List<Edge> outEdges = element.getOutEdges();
        if ( null != outEdges && !outEdges.isEmpty() ) {
            for ( Edge edge : outEdges ) {
                String edgeId = edge.getUUID();
                outgoingParser.addParser( new ObjectParser( "" ).addParser( new StringFieldParser( "resourceId", edgeId ) ) );
            }
        }
        
    }

    
}
