package org.kie.workbench.common.stunner.core.client.components.palette.model.definition;

import org.kie.workbench.common.stunner.core.client.components.palette.model.PaletteDefinitionBuilder;
import org.kie.workbench.common.stunner.core.client.service.ClientRuntimeError;

public interface DefinitionSetPaletteBuilder extends PaletteDefinitionBuilder<Object, DefinitionSetPalette, ClientRuntimeError> {

    interface PaletteCategoryProvider {

        String getTitle( String id );

        String getDescription( String id  );

    }

    interface PaletteMorphGroupProvider {

        String getTitle( String morphBaseId, Object definition );

        String getDescription( String morphBaseId, Object definition );

    }

    DefinitionSetPaletteBuilder setCategoryProvider( PaletteCategoryProvider categoryProvider );

    DefinitionSetPaletteBuilder setMorphGroupProvider( PaletteMorphGroupProvider groupProvider );

}
