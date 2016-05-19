package org.wirez.client.widgets.canvas.preview;

import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.uberfire.client.mvp.UberView;
import org.wirez.core.client.canvas.Canvas;
import org.wirez.core.client.canvas.Layer;
import org.wirez.core.client.canvas.event.CanvasClearEvent;
import org.wirez.core.client.canvas.event.CanvasDrawnEvent;
import org.wirez.core.client.canvas.event.registration.CanvasShapeAddedEvent;
import org.wirez.core.client.canvas.event.registration.CanvasShapeRemovedEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.uberfire.commons.validation.PortablePreconditions.checkNotNull;

// TODO: Unused for now -> improve performance by creating some context2D layer proxy instead of generating the image data as currently it does.
@Dependent
public class CanvasPreview implements IsWidget {

    private static Logger LOGGER = Logger.getLogger(CanvasPreview.class.getName());
    private static final String SIZE = "400px";
    
    
    public interface View extends UberView<CanvasPreview> {

        View show( String dataUrl, String width, String height );
        
        View clear();
        
    }

    View view;
    
    @Inject
    public CanvasPreview(final View view) {
        this.view = view;
    }

    @PostConstruct
    public void init() {
        view.init(this);
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }

    public void start( final Canvas<?> canvas ) {
        clear();
        
        if ( null != canvas ) {
            
            final Layer layer = canvas.getLayer();
            final String dataUrl = layer.toDataURL();
            view.show( dataUrl, SIZE, SIZE );
            
        }
    }

    public void clear() {
        view.clear();
    }

    void onCanvasClear(@Observes CanvasClearEvent canvasClearEvent) {
        checkNotNull("canvasClearEvent", canvasClearEvent);
        start( canvasClearEvent.getCanvas() );
    }

    void onCanvasShapeAdded(@Observes CanvasShapeAddedEvent canvasShapeAddedEvent) {
        start( canvasShapeAddedEvent.getCanvas() );
    }

    void onCanvasShapeRemoved(@Observes CanvasShapeRemovedEvent canvasShapeRemovedEvent) {
        start( canvasShapeRemovedEvent.getCanvas() );
    }
    
    void onCanvasDrawn(@Observes CanvasDrawnEvent canvasDrawnEvent) {
        checkNotNull("canvasDrawnEvent", canvasDrawnEvent);
        start( canvasDrawnEvent.getCanvas() );
    }
    
    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }

}
