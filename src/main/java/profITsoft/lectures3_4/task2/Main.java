package profITsoft.lectures3_4.task2;

public class Main {
    //private static final String TEST_JSON_DIR_PATH = "src/main/resources/lectures3_4/task2/inputFiles";
    private static final String TEST_JSON_DIR_PATH = "src/main/resources/lectures3_4/task2/inputLargeFiles";

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        ViolationConvertor.parseJSON(TEST_JSON_DIR_PATH,"statistic");
        System.out.println(System.currentTimeMillis()-time1);
    }
}
