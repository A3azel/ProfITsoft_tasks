package profITsoft.lectures5_6.task1;


import java.util.concurrent.ExecutionException;

public class Main {
    // швидкість однопоточної програми: 10396 мс
    // швидкість багатопоточної програми (2 потоки): 9665 мс
    // швидкість багатопоточної програми (4 потоки): 8361 мс
    // швидкість багатопоточної програми (8 потоків): 6569 мс
    private static final String TEST_JSON_DIR_PATH = "src/main/resources/lectures5_6/task1/inputFiles";
    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        try {
            MultithreadedConvertor.test(TEST_JSON_DIR_PATH);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis()-time1);

        //JsonGenerator.generateJsonFiles();
     }
}
