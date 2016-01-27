package org.wirez.core.api.diagram;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;

@Portable
public class SettingsImpl implements Settings {
    
    private final String title;
    private final String defSetId;
    private final String shapeSetId;
    private final String path;

    public SettingsImpl(@MapsTo("title")  String title,
                        @MapsTo("defSetId")  String defSetId,
                        @MapsTo("shapeSetId")  String shapeSetId,
                        @MapsTo("path")  String path) {
        this.title = title;
        this.defSetId = defSetId;
        this.shapeSetId = shapeSetId;
        this.path = path;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDefinitionSetId() {
        return defSetId;
    }

    @Override
    public String getShapeSetId() {
        return shapeSetId;
    }

    @Override
    public String getPath() {
        return path;
    }

}
