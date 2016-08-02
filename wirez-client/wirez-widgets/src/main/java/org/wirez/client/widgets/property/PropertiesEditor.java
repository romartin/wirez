/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wirez.client.widgets.property;

import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import org.uberfire.client.mvp.UberView;
import org.uberfire.ext.properties.editor.model.PropertyEditorCategory;
import org.uberfire.ext.properties.editor.model.PropertyEditorEvent;
import org.uberfire.ext.properties.editor.model.PropertyEditorFieldInfo;
import org.uberfire.ext.properties.editor.model.PropertyEditorType;
import org.wirez.core.api.DefinitionManager;
import org.wirez.core.client.canvas.event.AbstractCanvasHandlerEvent;
import org.wirez.core.client.canvas.event.registration.CanvasElementRemovedEvent;
import org.wirez.core.client.canvas.event.registration.CanvasElementUpdatedEvent;
import org.wirez.core.client.canvas.event.registration.CanvasElementsClearEvent;
import org.wirez.core.client.canvas.event.selection.CanvasClearSelectionEvent;
import org.wirez.core.client.canvas.event.selection.CanvasElementSelectedEvent;
import org.wirez.core.definition.adapter.DefinitionAdapter;
import org.wirez.core.definition.adapter.PropertyAdapter;
import org.wirez.core.definition.adapter.PropertySetAdapter;
import org.wirez.core.definition.property.PropertyType;
import org.wirez.core.definition.property.type.*;
import org.wirez.core.definition.util.DefinitionUtils;
import org.wirez.core.graph.Element;
import org.wirez.core.graph.content.view.Bounds;
import org.wirez.core.graph.util.GraphUtils;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.ShapeState;
import org.wirez.core.client.canvas.command.CanvasCommand;
import org.wirez.core.client.canvas.command.CanvasCommandManager;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.event.*;
import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.client.session.command.Session;
import org.wirez.core.client.shape.Shape;
import org.wirez.core.client.util.WirezClientLogger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.uberfire.commons.validation.PortablePreconditions.checkNotNull;

@Dependent
public class PropertiesEditor implements IsWidget {

    private static Logger LOGGER = Logger.getLogger( PropertiesEditor.class.getName() );

    public interface View extends UberView<PropertiesEditor> {

        void handle( PropertyEditorEvent propertyEditorEvent );

        void clear();

    }

    public interface EditorCallback {
        void onShowElement( Element<? extends org.wirez.core.graph.content.view.View<?>> element );
    }


    DefinitionUtils definitionUtils;
    GraphUtils graphUtils;
    ShapeManager wirezClientManager;
    CanvasCommandFactory canvasCommandFactory;
    CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager;
    View view;

    private AbstractCanvasHandler canvasHandler;
    private EditorCallback editorCallback;
    private String elementUUID;

    @Inject
    public PropertiesEditor( final DefinitionUtils definitionUtils,
                             final GraphUtils graphUtils,
                             final View view,
                             final ShapeManager wirezClientManager,
                             final CanvasCommandFactory canvasCommandFactory,
                             final @Session CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager ) {
        this.definitionUtils = definitionUtils;
        this.graphUtils = graphUtils;
        this.view = view;
        this.wirezClientManager = wirezClientManager;
        this.canvasCommandFactory = canvasCommandFactory;
        this.canvasCommandManager = canvasCommandManager;
    }

    @PostConstruct
    public void init() {
        view.init( this );
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }

    public void show( final AbstractCanvasHandler canvasHandler ) {
        this.canvasHandler = canvasHandler;
    }

    public void setEditorCallback( final EditorCallback editorCallback ) {
        this.editorCallback = editorCallback;
    }

    interface PropertyValueChangedHandler {

        void onValueChanged( Object value );
    }

