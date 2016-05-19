package org.wirez.core.graph.util;

import org.wirez.core.api.DefinitionManager;
import org.wirez.core.definition.adapter.DefinitionAdapter;
import org.wirez.core.definition.adapter.PropertyAdapter;
import org.wirez.core.definition.util.DefinitionUtils;
import org.wirez.core.graph.Edge;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.Node;
import org.wirez.core.graph.Graph;
import org.wirez.core.graph.content.definition.Definition;
import org.wirez.core.graph.content.definition.DefinitionSet;
import org.wirez.core.graph.content.view.BoundImpl;
import org.wirez.core.graph.content.view.Bounds;
import org.wirez.core.graph.content.view.BoundsImpl;
import org.wirez.core.graph.content.view.View;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class GraphUtils {

    DefinitionManager definitionManager;
    DefinitionUtils definitionUtils;

    protected GraphUtils() {

    }

    @Inject
    @SuppressWarnings("all")
    public GraphUtils(final DefinitionManager definitionManager,
                      final DefinitionUtils definitionUtils) {
        this.definitionManager = definitionManager;
        this.definitionUtils = definitionUtils;
    }

    public Object getProperty(final Element<? extends Definition> element, final String id) {
        return getProperty( definitionManager, element, id );
    }
    
    public static Object getProperty(final DefinitionManager definitionManager, 
                                     final Element<? extends Definition> element, 
                                     final String id ) {
        if ( null != element ) {
            final Object def = element.getContent().getDefinition();
            final DefinitionAdapter<Object> adapter = definitionManager.getDefinitionAdapter( def.getClass() );
            final Set<?> properties = adapter.getProperties( def );
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
                final PropertyAdapter<Object> adapter = definitionManager.getPropertyAdapter( property.getClass() );
                final String pId = adapter.getId( property );
                if (pId.equals(id)) {
                    return property;
                }
            }
        }

        return null;
    }

    public boolean isNode(Class<?> graphElementClass) {

        return graphElementClass.equals(Node.class);

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

    public  <T> String getDefinitionId( final T definition ) {

        return definitionUtils.getDefinitionId( definition );

    }

    public  <T> Set<String> getDefinitionLabels( final T definition ) {

        return definitionUtils.getDefinitionLabels( definition );

    }

    public String getElementDefinitionId( final Element<?> element ) {

        String targetId = null;

        if ( element.getContent() instanceof Definition) {
            
            final Object definition = ((Definition) element.getContent()).getDefinition();
            targetId = getDefinitionId( definition );
            
        } else if ( element.getContent() instanceof DefinitionSet) {
            
            targetId = ((DefinitionSet) element.getContent()).getId();
            
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
                new BoundImpl(x + width, y + height),
                new BoundImpl(x, y)
        );

        element.setBounds(bounds);
    }

}
