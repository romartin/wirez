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

package org.wirez.core.client.util;

import org.wirez.core.client.service.ClientRuntimeError;

import java.util.logging.Logger;

/**
 * Just for development use.
 */
public class WirezClientLogger {

    private static Logger LOGGER = Logger.getLogger("org.wirez.core.client.util.WirezLogger");

    public static String getErrorMessage(final ClientRuntimeError error) {
        final String message = error.getMessage();
        final Throwable t1 = error.getThrowable();
        final Throwable t2 = t1 != null ? t1.getCause() : null;

        if ( null != t2 ) {
            return t2.getMessage();
        } else if ( null != t1 ) {
            return t1.getMessage();
        } 
        
        return message;
    }
    
}
