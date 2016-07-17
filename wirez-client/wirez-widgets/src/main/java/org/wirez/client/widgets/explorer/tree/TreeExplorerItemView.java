package org.wirez.client.widgets.explorer.tree;

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import org.gwtbootstrap3.client.ui.Heading;
import org.wirez.core.client.shape.view.ShapeGlyph;

import javax.enterprise.context.Dependent;

@Dependent
public class TreeExplorerItemView extends Composite implements TreeExplorerItem.View {

    interface ViewBinder extends UiBinder<Widget, TreeExplorerItemView> {

    }

    private static ViewBinder uiBinder = GWT.create( ViewBinder.class );
    
    private TreeExplorerItem presenter;
    private LienzoPanel lienzoPanel;
    private final Layer lienzoLayer = new Layer();
    
    
    @UiField
    SimplePanel glyphPanel;
    
    @UiField
    Heading name;

    @UiField
    HTML uuid;

    @Override
    public void init(final TreeExplorerItem presenter) {
        this.presenter = presenter;
        initWidget( uiBinder.createAndBindUi( this ) );
        lienzoLayer.setTransformable(true);
    }

    @Override
    public TreeExplorerItem.View setUUID(final String uuid) {
        final String t = "[" + uuid + "]";
        this.uuid.setText(t);
        this.uuid.setTitle(uuid);
        return this;
    }

    @Override
    public TreeExplorerItem.View setName(final String name) {
        this.name.setText(name);
        this.name.setTitle(name);
        return this;
    }

    @Override
    public TreeExplorerItem.View setGlyph(final ShapeGlyph<Group> glyph) {
        initLienzoPanel(glyph.getWidth(), glyph.getHeight());
        lienzoLayer.add( glyph.getGroup() );
        return this;
    }

    
    private void initLienzoPanel(final double width, final double height) {
        lienzoPanel = new LienzoPanel( (int) width, (int) height );
        lienzoPanel.add(lienzoLayer);
        glyphPanel.add(lienzoPanel);
    }
    
    
}
