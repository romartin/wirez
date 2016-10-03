package org.kie.workbench.common.stunner.bpmn.backend.marshall.json.builder;

import org.codehaus.jackson.*;
import org.kie.workbench.common.stunner.bpmn.BPMNDefinitionSet;
import org.kie.workbench.common.stunner.bpmn.backend.marshall.json.oryx.Bpmn2OryxManager;
import org.kie.workbench.common.stunner.bpmn.definition.BPMNDiagram;
import org.kie.workbench.common.stunner.core.api.DefinitionManager;
import org.kie.workbench.common.stunner.core.api.FactoryManager;
import org.kie.workbench.common.stunner.core.command.Command;
import org.kie.workbench.common.stunner.core.command.CommandManager;
import org.kie.workbench.common.stunner.core.command.CommandResult;
import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Graph;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.command.EmptyRulesCommandExecutionContext;
import org.kie.workbench.common.stunner.core.graph.command.GraphCommandExecutionContext;
import org.kie.workbench.common.stunner.core.graph.command.factory.GraphCommandFactory;
import org.kie.workbench.common.stunner.core.graph.content.definition.DefinitionSet;
import org.kie.workbench.common.stunner.core.graph.content.view.View;
import org.kie.workbench.common.stunner.core.graph.util.GraphUtils;
import org.kie.workbench.common.stunner.core.rule.RuleViolation;
import org.kie.workbench.common.stunner.core.util.UUID;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Support for a basic single process hierarchy
 */
public class BPMNGraphGenerator extends JsonGenerator {

    BPMNGraphObjectBuilderFactory bpmnGraphBuilderFactory;
    DefinitionManager definitionManager;
    FactoryManager factoryManager;
    GraphUtils graphUtils;
    Bpmn2OryxManager oryxManager;
    CommandManager<GraphCommandExecutionContext, RuleViolation> commandManager;
    GraphCommandFactory commandFactory;
    Stack<GraphObjectBuilder> nodeBuilders = new Stack<>();
    Stack<GraphObjectParser> parsers = new Stack<GraphObjectParser>();
    Collection<GraphObjectBuilder<?, ?>> builders = new LinkedList<GraphObjectBuilder<?, ?>>();
    Graph<DefinitionSet, Node> graph;
    boolean isClosed;
    
