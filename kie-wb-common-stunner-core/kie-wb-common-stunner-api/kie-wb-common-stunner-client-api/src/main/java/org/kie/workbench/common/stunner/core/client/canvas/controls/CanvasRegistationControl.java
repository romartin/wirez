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

package org.kie.workbench.common.stunner.core.client.canvas.controls;

/**
 * A canvas shape/element registration control. 
 * It can implement <code>IsWidget</code> if the control have to include views outside the canvas.
 */
public interface CanvasRegistationControl<C, E> extends CanvasControl<C> {

    /**
     * An element is registered on the canvas.
     */
    void register(E element);

    /**
     * An element is de-registered from the canvas.
     */
    void deregister(E element);

}
