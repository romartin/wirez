package org.wirez.client.widgets.navigation.navigator.shapesets;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Widget;
import org.wirez.client.widgets.event.CreateEmptyDiagramEvent;
import org.wirez.client.widgets.navigation.navigator.NavigatorItem;
import org.wirez.client.widgets.navigation.navigator.NavigatorView;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.ShapeSet;
import org.wirez.core.definition.util.DefinitionUtils;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

@Dependent
public class ShapeSetsNavigatorImpl implements ShapeSetsNavigator {

    private static Logger LOGGER = Logger.getLogger( ShapeSetsNavigatorImpl.class.getName() );

    ShapeManager shapeManager;
    DefinitionUtils definitionUtils;
    Event<CreateEmptyDiagramEvent> createEmptyDiagramEventEvent;
    Instance<ShapeSetNavigatorItem> shapeSetNavigatorItemInstances;
    NavigatorView<?> view;

    private int width;
    private int height;
    private Style.Unit unit;

    private List<NavigatorItem<ShapeSet>> items = new LinkedList<>();

    @Inject
    public ShapeSetsNavigatorImpl( final ShapeManager shapeManager,
                                   final DefinitionUtils definitionUtils,
                                   final Event<CreateEmptyDiagramEvent> createEmptyDiagramEventEvent,
                                   final Instance<ShapeSetNavigatorItem> shapeSetNavigatorItemInstances,
                                   final NavigatorView<?> view ) {
        this.shapeManager = shapeManager;
        this.definitionUtils = definitionUtils;
        this.createEmptyDiagramEventEvent = createEmptyDiagramEventEvent;
        this.shapeSetNavigatorItemInstances = shapeSetNavigatorItemInstances;
        this.view = view;
        this.width = 140;
        this.height = 140;
        this.unit = Style.Unit.PX;
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }

    public ShapeSetsNavigatorImpl setItemSize( final int width,
                             final int height,
                             final Style.Unit unit ) {
        this.width = width;
        this.height = height;
        this.unit = unit;

        return this;
    }

    @SuppressWarnings( "unchecked" )
    public ShapeSetsNavigatorImpl show() {

        final Collection<ShapeSet> shapeSets = shapeManager.getShapeSets();

        if ( shapeSets != null && !shapeSets.isEmpty() ) {

            for ( final ShapeSet shapeSet : shapeSets ) {

                final String id = shapeSet.getId();

                final ShapeSetNavigatorItem item = newNavigatorItem();

                items.add( item );

                view.add( item.getView() );

                item.show( shapeSet,
                        width,
                        height,
                        unit,
                        () -> createEmptyDiagramEventEvent.fire( new CreateEmptyDiagramEvent( id ) ) );

            }

        }

        return this;
    }

    private ShapeSetNavigatorItem newNavigatorItem() {
        return shapeSetNavigatorItemInstances.get();
    }

    public ShapeSetsNavigatorImpl clear() {
        items.clear();
        view.clear();
        return this;
    }

    @Override
    public List<NavigatorItem<ShapeSet>> getItems() {
        return items;
    }

}
