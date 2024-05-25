package firewoody.learnspring.database.dbexception;

public class MyDbException extends RuntimeException {

    public MyDbException() {
    }

    public MyDbException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyDbException(Throwable cause) {
        super(cause);
    }
}
