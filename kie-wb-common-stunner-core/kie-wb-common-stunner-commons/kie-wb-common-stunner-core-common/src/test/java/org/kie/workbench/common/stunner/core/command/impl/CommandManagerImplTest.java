package org.kie.workbench.common.stunner.core.command.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.omg.CORBA.Object;
import org.kie.workbench.common.stunner.core.command.Command;
import org.kie.workbench.common.stunner.core.command.CommandManagerListener;
import org.kie.workbench.common.stunner.core.command.CommandResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CommandManagerImplTest {

    @Mock
    private Object context;

    @Mock
    private Command<Object, Object> command;

    @Mock
    private CommandResult<Object> commandResult;

    private CommandManagerImpl<Object, Object> tested;

    @Before
    public void setup() throws Exception {

        tested = new CommandManagerImpl<Object, Object>();

    }

    @Test
    public void testAllow() {

        when( command.allow( context ) ).thenReturn( commandResult );

        CommandResult<Object> result = tested.allow( context, command );

        verify( command, times( 1 ) ).allow( eq( context ) );
        verify( command, times( 0 ) ).execute( anyObject() );
        verify( command, times( 0 ) ).undo( anyObject() );

        assertNotNull( result );

        assertEquals( commandResult, result );

    }

    @Test
    @SuppressWarnings( "unchecked" )
    public void testAllowWithListener() {

        CommandManagerListener<Object, Object> listener = mock( CommandManagerListener.class );

        tested.setCommandManagerListener( listener );

        testAllow();

        verify( listener, times( 1 ) ).onAllow( eq( context ), eq( command ), eq( commandResult ) );

        verify( listener, times( 0 ) ).onExecute( anyObject(), anyObject(), anyObject() );

        verify( listener, times( 0 ) ).onUndo( anyObject(), anyObject(), anyObject() );

    }

    @Test
    public void testExecute() {

        when( command.execute( context ) ).thenReturn( commandResult );

        CommandResult<Object> result = tested.execute( context, command );

        verify( command, times( 1 ) ).execute( eq( context ) );
        verify( command, times( 0 ) ).allow( anyObject() );
        verify( command, times( 0 ) ).undo( anyObject() );

        assertNotNull( result );

        assertEquals( commandResult, result );

    }

    @Test
    @SuppressWarnings( "unchecked" )
    public void testExecuteWithListener() {

        CommandManagerListener<Object, Object> listener = mock( CommandManagerListener.class );

        tested.setCommandManagerListener( listener );

        testExecute();

        verify( listener, times( 0 ) ).onAllow( anyObject(), anyObject(), anyObject() );

        verify( listener, times( 1 ) ).onExecute( eq( context ), eq( command ), eq( commandResult ) );

        verify( listener, times( 0 ) ).onUndo( anyObject(), anyObject(), anyObject() );

    }

    @Test
    public void testUndo() {

        when( command.undo( context ) ).thenReturn( commandResult );

        CommandResult<Object> result = tested.undo( context, command );

        verify( command, times( 1 ) ).undo( eq( context ) );
        verify( command, times( 0 ) ).execute( anyObject() );
        verify( command, times( 0 ) ).allow( anyObject() );

        assertNotNull( result );

        assertEquals( commandResult, result );

    }

    @Test
    @SuppressWarnings( "unchecked" )
    public void testUndoWithListener() {

        CommandManagerListener<Object, Object> listener = mock( CommandManagerListener.class );

        tested.setCommandManagerListener( listener );

        testUndo();

        verify( listener, times( 0 ) ).onAllow( anyObject(), anyObject(), anyObject() );

        verify( listener, times( 0 ) ).onExecute( anyObject(), anyObject(), anyObject() );

        verify( listener, times( 1 ) ).onUndo( eq( context ), eq( command ), eq( commandResult ) );

    }

}
