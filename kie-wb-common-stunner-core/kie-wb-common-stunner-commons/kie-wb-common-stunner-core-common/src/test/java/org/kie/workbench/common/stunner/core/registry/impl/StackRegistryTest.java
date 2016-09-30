package org.kie.workbench.common.stunner.core.registry.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.workbench.common.stunner.core.registry.impl.KeyProvider;
import org.kie.workbench.common.stunner.core.registry.impl.StackRegistry;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StackRegistryTest {

    @Mock
    private Stack<Object> stack;

    private StackRegistry<Object> tested;

    private final KeyProvider<Object> keyProvider = Object::toString;

    @Before
    public void setup() throws Exception {
        tested = new StackRegistry<>(keyProvider, stack);
    }

    @Test
    public void testRegister() {
        final String s = "an string";
        tested.register(s);
        verify(stack, times(1)).add(s);
    }

    @Test
    public void testGetStack() {
        assertEquals(stack, tested.getStack());
    }

    @Test
    public void testPeek() {
        tested.peek();
        verify(stack, times(1)).peek();
    }

    @Test
    public void testPop() {
        tested.pop();
        verify(stack, times(1)).pop();
    }

    @Test
    public void testRemove() {
        final String s = "an string";
        tested.remove(s);
        verify(stack, times(1)).remove(s);
    }

    @Test
    public void testContains() {
        final String s = "an string";
        tested.contains(s);
        verify(stack, times(1)).contains(s);
    }

    @Test
    public void testClear() {
        tested.clear();
        verify(stack, times(1)).clear();
    }

    @Test
    public void testIndexOf() {
        final String s = "an string";
        tested.indexOf(s);
        verify(stack, times(1)).indexOf(s);
    }

    @Test
    public void testGetItems() {
        final String s1 = "an string 1";
        final String s2 = "an string 2";
        tested = new StackRegistry<>(keyProvider, new Stack<Object>() {{
            push(s1);
            push(s2);
        }});

        Collection<Object> items = tested.getItems();
        assertNotNull(items);
        assertEquals(2, items.size());
        Iterator<Object> it = items.iterator();
        assertEquals(s1, it.next());
        assertEquals(s2, it.next());
    }

    @Test
    public void testGetItemByKey() {
        final String s1 = "an string 1";
        final String s2 = "an string 2";
        tested = new StackRegistry<>(keyProvider, new Stack<Object>() {{
            push(s1);
            push(s2);
        }});

        Object o1 = tested.getItemByKey(s1);
        Object o2 = tested.getItemByKey(s2);

        assertEquals(s1, o1);
        assertEquals(s2, o2);
        assertEquals(null, tested.getItemByKey(null));
        assertEquals(null, tested.getItemByKey("unregistered string"));
    }

}
