package org.wirez.client.widgets.navigation.navigator.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.gwtbootstrap3.client.ui.Column;
import org.gwtbootstrap3.client.ui.Container;
import org.gwtbootstrap3.client.ui.Row;
import org.gwtbootstrap3.client.ui.constants.ColumnSize;
import org.wirez.client.widgets.navigation.navigator.NavigatorItem;
import org.wirez.client.widgets.navigation.navigator.NavigatorItemView;
import org.wirez.client.widgets.navigation.navigator.NavigatorView;

import javax.enterprise.context.Dependent;

@Dependent
public class BootstrapNavigatorView
        extends Composite
        implements NavigatorView<NavigatorItem<?>> {

    interface ViewBinder extends UiBinder<Widget, BootstrapNavigatorView> {

    }

    private static ViewBinder uiBinder = GWT.create( ViewBinder.class );
    
    @UiField
    Container mainPanel;

    private Row currentRow;
    private int itemCounter;

    public BootstrapNavigatorView() {
        initWidget( uiBinder.createAndBindUi( this ) );
        this.currentRow = null;
        this.itemCounter = 0;
    }

    @Override
    public BootstrapNavigatorView add( final NavigatorItemView<NavigatorItem<?>> view ) {
        addItem( view.asWidget() );
        return this;
    }

    @Override
    public BootstrapNavigatorView clear() {
        mainPanel.clear();
        this.itemCounter = 0;
        return this;
    }

    private void addItem( final IsWidget widget ) {

        if ( null == currentRow || ( itemCounter == 4 ) ) {
            currentRow = new Row();
            mainPanel.add( currentRow );
            itemCounter = 0;
        }

        final Column column = new Column( ColumnSize.MD_3 );
        column.add( widget );

        currentRow.add( column );

        itemCounter++;

    }
    
}
