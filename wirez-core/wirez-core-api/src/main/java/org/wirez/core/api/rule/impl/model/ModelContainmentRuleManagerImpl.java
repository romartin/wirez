package org.wirez.core.api.rule.impl.model;

import org.wirez.core.api.rule.ContainmentRule;
import org.wirez.core.api.rule.DefaultRuleViolations;
import org.wirez.core.api.rule.RuleViolations;
import org.wirez.core.api.rule.impl.AbstractContainmentRuleManager;
import org.wirez.core.api.rule.impl.violations.ContainmentRuleViolation;
import org.wirez.core.api.rule.model.ModelContainmentRuleManager;

import javax.enterprise.context.Dependent;
import java.util.HashSet;
import java.util.Set;

@Dependent
@Model
public class ModelContainmentRuleManagerImpl extends AbstractContainmentRuleManager<String, Set<String>> implements ModelContainmentRuleManager {

    public static final String NAME = "Domain Model Containment Rule Manager";

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
