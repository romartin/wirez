package org.wirez.core.api.rule.impl.model;

import org.wirez.core.api.rule.DefaultRuleViolations;
import org.wirez.core.api.rule.EdgeCardinalityRule;
import org.wirez.core.api.rule.RuleViolations;
import org.wirez.core.api.rule.impl.AbstractEdgeCardinalityRuleManager;
import org.wirez.core.api.rule.impl.violations.CardinalityMaxRuleViolation;
import org.wirez.core.api.rule.impl.violations.CardinalityMinRuleViolation;
import org.wirez.core.api.rule.model.ModelEdgeCardinalityRuleManager;

import javax.enterprise.context.Dependent;
import java.util.Set;

@Dependent
@Model
public class ModelEdgeCardinalityRuleManagerImpl extends AbstractEdgeCardinalityRuleManager<String, Set<String>, Integer> 
        implements ModelEdgeCardinalityRuleManager {

    public static final String NAME = "Domain Model Edge Cardinality Rule Manager";
    
    @Override
    public String getName() {
        return NAME;
    }
    
    
    @Override
    protected RuleViolations doEvaluate(final String edgeId, 
                                        final Set<String> outLabels, 
                                        final Set<String> inLabels, 
                                        final Integer outEdgesCount, 
                                        final Integer inEdgesCount, 
                                        final Operation operation) {

        final DefaultRuleViolations results = new DefaultRuleViolations();

        for ( EdgeCardinalityRule rule : rules ) {

            final int minOccurrences = rule.getMinOccurrences();
            final int maxOccurrences = rule.getMaxOccurrences();
            final EdgeCardinalityRule.Type type = rule.getType();
            
            if ( EdgeCardinalityRule.Type.OUTGOING.equals(type) &&  outLabels != null && outLabels.contains( rule.getRole() ) ) {
                if ( outEdgesCount < minOccurrences ) {
                    results.addViolation(new CardinalityMinRuleViolation( outLabels.toString(), rule.getId(), (int) minOccurrences, outEdgesCount));
                } else if ( maxOccurrences > -1 && outEdgesCount > maxOccurrences ) {
                    results.addViolation(new CardinalityMaxRuleViolation( outLabels.toString(), rule.getId(), (int) maxOccurrences, outEdgesCount));
                }
            } else if ( EdgeCardinalityRule.Type.INCOMING.equals(type) && inLabels != null && inLabels.contains( rule.getRole() ) ) {
                if ( inEdgesCount < minOccurrences ) {
                    results.addViolation(new CardinalityMinRuleViolation(inLabels.toString(), rule.getId(), (int) minOccurrences, inEdgesCount));
                } else if ( maxOccurrences > -1 && inEdgesCount > maxOccurrences ) {
                    results.addViolation(new CardinalityMaxRuleViolation(inLabels.toString(), rule.getId(), (int) maxOccurrences, inEdgesCount));
                }
            }
         
        }
        
        return results;
        
    }

    
}
