package org.kie.workbench.common.stunner.client.lienzo.shape.view.glyph;

import com.ait.lienzo.client.core.shape.Group;
import org.kie.workbench.common.stunner.core.client.shape.view.AbstractShapeGlyph;

public abstract class AbstractLienzoShapeGlyph extends AbstractShapeGlyph<Group> {

    public AbstractLienzoShapeGlyph(final Group group,
                                    final double width,
                                    final double height) {
        super( group, width, height );
    }

    @Override
    protected Group doCopy() {
        return group.copy();
    }
    
}
