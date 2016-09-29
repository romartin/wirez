package org.kie.workbench.common.stunner.core.lookup.rule;

import org.kie.workbench.common.stunner.core.api.DefinitionManager;
import org.kie.workbench.common.stunner.core.lookup.criteria.AbstractCriteriaLookupManager;
import org.kie.workbench.common.stunner.core.lookup.criteria.Criteria;
import org.kie.workbench.common.stunner.core.rule.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.*;

@ApplicationScoped
@Criteria
public class RuleLookupManagerImpl 
        extends AbstractCriteriaLookupManager<Rule, Rule, RuleLookupRequest>
        implements RuleLookupManager {
    
    DefinitionManager definitionManager;

    protected RuleLookupManagerImpl() {
    }
    
    @Inject
    public RuleLookupManagerImpl( final DefinitionManager definitionManager ) {
        this.definitionManager = definitionManager;
    }

    @Override
    protected List<Rule> getItems(final RuleLookupRequest request ) {
        final String defSetId = request.getDefinitionSetId();
        final Object defSet = definitionManager.definitionSets().getDefinitionSetById( defSetId );

        if ( null != defSet ) {
            final Collection<Rule> rules = definitionManager.adapters().forRules().getRules(defSet);
            return new LinkedList<>( rules );
        }
        
        return null;
    }

    @Override
    protected Rule buildResult( final Rule rule ) {
        return rule;
    }

    @Override
    protected boolean matches( final String key, final String value, final Rule rule ) {
        
        switch ( key ) {
            
            case "type":
                return "containment".equals( value ) && ( rule instanceof ContainmentRule ) ||
                        "connection".equals( value ) && ( rule instanceof ConnectionRule ) ||
                        "cardinality".equals( value ) && ( rule instanceof CardinalityRule ) ||
                        "edgecardinality".equals( value ) && ( rule instanceof EdgeCardinalityRule );
            
            case "roles":

                try {

                    // Permitted roles on containment rules.
                    final ContainmentRule cr = (ContainmentRule) rule;
                    final Set<String> rolesSet = toSet( value );
                    if ( null != rolesSet ) {
                        return isIntersect( cr.getPermittedRoles(), rolesSet );
                    }
                    
                } catch (final ClassCastException e) {
                    return false;
                }

                return true;
            
            case "id":

                String _id = null;
                
                if ( rule instanceof EdgeCardinalityRule ) {

                    final EdgeCardinalityRule er = (EdgeCardinalityRule) rule;
                    _id = er.getId();

                } else if ( rule instanceof ContainmentRule ) {

                    final ContainmentRule er = (ContainmentRule) rule;
                    _id = er.getId();
    
                } else if ( rule instanceof ConnectionRule ) {

                    final ConnectionRule er = (ConnectionRule) rule;
                    _id = er.getId();

                }

                return _id != null && _id.equals( value );
            
            case "role":
                
                if ( rule instanceof EdgeCardinalityRule ) {

                    final EdgeCardinalityRule er = (EdgeCardinalityRule) rule;
                    return ( er.getRole().equals( value ) );
                    
                }
                
                return false;

            case "roleIn":

                if ( rule instanceof EdgeCardinalityRule ) {

                    final EdgeCardinalityRule er = (EdgeCardinalityRule) rule;
                    final Set<String> set = toSet( value );
                    if ( null != set && !set.isEmpty() ) {
                        for ( final String s : set ) {
                            if ( er.getRole().equals( value ) ) {
                                return true;
                            }
                        }
                    }

                }

                return false;

            case "edgeType":

                try {
                    
                    final EdgeCardinalityRule er = (EdgeCardinalityRule) rule;
                    return er.getType().equals(  "incoming".equals( value ) ? 
                            EdgeCardinalityRule.Type.INCOMING : EdgeCardinalityRule.Type.OUTGOING );
                    
                } catch (final ClassCastException e) {
                    return false;
                }

            case "from":
            case "to":
                
                // Connection rules.
                try {
                    final ConnectionRule cr = (ConnectionRule) rule;
                    final Set<String> fromSet = toSet( value );
                    Set<String> ruleSet = getRoles( cr.getPermittedConnections(), "from".equals( key ) );
                    if ( null != fromSet ) {
                        return isIntersect( fromSet, ruleSet );
                    }
                    
                } catch (final Exception e) {
                    return false;
                }
        }
        
        throw new UnsupportedOperationException( "Cannot filter rules by key [" + key + "]" );
    }
    
    private Set<String> getRoles(final Set<ConnectionRule.PermittedConnection> connections,
                                 final boolean from) {
        if ( null != connections ) {
            final HashSet<String> result = new HashSet<>( connections.size() );
            for ( final ConnectionRule.PermittedConnection c :connections ) {
                if ( from ) {
                    result.add( c.getStartRole() );
                } else {
                    result.add( c.getEndRole() );   
                }
            }
            
            return result;
        }
        
        return null;
    }

    
}
