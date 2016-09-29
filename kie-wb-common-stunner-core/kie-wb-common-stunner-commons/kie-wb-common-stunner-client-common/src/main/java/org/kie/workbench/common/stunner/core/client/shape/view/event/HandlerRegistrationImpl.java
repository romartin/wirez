package org.kie.workbench.common.stunner.core.client.shape.view.event;

import com.google.gwt.event.shared.HandlerRegistration;

import java.util.LinkedList;
import java.util.List;

public class HandlerRegistrationImpl implements HandlerRegistration {
    private final List<HandlerRegistration> m_list = new LinkedList<>();

    public HandlerRegistrationImpl() {
    }

    public HandlerRegistrationImpl(HandlerRegistration handler, HandlerRegistration... handlers) {
        this.register(handler);
        HandlerRegistration[] arr$ = handlers;
        int len$ = handlers.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            HandlerRegistration h = arr$[i$];
            this.register(h);
        }

    }

    public final int size() {
        return this.m_list.size();
    }

    public final boolean isEmpty() {
        return this.m_list.isEmpty();
    }

    public final HandlerRegistrationImpl destroy() {
        int size = this.size();

        for(int i = 0; i < size; ++i) {
            ((HandlerRegistration)this.m_list.get(i)).removeHandler();
        }

        return this.clear();
    }

    public final HandlerRegistration register(HandlerRegistration handler) {
        if(null != handler && !this.m_list.contains(handler)) {
            this.m_list.add(handler);
        }

        return handler;
    }

    public final boolean isRegistered(HandlerRegistration handler) {
        return null != handler && this.size() > 0 && this.m_list.contains(handler);
    }

    public final HandlerRegistrationImpl deregister(HandlerRegistration handler) {
        if(null != handler) {
            if(this.size() > 0 && this.m_list.contains(handler)) {
                this.m_list.remove(handler);
            }

            handler.removeHandler();
        }

        return this;
    }

    public final HandlerRegistrationImpl clear() {
        this.m_list.clear();
        return this;
    }

    public void removeHandler() {
        this.destroy();
    }
}
