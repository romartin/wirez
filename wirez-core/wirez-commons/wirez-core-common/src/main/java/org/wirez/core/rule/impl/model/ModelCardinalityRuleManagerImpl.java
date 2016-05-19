package org.wirez.core.rule.impl.model;

import org.wirez.core.rule.CardinalityRule;
import org.wirez.core.rule.DefaultRuleViolations;
import org.wirez.core.rule.RuleViolations;
import org.wirez.core.rule.impl.AbstractCardinalityRuleManager;
import org.wirez.core.rule.impl.violations.CardinalityMaxRuleViolation;
import org.wirez.core.rule.impl.violations.CardinalityMinRuleViolation;
import org.wirez.core.rule.model.ModelCardinalityRuleManager;

import javax.enterprise.context.Dependent;
import java.util.Set;

@Dependent
public class ModelCardinalityRuleManagerImpl extends AbstractCardinalityRuleManager<Set<String>, Integer> 
        implements ModelCardinalityRuleManager {

    private static final String NAME = "Domain Model Cardinality Rule Manager";

    @Override
    public String getName() {
        return NAME;
    }
    
    @Override
    protected RuleViolations doEvaluate(final Set<String> labels, 
                                        final Integer candidatesCount, 
                                        final Operation operation) {
        
        final DefaultRuleViolations results = new DefaultRuleViolations();

        for ( CardinalityRule rule : rules ) {
            if ( labels.contains( rule.getRole() ) ) {
                final long minOccurrences = rule.getMinOccurrences();
                final long maxOccurrences = rule.getMaxOccurrences();
                
                if ( candidatesCount < minOccurrences ) {
                    results.addViolation(new CardinalityMinRuleViolation(labels.toString(), rule.getRole(), (int) minOccurrences, candidatesCount));
                } else if ( maxOccurrences > -1 && candidatesCount > maxOccurrences ) {
                    results.addViolation(new CardinalityMaxRuleViolation(labels.toString(), rule.getRole(), (int) maxOccurrences, candidatesCount));
                }
            }
        }
        
        return results;
        
    }

    
}
