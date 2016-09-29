package org.kie.workbench.common.stunner.lienzo.toolbox.builder;

import com.ait.lienzo.client.core.shape.IPrimitive;
import org.kie.workbench.common.stunner.lienzo.toolbox.ToolboxButton;

public interface ButtonsOrRegister extends Register {
    ButtonsOrRegister add(ToolboxButton button);

    Button add(IPrimitive<?> shape);
}
