package org.kie.workbench.common.stunner.client.widgets.palette.bs3;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.gwtbootstrap3.client.ui.AnchorListItem;
import org.gwtbootstrap3.client.ui.Icon;
import org.gwtbootstrap3.client.ui.NavPills;
import org.gwtbootstrap3.client.ui.constants.IconRotate;
import org.gwtbootstrap3.client.ui.constants.IconSize;
import org.gwtbootstrap3.client.ui.constants.IconType;

import javax.enterprise.context.Dependent;
import java.util.LinkedList;
import java.util.List;

@Dependent
public class BS3PaletteCategoriesView extends Composite implements BS3PaletteCategories.View {

    interface ViewBinder extends UiBinder<Widget, BS3PaletteCategoriesView> {

    }

    private static ViewBinder uiBinder = GWT.create( ViewBinder.class );

    private BS3PaletteCategories presenter;
    private final List<HandlerRegistration> handlerRegistrationList = new LinkedList<>();
    private int iconSize = 25;
    private int padding = 50;

    @UiField
    NavPills mainContainer;
    
    @Override
    public void init(final BS3PaletteCategories presenter) {
        this.presenter = presenter;
        initWidget( uiBinder.createAndBindUi( this ) );
    }

    @Override
    public BS3PaletteCategories.View setPadding( final int padding ) {
        this.padding = padding;
        return this;
    }

    @Override
    public BS3PaletteCategories.View setIconSize( final int iconSize ) {
        this.iconSize = iconSize;
        return this;
    }

    @Override
    public BS3PaletteCategories.View setBackgroundColor( final String color ) {
        mainContainer.getElement().getStyle().setBackgroundColor( color );
        return this;
    }

    public BS3PaletteCategories.View add( final String categoryId,
                                          final String categoryTitle,
                                          final String categoryGlyphId,
                                          final IsWidget view) {

        final AnchorListItem item = new AnchorListItem();
        item.setId( categoryId );
        item.setTitle( categoryTitle );

        if ( null != view ) {

            if ( view instanceof Icon ) {

                final Icon icon = ( Icon ) view;
                item.setIconSize( IconSize.TIMES3 );

                final IconType type = icon.getType();
                item.setIcon( type );

                final IconRotate rotate = icon.getRotate();
                if ( null != rotate ) {
                    item.setIconRotate( rotate );
                }

            } else {

                item.add( view );

            }

        } else {

            item.setText( categoryTitle );
        }

        // Styling.
        final double w = iconSize +  padding;
        final double h = iconSize +  padding;
        item.getElement().getStyle().setWidth( w, Style.Unit.PX );
        item.getElement().getStyle().setHeight( h, Style.Unit.PX );
        item.getElement().getStyle().setTextAlign( Style.TextAlign.CENTER );

        final HandlerRegistration handlerRegistration = item.addDomHandler( mouseOverEvent ->
                presenter.onItemHover( categoryId, mouseOverEvent.getX(), mouseOverEvent.getY(), mouseOverEvent.getX(), mouseOverEvent.getY() ), MouseOverEvent.getType() );

        final HandlerRegistration handlerRegistration1 = item.addClickHandler( clickEvent -> {

            presenter.onItemClick( categoryId, clickEvent.getX(), clickEvent.getY(), clickEvent.getX(), clickEvent.getY() );

        } );

        final HandlerRegistration handlerRegistration2 = item.addDomHandler( mouseOutEvent ->
                presenter.onItemOut( categoryId ), MouseOutEvent.getType() );

        handlerRegistrationList.add( handlerRegistration );
        handlerRegistrationList.add( handlerRegistration1 );
        handlerRegistrationList.add( handlerRegistration2 );

        mainContainer.add( item );

        return this;
    }

    private void setPadding( final AnchorListItem item ) {

        final double p = padding / 2;
        item.setPaddingBottom( p );
        item.setPaddingTop( p );
        item.setPaddingLeft( p );
        item.setPaddingRight( p );

    }

    @Override
    public BS3PaletteCategories.View clear() {
        clearHandlers();
        mainContainer.clear();
        return this;
    }

    private void clearHandlers() {

        for ( final HandlerRegistration registration : handlerRegistrationList ) {

            registration.removeHandler();

        }

    }

}
