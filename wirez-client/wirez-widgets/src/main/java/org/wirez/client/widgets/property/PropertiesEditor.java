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
import org.wirez.core.api.definition.adapter.DefinitionAdapter;
import org.wirez.core.api.definition.adapter.PropertyAdapter;
import org.wirez.core.api.definition.adapter.PropertySetAdapter;
import org.wirez.core.api.definition.property.PropertyType;
import org.wirez.core.api.definition.property.type.*;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.content.view.Bounds;
import org.wirez.core.api.graph.util.GraphUtils;
import org.wirez.core.client.Shape;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.canvas.AbstractCanvasHandler;
import org.wirez.core.client.canvas.ShapeState;
import org.wirez.core.client.canvas.command.CanvasCommand;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.listener.AbstractCanvasElementListener;
import org.wirez.core.client.canvas.listener.CanvasElementListener;
import org.wirez.core.client.event.ShapeStateModifiedEvent;
import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.client.util.WirezClientLogger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.uberfire.commons.validation.PortablePreconditions.checkNotNull;

@Dependent
public class PropertiesEditor implements IsWidget {
    
    private static Logger LOGGER = Logger.getLogger("org.wirez.client.widgets.property.PropertiesEditor");
    
    public interface View extends UberView<PropertiesEditor> {
        
        void handle(PropertyEditorEvent propertyEditorEvent);
        
        void clear();
        
    }
    
    public interface EditorCallback {
        void onShowElement(Element<? extends org.wirez.core.api.graph.content.view.View<?>> element);
    }

    
    DefinitionManager definitionManager;
    GraphUtils graphUtils;
    ShapeManager wirezClientManager;
    CanvasCommandFactory canvasCommandFactory;
    View view;
    
    private AbstractCanvasHandler canvasHandler;
    private CanvasElementListener canvasListener;
    private EditorCallback editorCallback;
    private String elementUUID;

    @Inject
    public PropertiesEditor(final DefinitionManager definitionManager,
                            final GraphUtils graphUtils,
                            final View view,
                            final ShapeManager wirezClientManager,
                            final CanvasCommandFactory canvasCommandFactory) {
        this.definitionManager = definitionManager;
        this.graphUtils = graphUtils;
        this.view = view;
        this.wirezClientManager = wirezClientManager;
        this.canvasCommandFactory = canvasCommandFactory;
    }

    @PostConstruct
    public void init() {
        view.init(this);
    }
    
    @Override
    public Widget asWidget() {
        return view.asWidget();
    }

    public void show(final AbstractCanvasHandler canvasHandler) {
        if ( this.canvasHandler != null ) {
            removeCanvasListener();
        }
        this.canvasHandler = canvasHandler;
        addCanvasListener();
    }

    public void setEditorCallback(final EditorCallback editorCallback) {
        this.editorCallback = editorCallback;
    }
    
    interface PropertyValueChangedHandler {

        void onValueChanged(Object value);
    }
    
    @SuppressWarnings("unchecked")
    protected void show(final Element<? extends org.wirez.core.api.graph.content.view.View<?>> element) {
        assert element != null;
        
        this.elementUUID = element.getUUID();
        
        final Object definition = element.getContent().getDefinition();
        final DefinitionAdapter definitionAdapter = definitionManager.getDefinitionAdapter( definition.getClass() );
        
        final List<PropertyEditorCategory> categories = new ArrayList<PropertyEditorCategory>();
        final Set<String> processedProperties = new HashSet<String>();

        // Default element's category.
        final PropertyEditorCategory elementCategory = buildElementCategory(element);
        categories.add(elementCategory);

        // Definition property packages.
        final Set<?> propertyPackageSet = definitionAdapter.getPropertySets( definition );
        for (final Object propertyPackage : propertyPackageSet) {
            final PropertySetAdapter propertySetAdapter = definitionManager.getPropertySetAdapter( propertyPackage.getClass() );
            final Set<?> properties = propertySetAdapter.getProperties( propertyPackage );

            final PropertyEditorCategory category = new PropertyEditorCategory(propertySetAdapter.getName(propertyPackage));
            if (properties != null) {
                for (final Object _property : properties) {
                    final PropertyAdapter propertyAdapter = definitionManager.getPropertyAdapter(_property.getClass());
                    final String propertyId = propertyAdapter.getId(_property);
                    final Object property = graphUtils.getProperty(element, propertyId);
                    final Object value = propertyAdapter.getValue(property);
                    final PropertyEditorFieldInfo propFieldInfo = buildGenericFieldInfo(element, property, value, new PropertyValueChangedHandler() {
                        @Override
                        public void onValueChanged(final Object value) {
                            executeUpdateProperty(element, propertyId, value);
                        }
                    });

                    if (propFieldInfo != null) {
                        processedProperties.add(propertyId);
                        category.withField(propFieldInfo);
                    }

                }
            }

            categories.add(category);

        }

        final Set<?> properties = definitionAdapter.getProperties( definition );
        PropertyEditorCategory pCategory = buildPropertiesCategory(element, processedProperties, properties);

        categories.add(pCategory);

        // Show the categories.
        view.handle(new PropertyEditorEvent("wirezPropertiesEditorEvent", categories));

        // Editor callback notifications.
        if ( null != editorCallback ) {
            editorCallback.onShowElement(element);
        }
        
    }

