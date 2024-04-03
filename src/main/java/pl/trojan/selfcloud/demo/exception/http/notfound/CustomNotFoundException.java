package pl.trojan.selfcloud.demo.exception.http.notfound;

public abstract class CustomNotFoundException extends RuntimeException{

    public CustomNotFoundException() {
        super();
    }

    public CustomNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CustomNotFoundException(final String message) {
        super(message);
    }

    public CustomNotFoundException(final Throwable cause) {
        super(cause);
    }
}
