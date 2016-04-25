package org.wirez.core.api.rule.impl.model;

import org.uberfire.commons.data.Pair;
import org.wirez.core.api.rule.ConnectionRule;
import org.wirez.core.api.rule.DefaultRuleViolations;
import org.wirez.core.api.rule.RuleViolations;
import org.wirez.core.api.rule.impl.AbstractConnectionRuleManager;
import org.wirez.core.api.rule.impl.violations.ConnectionRuleViolation;
import org.wirez.core.api.rule.model.ModelConnectionRuleManager;

import javax.enterprise.context.Dependent;
import java.util.HashSet;
import java.util.Set;

@Dependent
@Model
public class ModelConnectionRuleManagerImpl extends AbstractConnectionRuleManager<String, Set<String>> implements ModelConnectionRuleManager {

    public static final String NAME = "Domain Model Connection Rule Manager";
    
    @Override
    public String getName() {
        return NAME;
    }
    
    @Override
    protected RuleViolations doEvaluate(final String edgeId, 
                                        final Set<String> outgoingLabels, 
                                        final Set<String> incomingLabels) {

        final DefaultRuleViolations results = new DefaultRuleViolations();
        
        final Set<Pair<String, String>> couples = new HashSet<Pair<String, String>>();
        for ( ConnectionRule rule : rules ) {
            if ( edgeId.equals( rule.getId() ) ) {
                for ( ConnectionRule.PermittedConnection pc : rule.getPermittedConnections() ) {
                    couples.add( new Pair<String, String>( pc.getStartRole(), pc.getEndRole() ) );
                    if ( outgoingLabels.contains( pc.getStartRole() ) ) {
                        if ( incomingLabels.contains( pc.getEndRole() ) ) {
                            return results;
                        }
                    }
                }
            }
        }

        results.addViolation(new ConnectionRuleViolation(edgeId, couples));
        return results;
        
    }

   
    
}
