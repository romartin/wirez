package org.wirez.bpmn.backend.marshall.json.builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wirez.bpmn.api.BPMNDefinition;
import org.wirez.bpmn.backend.marshall.json.oryx.Bpmn2OryxIdMappings;
import org.wirez.bpmn.backend.marshall.json.oryx.Bpmn2OryxPropertyManager;
import org.wirez.core.api.command.CommandResult;
import org.wirez.core.api.command.CommandResults;
import org.wirez.core.api.definition.adapter.DefinitionAdapter;
import org.wirez.core.api.definition.adapter.PropertyAdapter;
import org.wirez.core.api.definition.property.PropertyType;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.content.view.View;
import org.wirez.core.api.rule.RuleViolation;

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
        properties = new HashMap<String, String>();
        outgoingResourceIds = new LinkedHashSet<String>();
        boundUL = null;
        boundLR = null;
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
    
    protected boolean hasErrors(CommandResults<RuleViolation> results) {
        return results.results(CommandResult.Type.ERROR).iterator().hasNext(); 
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
    
    protected void setProperties(BuilderContext context, BPMNDefinition definition) {
        assert definition != null;
        Bpmn2OryxPropertyManager propertyManager = context.getOryxPropertyManager();
        Bpmn2OryxIdMappings idMappings = context.getOryxIdMappings();

        DefinitionAdapter<BPMNDefinition> adapter = context.getDefinitionManager().getDefinitionAdapter( definition.getClass() );
        Set<?> defProperties = adapter.getProperties( definition );
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            final String oryxId = entry.getKey();
            final String pValue = entry.getValue();
            final String pId = idMappings.getId( oryxId );
            final Object property = getProperty(context, defProperties, pId);
            if ( null != property ) {
                try {
                    PropertyAdapter propertyAdapter = context.getDefinitionManager().getPropertyAdapter( property.getClass() );
                    PropertyType propertyType = propertyAdapter.getType( property );
                    Object value = propertyManager.parse( propertyType, pValue );
                    context.getDefinitionManager().getPropertyAdapter(property.getClass()).setValue(property, value);
                } catch (Exception e) {
                   LOG.error("Cannot parse value [" + pValue + "] for property [" + pId + "]");
                }
            } else {
                LOG.warn("Property [" + pId + "] not found for definition [" + pId + "]");
            }
        }
    }
    
    protected Object getProperty(final BuilderContext context, final Set<?> defProperties, final String id) {
        return context.getGraphUtils().getProperty(defProperties, id);
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
