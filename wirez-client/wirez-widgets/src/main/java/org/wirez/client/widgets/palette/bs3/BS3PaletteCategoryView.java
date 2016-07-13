package org.wirez.client.widgets.palette.bs3;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import org.gwtbootstrap3.client.ui.Column;
import org.gwtbootstrap3.client.ui.Container;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.Row;
import org.gwtbootstrap3.client.ui.constants.ColumnSize;
import org.gwtbootstrap3.client.ui.constants.HeadingSize;

import javax.enterprise.context.Dependent;

@Dependent
public class BS3PaletteCategoryView extends Composite implements BS3PaletteCategory.View {

    interface ViewBinder extends UiBinder<Widget, BS3PaletteCategoryView> {

    }

    private static ViewBinder uiBinder = GWT.create( ViewBinder.class );
    
    private BS3PaletteCategory presenter;

    @UiField
    Container mainContainer;

    private String bgColor = BS3PaletteWidgetImpl.HOVER_BG_COLOR;

    @Override
    public void init(final BS3PaletteCategory presenter) {
        this.presenter = presenter;
        initWidget( uiBinder.createAndBindUi( this ) );
    }

    @Override
    public BS3PaletteCategory.View setWidth( final int px ) {
        mainContainer.setWidth( px + "px" );
        return this;
    }

    @Override
    public BS3PaletteCategory.View setHeight( final int px ) {
        mainContainer.setHeight( px + "px" );
        return this;

    }

    @Override
    public BS3PaletteCategory.View addTitle( final String title ) {
        addHeader( title, HeadingSize.H1, true );
        return this;
    }

    @Override
    public BS3PaletteCategory.View addHeader( final String text ) {
        addHeader( text, HeadingSize.H3, false );
        return this;
    }

    @Override
    public BS3PaletteCategory.View addSeparator( final double height ) {
        final FlowPanel panel = new FlowPanel(  );
        panel.getElement().getStyle().setHeight( height, Style.Unit.PX );
        final Row separatorRow = createRow1Col( panel );
        mainContainer.add( separatorRow );
        return this;
    }

    @Override
    public BS3PaletteCategory.View setBackgroundColor( final String color ) {
        this.bgColor = color;
        mainContainer.getElement().getStyle().setBackgroundColor( color );
        return this;
    }

    @Override
    public BS3PaletteCategory.View addItem( final String id,
                                            final String text,
                                            final String description,
                                            final String glyphDefId,
                                            final IsWidget view ) {


        final Column iconColumn = new Column( ColumnSize.MD_2 );
        iconColumn.add( view );

        final HTML itemText = new HTML( text );
        itemText.setTitle( description );

        final Column textColumn = new Column( ColumnSize.MD_9 );
        textColumn.add( itemText );

        final Row row = new Row();
        row.getElement().getStyle().setCursor( Style.Cursor.POINTER );
        row.add( iconColumn );
        row.add( textColumn );
        row.setPaddingRight( 5 );
        row.setPaddingLeft( 5 );
        row.setPaddingBottom( 5 );
        row.setPaddingTop( 5 );

        row.addDomHandler( mouseOverEvent -> BS3PaletteCategoryView.this.onMouseOver( row ), MouseOverEvent.getType() );

        row.addDomHandler( mouseOutEvent -> BS3PaletteCategoryView.this.onMouseOut( row ), MouseOutEvent.getType() );

        row.addDomHandler( mouseDownEvent -> BS3PaletteCategoryView.this.onMouseDown( id,
                mouseDownEvent.getClientX(), mouseDownEvent.getClientY(),
                mouseDownEvent.getX(), mouseDownEvent.getY() ), MouseDownEvent.getType() );

        mainContainer.add( row );

        return this;
    }

    private void onMouseOver( final Row row ) {
        row.getElement().getStyle().setBackgroundColor( BS3PaletteWidgetImpl.BG_COLOR );
    }

    private void onMouseOut( final Row row ) {
        row.getElement().getStyle().setBackgroundColor( bgColor );
    }

    private void onMouseDown( final String id,
                              final int mouseX,
                              final int mouseY,
                              final int itemX,
                              final int itemY ) {
        presenter.onMouseDown( id, mouseX, mouseY, itemX, itemY );
    }

    @Override
    public BS3PaletteCategory.View clear() {
        mainContainer.clear();
        return this;
    }

    private void addHeader( final String text,
                            final HeadingSize size,
                            final boolean isAlignCenter ) {
        final Heading heading = new Heading( size );
        heading.setText( text );
        heading.setTitle( text );
        heading.getElement().getStyle().setFontWeight( Style.FontWeight.BOLD );
        final HorizontalPanel hp = new HorizontalPanel();
        hp.setWidth( "100%" );
        hp.setHorizontalAlignment( isAlignCenter ? HasHorizontalAlignment.ALIGN_CENTER : HasHorizontalAlignment.ALIGN_LEFT );
        hp.add( heading );
        final Row titleRow = createRow1Col( hp );
        mainContainer.add( titleRow );
    }

    private Row createRow1Col( final IsWidget widget ) {
        final Row row = new Row();
        final Column column = new Column( ColumnSize.MD_12 );
        row.add( column );
        column.add( widget );
        return row;
    }

}
