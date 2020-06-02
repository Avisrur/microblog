package microblog.exceptions;

public class UnknownQueryParam extends RuntimeException {
    public UnknownQueryParam(String message) {
        super(message);
    }
}
