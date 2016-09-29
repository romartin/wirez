package org.kie.workbench.common.stunner.basicset.client.widgets.palette.bs3.factory;

import org.gwtbootstrap3.client.ui.Icon;
import org.gwtbootstrap3.client.ui.constants.IconSize;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.kie.workbench.common.stunner.basicset.BasicSet;
import org.kie.workbench.common.stunner.basicset.definition.Categories;
import org.kie.workbench.common.stunner.client.widgets.palette.bs3.factory.BindableBS3PaletteGlyphViewFactory;
import org.kie.workbench.common.stunner.core.client.ShapeManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class BasicSetBS3PaletteViewFactory extends BindableBS3PaletteGlyphViewFactory<Icon> {

    private final static Map<String, Icon> CATEGORY_VIEWS = new HashMap<String, Icon>() {{
        put( Categories.BASIC,  getIcon( IconType.SQUARE ) );
        put( Categories.BASIC_WITH_ICONS,  getIcon( IconType.PLUS_SQUARE_O ) );
        put( Categories.ICONS,  getIcon( IconType.DASHBOARD ) );
        put( Categories.CONNECTORS,  getIcon( IconType.LONG_ARROW_RIGHT ) );
    }};

    @Inject
    public BasicSetBS3PaletteViewFactory( final ShapeManager shapeManager ) {
        super( shapeManager );
    }

    @Override
    protected Class<?> getDefinitionSetType() {
        return BasicSet.class;
    }

    @Override
    protected Map<Class<?>, Icon> getDefinitionViews() {
        return null;
    }

    @Override
    protected Map<String, Icon> getCategoryViews() {
        return CATEGORY_VIEWS;
    }

    @Override
    protected Icon resize( final Icon widget,
                           final int width,
                           final int height ) {
        widget.setSize( IconSize.TIMES2 );
        return widget;
    }

    private static Icon getIcon( final IconType iconType ) {
        return new Icon( iconType );
    }

}
