package org.wirez.core.lookup.rule;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.wirez.core.lookup.AbstractLookupRequest;
import org.wirez.core.lookup.AbstractLookupRequestBuilder;

import java.util.Set;

@Portable
public final class RuleLookupRequestImpl extends AbstractLookupRequest implements RuleLookupRequest {
    
    private final String definitionSetId;
    
    public RuleLookupRequestImpl(@MapsTo("criteria") String criteria,
                                 @MapsTo("page") int page,
                                 @MapsTo("pageSize") int pageSize,
                                 @MapsTo("definitionSetId") String definitionSetId) {
        super( criteria, page, pageSize );
        this.definitionSetId = definitionSetId;
    }

    @Override
    public String getDefinitionSetId() {
        return definitionSetId;
    }
    
    @NonPortable
    public static class Builder extends AbstractLookupRequestBuilder<Builder> {
        
        public enum RuleType {
            CONNECTION, CONTAINMENT, CARDINALITY, EDGECARDINALITY;    
        }

        public enum EdgeType {
            INCOMING, OUTGOING;
        }
        
        private String defSetId;
        private final StringBuilder criteria = new StringBuilder();

        public Builder id( final String id ) {
            criteria.append( "id=" ).append( id ).append( ";" );
            return this;
        }
        
        public Builder type( final RuleType ruleType ) {
            criteria.append( "type=" ).append( ruleType.toString().toLowerCase() ).append( ";" );
            return this;
        }

        public Builder definitionSetId( final String defSetId ) {
            this.defSetId = defSetId;
            return this;
        }

        public Builder from( final Set<String> labels  ) {
            criteria.append( "from=" ).append( fromSet( labels ) ).append( ";" );
            return this;
        }

        public Builder role( final String role ) {
            criteria.append( "role=" ).append( role ).append( ";" );
            return this;
        }

        public Builder roleIn( final Set<String> roles ) {
            criteria.append( "roleIn=" ).append( fromSet( roles ) ).append( ";" );
            return this;
        }

        public Builder edgeType( final EdgeType type ) {
            criteria.append( " edgeType=" ).append( type.name().toLowerCase() ).append( ";" );
            return this;
        }
        
        public RuleLookupRequest build( ) {
            return new RuleLookupRequestImpl( criteria.toString(), page, pageSize, defSetId );
        } 
        
    }
    
}
