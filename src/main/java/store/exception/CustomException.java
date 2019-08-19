package store.exception;

public class CustomException extends RuntimeException {

    private int statusCode;
    private String message;

    public CustomException(String message, int statusCode, Exception ex) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
    }

    public CustomException(String message, int statusCode) {
        super(message);

        this.statusCode = statusCode;
        this.message = message;
    }

    public CustomException(String message) {
        super(message);
        this.message = message;
        this.statusCode = 500;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}

