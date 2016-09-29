package org.kie.workbench.common.stunner.core.registry.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.workbench.common.stunner.core.registry.impl.AbstractDiagramListRegistry;
import org.kie.workbench.common.stunner.core.registry.impl.DiagramListRegistry;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.kie.workbench.common.stunner.core.diagram.Diagram;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DiagramListRegistryTest {

    private static final String DIAGRAM_1_UUID = "UUID1";
    private static final String DIAGRAM_2_UUID = "UUID2";
    private static final String DUMMY_STRING = "test";

    private AbstractDiagramListRegistry registry;

    @Mock
    Diagram diagram1;
    @Mock
    Diagram diagram2;

    @Before
    public void setup() {
        registry = new DiagramListRegistry();
        when(diagram1.getUUID()).thenReturn(DIAGRAM_1_UUID);
        when(diagram2.getUUID()).thenReturn(DIAGRAM_2_UUID);
    }

    @Test
    public void testGetDiagramByUUID() {
        registry.register(diagram1);
        registry.register(diagram2);

        assertEquals(diagram1, registry.getDiagramByUUID(DIAGRAM_1_UUID));
        assertEquals(diagram2, registry.getDiagramByUUID(DIAGRAM_2_UUID));
        assertEquals(null, registry.getDiagramByUUID(DUMMY_STRING));
    }

    @Test
    public void testUpdate() {
        registry.register(diagram1);
        registry.register(diagram2);

        registry.update(diagram1);
        registry.update(diagram2);
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidUpdate() {
        registry.register(diagram1);

        registry.update(diagram2);
    }
}
