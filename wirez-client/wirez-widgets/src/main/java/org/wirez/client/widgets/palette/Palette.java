package org.wirez.client.widgets.palette;

import com.google.gwt.user.client.ui.IsWidget;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.shape.factory.ShapeFactory;

public interface Palette<I> extends IsWidget {

    interface Callback {

        void onAddShape( Object definition, ShapeFactory<?, ?, ? extends Shape> factory, double x, double y );
    }

    void showEmpty();

    void show( int width, I item, Callback callback);
    
    void clear();
    
}