    private void showError(ClientRuntimeError error) {
        log(Level.SEVERE, WirezClientLogger.getErrorMessage(error));
    }

    private PropertyEditorCategory buildPropertiesCategory(final Element<? extends org.wirez.core.api.graph.content.view.View<?>> element,
                                                           final Set<String> processedPropertyIds,
                                                           final Set<?> properties ) {
        final Object definition = element.getContent().getDefinition();
        final DefinitionAdapter definitionAdapter = definitionManager.getDefinitionAdapter( definition.getClass() );
        final String title = definitionAdapter.getTitle( definition );
        final PropertyEditorCategory result = new PropertyEditorCategory(title, 1);

        if (properties != null) {
            
            for ( final Object property : properties ) {
                final PropertyAdapter propertyAdapter = definitionManager.getPropertyAdapter(property.getClass());
                String pId = propertyAdapter.getId(property);
                if (!processedPropertyIds.contains(pId)) {
                    final Object value = propertyAdapter.getValue(property);
                    final PropertyEditorFieldInfo fieldInfo = buildGenericFieldInfo(element, property, value, new PropertyValueChangedHandler() {
                        @Override
                        public void onValueChanged(final Object value) {
                            executeUpdateProperty(element, pId, value);
                        }
                    });

                    if (fieldInfo != null) {
                        result.withField(fieldInfo);
                    }
                }
            }
            
        }

        return result;
        
    }
    
    private PropertyEditorFieldInfo buildGenericFieldInfo(final Element<? extends org.wirez.core.api.graph.content.view.View<?>> element,
                                                          final Object property,
                                                          final Object value,
                                                          final PropertyValueChangedHandler changedHandler) {
        final PropertyAdapter propertyAdapter = definitionManager.getPropertyAdapter(property.getClass());
        final PropertyType sourceType = propertyAdapter.getType( property );
        PropertyEditorType type = null;
        if (sourceType instanceof StringType) {
            type = PropertyEditorType.TEXT;
        } else if (sourceType instanceof ColorType) {
            type = PropertyEditorType.COLOR;
        } else if (sourceType instanceof IntegerType) {
            type = PropertyEditorType.NATURAL_NUMBER;
        } else if (sourceType instanceof DoubleType) {
            type = PropertyEditorType.NATURAL_NUMBER;
        } else if (sourceType instanceof BooleanType) {
            type = PropertyEditorType.BOOLEAN;
        }

        if (type == null) {
            throw new RuntimeException("Unsupported property type for properties adaptor. [PropertyId=" + propertyAdapter.getId(property) + ", type=" + sourceType.getName() + "].");
        }

        final PropertyEditorType theType = type;
        return new PropertyEditorFieldInfo( propertyAdapter.getCaption(property) ,
                toEditorValue(theType, value),
                type) {

            @Override
            public void setCurrentStringValue( final String currentStringValue ) {
                super.setCurrentStringValue( currentStringValue );
                Object _v = fromEditorValue(sourceType, theType, currentStringValue);
                changedHandler.onValueChanged(_v);
            }
        };
        
    }

    private String toEditorValue(final PropertyEditorType type, final Object value) {
        if ( null != value ) {
            
            switch (type) {
                case COLOR:
                    final String color = value.toString();
                    if (color.startsWith("#")) {
                        return color.substring(1, color.length());
                    } else {
                        return color;
                    }
                default:
                    return value.toString();
            }
            
        }
        return "";
    }

    private Object fromEditorValue(final PropertyType propertyType, final PropertyEditorType type, final String value) {
        if ( null != value ) {

            switch (type) {
                case COLOR:
                    return "#" + value;
                case NATURAL_NUMBER:
                    if ( DoubleType.name.equals(propertyType.getName()) ) {
                        return Double.parseDouble(value);
                    } 
                    return Integer.parseInt(value);
                case BOOLEAN:
                    return Boolean.parseBoolean(value);
                default:
                    return value;
            }

        }
        return value;
    }
    
