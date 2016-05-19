package org.wirez.basicset.definition.factory;

import org.wirez.basicset.definition.Circle;
import org.wirez.basicset.definition.Polygon;
import org.wirez.basicset.definition.PolygonWithIcon;
import org.wirez.basicset.definition.Rectangle;
import org.wirez.basicset.definition.icon.MinusIcon;
import org.wirez.basicset.definition.icon.PlusIcon;
import org.wirez.basicset.definition.icon.XORIcon;
import org.wirez.core.definition.factory.BindableModelFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.LinkedHashSet;
import java.util.Set;

@ApplicationScoped
public class BasicDefinitionFactory extends BindableModelFactory<Object> {

    BasicPropertyFactory basicSetPropertyFactory;
    BasicPropertySetFactory basicSetPropertySetFactory;

    protected BasicDefinitionFactory() {
    }

    @Inject
    public BasicDefinitionFactory(BasicPropertyFactory basicSetPropertyFactory,
                                  BasicPropertySetFactory basicSetPropertySetFactory) {
        this.basicSetPropertyFactory = basicSetPropertyFactory;
        this.basicSetPropertySetFactory = basicSetPropertySetFactory;
    }

    private static final Set<Class<?>>  SUPPORTED_DEF_CLASSES = new LinkedHashSet<Class<?>>() {{
        
        add(Rectangle.class);
        add(Circle.class);
        add(Polygon.class);
        add(PolygonWithIcon.class);
        add(MinusIcon.class);
        add(PlusIcon.class);
        add(XORIcon.class);
        
    }};

    @Override
    public Set<Class<?>> getAcceptedClasses() {
        return SUPPORTED_DEF_CLASSES;
    }

    @Override
    public Object build(final Class<?> clazz) {

        if ( Rectangle.class.equals( clazz ) ) {
            return buildRectangle();
        }
        
        if ( Circle.class.equals( clazz ) ) {
            return buildCircle();
        }

        if ( Polygon.class.equals( clazz ) ) {
            return buildPolygon();
        }

        if ( PolygonWithIcon.class.equals( clazz ) ) {
            return buildPolygonWithIcon();
        }

        if ( MinusIcon.class.equals( clazz ) ) {
            return buildMinusIcon();
        }

        if ( PlusIcon.class.equals( clazz ) ) {
            return buildPlusIcon();
        }
        
        if ( XORIcon.class.equals( clazz ) ) {
            return buildXORIcon();
        }

        throw new RuntimeException( "This factory should provide a definition for [" + clazz + "]" );
    }


    public Rectangle buildRectangle() {
        Rectangle rectangle = new Rectangle(basicSetPropertyFactory.buildName(),
                basicSetPropertySetFactory.buildBackgroundSet(),
                basicSetPropertySetFactory.buildFontSet(),
                basicSetPropertyFactory.buildWidth(),
                basicSetPropertyFactory.buildHeight());
        rectangle.getName().setValue("My rectangle");
        rectangle.getBackgroundSet().getBgColor().setValue(Rectangle.COLOR);
        rectangle.getBackgroundSet().getBorderSize().setValue(Rectangle.BORDER_SIZE);
        rectangle.getWidth().setValue(Rectangle.WIDTH);
        rectangle.getHeight().setValue(Rectangle.HEIGHT);
        return rectangle;
    }

    public Circle buildCircle() {
        Circle circle = new Circle(basicSetPropertyFactory.buildName(),
                basicSetPropertySetFactory.buildBackgroundSet(),
                basicSetPropertySetFactory.buildFontSet(),
                basicSetPropertyFactory.buildRadius());
        circle.getName().setValue("My circle");
        circle.getBackgroundSet().getBgColor().setValue(Circle.COLOR);
        circle.getBackgroundSet().getBorderSize().setValue(Circle.BORDER_SIZE);
        circle.getRadius().setValue(Circle.RADIUS);
        return circle;
    }

    public Polygon buildPolygon() {
        Polygon polygon = new Polygon(basicSetPropertyFactory.buildName(),
                basicSetPropertySetFactory.buildBackgroundSet(),
                basicSetPropertySetFactory.buildFontSet(),
                basicSetPropertyFactory.buildRadius());
        polygon.getName().setValue("My polygon");
        polygon.getBackgroundSet().getBgColor().setValue(Polygon.COLOR);
        polygon.getBackgroundSet().getBorderSize().setValue(Polygon.BORDER_SIZE);
        polygon.getRadius().setValue(Polygon.RADIUS);
        return polygon;
    }

    public PolygonWithIcon buildPolygonWithIcon() {
        PolygonWithIcon polygon = new PolygonWithIcon(basicSetPropertyFactory.buildName(),
                basicSetPropertySetFactory.buildBackgroundSet(),
                basicSetPropertySetFactory.buildFontSet(),
                basicSetPropertyFactory.buildRadius(),
                basicSetPropertyFactory.buildIconType());
        polygon.getName().setValue("My polygon");
        polygon.getBackgroundSet().getBgColor().setValue(PolygonWithIcon.COLOR);
        polygon.getBackgroundSet().getBorderSize().setValue(PolygonWithIcon.BORDER_SIZE);
        polygon.getRadius().setValue(PolygonWithIcon.RADIUS);
        return polygon;
    }

    public MinusIcon buildMinusIcon() {
        MinusIcon icon = new MinusIcon(basicSetPropertyFactory.buildName(),
                basicSetPropertySetFactory.buildBackgroundSet(),
                basicSetPropertyFactory.buildWidth(),
                basicSetPropertyFactory.buildHeight());
        icon.getName().setValue("My minus icon");
        icon.getBackgroundSet().getBgColor().setValue(MinusIcon.COLOR);
        icon.getBackgroundSet().getBorderSize().setValue(MinusIcon.BORDER_SIZE);
        icon.getWidth().setValue(MinusIcon.WIDTH);
        icon.getHeight().setValue(MinusIcon.HEIGHT);
        return icon;
    }

    public PlusIcon buildPlusIcon() {
        PlusIcon icon = new PlusIcon(basicSetPropertyFactory.buildName(),
                basicSetPropertySetFactory.buildBackgroundSet(),
                basicSetPropertyFactory.buildWidth(),
                basicSetPropertyFactory.buildHeight());
        icon.getName().setValue("My plus icon");
        icon.getBackgroundSet().getBgColor().setValue(PlusIcon.COLOR);
        icon.getBackgroundSet().getBorderSize().setValue(PlusIcon.BORDER_SIZE);
        icon.getWidth().setValue(PlusIcon.WIDTH);
        icon.getHeight().setValue(PlusIcon.HEIGHT);
        return icon;
    }
    
    public XORIcon buildXORIcon() {
        XORIcon icon = new XORIcon(basicSetPropertyFactory.buildName(),
                basicSetPropertySetFactory.buildBackgroundSet(),
                basicSetPropertyFactory.buildWidth(),
                basicSetPropertyFactory.buildHeight());
        icon.getName().setValue("My xor icon");
        icon.getBackgroundSet().getBgColor().setValue(XORIcon.COLOR);
        icon.getBackgroundSet().getBorderSize().setValue(XORIcon.BORDER_SIZE);
        icon.getWidth().setValue(XORIcon.WIDTH);
        icon.getHeight().setValue(XORIcon.HEIGHT);
        return icon;
    }
    
}
