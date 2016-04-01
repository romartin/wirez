package org.wirez.bpmn.api.factory;

import org.wirez.bpmn.api.BPMNProperty;
import org.wirez.bpmn.api.property.Height;
import org.wirez.bpmn.api.property.Radius;
import org.wirez.bpmn.api.property.Width;
import org.wirez.bpmn.api.property.diagram.Executable;
import org.wirez.bpmn.api.property.diagram.Package;
import org.wirez.bpmn.api.property.general.*;
import org.wirez.bpmn.api.property.simulation.*;
import org.wirez.core.api.definition.factory.ModelFactory;
import org.wirez.core.api.definition.property.defaults.Name;

import javax.enterprise.context.ApplicationScoped;
import java.util.LinkedHashSet;
import java.util.Set;

@ApplicationScoped
public class BPMNPropertyFactory implements ModelFactory<BPMNProperty> {

    private static final Set<String> SUPPORTED_PROP_IDS = new LinkedHashSet<String>() {{
        add(Name.ID);
        add(Executable.class.getSimpleName());
        add(Package.class.getSimpleName());
        add(BgColor.class.getSimpleName());
        add(BorderColor.class.getSimpleName());
        add(BorderSize.class.getSimpleName());
        add(Documentation.class.getSimpleName());
        add(FontBorderSize.class.getSimpleName());
        add(FontColor.class.getSimpleName());
        add(FontFamily.class.getSimpleName());
        add(FontSize.class.getSimpleName());
        add(Height.class.getSimpleName());
        add(Width.class.getSimpleName());
        add(Radius.class.getSimpleName());
        add(Min.class.getSimpleName());
        add(Max.class.getSimpleName());
        add(Mean.class.getSimpleName());
        add(DistributionType.class.getSimpleName());
        add(Currency.class.getSimpleName());
        add(Quantity.class.getSimpleName());
        add(StandardDeviation.class.getSimpleName());
        add(TimeUnit.class.getSimpleName());
        add(UnitCost.class.getSimpleName());
        add(WorkingHours.class.getSimpleName());
    }};

    public BPMNPropertyFactory() {
    }

    @Override
    public boolean accepts(final String id) {
        return SUPPORTED_PROP_IDS.contains(id);
    }

    @Override
    public BPMNProperty build(final String id) {
        
        if (Name.class.getSimpleName().equals(id)) {
            return buildName();
        }
        if (Executable.class.getSimpleName().equals(id)) {
            return buildExecutable();
        }
        if (Package.class.getSimpleName().equals(id)) {
            return buildPackage();
        }
        if (BgColor.class.getSimpleName().equals(id)) {
            return buildBgColor();
        }
        if (BorderColor.class.getSimpleName().equals(id)) {
            return buildBorderColor();
        }
        if (BorderSize.class.getSimpleName().equals(id)) {
            return buildBorderSize();
        }
        if (Documentation.class.getSimpleName().equals(id)) {
            return buildDocumentation();
        }
        if (FontBorderSize.class.getSimpleName().equals(id)) {
            return buildFontBorderSize();
        }
        if (FontColor.class.getSimpleName().equals(id)) {
            return buildFontColor();
        }
        if (FontFamily.class.getSimpleName().equals(id)) {
            return buildFontFamily();
        }
        if (FontSize.class.getSimpleName().equals(id)) {
            return buildFontSize();
        }
        if (Height.class.getSimpleName().equals(id)) {
            return buildHeight();
        }
        if (Width.class.getSimpleName().equals(id)) {
            return buildWidth();
        }
        if (Radius.class.getSimpleName().equals(id)) {
            return buildRadius();
        }
        if (Min.class.getSimpleName().equals(id)) {
            return buildMin();
        }
        if (Max.class.getSimpleName().equals(id)) {
            return buildMax();
        }
        if (Mean.class.getSimpleName().equals(id)) {
            return buildMean();
        }
        if (DistributionType.class.getSimpleName().equals(id)) {
            return buildDistributionType();
        }
        if (Currency.class.getSimpleName().equals(id)) {
            return buildCurrency();
        }
        if (Quantity.class.getSimpleName().equals(id)) {
            return buildQuantity();
        }
        if (StandardDeviation.class.getSimpleName().equals(id)) {
            return buildStandardDeviation();
        }
        if (TimeUnit.class.getSimpleName().equals(id)) {
            return buildTimeUnit();
        }
        if (UnitCost.class.getSimpleName().equals(id)) {
            return buildUnitCost();
        }
        if (WorkingHours.class.getSimpleName().equals(id)) {
            return buildWorkingHours();
        }
        
        throw new RuntimeException("Instance expected to be build here.");
    }

    public org.wirez.bpmn.api.property.general.Name buildName() {
        return new org.wirez.bpmn.api.property.general.Name();
    }

    public Min buildMin() {
        return new Min();
    }

    public Max buildMax() {
        return new Max();
    }

    public Mean buildMean() {
        return new Mean();
    }
    
    public DistributionType buildDistributionType() {
        return new DistributionType();
    }

    public Currency buildCurrency() {
        return new Currency();
    }

    public Quantity buildQuantity() {
        return new Quantity();
    }

    public StandardDeviation buildStandardDeviation() {
        return new StandardDeviation();
    }

    public TimeUnit buildTimeUnit() {
        return new TimeUnit();
    }

    public UnitCost buildUnitCost() {
        return new UnitCost();
    }

    public WorkingHours buildWorkingHours() {
        return new WorkingHours();
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
