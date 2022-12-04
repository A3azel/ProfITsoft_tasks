package profITsoft.lectures5_6.task2.exeptions;

public class FieldNotFound extends Throwable{
    public FieldNotFound() {
        super();
    }

    public FieldNotFound(String message) {
        super(message);
    }
}
