package profITsoft.lectures5_6.task2.exeptions;

public class CustomParseException extends RuntimeException{
    public CustomParseException() {
        super();
    }

    public CustomParseException(String message) {
        super(message);
    }
}
