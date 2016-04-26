package org.wirez.core.api.registry.diagram;

import org.wirez.core.api.diagram.Diagram;
import org.wirez.core.api.registry.List;

import javax.enterprise.context.Dependent;
import java.io.Serializable;
import java.util.ArrayList;

@Dependent
@List
public class DiagramListRegistry<D extends Diagram> extends BaseDiagramListRegistry<D> implements Serializable {
    
    public DiagramListRegistry() {
        super(new ArrayList<D>());
    }

}
