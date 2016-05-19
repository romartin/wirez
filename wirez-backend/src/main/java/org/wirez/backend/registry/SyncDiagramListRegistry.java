package org.wirez.backend.registry;

import org.wirez.core.diagram.Diagram;
import org.wirez.core.registry.diagram.BaseDiagramListRegistry;

import javax.enterprise.context.Dependent;
import java.util.ArrayList;
import java.util.Collections;

@Dependent
@SyncRegistry
public class SyncDiagramListRegistry<D extends Diagram> extends BaseDiagramListRegistry<D> {

    public SyncDiagramListRegistry() {
        super(Collections.synchronizedList(new ArrayList<D>()));
    }
    
}
