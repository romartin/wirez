package org.kie.workbench.common.stunner.core.rule.impl.model;

import org.kie.workbench.common.stunner.core.rule.DefaultRuleViolations;
import org.kie.workbench.common.stunner.core.rule.impl.AbstractConnectionRuleManager;
import org.kie.workbench.common.stunner.core.rule.impl.violations.ConnectionRuleViolation;
import org.uberfire.commons.data.Pair;
import org.kie.workbench.common.stunner.core.rule.ConnectionRule;
import org.kie.workbench.common.stunner.core.rule.RuleViolations;
import org.kie.workbench.common.stunner.core.rule.model.ModelConnectionRuleManager;

import javax.enterprise.context.Dependent;
import java.util.HashSet;
import java.util.Set;

@Dependent
public class ModelConnectionRuleManagerImpl extends AbstractConnectionRuleManager<String, Set<String>> implements ModelConnectionRuleManager {

    private static final String NAME = "Domain Model Connection Rule Manager";
    
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
