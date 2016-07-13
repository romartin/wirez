package org.wirez.core.client.components.palette.factory;

import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.components.palette.model.definition.DefinitionSetPaletteBuilder;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 * The default PaletteDefinition factory for a DefinitionSetPalette model.
 * It does not accepts any identifier, it's purpose is for being injected where necessary.
 */
@Dependent
public class DefaultDefSetPaletteDefinitionFactory extends AbstractPaletteDefinitionFactory<DefinitionSetPaletteBuilder>
    implements DefSetPaletteDefinitionFactory {

    @Inject
    public DefaultDefSetPaletteDefinitionFactory( final ShapeManager shapeManager,
                                                  final DefinitionSetPaletteBuilder paletteBuilder ) {
        super( shapeManager, paletteBuilder );
    }

    @Override
    public boolean accepts( final String defSetId ) {
        return false;
    }

}
