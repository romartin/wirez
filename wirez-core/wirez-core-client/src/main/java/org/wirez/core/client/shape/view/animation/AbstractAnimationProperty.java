package org.wirez.core.client.shape.view.animation;

public abstract class AbstractAnimationProperty<T> implements AnimationProperty<T> {
    
    private T value;

    public AbstractAnimationProperty() {
    }

    public AbstractAnimationProperty(final T value) {
        this.value = value;
    }

    @Override
    public void setValue(final T value) {
        this.value = value;
    }

    @Override
    public T getValue() {
        return value;
    }


    @Override
    public boolean equals( final Object o ) {
        return null != o && (this == o || this.getClass().equals(o.getClass()));
    }
    
}
