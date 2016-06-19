package org.wirez.client.widgets.palette.view;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import org.wirez.core.client.components.glyph.ShapeGlyphDragHandler;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class PaletteWidgetViewImpl extends AbstractPaletteWidgetView implements PaletteWidgetView {

    interface ViewBinder extends UiBinder<Widget, PaletteWidgetViewImpl> {

    }

    private static PaletteWidgetViewImpl.ViewBinder uiBinder = GWT.create( PaletteWidgetViewImpl.ViewBinder.class );

    @UiField
    FlowPanel mainPanel;

    @UiField
    FlowPanel noCanvasPanel;

    @UiField
    SimplePanel palettePanel;

    @Inject
    public PaletteWidgetViewImpl( final ShapeGlyphDragHandler<LienzoPanel, Group> shapeGlyphDragHandler ) {
        super( shapeGlyphDragHandler );
    }

    @PostConstruct
    public void init() {
        initWidget( uiBinder.createAndBindUi( this ) );
    }

    @Override
    protected Panel getParentPanel() {
        return palettePanel;
    }

    @Override
    public void showEmptyView( final boolean visible ) {
        noCanvasPanel.setVisible( visible );
    }

}
