/*
 * Copyright 2016  Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wirez.forms.client.fields.assigneeEditor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.wirez.forms.client.fields.model.Assignee;
import org.wirez.forms.client.fields.model.AssigneeRow;
import org.wirez.forms.client.fields.assigneeEditor.AssigneeListItemWidgetView;
import org.wirez.forms.client.fields.assigneeEditor.AssigneeEditorFieldRenderer;
import org.wirez.forms.client.fields.assigneeEditor.AssigneeEditorWidgetView;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AssigneeEditorFieldRendererTest {

    @Mock
    private AssigneeEditorWidgetView assigneeEditorWidgetView;

    @Mock
    private AssigneeListItemWidgetView assigneeListItemWidgetView;

    @Spy
    @InjectMocks
    private AssigneeEditorFieldRenderer assigneeEditor = new AssigneeEditorFieldRenderer(assigneeEditorWidgetView);

    @Test
    public void testAddAssignee() {
        when(assigneeEditorWidgetView.getAssigneeWidget(anyInt())).thenReturn(assigneeListItemWidgetView);

        when(assigneeEditorWidgetView.getAssigneeRowsCount()).thenReturn(1);
        assigneeEditor.addAssignee();
        verify(assigneeEditorWidgetView, times(1)).setTableDisplayStyle();
        verify(assigneeEditorWidgetView, times(1)).getAssigneeRowsCount();
        verify(assigneeEditorWidgetView, times(1)).getAssigneeWidget(0);

        when(assigneeEditorWidgetView.getAssigneeRowsCount()).thenReturn(2);
        assigneeEditor.addAssignee();
        verify(assigneeEditorWidgetView, times(2)).getAssigneeRowsCount();
        verify(assigneeEditorWidgetView, times(1)).getAssigneeWidget(1);
    }

    @Test
    public void testRemoveAssignee() {
        when(assigneeEditorWidgetView.getAssigneeWidget(anyInt())).thenReturn(assigneeListItemWidgetView);

        when(assigneeEditorWidgetView.getAssigneeRowsCount()).thenReturn(1);
        assigneeEditor.addAssignee();
        assigneeEditor.addAssignee();

        assigneeEditor.removeAssignee(null);
        verify(assigneeEditorWidgetView, times(3)).getAssigneeRows();
        verify(assigneeEditorWidgetView, times(1)).doSave();

        assigneeEditor.removeAssignee(null);
        verify(assigneeEditorWidgetView, times(4)).getAssigneeRows();
        verify(assigneeEditorWidgetView, times(2)).doSave();
    }

    @Test
    public void testDeserializeAssignee() {
        List<String> assignees = new ArrayList<String>(Arrays.asList("user1", "user2", "user3", "user4", "user5"));
        assigneeEditor.setNames(assignees);
        List<AssigneeRow> assigneeRows = assigneeEditor.deserializeAssignees("user3,user2,user5");

        assertEquals(3, assigneeRows.size());

        AssigneeRow a = assigneeRows.get(0);
        assertEquals("user3", a.getName());

        a = assigneeRows.get(1);
        assertEquals("user2", a.getName());

        a = assigneeRows.get(2);
        assertEquals("user5", a.getName());
    }

    @Test
    public void testSerializeAssignee() {
        List<AssigneeRow> assigneeRows = new ArrayList<AssigneeRow>();
        assigneeRows.add(new AssigneeRow("user3", null));
        assigneeRows.add(new AssigneeRow("user2", null));
        assigneeRows.add(new AssigneeRow("user5", null));

        String s = assigneeEditor.serializeAssignees(assigneeRows);
        assertEquals("user3,user2,user5", s);
    }

}
