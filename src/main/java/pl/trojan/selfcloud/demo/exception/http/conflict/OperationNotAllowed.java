package pl.trojan.selfcloud.demo.exception.http.conflict;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class OperationNotAllowed extends CustomConflictException{

    public OperationNotAllowed() {
        super();
    }

    public OperationNotAllowed(final String message, final Throwable cause) {
        super(message, cause);
    }

    public OperationNotAllowed(final String message) {
        super(message);
    }

    public OperationNotAllowed(final Throwable cause) {
        super(cause);
    }
}