    @SuppressWarnings( "unchecked" )
    protected void show( final Element<? extends org.wirez.core.graph.content.view.View<?>> element ) {
        assert element != null;

        this.elementUUID = element.getUUID();

        final Object definition = element.getContent().getDefinition();
        final DefinitionAdapter definitionAdapter = getDefinitionManager().adapters().registry().getDefinitionAdapter( definition.getClass() );

        final List<PropertyEditorCategory> categories = new ArrayList<PropertyEditorCategory>();
        final Set<String> processedProperties = new HashSet<String>();

        // Default element's category.
        final PropertyEditorCategory elementCategory = buildElementCategory( element );
        categories.add( elementCategory );

        // Definition property packages.
        final Set<?> propertyPackageSet = definitionAdapter.getPropertySets( definition );
        if ( null != propertyPackageSet && !propertyPackageSet.isEmpty() ) {

            for ( final Object propertyPackage : propertyPackageSet ) {
                final PropertySetAdapter propertySetAdapter = getDefinitionManager().adapters().registry().getPropertySetAdapter( propertyPackage.getClass() );
                final Set<?> properties = propertySetAdapter.getProperties( propertyPackage );

                final PropertyEditorCategory category = new PropertyEditorCategory( propertySetAdapter.getName( propertyPackage ) );

                if ( properties != null ) {

                    for ( final Object _property : properties ) {

                        final PropertyAdapter propertyAdapter = getDefinitionManager().adapters().registry().getPropertyAdapter( _property.getClass() );
                        final String propertyId = propertyAdapter.getId( _property );
                        final Object property = graphUtils.getProperty( element, propertyId );
                        final Object value = propertyAdapter.getValue( property );

                        final PropertyEditorFieldInfo propFieldInfo =
                                buildGenericFieldInfo( element,
                                        property,
                                        value,
                                        value1 -> executeUpdateProperty( element, propertyId, value1 ) );

                        if ( propFieldInfo != null ) {

                            processedProperties.add( propertyId );
                            category.withField( propFieldInfo );

                        }

                    }

                }

                categories.add( category );

            }

        }


        final Set<?> properties = definitionAdapter.getProperties( definition );
        PropertyEditorCategory pCategory = buildPropertiesCategory( element, processedProperties, properties );

        categories.add( pCategory );

        // Show the categories.
        view.handle( new PropertyEditorEvent( "wirezPropertiesEditorEvent", categories ) );

        // Editor callback notifications.
        if ( null != editorCallback ) {
            editorCallback.onShowElement( element );
        }

    }

    private void showError( ClientRuntimeError error ) {
        log( Level.SEVERE, WirezClientLogger.getErrorMessage( error ) );
    }

    @SuppressWarnings( "unchecked" )
    private PropertyEditorCategory buildPropertiesCategory( final Element<? extends org.wirez.core.graph.content.view.View<?>> element,
                                                            final Set<String> processedPropertyIds,
                                                            final Set<?> properties ) {

        final Object definition = element.getContent().getDefinition();
        final String title = getDefinitionManager().adapters().forDefinition().getTitle( definition );

        final PropertyEditorCategory result = new PropertyEditorCategory( title, 1 );

        if ( null != properties && !properties.isEmpty() ) {

            for ( final Object property : properties ) {

                final PropertyAdapter propertyAdapter = getDefinitionManager().adapters().registry().getPropertyAdapter( property.getClass() );

                final String pId = propertyAdapter.getId( property );

                // TODO: final boolean readOnly = propertyAdapter.isReadOnly( property );

                if ( !processedPropertyIds.contains( pId ) ) {

                    final Object value = propertyAdapter.getValue( property );

                    final PropertyEditorFieldInfo fieldInfo =
                            buildGenericFieldInfo( element,
                                    property,
                                    value, value1 -> executeUpdateProperty( element, pId, value1 ) );

                    if ( fieldInfo != null ) {

                        result.withField( fieldInfo );

                    }

                }

            }

        }

        return result;

    }

