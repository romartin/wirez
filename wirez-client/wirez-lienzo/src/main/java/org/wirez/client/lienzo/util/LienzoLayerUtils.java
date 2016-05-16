package org.wirez.client.lienzo.util;

import com.ait.lienzo.client.core.shape.Shape;
import org.wirez.client.lienzo.LienzoLayer;
import org.wirez.core.client.shape.view.ShapeView;

public class LienzoLayerUtils {
    
    public static String getUUID_At(final LienzoLayer lienzoLayer, final double x, final double y ) {
        int sx = (int) x;
        int sy = (int) y;
        final Shape<?> shape = lienzoLayer.getLienzoLayer().getLayer().findShapeAtPoint( sx, sy );
        final String viewUUID = getShapeUUID( shape );
        return viewUUID;
    }

    private static String getShapeUUID( final Shape<?> lienzoShape ) {
        if ( null != lienzoShape ) {
            if ( hasUUID( lienzoShape ) ) {
                return getNodeViewUUID( lienzoShape );
            }
            com.ait.lienzo.client.core.shape.Node<?> parent = lienzoShape.getParent();
            while ( null != parent && !hasUUID( parent ) ) {
                parent = parent.getParent();
            }

            if ( null != parent ) {
                return getNodeViewUUID( parent );
            }
        }

        return null;
    }

    private static boolean hasUUID(final com.ait.lienzo.client.core.shape.Node<?> node) {
        return node != null && node.getUserData() != null && ( node.getUserData() instanceof String ) &&
                ( ( (String) node.getUserData()).startsWith(ShapeView.UUID_PREFFIX) );
    }

    private static String getNodeViewUUID(final com.ait.lienzo.client.core.shape.Node<?> node) {
        final String userData = (String) node.getUserData();
        return userData.substring( ShapeView.UUID_PREFFIX.length(), userData.length() - 1);
    }
    
}
