package org.wirez.core.api.registry;

import org.wirez.core.api.diagram.Diagram;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;

@Dependent
@List
public class DiagramListRegistry<D extends Diagram> extends BaseDiagramListRegistry<D> implements Serializable {
    
    public DiagramListRegistry() {
        super(new ArrayList<D>());
    }

}
