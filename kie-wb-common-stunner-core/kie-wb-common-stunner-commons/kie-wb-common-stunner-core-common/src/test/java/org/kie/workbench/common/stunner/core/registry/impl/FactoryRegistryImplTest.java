package org.kie.workbench.common.stunner.core.registry.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.workbench.common.stunner.core.registry.impl.FactoryRegistryImpl;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.kie.workbench.common.stunner.core.definition.adapter.AdapterManager;
import org.kie.workbench.common.stunner.core.factory.definition.DefinitionFactory;
import org.kie.workbench.common.stunner.core.factory.impl.EdgeFactoryImpl;
import org.kie.workbench.common.stunner.core.factory.impl.NodeFactoryImpl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FactoryRegistryImplTest {

    private static final String NOT_VALID_ID = "Not valid ID";
    @Mock
    private AdapterManager adapter;
    @Mock
    private DefinitionFactory definitionFactory;
    @Mock
    private DefinitionFactory missingFactory;
    @Mock
    private EdgeFactoryImpl elementFactory;
    @Mock
    private NodeFactoryImpl elementFactory2;

    private FactoryRegistryImpl factory;

    @Before
    public void setup() {
        factory = new FactoryRegistryImpl(adapter);

        Class clazz = EdgeFactoryImpl.class;
        when(elementFactory.getFactoryType()).thenReturn(clazz);

        when(definitionFactory.accepts(DefinitionFactory.class.getName())).thenReturn(true);
    }

    @Test
    public void testGetDefinitionFactory() {
        assertNull(factory.getDefinitionFactory(DefinitionFactory.class.getName()));
        factory.register(definitionFactory);

        assertNull(factory.getDefinitionFactory(NOT_VALID_ID));
        assertEquals(definitionFactory, factory.getDefinitionFactory(DefinitionFactory.class.getName()));
        assertEquals(definitionFactory, factory.getDefinitionFactory(DefinitionFactory.class));
    }

    @Test
    public void testGetGraphFactory() {
        assertNull(factory.getGraphFactory(elementFactory.getFactoryType()));
        factory.register(elementFactory);

        assertEquals(elementFactory, factory.getGraphFactory(elementFactory.getFactoryType()));
    }

    @Test
    public void testGetItems() {
        factory.register(elementFactory);
        factory.register(definitionFactory);

        assertArrayEquals(new Object[]{definitionFactory, elementFactory}, factory.getItems().toArray());
    }

    @Test
    public void testContains() {
        assertFalse(factory.contains(elementFactory));
        assertFalse(factory.contains(definitionFactory));
        assertFalse(factory.contains(null));


        factory.register(elementFactory);
        factory.register(definitionFactory);

        assertTrue(factory.contains(elementFactory));
        assertTrue(factory.contains(definitionFactory));
        assertFalse(factory.contains(missingFactory));
        assertFalse(factory.contains(null));
    }

    @Test
    public void testClear() {
        factory.register(elementFactory);
        factory.register(definitionFactory);
        factory.register(missingFactory);

        factory.clear();

        assertArrayEquals(new Object[0], factory.getItems().toArray());
    }

    @Test
    public void testRemove() {
        factory.register(elementFactory);
        factory.register(definitionFactory);

        assertFalse(factory.remove(missingFactory));
        assertTrue(factory.contains(elementFactory));
        assertTrue(factory.contains(definitionFactory));

        assertTrue(factory.remove(elementFactory));
        assertFalse(factory.contains(elementFactory));
        assertTrue(factory.contains(definitionFactory));

        assertFalse(factory.remove(null));
        assertTrue(factory.contains(definitionFactory));

        assertTrue(factory.remove(definitionFactory));
        assertFalse(factory.contains(definitionFactory));

        assertArrayEquals(new Object[0], factory.getItems().toArray());
    }
}
