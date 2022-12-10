package profITsoft.lectures5_6.task1;


public class Main {
    // 50 ітерацій (Для першого варіанту програми)
    // швидкість однопоточної програми: 17502 мс
    // швидкість багатопоточної програми (2 потоки): 11211 мс
    // швидкість багатопоточної програми (4 потоки): 8138 мс
    // швидкість багатопоточної програми (8 потоків): 7582 мс

    // 50 ітерацій (Для другого варіанту програми)
    // швидкість багатопоточної програми (2 потоки): 10153 мс
    // швидкість багатопоточної програми (4 потоки): 8080 мс
    // швидкість багатопоточної програми (8 потоків): 7391 мс
    private static final String TEST_JSON_DIR_PATH = "src/main/resources/lectures5_6/task1/inputFiles";

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        //for (int i = 0; i < 50; i++) {
            MultithreadedViolationConvertor.multithreadingParseJSON(TEST_JSON_DIR_PATH,"test");
         //}
        System.out.println(System.currentTimeMillis()-time1);

        //JsonGenerator.generateJsonFiles();
     }
}
