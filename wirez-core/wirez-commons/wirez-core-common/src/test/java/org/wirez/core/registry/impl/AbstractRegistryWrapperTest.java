package org.wirez.core.registry.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.wirez.core.registry.Registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AbstractRegistryWrapperTest {

    @Mock
    Registry<Object> registry;

    private AbstractRegistryWrapper tested;
    private String s1 = "s1";
    private String s2 = "s2";

    @Before
    public void setup() throws Exception {
        when( registry.contains( anyObject() ) ).thenReturn( false );
        when( registry.contains( eq( s1 ) ) ).thenReturn( true );
        when( registry.contains( eq( s2 ) ) ).thenReturn( true );
        when( registry.getItems() ).thenReturn( new ArrayList<Object>( 2 ) {{
            add( s1 );
            add( s2 );
        }});

        tested = new AbstractRegistryWrapper<Object, Registry<Object>>( registry ) {};
    }

    @Test
    @SuppressWarnings( "unchecked" )
    public void testContains() {
        assertTrue( tested.contains( s1 ) );
        assertTrue( tested.contains( s2 ) );
        assertFalse( tested.contains( "" ) );
    }

    @Test
    @SuppressWarnings( "unchecked" )
    public void testGetItems() {
        Collection<Object> items = tested.getItems();
        assertNotNull( items );
        assertEquals( 2, items.size() );
        Iterator<Object> it = items.iterator();
        assertEquals( s1, it.next() );
        assertEquals( s2, it.next() );

    }

}
