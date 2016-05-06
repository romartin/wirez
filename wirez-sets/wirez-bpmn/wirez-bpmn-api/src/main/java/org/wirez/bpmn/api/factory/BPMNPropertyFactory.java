package org.wirez.bpmn.api.factory;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;

import org.wirez.bpmn.api.BPMNProperty;
import org.wirez.bpmn.api.property.Height;
import org.wirez.bpmn.api.property.Radius;
import org.wirez.bpmn.api.property.Width;
import org.wirez.bpmn.api.property.diagram.Executable;
import org.wirez.bpmn.api.property.diagram.Package;
import org.wirez.bpmn.api.property.general.BgColor;
import org.wirez.bpmn.api.property.general.BorderColor;
import org.wirez.bpmn.api.property.general.BorderSize;
import org.wirez.bpmn.api.property.general.Documentation;
import org.wirez.bpmn.api.property.general.FontBorderSize;
import org.wirez.bpmn.api.property.general.FontColor;
import org.wirez.bpmn.api.property.general.FontFamily;
import org.wirez.bpmn.api.property.general.FontSize;
import org.wirez.bpmn.api.property.general.Name;
import org.wirez.bpmn.api.property.simulation.Currency;
import org.wirez.bpmn.api.property.simulation.DistributionType;
import org.wirez.bpmn.api.property.simulation.Max;
import org.wirez.bpmn.api.property.simulation.Mean;
import org.wirez.bpmn.api.property.simulation.Min;
import org.wirez.bpmn.api.property.simulation.Quantity;
import org.wirez.bpmn.api.property.simulation.StandardDeviation;
import org.wirez.bpmn.api.property.simulation.TimeUnit;
import org.wirez.bpmn.api.property.simulation.UnitCost;
import org.wirez.bpmn.api.property.simulation.WorkingHours;
import org.wirez.core.api.definition.factory.BindableModelFactory;

@ApplicationScoped
public class BPMNPropertyFactory extends BindableModelFactory<BPMNProperty> {

    private static final Set<Class<?>> SUPPORTED_PROP_CLASSES = new LinkedHashSet<Class<?>>() {{
        add(Name.class);
        add(Executable.class);
        add(Package.class);
        add(BgColor.class);
        add(BorderColor.class);
        add(BorderSize.class);
        add(Documentation.class);
        add(FontBorderSize.class);
        add(FontColor.class);
        add(FontFamily.class);
        add(FontSize.class);
        add(Height.class);
        add(Width.class);
        add(Radius.class);
        add(Min.class);
        add(Max.class);
        add(Mean.class);
        add(DistributionType.class);
        add(Currency.class);
        add(Quantity.class);
        add(StandardDeviation.class);
        add(TimeUnit.class);
        add(UnitCost.class);
        add(WorkingHours.class);
    }};

    public BPMNPropertyFactory() {
    }

    @Override
    public Set<Class<?>> getAcceptedClasses() {
        return SUPPORTED_PROP_CLASSES;
    }

    @Override
    public BPMNProperty build(final Class<?> clazz) {
        if (Name.class.equals(clazz)) {
            return buildName();
        }
        if (Executable.class.equals(clazz)) {
            return buildExecutable();
        }
        if (Package.class.equals(clazz)) {
            return buildPackage();
        }
        if (BgColor.class.equals(clazz)) {
            return buildBgColor();
        }
        if (BorderColor.class.equals(clazz)) {
            return buildBorderColor();
        }
        if (BorderSize.class.equals(clazz)) {
            return buildBorderSize();
        }
        if (Documentation.class.equals(clazz)) {
            return buildDocumentation();
        }
        if (FontBorderSize.class.equals(clazz)) {
            return buildFontBorderSize();
        }
        if (FontColor.class.equals(clazz)) {
            return buildFontColor();
        }
        if (FontFamily.class.equals(clazz)) {
            return buildFontFamily();
        }
        if (FontSize.class.equals(clazz)) {
            return buildFontSize();
        }
        if (Height.class.equals(clazz)) {
            return buildHeight();
        }
        if (Width.class.equals(clazz)) {
            return buildWidth();
        }
        if (Radius.class.equals(clazz)) {
            return buildRadius();
        }
        if (Min.class.equals(clazz)) {
            return buildMin();
        }
        if (Max.class.equals(clazz)) {
            return buildMax();
        }
        if (Mean.class.equals(clazz)) {
            return buildMean();
        }
        if (DistributionType.class.equals(clazz)) {
            return buildDistributionType();
        }
        if (Currency.class.equals(clazz)) {
            return buildCurrency();
        }
        if (Quantity.class.equals(clazz)) {
            return buildQuantity();
        }
        if (StandardDeviation.class.equals(clazz)) {
            return buildStandardDeviation();
        }
        if (TimeUnit.class.equals(clazz)) {
            return buildTimeUnit();
        }
        if (UnitCost.class.equals(clazz)) {
            return buildUnitCost();
        }
        if (WorkingHours.class.equals(clazz)) {
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
