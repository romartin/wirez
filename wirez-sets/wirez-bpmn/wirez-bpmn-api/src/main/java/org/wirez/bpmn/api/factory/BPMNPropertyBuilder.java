package org.wirez.bpmn.api.factory;

import org.wirez.bpmn.api.BPMNProperty;
import org.wirez.bpmn.api.property.Height;
import org.wirez.bpmn.api.property.Radius;
import org.wirez.bpmn.api.property.Width;
import org.wirez.bpmn.api.property.diagram.Executable;
import org.wirez.bpmn.api.property.diagram.Package;
import org.wirez.bpmn.api.property.general.*;
import org.wirez.core.api.definition.property.defaults.Name;
import org.wirez.core.api.factory.PropertyBuilder;

import javax.enterprise.context.ApplicationScoped;
import java.util.LinkedHashSet;
import java.util.Set;

@ApplicationScoped
public class BPMNPropertyBuilder implements PropertyBuilder<BPMNProperty> {

    private static final Set<String> SUPPORTED_PROP_IDS = new LinkedHashSet<String>() {{
        add(Name.ID);
        add(Executable.ID);
        add(Package.ID);
        add(BgColor.ID);
        add(BorderColor.ID);
        add(BorderSize.ID);
        add(Documentation.ID);
        add(FontBorderSize.ID);
        add(FontColor.ID);
        add(FontFamily.ID);
        add(FontSize.ID);
        add(Height.ID);
        add(Width.ID);
        add(Radius.ID);
    }};

    public BPMNPropertyBuilder() {
    }

    @Override
    public boolean accepts(final String id) {
        return SUPPORTED_PROP_IDS.contains(id);
    }

    @Override
    public BPMNProperty build(final String id) {
        
        if (Name.ID.equals(id)) {
            return buildName();
        }
        if (Executable.ID.equals(id)) {
            return buildExecutable();
        }
        if (Package.ID.equals(id)) {
            return buildPackage();
        }
        if (BgColor.ID.equals(id)) {
            return buildBgColor();
        }
        if (BorderColor.ID.equals(id)) {
            return buildBorderColor();
        }
        if (BorderSize.ID.equals(id)) {
            return buildBorderSize();
        }
        if (Documentation.ID.equals(id)) {
            return buildDocumentation();
        }
        if (FontBorderSize.ID.equals(id)) {
            return buildFontBorderSize();
        }
        if (FontColor.ID.equals(id)) {
            return buildFontColor();
        }
        if (FontFamily.ID.equals(id)) {
            return buildFontFamily();
        }
        if (FontSize.ID.equals(id)) {
            return buildFontSize();
        }
        if (Height.ID.equals(id)) {
            return buildHeight();
        }
        if (Width.ID.equals(id)) {
            return buildWidth();
        }
        if (Radius.ID.equals(id)) {
            return buildRadius();
        }
        
        throw new RuntimeException("Instance expected to be build here.");
    }

    public org.wirez.bpmn.api.property.general.Name buildName() {
        return new org.wirez.bpmn.api.property.general.Name();
    }

    public Executable buildExecutable() {
        return new Executable();
    }

    public Package buildPackage() {
        return new Package();
    }

    public BgColor buildBgColor() {
        return new BgColor();
    }

    public BorderColor buildBorderColor() {
        return new BorderColor();
    }

    public BorderSize buildBorderSize() {
        return new BorderSize();
    }

    public Documentation buildDocumentation() {
        return new Documentation();
    }

    public FontBorderSize buildFontBorderSize() {
        return new FontBorderSize();
    }

    public FontColor buildFontColor() {
        return new FontColor();
    }

    public FontFamily buildFontFamily() {
        return new FontFamily();
    }

    public FontSize buildFontSize() {
        return new FontSize();
    }
    
    public Height buildHeight() {
        return new Height();
    }

    public Width buildWidth() {
        return new Width();
    }

    public Radius buildRadius() {
        return new Radius();
    }

}
