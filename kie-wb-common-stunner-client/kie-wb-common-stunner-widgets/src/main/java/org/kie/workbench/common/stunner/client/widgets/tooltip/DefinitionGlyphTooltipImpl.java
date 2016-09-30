package org.kie.workbench.common.stunner.client.widgets.tooltip;

import com.ait.lienzo.client.core.shape.Group;
import org.kie.workbench.common.stunner.core.api.DefinitionManager;
import org.kie.workbench.common.stunner.core.api.FactoryManager;
import org.kie.workbench.common.stunner.core.client.ShapeManager;
import org.kie.workbench.common.stunner.core.client.components.glyph.DefinitionGlyphTooltip;
import org.kie.workbench.common.stunner.core.client.components.glyph.GlyphTooltip;
import org.kie.workbench.common.stunner.core.client.shape.factory.ShapeFactory;
import org.kie.workbench.common.stunner.core.client.shape.view.ShapeGlyph;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefinitionGlyphTooltipImpl
        extends GlyphTooltipImpl
        implements DefinitionGlyphTooltip<Group> {

    DefinitionManager definitionManager;
    ShapeManager shapeManager;
    FactoryManager factoryManager;

    private String prefix;
    private String suffix;

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
    public DefinitionGlyphTooltip<Group> setPrefix( final String prefix ) {
        this.prefix = prefix;
        return this;
    }

    @Override
    public DefinitionGlyphTooltip<Group> setSuffix( final String suffix ) {
        this.suffix = suffix;
        return this;
    }

    @Override
    public DefinitionGlyphTooltipImpl showTooltip(final String definitionId,
                            final double x,
                            final double y,
                            final GlyphTooltip.Direction direction) {

        final String title = getTitle( definitionId );

        if ( null != title ) {

            this.show( getTitleToShow( title ), x, y, direction );

        }

        return this;
    }

    @Override
    public DefinitionGlyphTooltipImpl showGlyph( final String definitionId,
                      final double x,
                      final double y,
                      final double width,
                      final double height,
                       final GlyphTooltip.Direction direction) {

        final String title = getTitle( definitionId );

        if ( null != title ) {

            final ShapeFactory<?, ?, ?> factory = shapeManager.getFactory( definitionId );

            final ShapeGlyph glyph = factory.glyph ( definitionId, width, height );

            this.show( glyph, getTitleToShow( title ), x, y, direction );

        }

        return this;
    }

    // TODO: Do not create model instances here.
    private String getTitle( final String id ) {

        if ( null != id && id.trim().length() > 0 ) {

            final Object def = factoryManager.newDefinition( id );

            if ( null != def ) {

                return definitionManager.adapters().forDefinition().getTitle( def );

            }

        }

        return null;
    }

    private String getTitleToShow( final String text ) {
        return ( null != prefix && prefix.trim().length() > 0 ? prefix : "" )
                + text
                + ( null != suffix && suffix.trim().length() > 0 ? suffix : "" );

    }

}
