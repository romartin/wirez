package org.wirez.bpmn.backend.marshall.json.builder;

import org.codehaus.jackson.*;
import org.uberfire.ext.wirez.bpmn.api.BPMNDiagram;
import org.uberfire.ext.wirez.bpmn.backend.marshall.json.builder.nodes.BPMNDiagramBuilder;
import org.uberfire.ext.wirez.core.api.graph.DefaultEdge;
import org.uberfire.ext.wirez.core.api.graph.DefaultGraph;
import org.uberfire.ext.wirez.core.api.graph.DefaultNode;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Support for a basic single process hierarchy
 */
public class BPMNGraphGenerator extends JsonGenerator {

    BPMNGraphObjectBuilderFactory bpmnGraphBuilderFactory;
    Stack<GraphObjectBuilder> nodeBuilders = new LoggableStack<GraphObjectBuilder>("nodeBuilders");
    Stack<GraphObjectParser> parsers = new LoggableStack<GraphObjectParser>("parsers");
    Collection<GraphObjectBuilder<?, ?>> builders = new LinkedList<GraphObjectBuilder<?, ?>>();
    Collection<DefaultGraph> graphs;
    boolean isClosed;
    
    // Just for development & testing
    private class LoggableStack<T> extends Stack<T> {
        
        final boolean isLogEnabled = false;
        String name;

        public LoggableStack(String name) {
            this.name = name;
        }

        @Override
        public T push(T item) {
            if (isLogEnabled) {
                log(name + "#push - " + item.toString());
            }
            return super.push(item);
        }

        @Override
        public synchronized T pop() {
            T item = super.pop();
            if (isLogEnabled) {
                log(name + "#pop - " + item.toString());
            }
            return item;
        }
    }
    
    public BPMNGraphGenerator(BPMNGraphObjectBuilderFactory bpmnGraphBuilderFactory) {
        this.bpmnGraphBuilderFactory = bpmnGraphBuilderFactory;
        this.parsers.push(new RootObjectParser());
        this.isClosed = false;
    }

    @Override
    public void writeStartObject() throws IOException, JsonGenerationException {
        parsers.peek().writeStartObject();
    }

    @Override
    public void writeEndObject() throws IOException, JsonGenerationException {
        parsers.peek().writeEndObject();
    }

    @Override
    public void writeFieldName(String s) throws IOException, JsonGenerationException {
        parsers.peek().writeFieldName(s);
    }
    
    @Override
    public void writeObject(Object o) throws IOException, JsonProcessingException {
        parsers.peek().writeObject(o);
    }

    @Override
    public void writeStartArray() throws IOException, JsonGenerationException {
        parsers.peek().writeStartArray();
    }

    @Override
    public void writeEndArray() throws IOException, JsonGenerationException {
        parsers.peek().writeEndArray();
    }

    @Override
    public boolean isClosed() {
        return this.isClosed;
    }
    
    @Override
    public void close() throws IOException {
        logBuilders();

        BPMNDiagramBuilder processNodeBuilder = getRootBuilder();
        if (processNodeBuilder == null) {
            throw new RuntimeException("No process node found!");
        }

        this.graphs = new ArrayList<DefaultGraph>(1);
        DefaultGraph processNode = processNodeBuilder.build(builderContext);
        this.graphs.add(processNode);
        
        this.isClosed = true;
    }

    public Collection<DefaultGraph> getGraphs() {
        assert isClosed();
        return this.graphs;
    }
    
    private BPMNDiagramBuilder getRootBuilder() {
        for (GraphObjectBuilder builder : builders) {
            try {
                return  (BPMNDiagramBuilder) builder;
            } catch (ClassCastException e) {
                // Continue searching the root process builder.
            }
        }
        
        return null;
    }
    
