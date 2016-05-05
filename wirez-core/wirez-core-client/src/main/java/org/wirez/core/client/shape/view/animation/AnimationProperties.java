package org.wirez.core.client.shape.view.animation;

public class AnimationProperties {
    
    public static final class X extends AbstractAnimationProperty<Double> {
        
        public X() {
        }

        public X(final Double value) {
            super(value);
        }
        
    }

    public static final class Y extends AbstractAnimationProperty<Double> {

        public Y() {
        }

        public Y(final Double value) {
            super(value);
        }

    }

    public static final class WIDTH extends AbstractAnimationProperty<Double> {

        public WIDTH() {
        }

        public WIDTH(final Double value) {
            super(value);
        }

    }

    public static final class HEIGHT extends AbstractAnimationProperty<Double> {

        public HEIGHT() {
        }

        public HEIGHT(final Double value) {
            super(value);
        }

    }

    public static final class RADIUS extends AbstractAnimationProperty<Double> {

        public RADIUS() {
        }

        public RADIUS(final Double value) {
            super(value);
        }

    }

    public static final class FILL_COLOR extends AbstractAnimationProperty<String> {

        public FILL_COLOR() {
        }

        public FILL_COLOR(final String value) {
            super(value);
        }

    }

    public static final class STROKE_COLOR extends AbstractAnimationProperty<String> {

        public STROKE_COLOR() {
        }

        public STROKE_COLOR(final String value) {
            super(value);
        }

    }

    public static final class STROKE_WIDTH extends AbstractAnimationProperty<Double> {

        public STROKE_WIDTH() {
        }

        public STROKE_WIDTH(final Double value) {
            super(value);
        }

    }
    
}
