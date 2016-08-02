package org.wirez.client.widgets.explorer.tree;

import com.ait.lienzo.client.core.shape.Group;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.uberfire.client.mvp.UberView;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.definition.util.DefinitionUtils;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.util.GraphUtils;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.shape.factory.ShapeFactory;
import org.wirez.core.client.shape.view.ShapeGlyph;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.logging.Logger;

@Dependent
public class TreeExplorerItem implements IsWidget {

    private static Logger LOGGER = Logger.getLogger( TreeExplorerItem.class.getName() );

    public interface View extends UberView<TreeExplorerItem> {

        View setUUID( String uuid );

        View setName( String name );

        View setGlyph( ShapeGlyph<Group> glyph );

    }

    ShapeManager shapeManager;
    GraphUtils graphUtils;
    DefinitionUtils definitionUtils;
    View view;

    @Inject
    public TreeExplorerItem( final ShapeManager shapeManager,
                             final GraphUtils graphUtils,
                             final DefinitionUtils definitionUtils,
                             final View view ) {
        this.shapeManager = shapeManager;
        this.graphUtils = graphUtils;
        this.definitionUtils = definitionUtils;
        this.view = view;
    }

    @PostConstruct
    public void init() {
        view.init( this );
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }

    @SuppressWarnings( "unchecked" )
    public void show( final Element<org.wirez.core.graph.content.view.View> element ) {

        final Object definition = element.getContent().getDefinition();
        final String defId = definitionUtils.getDefinitionManager().adapters().forDefinition().getId( definition );
        final ShapeFactory factory = shapeManager.getFactory( defId );

        view.setUUID( element.getUUID() )
                .setName( getItemText( element ) )
                .setGlyph( factory.glyph( defId, 25, 25 ) );

    }

    private String getItemText( final Element<org.wirez.core.graph.content.view.View> item ) {
        final String name = definitionUtils.getName( item.getContent().getDefinition() );
        return ( name != null ? name : "- No name -" );
    }

}
