package org.kie.workbench.common.stunner.core.definition.adapter.exception;

import org.kie.workbench.common.stunner.core.definition.adapter.Adapter;

public class AdapterNotFoundException extends RuntimeException {

    private final Class<? extends Adapter> adapterClass;
    private final Class<?> type;

    public AdapterNotFoundException( Class<? extends Adapter> adapterClass, Class<?> type ) {
        this.adapterClass = adapterClass;
        this.type = type;
    }

    @Override
    public String getMessage() {
        return "Adapter of type [" + adapterClass.getName() + "] not found for domain type [" + type.getName() + "].";
    }
}
