package net.edgefox.spring.entities;

public class ApplicationError {
    private int code;
    private String message;

    public ApplicationError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return String.format("Code: %d, Message: %s", code, message);
    }
}
