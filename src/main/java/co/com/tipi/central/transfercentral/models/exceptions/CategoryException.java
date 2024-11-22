package co.com.tipi.central.transfercentral.models.exceptions;

import java.io.Serial;

public class CategoryException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = -8349956142429980336L;

    public CategoryException() {
        super();
    }

    public CategoryException(String message) {
        super(message);
    }

    public CategoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public CategoryException(Throwable cause) {
        super(cause);
    }

    protected CategoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
