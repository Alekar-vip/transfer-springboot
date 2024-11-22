package co.com.tipi.central.transfercentral.models.exceptions;

import java.io.Serial;

public class NotFileException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 159395887038951044L;

    public NotFileException() {
        super();
    }

    public NotFileException(String message) {
        super(message);
    }

    public NotFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFileException(Throwable cause) {
        super(cause);
    }

    protected NotFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
