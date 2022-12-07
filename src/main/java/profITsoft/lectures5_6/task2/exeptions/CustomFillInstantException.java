package profITsoft.lectures5_6.task2.exeptions;

public class CustomFillInstantException extends Throwable{
    public CustomFillInstantException() {
        super();
    }

    public CustomFillInstantException(String message) {
        super(message);
    }
}
