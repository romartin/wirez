package org.wirez.core.command.stack;

import org.wirez.core.command.Command;
import org.wirez.core.command.batch.BatchCommandManager;

import java.util.Stack;

public interface StackCommandManager<T, V> extends BatchCommandManager<T, V> {

    Stack<Stack<Command<T,V>>> getHistory();
    
    void clearHistory();
    
}
