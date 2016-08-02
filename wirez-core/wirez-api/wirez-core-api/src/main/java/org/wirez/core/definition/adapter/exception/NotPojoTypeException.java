package org.wirez.core.definition.adapter.exception;

public class NotPojoTypeException extends RuntimeException {

    private final Class<?> type;

    public NotPojoTypeException( Class<?> type ) {
        this.type = type;
    }

    @Override
    public String getMessage() {
        return "Domain model class [" + type.getName() + "] is not pojo type based.";
    }
}
