package microblog.exceptions;

public class UpdateFailureException extends RuntimeException {
    public UpdateFailureException(String message) {
        super(message);
    }
}
