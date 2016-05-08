package org.wirez.basicset.api.factory;

import org.wirez.basicset.api.Circle;
import org.wirez.basicset.api.Polygon;
import org.wirez.basicset.api.Rectangle;
import org.wirez.core.api.definition.factory.BindableModelFactory;

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
    
}
