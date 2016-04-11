package org.wirez.core.client.mutation;

import org.wirez.core.api.graph.util.GraphUtils;

/**
 * Provides a graph mutation context (with graph related stuff) and wraps a regular mutation context.
 */
public class GraphContextImpl implements GraphContext {
    
    private final Context wrapped;
    private final GraphUtils graphUtils;

    public GraphContextImpl(Context wrapped, GraphUtils graphUtils) {
        this.wrapped = wrapped;
        this.graphUtils = graphUtils;
    }


    @Override
    public GraphUtils getGraphUtils() {
        return graphUtils;
    }

    @Override
    public MutationType getType() {
        return wrapped.getType();
    }
    
}