    @SuppressWarnings( "unchecked" )
    private PropertyEditorFieldInfo buildGenericFieldInfo( final Element<? extends org.wirez.core.graph.content.view.View<?>> element,
                                                           final Object property,
                                                           final Object value,
                                                           final PropertyValueChangedHandler changedHandler ) {
        final PropertyAdapter propertyAdapter = getDefinitionManager().adapters().registry().getPropertyAdapter( property.getClass() );
        final PropertyType sourceType = propertyAdapter.getType( property );
        PropertyEditorType type = null;

        final Map<Object, String> allowedValues = new LinkedHashMap<>();

        if ( ( sourceType instanceof StringType )
                || ( sourceType instanceof AssignmentsType )
                || ( sourceType instanceof VariablesType ) ) {

            type = PropertyEditorType.TEXT;

        } else if ( sourceType instanceof ColorType ) {

            type = PropertyEditorType.COLOR;

        } else if ( sourceType instanceof IntegerType ) {

            type = PropertyEditorType.NATURAL_NUMBER;

        } else if ( sourceType instanceof DoubleType ) {

            type = PropertyEditorType.NATURAL_NUMBER;

        } else if ( sourceType instanceof BooleanType ) {

            type = PropertyEditorType.BOOLEAN;

        } else if ( sourceType instanceof EnumType ) {

            type = PropertyEditorType.COMBO;

            final Map<Object, String> comboValues = propertyAdapter.getAllowedValues( property );

            if ( null != comboValues && !comboValues.isEmpty() ) {

                allowedValues.putAll( comboValues );

            }

        }

        if ( type == null ) {
            throw new RuntimeException( "Unsupported property type for properties adaptor. [PropertyId=" + propertyAdapter.getId( property ) + ", type=" + sourceType.getName() + "]." );
        }

        final PropertyEditorType theType = type;

        PropertyEditorFieldInfo result =
                new PropertyEditorFieldInfo( propertyAdapter.getCaption( property ), toEditorValue( theType, value ), type ) {

                    @Override
                    public void setCurrentStringValue( final String currentStringValue ) {

                        super.setCurrentStringValue( currentStringValue );

                        Object _v = fromEditorValue( sourceType, theType, currentStringValue, allowedValues );

                        changedHandler.onValueChanged( _v );

                    }

                };

        if ( !allowedValues.isEmpty() ) {

            result.withComboValues( new ArrayList<String>( allowedValues.values() ) );

        }

        return result;
    }

    private String toEditorValue( final PropertyEditorType type, final Object value ) {

        if ( null != value ) {

            switch ( type ) {

                case COLOR:

                    final String color = value.toString();

                    if ( color.startsWith( "#" ) ) {

                        return color.substring( 1, color.length() );

                    } else {

                        return color;

                    }

                default:

                    return value.toString();

            }

        }

        return "";

    }

    private Object fromEditorValue( final PropertyType propertyType,
                                    final PropertyEditorType type,
                                    final String value,
                                    final Map<Object, String> allowedValues ) {
        if ( null != value ) {

            switch ( type ) {

                case COLOR:

                    return "#" + value;

                case NATURAL_NUMBER:

                    if ( DoubleType.name.equals( propertyType.getName() ) ) {
                        return Double.parseDouble( value );
                    }

                    return Integer.parseInt( value );

                case BOOLEAN:

                    return Boolean.parseBoolean( value );

                case COMBO:

                    return getAllowedValue( allowedValues, value );

                default:

                    return value;

            }

        }

        return null;

    }

    private Object getAllowedValue( final Map<Object, String> allowedValues,
                                    final String value ) {

        if ( null != value && null != allowedValues && !allowedValues.isEmpty() ) {

            for ( final Map.Entry<Object, String> entry : allowedValues.entrySet() ) {

                final String v = entry.getValue();

                if ( value.equals( v ) ) {

                    return entry.getKey();

                }

            }

        }

        return null;
    }

    private PropertyEditorCategory buildElementCategory( final Element<? extends org.wirez.core.graph.content.view.View<?>> element ) {
        final PropertyEditorCategory result = new PropertyEditorCategory( "Element", 0 );

        final String id = element.getUUID();
        final double x = element.getContent().getBounds().getUpperLeft().getX();
        final double y = element.getContent().getBounds().getUpperLeft().getY();

        // Element ID
        final PropertyEditorFieldInfo idField = new PropertyEditorFieldInfo( "Id",
                id,
                PropertyEditorType.TEXT ) {

            @Override
            public void setCurrentStringValue( final String currentStringValue ) {
                // Cannot be modified.
                super.setCurrentStringValue( id );
            }
        };

        // Element X
        final PropertyEditorFieldInfo xField = new PropertyEditorFieldInfo( "X",
                Double.toString( x ),
                PropertyEditorType.NATURAL_NUMBER ) {

            @Override
            public void setCurrentStringValue( final String currentStringValue ) {
                super.setCurrentStringValue( currentStringValue );
                final Double x = Double.parseDouble( currentStringValue );
                final Double y = element.getContent().getBounds().getUpperLeft().getY();
                executeMove( element, x, y );
            }
        };

        // Element Y
        final PropertyEditorFieldInfo yField = new PropertyEditorFieldInfo( "Y",
                Double.toString( y ),
                PropertyEditorType.NATURAL_NUMBER ) {

            @Override
            public void setCurrentStringValue( final String currentStringValue ) {
                super.setCurrentStringValue( currentStringValue );
                final Double y = Double.parseDouble( currentStringValue );
                final Double x = element.getContent().getBounds().getUpperLeft().getX();
                executeMove( element, x, y );
            }
        };

        return result.withField( idField ).withField( xField ).withField( yField );
    }

