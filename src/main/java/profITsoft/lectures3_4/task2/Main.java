package profITsoft.lectures3_4.task2;

public class Main {
    private static final String TEST_JSON_DIR_PATH = "src/main/java/profITsoft/lectures3_4/task2/violationJSONs";
    public static void main(String[] args) {
        ViolationConvertor.parseJSON(TEST_JSON_DIR_PATH,"statistic");
    }
}
