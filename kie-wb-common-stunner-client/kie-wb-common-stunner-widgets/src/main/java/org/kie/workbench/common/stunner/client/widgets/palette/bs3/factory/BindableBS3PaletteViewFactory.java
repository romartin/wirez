package org.kie.workbench.common.stunner.client.widgets.palette.bs3.factory;

import com.google.gwt.user.client.ui.IsWidget;
import org.kie.workbench.common.stunner.core.definition.adapter.binding.BindableAdapterUtils;

import java.util.Map;

public abstract class BindableBS3PaletteViewFactory<V extends IsWidget> implements BS3PaletteViewFactory {

    protected abstract Class<?> getDefinitionSetType();
    protected abstract Map<Class<?>, V> getDefinitionViews();
    protected abstract Map<String, V> getCategoryViews();
    protected abstract V resize( V widget, int width, int height );

    @Override
    public boolean accepts( final String id ) {
        final String dId = getDefinitionSetId( getDefinitionSetType() );
        return null != id && id.equals( dId );
    }

    @Override
    public IsWidget getCategoryView( final String categoryId,
                                     final int width,
                                     final int height ) {

        final Map.Entry<String, V> entry = getDCategoryViewEntry( categoryId );

        if ( null != entry ) {

            final V w = entry.getValue();

            return resize( w, width, height );

        }

        return null;


    }

    @Override
    public IsWidget getDefinitionView( final String defId,
                                       final int width,
                                       final int height ) {

        final Map.Entry<Class<?>, V> entry = getDefinitionViewEntry( defId );

        if ( null != entry ) {

            final V w = entry.getValue();

            return resize( w, width, height );

        }

        return null;
    }

    private Map.Entry<Class<?>, V> getDefinitionViewEntry( final String id ) {

        final Map<Class<?>, V> map = getDefinitionViews();

        if ( null != map && !map.isEmpty() ) {

            for ( final Map.Entry<Class<?>, V> entry : map.entrySet() ) {

                final String _id = getDefinitionId( entry.getKey() );

                if ( _id.equals( id ) ) {

                    return entry;

                }

            }

        }

        return null;
    }

    private Map.Entry<String, V> getDCategoryViewEntry( final String id ) {

        final Map<String, V> map = getCategoryViews();

        if ( null != map && !map.isEmpty() ) {

            for ( final Map.Entry<String, V> entry : map.entrySet() ) {

                if ( entry.getKey().equals( id ) ) {

                    return entry;

                }

            }

        }

        return null;
    }

    private String getDefinitionSetId( final Class<?> type ) {
        return BindableAdapterUtils.getDefinitionSetId( type );
    }

    private String getDefinitionId( final Class<?> type ) {
        return BindableAdapterUtils.getDefinitionId( type );
    }

}
