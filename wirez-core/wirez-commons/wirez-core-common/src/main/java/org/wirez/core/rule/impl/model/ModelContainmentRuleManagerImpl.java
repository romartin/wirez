package org.wirez.core.rule.impl.model;

import org.wirez.core.rule.ContainmentRule;
import org.wirez.core.rule.DefaultRuleViolations;
import org.wirez.core.rule.RuleViolations;
import org.wirez.core.rule.impl.AbstractContainmentRuleManager;
import org.wirez.core.rule.impl.violations.ContainmentRuleViolation;
import org.wirez.core.rule.model.ModelContainmentRuleManager;

import javax.enterprise.context.Dependent;
import java.util.HashSet;
import java.util.Set;

@Dependent
public class ModelContainmentRuleManagerImpl extends AbstractContainmentRuleManager<String, Set<String>> implements ModelContainmentRuleManager {

    private static final String NAME = "Domain Model Containment Rule Manager";

    @Override
    public String getName() {
        return NAME;
    }
    
    @Override
    protected RuleViolations dpEvaluate(final String targetId, 
                                        final Set<String> candidateRoles) {

        final DefaultRuleViolations results = new DefaultRuleViolations();
        
        for ( ContainmentRule rule : rules ) {
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
