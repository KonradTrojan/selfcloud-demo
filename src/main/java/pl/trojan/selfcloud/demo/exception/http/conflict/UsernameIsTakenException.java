package pl.trojan.selfcloud.demo.exception.http.conflict;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UsernameIsTakenException extends CustomConflictException{

    public UsernameIsTakenException() {
        super();
    }

    public UsernameIsTakenException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UsernameIsTakenException(final String message) {
        super(message);
    }

    public UsernameIsTakenException(final Throwable cause) {
        super(cause);
    }
}