    final GraphObjectBuilder.BuilderContext<BPMNDiagram> builderContext = new GraphObjectBuilder.BuilderContext<BPMNDiagram>() {

        DefaultGraph<BPMNDiagram, DefaultNode, DefaultEdge> graph;

        @Override
        public void init(final DefaultGraph<BPMNDiagram, DefaultNode, DefaultEdge> graph) {
            this.graph = graph;
        }

        @Override
        public DefaultGraph<BPMNDiagram, DefaultNode, DefaultEdge> getGraph() {
            return graph;
        }

        @Override
        public Collection<GraphObjectBuilder<?, ?>> getBuilders() {
            return builders;
        }

        
        

    };
    
    // For local testing...
    private void logBuilders() {
        log("Logging builders at close time...");
        for (GraphObjectBuilder<?, ?> builder : builders) {
            log(builder.toString());
        }
    }

    private interface GraphObjectParser {
        
        void writeStartObject();
        
        void writeEndObject();
        
        void writeFieldName(String s);
        
        void writeObject(Object o);
        
        void writeStartArray();
        
        void writeEndArray();
        
    }

    /*
     Handles these fields:
     - resourceId -> The node identifier.
     - properties -> Delegates to properties object parser
     - stencil -> Delegates to stencil object parser
     - childShapes -> Delegates to other root object parsers
     - outgoing -> Delegates to outgoing object parser
     */
    final class RootObjectParser implements GraphObjectParser {
        
        String fieldName;
        
        @Override
        public void writeStartObject() {
            if (fieldName == null) {
                nodeBuilders.push(bpmnGraphBuilderFactory.bootstrapBuilder());
            } else if ("properties".equals(fieldName)) {
                parsers.push(new PropertiesObjectParser());
            } else if ("stencil".equals(fieldName)) {
                parsers.push(new StencilObjectParser());
            } else if ("childShapes".equals(fieldName)) {
                parsers.push(new RootObjectParser());
                nodeBuilders.push(bpmnGraphBuilderFactory.bootstrapBuilder());
            } else if ("outgoing".equals(fieldName)) {
                parsers.push(new OutgoingObjectParser());
            } else {
                parsers.push(new DummyObjectParser());
            }
        }

        @Override
        public void writeEndObject() {
            GraphObjectBuilder builder = nodeBuilders.pop();
            builders.add(builder);
            parsers.pop();
        }

        @Override
        public void writeFieldName(String s) {
            this.fieldName = s;
        }

        @Override
        public void writeObject(Object o) {
            String value = o.toString();
            if ("resourceId".equals(fieldName)) {
                nodeBuilders.peek().nodeId(value);
            }
        }

        @Override
        public void writeStartArray() {

        }

        @Override
        public void writeEndArray() {

        }
    };

    final class PropertiesObjectParser implements GraphObjectParser {

        String fieldName;

        @Override
        public void writeStartObject() {
            
        }

        @Override
        public void writeEndObject() {
            parsers.pop();
        }

        @Override
        public void writeFieldName(String s) {
            this.fieldName = s;
        }

        @Override
        public void writeObject(Object o) {
            nodeBuilders.peek().property(fieldName, o.toString());
        }

        @Override
        public void writeStartArray() {

        }

        @Override
        public void writeEndArray() {

        }
    }

    final class StencilObjectParser implements GraphObjectParser {

        String fieldName;

        @Override
        public void writeStartObject() {

        }

        @Override
        public void writeEndObject() {
            parsers.pop();
        }

        @Override
        public void writeFieldName(String s) {
            this.fieldName = s;
        }

        @Override
        public void writeObject(Object o) {
            if ("id".equals(fieldName)) {
                // Replace the current node builder by the implementation for the specific stencil identifier.
                GraphObjectBuilder builder = nodeBuilders.pop().stencil(o.toString());
                nodeBuilders.push(builder);
            }
        }

        @Override
        public void writeStartArray() {

        }

        @Override
        public void writeEndArray() {

        }
    }

    final class OutgoingObjectParser implements GraphObjectParser {
        
        String fieldName;
        
        @Override
        public void writeStartObject() {
            
        }

        @Override
        public void writeEndObject() {
            parsers.pop();
        }

        @Override
        public void writeFieldName(String s) {
            this.fieldName = s;
        }

