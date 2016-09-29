package org.kie.workbench.common.stunner.core.definition.adapter;

/**
 * Adapters based on priorities at runtime.
 */
public interface PriorityAdapter extends Adapter {

    /**
     * As small priority value, highest priority.
     */
    int getPriority();
    
}
