package org.wirez.core.client.components.palette;

import org.wirez.core.client.components.palette.model.GlyphPaletteItem;
import org.wirez.core.client.components.palette.model.HasPaletteItems;
import org.wirez.core.client.components.palette.model.definition.DefinitionPaletteItem;
import org.wirez.core.client.components.palette.model.definition.DefinitionsPalette;

import java.util.List;

public class ClientPaletteUtils {

    public static String getLongestText( final List<GlyphPaletteItem> paletteItems ) {

        if ( null == paletteItems || paletteItems.isEmpty() ) {

            return null;

        }

        String longestTitle = "";

        for ( final GlyphPaletteItem item : paletteItems ) {

            final String iTitle = item.getTitle();

            if ( null != iTitle && iTitle.length() > longestTitle.length() ) {

                longestTitle = iTitle;

            }


        }

        return longestTitle.length() > 0 ? longestTitle : null;
    }

    public static double[] computeSizeForVerticalLayout(final int itemsSize,
                                                        final int iconSize,
                                                        final int padding,
                                                        final int textLength) {

        return computeSizeForLayout( itemsSize, iconSize, padding, textLength, true );

    }

    public static double[] computeSizeForHorizontalLayout(final int itemsSize,
                                                          final int iconSize,
                                                          final int padding,
                                                          final int textLength) {

        return computeSizeForLayout( itemsSize, iconSize, padding, textLength, false );

    }

    public static double computeFontSize(final double width,
                                         final double height,
                                         final int textLength) {

        // TODO

        return 10;

    }

    public static double[] computeFontBoundingBoxSize( final double fontSize,
                                                       final int textLength ) {

        // TODO

        return new double[] { 100, 50 };

    }

    private static double[] computeSizeForLayout(final int itemsSize,
                                                 final int iconSize,
                                                 final int padding,
                                                 final int textLength,
                                                 final boolean verticalLayout ) {
        double width = 0;
        double height = 0;

        final double fixedSize = iconSize + ( padding * 2 );
        final double dynSize = ( iconSize * itemsSize ) + ( padding * 2 * itemsSize );

        width = verticalLayout ? fixedSize : dynSize;
        height = verticalLayout ? dynSize : fixedSize;

        if ( textLength > 0 ) {

            final double fontSize = computeFontSize( fixedSize, fixedSize, textLength );
            final double[] fontBBSize = computeFontBoundingBoxSize( fontSize, textLength );

            width += verticalLayout ? fontBBSize[0] : 0;
            height += !verticalLayout ? fontBBSize[1] : 0;

        }


        return new double[] { width, height };
    }


}
