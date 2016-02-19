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
import org.wirez.core.api.command.Command;
import org.wirez.core.api.definition.Definition;
import org.wirez.core.api.definition.property.Property;
import org.wirez.core.api.definition.property.PropertySet;
import org.wirez.core.api.definition.property.PropertyType;
import org.wirez.core.api.definition.property.type.*;
import org.wirez.core.api.graph.Bounds;
import org.wirez.core.api.graph.Element;
import org.wirez.core.api.graph.Graph;
import org.wirez.core.api.graph.content.ViewContent;
import org.wirez.core.api.graph.processing.handler.GraphHandlerImpl;
import org.wirez.core.api.service.definition.DefinitionServiceResponse;
import org.wirez.core.api.util.ElementUtils;
import org.wirez.core.client.Shape;
import org.wirez.core.client.ShapeManager;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.ShapeState;
import org.wirez.core.client.canvas.command.CanvasCommandViolation;
import org.wirez.core.client.canvas.command.factory.CanvasCommandFactory;
import org.wirez.core.client.canvas.impl.WiresCanvasHandler;
import org.wirez.core.client.canvas.listener.AbstractCanvasModelListener;
import org.wirez.core.client.canvas.listener.CanvasModelListener;
import org.wirez.core.client.event.ShapeStateModifiedEvent;
import org.wirez.core.client.service.ClientDefinitionServices;
import org.wirez.core.client.service.ClientRuntimeError;
import org.wirez.core.client.service.ServiceCallback;
import org.wirez.core.client.util.WirezLogger;

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
    
    private static Logger LOGGER = Logger.getLogger("org.wirez.client.widgets.property.PropertiesEditor");
    
    public interface View extends UberView<PropertiesEditor> {
        
        void handle(PropertyEditorEvent propertyEditorEvent);
        
        void clear();
        
    }
    
    public interface EditorCallback {
        void onShowElement(Element<? extends ViewContent<?>> element);
    }

    ClientDefinitionServices clientDefinitionServices;
    DefinitionManager definitionManager;
    ShapeManager wirezClientManager;
    CanvasCommandFactory canvasCommandFactory;
    GraphHandlerImpl defaultGraphHandler;
    View view;
    private WiresCanvasHandler canvasHandler;
    private CanvasModelListener canvasListener;
    private EditorCallback editorCallback;
    private String elementUUID;

    @Inject
    public PropertiesEditor(final ClientDefinitionServices clientDefinitionServices,
                            final DefinitionManager definitionManager,
                            final View view,
                            final ShapeManager wirezClientManager,
                            final CanvasCommandFactory canvasCommandFactory,
                            final GraphHandlerImpl defaultGraphHandler) {
        this.clientDefinitionServices = clientDefinitionServices;
        this.definitionManager = definitionManager;
        this.view = view;
        this.wirezClientManager = wirezClientManager;
        this.canvasCommandFactory = canvasCommandFactory;
        this.defaultGraphHandler = defaultGraphHandler;
    }

    @PostConstruct
    public void init() {
        view.init(this);
    }
    
    @Override
    public Widget asWidget() {
        return view.asWidget();
    }

    public void show(final WiresCanvasHandler canvasHandler) {
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
    protected void show(final Element<? extends ViewContent<?>> element) {
        assert element != null;
        
        this.elementUUID = element.getUUID();
        
        final String elementId = element.getUUID();
        final Definition definition = element.getContent().getDefinition();
        
        final List<PropertyEditorCategory> categories = new ArrayList<PropertyEditorCategory>();
        final Set<String> processedProperties = new HashSet<String>();

        // Default element's category.
        final PropertyEditorCategory elementCategory = buildElementCategory(element);
        categories.add(elementCategory);

        // Definition property packages.

        clientDefinitionServices.getDefinitionResponse(definition.getId(), new ServiceCallback<DefinitionServiceResponse>() {
            @Override
            public void onSuccess(final DefinitionServiceResponse definitionResponse) {
                final Set<PropertySet> propertyPackageSet = definitionResponse.getPropertySets().keySet();
                for (final PropertySet propertyPackage : propertyPackageSet) {
                    final Set<Property> properties = definitionResponse.getPropertySets().get(propertyPackage);
                    
                    final PropertyEditorCategory category = new PropertyEditorCategory(propertyPackage.getPropertySetName());
                    if (properties != null) {
                        for (final Property _property : properties) {
                            final String propertyId = _property.getId();
                            final Property property = ElementUtils.getProperty(element, propertyId);
                            final Object value = definitionManager.getPropertyAdapter(property).getValue(property);
                            final PropertyEditorFieldInfo propFieldInfo = buildGenericFieldInfo(element, property, value, new PropertyValueChangedHandler() {
                                @Override
                                public void onValueChanged(final Object value) {
                                    executeUpdateProperty(element, property, value);
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

                PropertyEditorCategory pCategory = buildPropertiesCategory(element, processedProperties, definitionResponse.getProperties());

                categories.add(pCategory);

                // Show the categories.
                view.handle(new PropertyEditorEvent("wirezPropertiesEditorEvent", categories));

                // Editor callback notifications.
                if ( null != editorCallback ) {
                    editorCallback.onShowElement(element);
                }
            }

            @Override
            public void onError(ClientRuntimeError error) {
                showError(error);
            }
        });
        
        
        
    }

    private void showError(ClientRuntimeError error) {
        log(Level.SEVERE, WirezLogger.getErrorMessage(error));
    }

    private PropertyEditorCategory buildPropertiesCategory(final Element<? extends ViewContent<?>> element,
                                                           final Set<String> processedPropertyIds,
                                                           final Map<Property, Object> propertySet) {
        final Definition definition = element.getContent().getDefinition();
        final String title = definition.getTitle();
        final PropertyEditorCategory result = new PropertyEditorCategory(title, 1);

        if (propertySet != null) {
            for (final Map.Entry<Property, Object> entry : propertySet.entrySet()) {
                final Property _property = entry.getKey();
                final Property property = ElementUtils.getProperty(element, _property.getId());
                final Object value = definitionManager.getPropertyAdapter(property).getValue(property);
                if (!processedPropertyIds.contains(property.getId())) {
                    final PropertyEditorFieldInfo fieldInfo = buildGenericFieldInfo(element, property, value, new PropertyValueChangedHandler() {
                        @Override
                        public void onValueChanged(final Object value) {
                            executeUpdateProperty(element, property, value);
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
    
    private PropertyEditorFieldInfo buildGenericFieldInfo(final Element<? extends ViewContent<?>> element,
                                                          final Property property,
                                                          final Object value,
                                                          final PropertyValueChangedHandler changedHandler) {
        PropertyEditorType type = null;
        if (property.getType() instanceof StringType) {
            type = PropertyEditorType.TEXT;
        } else if (property.getType() instanceof ColorType) {
            type = PropertyEditorType.COLOR;
        } else if (property.getType() instanceof IntegerType) {
            type = PropertyEditorType.NATURAL_NUMBER;
        } else if (property.getType() instanceof DoubleType) {
            type = PropertyEditorType.NATURAL_NUMBER;
        } else if (property.getType() instanceof BooleanType) {
            type = PropertyEditorType.BOOLEAN;
        }

        if (type == null) {
            throw new RuntimeException("Unsupported property type for properties adaptor. [PropertyId=" + property.getId() + ", type=" + property.getType().getName() + "].");
        }

        final PropertyEditorType theType = type;
        return new PropertyEditorFieldInfo( property.getCaption() ,
                toEditorValue(theType, value),
                type) {

            @Override
            public void setCurrentStringValue( final String currentStringValue ) {
                super.setCurrentStringValue( currentStringValue );
                Object _v = fromEditorValue(property.getType(), theType, currentStringValue);
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
    
    private PropertyEditorCategory buildElementCategory(final Element<? extends ViewContent<?>> element) {
        final PropertyEditorCategory result = new PropertyEditorCategory("Element", 0);

        final String id = element.getUUID();
        final double x = element.getContent().getBounds().getUpperLeft().getX();
        final double y = element.getContent().getBounds().getLowerRight().getY();

        final double w = getWidth(element);
        final double h = getHeight(element);

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
    
    private void executeUpdateProperty(final Element<? extends ViewContent<?>> element, 
                                       final Property property,
                                       final Object value) {
        execute( canvasCommandFactory.UPDATE_PROPERTY(element, property.getId(), value) );
    }

    private void executeMove(final Element<? extends ViewContent<?>> element,
                                       final double x,
                                       final double y) {
        execute( canvasCommandFactory.UPDATE_POSITION(element, x, y) );
    }
    
    private void execute(final Command<WiresCanvasHandler, CanvasCommandViolation> command) {
        canvasHandler.execute( command );   
    }

    private double getWidth(final Element<? extends ViewContent<?>> element) {
        final Bounds.Bound ul = element.getContent().getBounds().getUpperLeft();
        final Bounds.Bound lr = element.getContent().getBounds().getLowerRight();
        return lr.getX() - ul.getX();
    }

    private double getHeight(final Element<? extends ViewContent<?>> element) {
        final Bounds.Bound ul = element.getContent().getBounds().getUpperLeft();
        final Bounds.Bound lr = element.getContent().getBounds().getLowerRight();
        return lr.getY() - ul.getY();
    }
    
    public void clear() {
        removeCanvasListener();
        doClear();   
    }
    
    private void doClear() {
        canvasHandler = null;
        elementUUID = null;
        view.clear();
    }
    
    private void showDefault() {
        final Graph defaultGraph = PropertiesEditor.this.canvasHandler.getDiagram().getGraph();
        if ( null != defaultGraph) {
            this.elementUUID = defaultGraph.getUUID();
            show(defaultGraph);
        } else {
            doClear();
        }
    }

    void onCanvasShapeStateModifiedEvent(@Observes ShapeStateModifiedEvent event) {
        checkNotNull("event", event);
        final ShapeState state = event.getState();
        final Graph defaultGraph = this.canvasHandler.getDiagram().getGraph();
        final Shape shape = event.getShape();
        if ( shape != null ) {
            // If shape exist, show the properties for the underlying model element.
            final String shapeUUID = shape.getId();
            final Element<? extends ViewContent<?>> element = defaultGraphHandler.initialize(defaultGraph).get(shapeUUID);
            if (element != null && ShapeState.SELECTED.equals(state)) {
                show(element);
            } else if (ShapeState.DESELECTED.equals(state)) {
                view.clear();
            }
        } else {
            // If shape is null means no shape selected, so show the properties for the underlying graph.
            showDefault();
        }
        
    }
    
    private void addCanvasListener() {
        removeCanvasListener();

        canvasListener = new AbstractCanvasModelListener(canvasHandler) {
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
                    showDefault();
                }
            }

            @Override
            public void onClear() {
                showDefault();
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