        @Override
        public void writeObject(Object o) {
            if ("resourceId".equals(fieldName)) {
                nodeBuilders.peek().out(o.toString());
            }
        }

        @Override
        public void writeStartArray() {

        }

        @Override
        public void writeEndArray() {

        }
    }

    final class DummyObjectParser implements GraphObjectParser {

        @Override
        public void writeStartObject() {
            parsers.push(new DummyObjectParser());
        }

        @Override
        public void writeEndObject() {
            parsers.pop();
        }

        @Override
        public void writeFieldName(String s) {

        }

        @Override
        public void writeObject(Object o) {

        }

        @Override
        public void writeStartArray() {

        }

        @Override
        public void writeEndArray() {

        }
    }

    private void log(String message) {
        System.out.println(message);
    }

    /***********************************************************************************
           NOT IMPLEMENTED METHODS.
     ***********************************************************************************/
    @Override
    public void flush() throws IOException {
        // Not called...
    }

    @Override
    public JsonGenerator enable(Feature feature) {
        return null;
    }

    @Override
    public JsonGenerator disable(Feature feature) {
        return null;
    }

    @Override
    public boolean isEnabled(Feature feature) {
        return false;
    }

    @Override
    public JsonGenerator setCodec(ObjectCodec objectCodec) {
        return null;
    }

    @Override
    public ObjectCodec getCodec() {
        return null;
    }

    @Override
    public JsonGenerator useDefaultPrettyPrinter() {
        return null;
    }

    @Override
    public void writeString(String s) throws IOException, JsonGenerationException {

    }

    @Override
    public void writeString(char[] chars, int i, int i1) throws IOException, JsonGenerationException {

    }

    @Override
    public void writeRawUTF8String(byte[] bytes, int i, int i1) throws IOException, JsonGenerationException {

    }

    @Override
    public void writeUTF8String(byte[] bytes, int i, int i1) throws IOException, JsonGenerationException {

    }

    @Override
    public void writeRaw(String s) throws IOException, JsonGenerationException {

    }

    @Override
    public void writeRaw(String s, int i, int i1) throws IOException, JsonGenerationException {

    }

    @Override
    public void writeRaw(char[] chars, int i, int i1) throws IOException, JsonGenerationException {

    }

    @Override
    public void writeRaw(char c) throws IOException, JsonGenerationException {

    }

    @Override
    public void writeRawValue(String s) throws IOException, JsonGenerationException {

    }

    @Override
    public void writeRawValue(String s, int i, int i1) throws IOException, JsonGenerationException {

    }

    @Override
    public void writeRawValue(char[] chars, int i, int i1) throws IOException, JsonGenerationException {

    }

    @Override
    public void writeBinary(Base64Variant base64Variant, byte[] bytes, int i, int i1) throws IOException, JsonGenerationException {

    }

    @Override
    public void writeNumber(int i) throws IOException, JsonGenerationException {

    }

    @Override
    public void writeNumber(long l) throws IOException, JsonGenerationException {

    }

    @Override
    public void writeNumber(BigInteger bigInteger) throws IOException, JsonGenerationException {

    }

    @Override
    public void writeNumber(double v) throws IOException, JsonGenerationException {

    }

    @Override
    public void writeNumber(float v) throws IOException, JsonGenerationException {

    }

    @Override
    public void writeNumber(BigDecimal bigDecimal) throws IOException, JsonGenerationException {

    }

    @Override
    public void writeNumber(String s) throws IOException, JsonGenerationException, UnsupportedOperationException {

    }

    @Override
    public void writeBoolean(boolean b) throws IOException, JsonGenerationException {

    }

    @Override
    public void writeNull() throws IOException, JsonGenerationException {

    }

    @Override
    public void writeTree(JsonNode jsonNode) throws IOException, JsonProcessingException {

    }

    @Override
    public void copyCurrentEvent(JsonParser jsonParser) throws IOException, JsonProcessingException {

    }

    @Override
    public void copyCurrentStructure(JsonParser jsonParser) throws IOException, JsonProcessingException {

    }

    @Override
    public JsonStreamContext getOutputContext() {
        return null;
    }

}
