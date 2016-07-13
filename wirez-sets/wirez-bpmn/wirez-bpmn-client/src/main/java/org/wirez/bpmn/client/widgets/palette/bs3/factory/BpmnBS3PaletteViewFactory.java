package org.wirez.bpmn.client.widgets.palette.bs3.factory;

import org.gwtbootstrap3.client.ui.Icon;
import org.gwtbootstrap3.client.ui.constants.IconSize;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.wirez.bpmn.BPMNDefinitionSet;
import org.wirez.bpmn.definition.*;
import org.wirez.client.widgets.palette.bs3.factory.BindableBS3PaletteGlyphViewFactory;
import org.wirez.core.client.ShapeManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class BpmnBS3PaletteViewFactory extends BindableBS3PaletteGlyphViewFactory<Icon> {

    private final static Map<String, Icon> CATEGORY_VIEWS = new HashMap<String, Icon>() {{
        put( Categories.ACTIVITIES,  getIcon( IconType.SQUARE ) );
        put( Categories.LANES,  getIcon( IconType.COLUMNS ) );
        put( Categories.GATEWAYS,  getIcon( IconType.RANDOM ) );
        put( Categories.EVENTS,  getIcon( IconType.CIRCLE ) );
        put( Categories.CONNECTING_OBJECTS,  getIcon( IconType.LONG_ARROW_RIGHT ) );
    }};

    private final static Map<Class<?>, Icon> DEFINITION_VIEWS = new HashMap<Class<?>, Icon>() {{
        put( BPMNDiagram.class,  getIcon( IconType.FOLDER_O) );
        put( Lane.class,  getIcon( IconType.FOLDER_O ) );
        put( NoneTask.class,  getIcon( IconType.SQUARE_O ) );
        put( UserTask.class,  getIcon( IconType.USER ) );
        put( ScriptTask.class,  getIcon( IconType.CODE ) );
        put( BusinessRuleTask.class,  getIcon( IconType.INSTITUTION ) );
        put( StartNoneEvent.class,  getIcon( IconType.CIRCLE_O ) );
        put( EndNoneEvent.class,  getIcon( IconType.CIRCLE_O ) );
        put( EndTerminateEvent.class,  getIcon( IconType.DOT_CIRCLE_O) );
        put( IntermediateTimerEvent.class,  getIcon( IconType.CLOCK_O) );
        put( ParallelGateway.class,  getIcon( IconType.PLUS ) );
        put( ExclusiveDatabasedGateway.class,  getIcon( IconType.CLOSE) );
        put( ReusableSubprocess.class,  getIcon( IconType.PLUS_SQUARE_O ) );
        put( SequenceFlow.class,  getIcon( IconType.ARROW_RIGHT ) );
    }};

    @Inject
    public BpmnBS3PaletteViewFactory( final ShapeManager shapeManager ) {
        super( shapeManager );
    }

    @Override
    protected Class<?> getDefinitionSetType() {
        return BPMNDefinitionSet.class;
    }

    @Override
    protected Map<Class<?>, Icon> getDefinitionViews() {
        return DEFINITION_VIEWS;
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
