package org.wirez.core.api.rule.impl.empty;

import org.wirez.core.api.rule.DefaultRuleViolations;
import org.wirez.core.api.rule.EdgeCardinalityRuleManager;
import org.wirez.core.api.rule.RuleViolations;
import org.wirez.core.api.rule.impl.AbstractEdgeCardinalityRuleManager;

import javax.enterprise.context.Dependent;

@Dependent
@Empty
public class EmptyEdgeCardinalityRuleManager extends AbstractEdgeCardinalityRuleManager<Object, Object, Object> 
        implements EdgeCardinalityRuleManager<Object, Object, Object> {

    public static final transient EmptyEdgeCardinalityRuleManager INSTANCE = new EmptyEdgeCardinalityRuleManager();

    private static final String NAME = "Empty Edge Cardinality Rule Manager";
    
    @Override
    public String getName() {
        return NAME;
    }


    @Override
    protected RuleViolations doEvaluate(final Object edgeId, 
                                        final Object outLabels, 
                                        final Object inLabels, 
                                        final Object outEdgesCount, 
                                        final Object inEdgesCount, 
                                        final Operation operation) {
        return new DefaultRuleViolations();
    }
    
}
