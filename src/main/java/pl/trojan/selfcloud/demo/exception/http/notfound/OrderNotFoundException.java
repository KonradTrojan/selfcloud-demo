package pl.trojan.selfcloud.demo.exception.http.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends CustomNotFoundException {
    public OrderNotFoundException() {
        super();
    }

    public OrderNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public OrderNotFoundException(final String message) {
        super(message);
    }

    public OrderNotFoundException(final Throwable cause) {
        super(cause);
    }
}
