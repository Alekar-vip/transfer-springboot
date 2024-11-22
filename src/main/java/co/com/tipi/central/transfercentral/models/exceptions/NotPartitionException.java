package co.com.tipi.central.transfercentral.models.exceptions;

import java.io.Serial;

public class NotPartitionException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 7921699371134721583L;

    public NotPartitionException() {
        super();
    }

    public NotPartitionException(String message) {
        super(message);
    }

    public NotPartitionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotPartitionException(Throwable cause) {
        super(cause);
    }

    protected NotPartitionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
