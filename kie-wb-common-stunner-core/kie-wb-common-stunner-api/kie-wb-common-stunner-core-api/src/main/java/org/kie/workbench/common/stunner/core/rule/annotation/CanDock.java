/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
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

package org.kie.workbench.common.stunner.core.rule.annotation;

import java.lang.annotation.*;

/**
 * Annotation for specifying a docking rule.
 * The <code>roles</code> member contains the allowed roles that the domain object can dock.
 * It's only allowed to use on  Definitions of type Node.
 */
@Inherited
@Target(ElementType.TYPE) 
@Retention(RetentionPolicy.RUNTIME)
public @interface CanDock {

    String id() default "";
    
    String[] roles();
    
}
