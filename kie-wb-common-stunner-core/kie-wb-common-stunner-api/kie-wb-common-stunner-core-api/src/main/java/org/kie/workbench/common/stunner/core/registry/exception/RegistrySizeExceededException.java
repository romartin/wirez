package org.kie.workbench.common.stunner.core.registry.exception;

public class RegistrySizeExceededException extends RuntimeException {

    private final int maxSize;

    public RegistrySizeExceededException( final int maxSize ) {
        this.maxSize = maxSize;
    }

    public RegistrySizeExceededException( final String message,
                                          final int maxSize ) {
        super( message );
        this.maxSize = maxSize;
    }

    public RegistrySizeExceededException( final String message,
                                          final Throwable cause,
                                          final int maxSize ) {
        super( message, cause );
        this.maxSize = maxSize;
    }

    public RegistrySizeExceededException( final Throwable cause,
                                          final int maxSize ) {
        super( cause );
        this.maxSize = maxSize;
    }

    public RegistrySizeExceededException( final String message,
                                          final Throwable cause,
                                          final boolean enableSuppression,
                                          final boolean writableStackTrace,
                                          final int maxSize ) {
        super( message, cause, enableSuppression, writableStackTrace );
        this.maxSize = maxSize;
    }

    @Override
    public String getMessage() {
        return "Registry size exceeded [max=" + maxSize +"]. " + super.getMessage();
    }
}
