package org.wirez.bpmn.backend.marshall.json.parser;

import org.codehaus.jackson.*;
import org.codehaus.jackson.impl.JsonParserMinimalBase;
import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.diagram.Settings;
import org.wirez.core.api.graph.Edge;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.Node;
import org.wirez.core.api.graph.content.Child;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.graph.processing.traverse.content.AbstractContentTraverseCallback;
import org.wirez.core.api.graph.processing.traverse.content.ChildrenTraverseProcessorImpl;
import org.wirez.core.api.graph.processing.traverse.tree.TreeWalkTraverseProcessorImpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Stack;

// See org.codehaus.jackson.impl.ReaderBasedParser

public class BPMN2JsonParser extends JsonParserMinimalBase {

    private Diagram<Settings> diagram;
    private NodeParser rootParser;
    
    public BPMN2JsonParser(Diagram<Settings> diagram) {
        this.diagram = diagram;
    }
    
    /*
        ****************************************************************
        *               Custom wirez logic
        ****************************************************************
     */
    
    @SuppressWarnings("unchecked")
    private void traverseChildren() {

        Graph graph = diagram.getGraph();

        // TODO: Only support for a single root node.
        new ChildrenTraverseProcessorImpl(new TreeWalkTraverseProcessorImpl())
                .traverse(graph, new AbstractContentTraverseCallback<Child, Node<View, Edge>, Edge<Child, Node>>() {

                    final Stack<NodeParser> parsers = new Stack<NodeParser>();
                    NodeParser currentParser = null;
                    
                    @Override
                    public void startGraphTraversal(Graph<View, Node<View, Edge>> graph) {
                        super.startGraphTraversal(graph);
                    }

                    @Override
                    public void startNodeTraversal(Node<View, Edge> node) {
                        super.startNodeTraversal(node);

                        NodeParser p = new NodeParser("", node);
                        
                        if ( null != currentParser ) {
                            parsers.peek().addChild( p );
                        } else {
                            BPMN2JsonParser.this.rootParser = p;
                        }

                        currentParser = p;
                    }
                    

                    @Override
                    public void startEdgeTraversal(Edge<Child, Node> edge) {
                        super.startEdgeTraversal(edge);
                        parsers.push( currentParser );
                        
                    }

                    @Override
                    public void endEdgeTraversal(Edge<Child, Node> edge) {
                        super.endEdgeTraversal(edge);
                        parsers.pop();
                    }

                    @Override
                    public void endGraphTraversal() {
                        super.endGraphTraversal();
                    }

                });

        // TODO: Initialize parsers with context and start the json processing.

    }
    
    
    /*
        ****************************************************************
        *               JsonParser interface methods
        ****************************************************************
     */

    @Override
    public ObjectCodec getCodec() {
        doLog("Call getCodec");
        return null;
    }

    @Override
    public void setCodec(ObjectCodec c) {
        doLog("Call setCodec");

    }

    @Override
    public JsonToken nextToken() throws IOException, JsonParseException {
        doLog("Call nextToken");
        return rootParser.nextToken();
    }

    @Override
    protected void _handleEOF() throws JsonParseException {
        
    }

    @Override
    public String getCurrentName() throws IOException, JsonParseException {
        doLog("Call getCurrentName");
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
        doLog("Call getText");
        return rootParser.getText();
    }

    @Override
    public char[] getTextCharacters() throws IOException, JsonParseException {
        doLog("Call getTextCharacters");
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
        doLog("Call getIntValue");
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
        doLog("Call getTextLength");
        return 0;
    }

    @Override
    public int getTextOffset() throws IOException, JsonParseException {
        doLog("Call getTextOffset");
        return 0;
    }

    @Override
    public byte[] getBinaryValue(Base64Variant b64variant) throws IOException, JsonParseException {
        doLog("Call getBinaryValue");
        return new byte[0];
    }

    @Override
    public JsonParser skipChildren() throws IOException, JsonParseException {
        doLog("Call skipChildren");
        return rootParser.skipChildren();
    }

    private void doLog(String message) {
        System.out.println("BPMN2JsonParser - " + message);
    }
}