    public BPMNGraphGenerator(final BPMNGraphObjectBuilderFactory bpmnGraphBuilderFactory,
                              final DefinitionManager definitionManager,
                              final FactoryManager factoryManager,
                              final GraphUtils graphUtils,
                              final Bpmn2OryxManager oryxManager,
                              final CommandManager<GraphCommandExecutionContext, RuleViolation> commandManager,
                              final GraphCommandFactory commandFactory) {
        this.bpmnGraphBuilderFactory = bpmnGraphBuilderFactory;
        this.definitionManager = definitionManager;
        this.factoryManager = factoryManager;
        this.graphUtils = graphUtils;
        this.oryxManager = oryxManager;
        this.commandManager = commandManager;
        this.commandFactory = commandFactory;
        this.parsers.push(new RootObjectParser(null));
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
    @SuppressWarnings("unchecked")
    public void close() throws IOException {
        logBuilders();

        this.graph = ( Graph<DefinitionSet, Node> ) factoryManager.newElement(UUID.uuid(), BPMNDefinitionSet.class );;

        // TODO: Improve this - Remove the default diagram built by the bpmn graph factory.
        Iterator<Node> nodes = this.graph.nodes().iterator();
        while ( nodes.hasNext() ) {
            graph.removeNode(nodes.next().getUUID());
        }
        
        // Initialize the builder context.
        builderContext.init(graph);
        
        NodeObjectBuilder diagramBuilder = getDiagramBuilder(builderContext);
        if (diagramBuilder == null) {
            throw new RuntimeException("No diagrams found!");
        }

        Node<View<BPMNDiagram>, Edge> diagramNode = (Node<View<BPMNDiagram>, Edge>) diagramBuilder.build(builderContext);
        graph.addNode(diagramNode);
        
        this.isClosed = true;
    }

    // TODO: Can be multiple.
    @SuppressWarnings("unchecked")
    protected NodeObjectBuilder getDiagramBuilder(final GraphObjectBuilder.BuilderContext context) {
        Collection<GraphObjectBuilder<?, ?>> builders = context.getBuilders();
        if (builders != null && !builders.isEmpty()) {
            for (GraphObjectBuilder<?, ?> builder : builders) {
                try {
                    NodeObjectBuilder nodeBuilder = (NodeObjectBuilder) builder;
                    if ( BPMNDiagram.class.equals( nodeBuilder.getDefinitionClass() ) ) {
                        return nodeBuilder;
                    }
                } catch (ClassCastException e) {
                    // Not a node. Continue with the search...
                }
            }
        }
        return null;
    }

    public Graph<DefinitionSet, Node> getGraph() {
        assert isClosed();
        return this.graph;
    }
    
    final GraphObjectBuilder.BuilderContext builderContext = new GraphObjectBuilder.BuilderContext() {

        Graph<DefinitionSet, Node> graph;

        @Override
        public void init(final Graph<DefinitionSet, Node> graph) {
            this.graph = graph;
        }

        @Override
        public Graph<DefinitionSet, Node> getGraph() {
            return graph;
        }

        @Override
        public Collection<GraphObjectBuilder<?, ?>> getBuilders() {
            return builders;
        }

        @Override
        public DefinitionManager getDefinitionManager() {
            return definitionManager;
        }

        @Override
        public FactoryManager getFactoryManager() {
            return factoryManager;
        }

        @Override
        public GraphUtils getGraphUtils() {
            return graphUtils;
        }

        @Override
        public Bpmn2OryxManager getOryxManager() {
            return oryxManager;
        }

        @SuppressWarnings("unchecked")
        public CommandResult<RuleViolation> execute (Command<GraphCommandExecutionContext, RuleViolation> command) {
            GraphCommandExecutionContext executionContext = 
                    new EmptyRulesCommandExecutionContext( definitionManager, factoryManager );
            return commandManager.execute( executionContext, command );
        }
        
        public GraphCommandFactory getCommandFactory() {
            return commandFactory;
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
     - bounds -> Delegates to bounds object parser
     */
    final class RootObjectParser implements GraphObjectParser {
        
        String fieldName;
        NodeObjectBuilder parentNodeBuilder;

        public RootObjectParser(NodeObjectBuilder parentNodeBuilder) {
            this.parentNodeBuilder = parentNodeBuilder;
        }

        @Override
        public void writeStartObject() {
            if (fieldName == null) {
                nodeBuilders.push(bpmnGraphBuilderFactory.bootstrapBuilder());
            } else if ("properties".equals(fieldName)) {
                parsers.push(new PropertiesObjectParser());
            } else if ("stencil".equals(fieldName)) {
                parsers.push(new StencilObjectParser());
            } else if ("childShapes".equals(fieldName)) {
                RootObjectParser rootObjectParser = nodeBuilders.empty() ? null : 
                        new RootObjectParser((NodeObjectBuilder) nodeBuilders.peek()); 
                parsers.push(rootObjectParser);
                nodeBuilders.push(bpmnGraphBuilderFactory.bootstrapBuilder());
            } else if ("outgoing".equals(fieldName)) {
                parsers.push(new OutgoingObjectParser());
            } else if ("bounds".equals(fieldName)) {
                parsers.push(new BoundsObjectParser());
            } else if ("dockers".equals(fieldName)) {
                parsers.push(new DockersObjectParser());
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
                if ( null != parentNodeBuilder ) {
                    parentNodeBuilder.child(value);
                }
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

    final class BoundsObjectParser implements GraphObjectParser {

        String fieldName;
        boolean isLR = false;
        boolean isUL = false;
        boolean end = false;
        Double ulX;
        Double ulY;
        Double lrX;
        Double lrY;
        
        @Override
        public void writeStartObject() {
            if ("lowerRight".equals(fieldName)) {
                isLR = true;
            }
            if ("upperLeft".equals(fieldName)) {
                isUL = true;
            }
        }

        @Override
        public void writeEndObject() {
            
            if (end) {
                nodeBuilders.peek().boundUL(ulX, ulY);
                nodeBuilders.peek().boundLR(lrX, lrY);
                parsers.pop();
            }
            
            if ( isLR && isUL ) {
                end = true;   
            }

        }

        @Override
        public void writeFieldName(String s) {
            this.fieldName = s;
        }

        @Override
        public void writeObject(Object o) {
            String value = o.toString();
            Double d = Double.valueOf(value);

            if ("x".equals(fieldName)) {
                if ( isUL && ulX == null ) {
                    ulX = d;
                }
                if ( isLR && lrX == null ) {
                    lrX = d;
                }
            }
            if ("y".equals(fieldName)) {
                if ( isUL && ulY == null ) {
                    ulY = d;
                }
                if ( isLR && lrY == null ) {
                    lrY = d;
                }
            }
            
        }

        @Override
        public void writeStartArray() {

        }

        @Override
        public void writeEndArray() {

        }
    }

    final class DockersObjectParser implements GraphObjectParser {

        String fieldName;
        Double x = 0d;
        Double y = 0d;

        @Override
        public void writeStartObject() {
            
        }

        @Override
        public void writeEndObject() {

                nodeBuilders.peek().docker( x, y );

        }

        @Override
        public void writeFieldName(String s) {
            this.fieldName = s;
        }

        @Override
        public void writeObject(Object o) {
            String value = o.toString();
            Double d = Double.valueOf(value);

            if ("x".equals(fieldName)) {
                
                this.x = d;
                
            }

            if ("y".equals(fieldName)) {

                this.y = d;
                
            }
            
        }

        @Override
        public void writeStartArray() {

        }

        @Override
        public void writeEndArray() {

            parsers.pop();

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
