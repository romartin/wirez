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

package org.wirez.core.api.event;

import org.uberfire.workbench.events.UberFireEvent;

/**
 * <p>CDI event for Wirez logging.</p>
 *
 */
public class NotificationEvent implements UberFireEvent {

    public enum Type {
        ERROR, WARNING, INFO, DEBUG
    }
    
    private Type type;
    private Object context;
    private String message;
    private Exception exception;

    public NotificationEvent(Type type, Object context, String message) {
        this.type = type;
        this.context = context;
        this.message = message;
    }

    public NotificationEvent(Type type, Object context, String message, Exception exception) {
        this.type = type;
        this.context = context;
        this.message = message;
        this.exception = exception;
    }

    public Type getType() {
        return type;
    }

    public Object getContext() {
        return context;
    }

    public String getMessage() {
        return message;
    }

    public Exception getException() {
        return exception;
    }

    @Override
    public String toString() {
        return "NotificationEvent [type=" + type.name() + ", context=" + context + ", message='" + message + "']";
    }

}
