package org.wirez.core.client.canvas.command;

import org.wirez.core.command.Command;
import org.wirez.core.graph.command.GraphCommandExecutionContext;
import org.wirez.core.rule.RuleViolation;
import org.wirez.core.client.canvas.AbstractCanvasHandler;

public abstract class AbstractCanvasGraphCommand extends AbstractCanvasCommand implements HasGraphCommand<AbstractCanvasHandler> {

    protected Command<GraphCommandExecutionContext, RuleViolation> graphCommand;
    
    public AbstractCanvasGraphCommand() {
    }
    
    protected abstract Command<GraphCommandExecutionContext, RuleViolation> buildGraphCommand(final AbstractCanvasHandler context);
    
    @Override
    public Command<GraphCommandExecutionContext, RuleViolation> getGraphCommand(final AbstractCanvasHandler context) {
        if ( null == graphCommand ) {
            graphCommand = buildGraphCommand( context );
        }
        
        return graphCommand;
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder( super.toString() );
        if ( null != graphCommand) {
            result.append( " [graphCommand=" )
                    .append( graphCommand.toString() )
                    .append( "]" );
        }
        
        return result.toString();
    }
    
}
