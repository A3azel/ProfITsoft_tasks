package profITsoft.lectures5_6.task1;


import java.util.concurrent.ExecutionException;

public class Main {
    // швидкість однопоточної програми: 10396 мс
    // швидкість багатопоточної програми (2 потоки): 9462 мс
    // швидкість багатопоточної програми (4 потоки): 7646 мс
    // швидкість багатопоточної програми (8 потоків): 6338 мс
    private static final String TEST_JSON_DIR_PATH = "src/main/resources/lectures5_6/task1/inputFiles";
    //private static final String TEST_JSON_DIR_PATH = "src/main/resources/lectures5_6/task1/test";
    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        //for (int i = 0; i < 50; i++) {
            ViolationConvertor.multithreadingParseJSON(TEST_JSON_DIR_PATH,"test");
       // }

        /*try {
            MultithreadedConvertor.test(TEST_JSON_DIR_PATH);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }*/
        System.out.println(System.currentTimeMillis()-time1);

        //JsonGenerator.generateJsonFiles();
     }
}
