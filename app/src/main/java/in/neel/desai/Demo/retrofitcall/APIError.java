package in.neel.desai.Demo.retrofitcall;

/**
 * Created on 12-Dec-18.
 */

public class APIError {

    private int statusCode;
    private String message;

    public APIError() {
    }

    public APIError(int statusCode, String message) {
        this.statusCode=statusCode;
        this.message=message;
    }

    public int status() {
        return statusCode;
    }

    public String message() {
        return message;
    }
}
