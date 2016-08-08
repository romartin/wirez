package org.wirez.bpmn.backend.marshall.json.parser;

import org.codehaus.jackson.*;
import org.codehaus.jackson.impl.JsonParserMinimalBase;
import org.wirez.core.diagram.Diagram;
import org.wirez.core.diagram.Settings;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Graph;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.content.definition.DefinitionSet;
import org.wirez.core.graph.content.relationship.Child;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.graph.content.view.ViewConnector;
import org.wirez.core.graph.processing.traverse.content.AbstractChildrenTraverseCallback;
import org.wirez.core.graph.processing.traverse.content.AbstractContentTraverseCallback;
import org.wirez.core.graph.processing.traverse.content.ChildrenTraverseProcessorImpl;
import org.wirez.core.graph.processing.traverse.tree.TreeWalkTraverseProcessorImpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

// See org.codehaus.jackson.impl.ReaderBasedParser

public class BPMN2JsonParser extends JsonParserMinimalBase {

    private Diagram<Graph, Settings> diagram;
    private NodeParser rootParser;
    
    public BPMN2JsonParser(Diagram<Graph, Settings> diagram, ContextualParser.Context parsingContext) {
        this.diagram = diagram;
        initialize(parsingContext);
    }
    
    /*
        ****************************************************************
        *               Custom wirez logic
        ****************************************************************
     */
    
    @SuppressWarnings("unchecked")
    private void initialize(ContextualParser.Context parsingContext) {

        Graph graph = diagram.getGraph();

        final Map<String, EdgeParser> edgeParsers = new HashMap<>();
        
        new ChildrenTraverseProcessorImpl(new TreeWalkTraverseProcessorImpl())
                .traverse(graph, new AbstractChildrenTraverseCallback<Node<View, Edge>, Edge<Child, Node>>() {

                    final Stack<NodeParser> parsers = new Stack<NodeParser>();
                    NodeParser currentParser = null;
                    
                    @Override
                    public void startGraphTraversal(Graph<DefinitionSet, Node<View, Edge>> graph) {
                        super.startGraphTraversal(graph);
                    }

                    @Override
                    public boolean startNodeTraversal( final Iterator<Node<View, Edge>> parents,
                                                       final Node<View, Edge> node ) {
                        super.startNodeTraversal( parents, node );

                        onNodeTraversal( node );

                        return true;
                    }

                    @Override
                    public void startNodeTraversal( final Node<View, Edge> node ) {
                        super.startNodeTraversal(node);

                        onNodeTraversal( node );
                    }

                    private void onNodeTraversal( final Node node ) {

                        NodeParser p = new NodeParser("", node);

                        if ( null != currentParser ) {
                            parsers.peek().addChild( p );
                        } else {
                            BPMN2JsonParser.this.rootParser = p;

                        }

                        currentParser = p;

                        List<Edge> outEdges = node.getOutEdges();
                        if ( null != outEdges && !outEdges.isEmpty() ) {
                            for ( Edge edge : outEdges ) {
                                // Only add the edges with view connector types into the resulting structure to generate the bpmn definition.
                                if ( edge.getContent() instanceof ViewConnector && !edgeParsers.containsKey(edge.getUUID()) ) {
                                    edgeParsers.put( edge.getUUID(), new EdgeParser( "", (Edge) edge ) );
                                }
                            }
                        }

                    }
                    

                    @Override
                    public void startEdgeTraversal(Edge<Child, Node> edge) {
                        super.startEdgeTraversal(edge);
                        parsers.push( currentParser );
                        
                    }

                    @Override
                    public void endEdgeTraversal(Edge<Child, Node> edge) {
                        super.endEdgeTraversal(edge);
                        currentParser = parsers.pop();
                    }

                    @Override
                    public void endGraphTraversal() {
                        super.endGraphTraversal();
                    }

                });

        // In oryx format, all edges are added into the main BPMNDiagram node.
        if ( null != rootParser && !edgeParsers.isEmpty() ) {
            
            for ( EdgeParser edgeParser : edgeParsers.values() ) {
                rootParser.addChild( edgeParser );
            }
            
        }

        // Initialize all the element parsers added in the tree.
        BPMN2JsonParser.this.rootParser.initialize(parsingContext);

        System.out.println("End of children and view traverse");

    }
    
    
    /*
        ****************************************************************
        *               JsonParser interface methods
        ****************************************************************
     */

    @Override
    public ObjectCodec getCodec() {
        return null;
    }

    @Override
    public void setCodec(ObjectCodec c) {

    }

    @Override
    public JsonToken nextToken() throws IOException, JsonParseException {
        return rootParser.nextToken();
    }

    @Override
    protected void _handleEOF() throws JsonParseException {
        
    }

    @Override
    public String getCurrentName() throws IOException, JsonParseException {
        return rootParser.getCurrentName();
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public JsonStreamContext getParsingContext() {
        return null;
    }

    @Override
    public JsonLocation getTokenLocation() {
        return null;
    }

    @Override
    public JsonLocation getCurrentLocation() {
        return null;
    }

    @Override
    public String getText() throws IOException, JsonParseException {
        return rootParser.getText();
    }

    @Override
    public char[] getTextCharacters() throws IOException, JsonParseException {
        return new char[0];
    }

    @Override
    public boolean hasTextCharacters() {
        return false;
    }

    @Override
    public Number getNumberValue() throws IOException, JsonParseException {
        return null;
    }

    @Override
    public NumberType getNumberType() throws IOException, JsonParseException {
        return null;
    }

    @Override
    public int getIntValue() throws IOException, JsonParseException {
        return rootParser.getIntValue();
    }

    @Override
    public long getLongValue() throws IOException, JsonParseException {
        return 0;
    }

    @Override
    public BigInteger getBigIntegerValue() throws IOException, JsonParseException {
        return null;
    }

    @Override
    public float getFloatValue() throws IOException, JsonParseException {
        return 0;
    }

    @Override
    public double getDoubleValue() throws IOException, JsonParseException {
        return 0;
    }

    @Override
    public BigDecimal getDecimalValue() throws IOException, JsonParseException {
        return null;
    }

    @Override
    public int getTextLength() throws IOException, JsonParseException {
        return 0;
    }

    @Override
    public int getTextOffset() throws IOException, JsonParseException {
        return 0;
    }

    @Override
    public byte[] getBinaryValue(Base64Variant b64variant) throws IOException, JsonParseException {
        return new byte[0];
    }

    @Override
    public JsonParser skipChildren() throws IOException, JsonParseException {
        return null;
    }

}
