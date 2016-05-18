package org.wirez.core.api.command.stack;

import org.wirez.core.api.command.Command;
import org.wirez.core.api.command.batch.BatchCommandManager;

import java.util.Stack;

public interface StackCommandManager<T, V> extends BatchCommandManager<T, V> {

    Stack<Stack<Command<T,V>>> getHistory();
    
    void clearHistory();
    
}
