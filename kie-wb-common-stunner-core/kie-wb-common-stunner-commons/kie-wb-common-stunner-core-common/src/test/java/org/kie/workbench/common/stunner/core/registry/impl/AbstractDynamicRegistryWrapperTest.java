package org.kie.workbench.common.stunner.core.registry.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.workbench.common.stunner.core.registry.impl.AbstractDynamicRegistryWrapper;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.kie.workbench.common.stunner.core.registry.DynamicRegistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AbstractDynamicRegistryWrapperTest {

    @Mock
    DynamicRegistry<Object> registry;

    private AbstractDynamicRegistryWrapper tested;
    private String s1 = "s1";
    private String s2 = "s2";

    @Before
    public void setup() throws Exception {
        tested = new AbstractDynamicRegistryWrapper<Object, DynamicRegistry<Object>>( registry ) {};
    }

    @Test
    @SuppressWarnings( "unchecked" )
    public void testRegister() {
        tested.register( s1 );
        verify( registry, times( 1 ) ).register( eq( s1 ) );
    }

    @Test
    @SuppressWarnings( "unchecked" )
    public void testRemove() {
        tested.remove( s1 );
        verify( registry, times( 1 ) ).remove( eq( s1 ) );
    }

    @Test
    @SuppressWarnings( "unchecked" )
    public void testClear() {
        tested.clear();
        verify( registry, times( 1 ) ).clear();
    }


    @Test
    @SuppressWarnings( "unchecked" )
    public void testContains() {
        when( registry.contains( anyObject() ) ).thenReturn( false );
        when( registry.contains( eq( s1 ) ) ).thenReturn( true );
        when( registry.contains( eq( s2 ) ) ).thenReturn( true );
        assertTrue( tested.contains( s1 ) );
        assertTrue( tested.contains( s2 ) );
        assertFalse( tested.contains( "" ) );
    }

    @Test
    @SuppressWarnings( "unchecked" )
    public void testGetItems() {
        when( registry.getItems() ).thenReturn( new ArrayList<Object>( 2 ) {{
            add( s1 );
            add( s2 );
        }});

        Collection<Object> items = tested.getItems();
        assertNotNull( items );
        assertEquals( 2, items.size() );
        Iterator<Object> it = items.iterator();
        assertEquals( s1, it.next() );
        assertEquals( s2, it.next() );

    }

}
