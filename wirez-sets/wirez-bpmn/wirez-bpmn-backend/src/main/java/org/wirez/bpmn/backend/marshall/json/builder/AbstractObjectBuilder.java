package org.wirez.bpmn.backend.marshall.json.builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wirez.bpmn.definition.BPMNDefinition;
import org.wirez.bpmn.backend.marshall.json.oryx.Bpmn2OryxIdMappings;
import org.wirez.bpmn.backend.marshall.json.oryx.property.Bpmn2OryxPropertyManager;
import org.wirez.core.command.CommandResult;
import org.wirez.core.command.batch.BatchCommandResult;
import org.wirez.core.definition.adapter.DefinitionAdapter;
import org.wirez.core.definition.adapter.PropertyAdapter;
import org.wirez.core.definition.property.PropertyType;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.content.view.View;
import org.wirez.core.rule.RuleViolation;

import java.util.*;

public abstract class AbstractObjectBuilder<W, T extends Element<View<W>>> implements GraphObjectBuilder<W, T> {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractObjectBuilder.class);

    protected String nodeId;
    protected Map<String, String> properties;
    protected Set<String> outgoingResourceIds;
    protected Double[] boundUL;
    protected Double[] boundLR;
    protected T result;
            
    public AbstractObjectBuilder() {
        this.properties = new HashMap<String, String>();
        this.outgoingResourceIds = new LinkedHashSet<String>();
        this.boundUL = null;
        this.boundLR = null;
    }

    @Override
    public GraphObjectBuilder<W, T> nodeId(String nodeId) {
        this.nodeId = nodeId;
        return this;
    }

    @Override
    public GraphObjectBuilder<W, T> property(String key, String value) {
        properties.put(key, value);
        return this;
    }

    @Override
    public GraphObjectBuilder<W, T> out(String nodeId) {
        outgoingResourceIds.add(nodeId);
        return this;
    }

    @Override
    public GraphObjectBuilder<W, T> stencil(String stencilId) {
        return this;
    }

    @Override
    public GraphObjectBuilder<W, T> boundUL(Double x, Double y) {
        this.boundUL = new Double[] { x, y };
        return this;
    }

    @Override
    public GraphObjectBuilder<W, T> boundLR(Double x, Double y) {
        this.boundLR  = new Double[] { x, y };
        return this;
    }
    
    protected abstract T doBuild(BuilderContext context);

    @Override
    public T build(BuilderContext context) {
        if ( null == this.result ) {
            this.result = doBuild(context);
        }
        return this.result;
    }
    
    protected boolean hasErrors(BatchCommandResult<RuleViolation> results) {
        
        Iterator<CommandResult<RuleViolation>> iterator = results.iterator();
        while ( iterator.hasNext() ) {
            CommandResult<RuleViolation> result = iterator.next();
            if ( CommandResult.Type.ERROR.equals( result.getType() )) {
                return true;
            }
        }
        
        
        return false; 
    }

    protected boolean hasErrors(CommandResult<RuleViolation> results) {
        return CommandResult.Type.ERROR.equals( results.getType() );
    }

    protected GraphObjectBuilder<?, ?> getBuilder(BuilderContext context, String nodeId) {
        Collection<GraphObjectBuilder<?, ?>> builders = context.getBuilders();
        if (builders != null && !builders.isEmpty()) {
            for (GraphObjectBuilder<?, ?> builder : builders) {
                AbstractObjectBuilder<?, ?> abstractBuilder = (AbstractObjectBuilder<?, ?>) builder;
                if (abstractBuilder.nodeId.equals(nodeId)) {
                    return builder;
                }
            }
        }
        return null;
    }
    
    @SuppressWarnings("unchecked")
    protected void setProperties(BuilderContext context, BPMNDefinition definition) {
        assert definition != null;
        Bpmn2OryxPropertyManager propertyManager = context.getOryxManager().getPropertyManager();
        Bpmn2OryxIdMappings idMappings = context.getOryxManager().getMappingsManager();

        DefinitionAdapter<BPMNDefinition> adapter = context.getDefinitionManager().getDefinitionAdapter( definition.getClass() );
        
        Set<?> defProperties = adapter.getProperties( definition );
        
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            final String oryxId = entry.getKey();
            
            if ( !idMappings.isSkipProperty(definition.getClass(), oryxId) ) {

                final String pValue = entry.getValue();
                final String pId = idMappings.getPropertyId( definition, oryxId );
                boolean found = false;
                
                if ( null != pId ) {
                    
                    final Object property = context.getGraphUtils().getProperty( defProperties, pId );
                    
                    if ( null != property ) {
                        
                        try {
                            
                            PropertyAdapter<Object, Object> propertyAdapter = 
                                    (PropertyAdapter<Object, Object>) context.getDefinitionManager().getPropertyAdapter( property.getClass() );
                            
                            PropertyType propertyType = propertyAdapter.getType( property );
                            
                            Object value = propertyManager.parse( property, propertyType, pValue );

                            PropertyAdapter<Object, Object> propertyAdapter2 =
                                    (PropertyAdapter<Object, Object>) context.getDefinitionManager().getPropertyAdapter(property.getClass());

                            propertyAdapter2.setValue(property, value);
                            
                            found = true;
                            
                        } catch (Exception e) {
                            
                            LOG.error("Cannot parse value [" + pValue + "] for property [" + pId + "]");
                            
                        }
                        
                    }
                    
                }

                if ( !found ) {
                    
                    LOG.warn("Property [" + pId + "] not found for definition [" + pId + "]");
                    
                }
                
            }
            
        }
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(" [NodeId=").append(nodeId).append("] ");
        builder.append(" [properties=").append(properties).append("] ");
        builder.append(" [outgoingResourceIds=").append(outgoingResourceIds).append("] ");
        builder.append(" [boundUL=").append(boundUL).append("] ");
        builder.append(" [boundLR=").append(boundLR).append("] ");
        return builder.toString();
    }
}
