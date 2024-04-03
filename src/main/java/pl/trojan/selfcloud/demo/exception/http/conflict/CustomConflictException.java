package pl.trojan.selfcloud.demo.exception.http.conflict;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public abstract class CustomConflictException extends RuntimeException{

    public CustomConflictException() {
        super();
    }

    public CustomConflictException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CustomConflictException(final String message) {
        super(message);
    }

    public CustomConflictException(final Throwable cause) {
        super(cause);
    }
}
