package exceptions;

public class UserNotFoundException extends Exception {
    private final String userId;
    private final String errorCode;

    public UserNotFoundException(String message) {
        super(message);
        this.userId = "";
        this.errorCode = "";
    }
    
    public UserNotFoundException(String message, String userId) {
        super(message);
        this.userId = userId;
        this.errorCode = "USER_404";
    }

    public UserNotFoundException(String message, String userId, Throwable cause) {
        super(message, cause);
        this.userId = userId;
        this.errorCode = "USER_404";
    }

    // Getter methods
    public String getUserId() {
        return userId;
    }

    public String getErrorCode() {
        return errorCode;
    }
}