    private void executeUpdateProperty( final Element<? extends org.wirez.core.graph.content.view.View<?>> element,
                                        final String propertyId,
                                        final Object value ) {
        execute( canvasCommandFactory.UPDATE_PROPERTY( element, propertyId, value ) );
    }

    private void executeMove( final Element<? extends org.wirez.core.graph.content.view.View<?>> element,
                              final double x,
                              final double y ) {
        execute( canvasCommandFactory.UPDATE_POSITION( element, x, y ) );
    }

    private void execute( final CanvasCommand<AbstractCanvasHandler> command ) {
        canvasCommandManager.execute( canvasHandler, command );
    }

    private double getWidth( final Element<? extends org.wirez.core.graph.content.view.View<?>> element ) {
        final Bounds.Bound ul = element.getContent().getBounds().getUpperLeft();
        final Bounds.Bound lr = element.getContent().getBounds().getLowerRight();
        return lr.getX() - ul.getX();
    }

    private double getHeight( final Element<? extends org.wirez.core.graph.content.view.View<?>> element ) {
        final Bounds.Bound ul = element.getContent().getBounds().getUpperLeft();
        final Bounds.Bound lr = element.getContent().getBounds().getLowerRight();
        return lr.getY() - ul.getY();
    }

    public void clear() {
        this.canvasHandler = null;
        doClear();
    }

    private void doClear() {
        elementUUID = null;
        view.clear();
    }

    void onCanvasElementSelectedEvent( @Observes CanvasElementSelectedEvent event ) {
        checkNotNull( "event", event );

        final String uuid = event.getElementUUID();

        if ( null != uuid ) {

            final Element<? extends org.wirez.core.graph.content.view.View<?>> element = this.canvasHandler.getGraphIndex().get( uuid );
            show( element );

        } else {

            doClear();

        }

    }

    void CanvasClearSelectionEvent( @Observes CanvasClearSelectionEvent clearSelectionEvent ) {
        checkNotNull( "clearSelectionEvent", clearSelectionEvent );
        doClear();
    }

    void onCanvasElementsClearEvent( @Observes CanvasElementsClearEvent canvasClearEvent ) {
        if ( checkEventContext( canvasClearEvent ) ) {
            doClear();
        }
    }

    void onCanvasElementRemovedEvent( @Observes CanvasElementRemovedEvent elementRemovedEvent ) {
        if ( checkEventContext( elementRemovedEvent ) ) {
            final Element element = elementRemovedEvent.getElement();
            final String _elementUUID = element.getUUID();
            if ( PropertiesEditor.this.elementUUID != null
                    && PropertiesEditor.this.elementUUID.equals( _elementUUID ) ) {
                doClear();
            }
        }
    }

    void onCanvasElementUpdatedEvent( @Observes CanvasElementUpdatedEvent canvasElementUpdatedEvent ) {
        if ( checkEventContext( canvasElementUpdatedEvent ) ) {
            final Element element = canvasElementUpdatedEvent.getElement();
            final String _elementUUID = element.getUUID();
            if ( PropertiesEditor.this.elementUUID != null
                    && PropertiesEditor.this.elementUUID.equals( _elementUUID ) ) {
                show( element );
            }
        }
    }

    protected boolean checkEventContext( final AbstractCanvasHandlerEvent canvasHandlerEvent ) {
        final CanvasHandler _canvasHandler = canvasHandlerEvent.getCanvasHandler();
        return canvasHandler != null && canvasHandler.equals( _canvasHandler );
    }

    private DefinitionManager getDefinitionManager() {
        return definitionUtils.getDefinitionManager();
    }

    private void log( final Level level, final String message ) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log( level, message );
        }
    }

}
