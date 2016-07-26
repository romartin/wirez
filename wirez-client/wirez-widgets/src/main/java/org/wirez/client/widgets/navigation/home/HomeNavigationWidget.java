package org.wirez.client.widgets.navigation.home;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.uberfire.client.mvp.UberView;
import org.wirez.client.widgets.navigation.home.item.HomeNavigationItem;
import org.wirez.client.widgets.navigation.navigator.diagrams.DiagramsNavigator;
import org.wirez.client.widgets.navigation.navigator.shapesets.ShapeSetsNavigator;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.logging.Logger;

@Dependent
public class HomeNavigationWidget implements IsWidget {

    private static Logger LOGGER = Logger.getLogger( HomeNavigationWidget.class.getName() );

    public interface View extends UberView<HomeNavigationWidget> {

        View add( HomeNavigationItem.View view );

        View clear();

    }

    DiagramsNavigator diagramsNavigator;
    ShapeSetsNavigator shapeSetsNavigator;
    Instance<HomeNavigationItem> navigationItemInstances;
    View view;

    @Inject
    public HomeNavigationWidget( final View view,
                                 final Instance<HomeNavigationItem> navigationItemInstances,
                                 final DiagramsNavigator diagramsNavigator,
                                 final ShapeSetsNavigator shapeSetsNavigator ) {
        this.view = view;
        this.navigationItemInstances = navigationItemInstances;
        this.diagramsNavigator = diagramsNavigator;
        this.shapeSetsNavigator = shapeSetsNavigator;
    }

    @PostConstruct
    public void init() {
        view.init(this);
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }
    
    public void show() {

        clear();


        // A group for creating new diagrams using the Shape Sets navigator.

        final HomeNavigationItem shapeSetsNavigatorItem = newItem();

        view.add( shapeSetsNavigatorItem.getView() );

        shapeSetsNavigatorItem
                .setCollapsible( true )
                // .show( "Choose a type below to create a new diagram", "New diagram", shapeSetsNavigator );
                .show( IconType.PLUS_CIRCLE, "Create a new diagram", shapeSetsNavigator );

        // A group that contains existing diagrams using the Diagrams navigator.

        final HomeNavigationItem diagramsNavigatorItem = newItem();

        view.add( diagramsNavigatorItem.getView() );

        diagramsNavigatorItem
                .setCollapsible( false )
                .show( "My diagrams", "Load a diagram", diagramsNavigator );

    }

    public void clear() {

        view.clear();

    }

    private HomeNavigationItem newItem() {
        return navigationItemInstances.get();
    }
}
