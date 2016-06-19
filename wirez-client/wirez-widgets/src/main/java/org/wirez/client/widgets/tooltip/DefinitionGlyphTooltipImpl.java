package org.wirez.client.widgets.tooltip;

import com.ait.lienzo.client.core.shape.Group;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.api.FactoryManager;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.components.glyph.GlyphTooltip;
import org.wirez.core.client.shape.factory.ShapeFactory;
import org.wirez.core.client.shape.view.ShapeGlyph;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefinitionGlyphTooltipImpl
        extends GlyphTooltipImpl
        implements org.wirez.core.client.components.glyph.DefinitionGlyphTooltip<Group> {

    DefinitionManager definitionManager;
    ShapeManager shapeManager;
    FactoryManager factoryManager;

    protected DefinitionGlyphTooltipImpl() {
        this ( null, null, null, null );
    }

    @Inject
    public DefinitionGlyphTooltipImpl(final DefinitionManager definitionManager,
                                      final ShapeManager shapeManager,
                                      final FactoryManager factoryManager,
                                      final View view) {
        super( view );
        this.definitionManager = definitionManager;
        this.factoryManager = factoryManager;
        this.shapeManager = shapeManager;
    }

    @Override
    public void showTooltip(final String definitionId,
                            final double x,
                            final double y,
                            final GlyphTooltip.Direction direction) {

        final String title = getTitle( definitionId );

        if ( null != title ) {

            this.show( title, x, y, direction );

        }

    }

    @Override
    public void showGlyph( final String definitionId,
                      final double x,
                      final double y,
                      final double width,
                      final double height,
                       final GlyphTooltip.Direction direction) {

        final String title = getTitle( definitionId );

        if ( null != title ) {

            final ShapeFactory<?, ?, ?> factory = shapeManager.getFactory( definitionId );

            final ShapeGlyph glyph = factory.glyph ( definitionId, width, height );

            this.show( glyph, title, x, y, direction );

        }

    }

    // TODO: Do not create model isntances here.
    private String getTitle( final String id ) {

        if ( null != id && id.trim().length() > 0 ) {

            final Object def = factoryManager.newDomainObject( id );

            if ( null != def ) {

                return definitionManager.getDefinitionAdapter( def.getClass() ).getTitle( def );

            }

        }

        return null;
    }

}
