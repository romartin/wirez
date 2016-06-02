package org.wirez.core.rule.impl.model;

import org.wirez.core.rule.DefaultRuleViolations;
import org.wirez.core.rule.DockingRule;
import org.wirez.core.rule.RuleViolations;
import org.wirez.core.rule.impl.AbstractDockingRuleManager;
import org.wirez.core.rule.impl.violations.ContainmentRuleViolation;
import org.wirez.core.rule.model.ModelDockingRuleManager;

import javax.enterprise.context.Dependent;
import java.util.HashSet;
import java.util.Set;

@Dependent
public class ModelDockingRuleManagerImpl extends AbstractDockingRuleManager<String, Set<String>> implements ModelDockingRuleManager {

    private static final String NAME = "Domain Model Docking Rule Manager";

    @Override
    public String getName() {
        return NAME;
    }
    
    @Override
    protected RuleViolations doEvaluate(final String targetId,
                                        final Set<String> candidateRoles) {

        final DefaultRuleViolations results = new DefaultRuleViolations();
        
        for (DockingRule rule : rules ) {
            if ( rule.getId().equals( targetId ) ) {
                final Set<String> permittedStrings = new HashSet<String>( rule.getPermittedRoles() );
                permittedStrings.retainAll( candidateRoles );
                if ( permittedStrings.size() > 0 ) {
                    return results;
                }
            }
        }

        results.addViolation(new ContainmentRuleViolation(targetId, candidateRoles.toString()));
        
        return results;
        
    }

    
}
