package org.kie.workbench.common.stunner.core.graph.util;

import org.kie.workbench.common.stunner.core.api.DefinitionManager;
import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Element;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.Graph;
import org.kie.workbench.common.stunner.core.graph.content.definition.Definition;
import org.kie.workbench.common.stunner.core.graph.content.definition.DefinitionSet;
import org.kie.workbench.common.stunner.core.graph.content.view.BoundImpl;
import org.kie.workbench.common.stunner.core.graph.content.view.BoundsImpl;
import org.kie.workbench.common.stunner.core.graph.content.Bounds;
import org.kie.workbench.common.stunner.core.graph.content.view.View;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class GraphUtils {

    DefinitionManager definitionManager;

    protected GraphUtils() {

    }

    @Inject
    @SuppressWarnings("all")
    public GraphUtils(final DefinitionManager definitionManager ) {
        this.definitionManager = definitionManager;
    }

    public Object getProperty(final Element<? extends Definition> element, final String id) {
        return getProperty( definitionManager, element, id );
    }
    
    public static Object getProperty(final DefinitionManager definitionManager, 
                                     final Element<? extends Definition> element, 
                                     final String id ) {
        if ( null != element ) {
            final Object def = element.getContent().getDefinition();
            final Set<?> properties = definitionManager.adapters().forDefinition().getProperties( def );
            return getProperty(definitionManager, properties, id);
        }

        return null;
    }

    public Object getProperty(final Set<?> properties, final String id) {
        return getProperty( definitionManager, properties, id );
    }

    public static Object getProperty(final DefinitionManager definitionManager, 
                                     final Set<?> properties, 
                                     final String id ) {
        if ( null != id && null != properties ) {
            for (final Object property : properties) {
                final String pId = definitionManager.adapters().forProperty().getId( property );
                if (pId.equals(id)) {
                    return property;
                }
            }
        }

        return null;
    }

    public <T> int countDefinitions(final Graph<?, ? extends Node> target,
                                    final T definition) {

        final String id = getDefinitionId( definition );

        int count = 1;
        for ( Node<? extends View, ? extends Edge> node : target.nodes() ) {
            if (getElementDefinitionId( node ).equals( id ))  {
                count++;
            }
        }

        return count;
    }

    public int countEdges( final String edgeId, 
                               final List<? extends Edge> edges ) {
        if ( null != edges ) {
            int c = 0;
            for ( Edge e : edges ) {
                final String eId = getElementDefinitionId( e );
                if ( null != eId && edgeId.equals( eId ) ) {
                    c++;
                }

            }

            return c;
        }

        return 0;
    }

    private <T> String getDefinitionId( final T definition ) {

        return definitionManager.adapters().forDefinition().getId( definition );

    }

    public String getElementDefinitionId( final Element<?> element ) {

        String targetId = null;

        if ( element.getContent() instanceof Definition) {
            
            final Object definition = ((Definition) element.getContent()).getDefinition();
            targetId = getDefinitionId( definition );
            
        } else if ( element.getContent() instanceof DefinitionSet) {
            
            targetId = ((DefinitionSet) element.getContent()).getDefinition();
            
        }

        return targetId;
    }

    public static Double[] getPosition(final View element) {
        final Bounds.Bound ul = element.getBounds().getUpperLeft();
        final double x = ul.getX();
        final double y = ul.getY();
        return new Double[] { x, y };
    }

    public static Double[] getSize(final View element) {
        final Bounds.Bound ul = element.getBounds().getUpperLeft();
        final Bounds.Bound lr = element.getBounds().getLowerRight();
        final double w = lr.getX() - ul.getX();
        final double h = lr.getY() - ul.getY();
        return new Double[] { Math.abs(w), Math.abs(h) };
    }

    public static void updateBounds(final double radius,
                                    final View element) {
        final Double[] coords = getPosition(element);
        updateBounds(coords[0], coords[1], radius, element);
    }

    public static void updateBounds(final double x,
                                    final double y,
                                    final double radius,
                                    final View element) {
        updateBounds(x, y ,radius * 2, radius * 2, element);
    }

    public static void updateBounds(final double width, final double height, final View element) {
        final Double[] coords = getPosition(element);
        updateBounds(coords[0], coords[1], width, height, element);
    }


    public static void updateBounds(final double x,
                                    final double y,
                                    final double width,
                                    final double height,
                                    final View element) {

        final Bounds bounds = new BoundsImpl(
                new BoundImpl(x, y),
                new BoundImpl(x + width, y + height)
        );

        element.setBounds(bounds);
    }

}
