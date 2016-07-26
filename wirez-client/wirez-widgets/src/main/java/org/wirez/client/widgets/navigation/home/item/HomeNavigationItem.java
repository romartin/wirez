package org.wirez.client.widgets.navigation.home.item;

import com.google.gwt.user.client.ui.IsWidget;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.uberfire.client.mvp.UberView;
import org.wirez.client.widgets.navigation.navigator.Navigator;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.logging.Logger;

@Dependent
public class HomeNavigationItem {

    private static Logger LOGGER = Logger.getLogger( HomeNavigationItem.class.getName() );

    public interface View extends UberView<HomeNavigationItem> {

        View setCollapsible( boolean collapsible );

        View setCollapsed( boolean collapsed );

        View setPanelTitle( String title );

        View setPanelIcon( IconType icon );

        View setTooltip( String tooltip );

        View add( IsWidget widget );

        View clear();

    }

    View view;

    @Inject
    public HomeNavigationItem( final View view) {
        this.view = view;
    }

    @PostConstruct
    public void init() {
        view.init(this);
    }

    public HomeNavigationItem setCollapsible( final boolean collapsible ) {
        view.setCollapsible( collapsible );
        return this;
    }

    public void show( final String title,
                      final String tooltip,
                      final Navigator<?> navigator ) {
        show( title, null, tooltip, navigator );
    }

    public void show( final IconType icon,
                      final String tooltip,
                      final Navigator<?> navigator ) {
        show( null, icon, tooltip, navigator );
    }

    private void show( final String title,
                      final IconType icon,
                      final String tooltip,
                      final Navigator<?> navigator ) {

        clear();

        if ( null != navigator ) {

            if ( null != title ) {

                view.setPanelTitle( title );
                view.setCollapsed( false );

            } else {

                view.setPanelIcon( icon );
                view.setCollapsed( true );

            }

            view.setTooltip( tooltip );

            view.add( navigator.asWidget() );

            navigator.show();

        }

    }

    public void clear() {

        view.clear();
    }

    public View getView() {
        return view;
    }
}