    private PropertyEditorCategory buildElementCategory(final Element<? extends org.wirez.core.api.graph.content.view.View<?>> element) {
        final PropertyEditorCategory result = new PropertyEditorCategory("Element", 0);

        final String id = element.getUUID();
        final double x = element.getContent().getBounds().getUpperLeft().getX();
        final double y = element.getContent().getBounds().getUpperLeft().getY();

        // Element ID
        final PropertyEditorFieldInfo idField = new PropertyEditorFieldInfo( "Id",
                id,
                PropertyEditorType.TEXT) {

            @Override
            public void setCurrentStringValue( final String currentStringValue ) {
                // Cannot be modified.
                super.setCurrentStringValue( id );
            }
        };

        // Element X
        final PropertyEditorFieldInfo xField = new PropertyEditorFieldInfo( "X",
                Double.toString(x),
                PropertyEditorType.NATURAL_NUMBER) {
            
            @Override
            public void setCurrentStringValue( final String currentStringValue ) {
                super.setCurrentStringValue( currentStringValue );
                final Double x = Double.parseDouble(currentStringValue);
                final Double y = element.getContent().getBounds().getUpperLeft().getY();
                executeMove(element, x, y);
            }
        };

        // Element Y
        final PropertyEditorFieldInfo yField = new PropertyEditorFieldInfo( "Y",
                Double.toString(y),
                PropertyEditorType.NATURAL_NUMBER) {

            @Override
            public void setCurrentStringValue( final String currentStringValue ) {
                super.setCurrentStringValue( currentStringValue );
                final Double y = Double.parseDouble(currentStringValue);
                final Double x = element.getContent().getBounds().getUpperLeft().getX();
                executeMove(element, x, y);
            }
        };

        return result.withField(idField).withField(xField).withField(yField);
    }
    
    private void executeUpdateProperty(final Element<? extends org.wirez.core.api.graph.content.view.View<?>> element, 
                                       final String propertyId,
                                       final Object value) {
        execute( canvasCommandFactory.UPDATE_PROPERTY(element, propertyId, value) );
    }

    private void executeMove(final Element<? extends org.wirez.core.api.graph.content.view.View<?>> element,
                                       final double x,
                                       final double y) {
        execute( canvasCommandFactory.UPDATE_POSITION(element, x, y) );
    }
    
    private void execute(final CanvasCommand<AbstractCanvasHandler> command) {
        canvasHandler.execute( command );
    }

    private double getWidth(final Element<? extends org.wirez.core.api.graph.content.view.View<?>> element) {
        final Bounds.Bound ul = element.getContent().getBounds().getUpperLeft();
        final Bounds.Bound lr = element.getContent().getBounds().getLowerRight();
        return lr.getX() - ul.getX();
    }

    private double getHeight(final Element<? extends org.wirez.core.api.graph.content.view.View<?>> element) {
        final Bounds.Bound ul = element.getContent().getBounds().getUpperLeft();
        final Bounds.Bound lr = element.getContent().getBounds().getLowerRight();
        return lr.getY() - ul.getY();
    }
    
    public void clear() {
        removeCanvasListener();
        doClear();   
    }
    
    private void doClear() {
        elementUUID = null;
        view.clear();
    }
    
    void onCanvasShapeStateModifiedEvent(@Observes ShapeStateModifiedEvent event) {
        checkNotNull("event", event);
        final ShapeState state = event.getState();
        final Shape shape = event.getShape();
        if ( shape != null ) {
            // If shape exist, show the properties for the underlying model element.
            final String shapeUUID = shape.getId();
            final Element<? extends org.wirez.core.api.graph.content.view.View<?>> element = this.canvasHandler.getGraphIndex().get(shapeUUID);
            if (element != null && ShapeState.SELECTED.equals(state)) {
                show(element);
            } else if (ShapeState.DESELECTED.equals(state)) {
                view.clear();
            }
        } else {
            // If shape is null means no shape selected, so show the properties for the underlying graph.
            doClear();
        }
        
    }
    
    private void addCanvasListener() {
        removeCanvasListener();

        canvasListener = new AbstractCanvasElementListener(canvasHandler) {
            @Override
            public void onElementAdded(final Element element) {

            }

            @Override
            public void onElementModified(final Element _element) {
                super.onElementModified(_element);
                final String _elementUUID = _element.getUUID();
                if (PropertiesEditor.this.elementUUID != null 
                        && PropertiesEditor.this.elementUUID.equals(_elementUUID)) {
                    show(_element);
                }
            }

            @Override
            public void onElementDeleted(final Element _element) {
                final String _elementUUID = _element.getUUID();
                if (PropertiesEditor.this.elementUUID != null
                        && PropertiesEditor.this.elementUUID.equals(_elementUUID)) {
                    doClear();
                }
            }

            @Override
            public void onClear() {
                doClear();
            }
        };
        canvasHandler.addListener(canvasListener);
    }

    private void removeCanvasListener() {
        if (canvasListener != null) {
            canvasListener.detach();
        }
    }

    private void log(final Level level, final String message) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log(level, message);
        }
    }
    
}
