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

package org.wirez.core.client.canvas.settings;

import org.wirez.core.api.command.CommandManager;
import org.wirez.core.api.rule.RuleManager;
import org.wirez.core.client.canvas.CanvasHandler;
import org.wirez.core.client.canvas.command.CanvasCommandViolation;
import org.wirez.core.client.canvas.control.ConnectionAcceptor;
import org.wirez.core.client.canvas.control.ContainmentAcceptor;
import org.wirez.core.client.canvas.impl.WiresCanvasHandler;

public interface WiresCanvasSettingsBuilder extends CanvasSettingsBuilder<WiresCanvasSettingsBuilder, WiresCanvasSettings> {

    WiresCanvasSettingsBuilder commandManager(CommandManager<WiresCanvasHandler, CanvasCommandViolation> commandManager);

    WiresCanvasSettingsBuilder ruleManager(RuleManager ruleManager);

    WiresCanvasSettingsBuilder connectionAcceptor(ConnectionAcceptor connectionAcceptor);

    WiresCanvasSettingsBuilder containmentAcceptor(ContainmentAcceptor  connectionAcceptor);


}
