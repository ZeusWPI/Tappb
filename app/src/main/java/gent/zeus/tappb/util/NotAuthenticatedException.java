package gent.zeus.tappb.util;

public class NotAuthenticatedException extends RuntimeException {
    public NotAuthenticatedException() {
        super("Not logged in!");
    }
    public NotAuthenticatedException(String message) {
        super(message);
    }
}
