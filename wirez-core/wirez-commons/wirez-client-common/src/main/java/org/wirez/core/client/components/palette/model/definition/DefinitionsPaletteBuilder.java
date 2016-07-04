package org.wirez.core.client.components.palette.model.definition;

import org.wirez.core.client.components.palette.model.PaletteDefinitionBuilder;
import org.wirez.core.client.service.ClientRuntimeError;

import java.util.List;

public interface DefinitionsPaletteBuilder extends PaletteDefinitionBuilder<Iterable<String>, DefinitionsPalette, ClientRuntimeError> {

    void buildFromDefinitionSet( String defintionSetId , Callback<DefinitionsPalette, ClientRuntimeError> callback );

    void buildFromPaletteItems(List<DefinitionPaletteItem> definitionPaletteItems, Callback<DefinitionsPalette, ClientRuntimeError> callback );

}
