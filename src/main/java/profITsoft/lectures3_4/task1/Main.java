package profITsoft.lectures3_4.task1;

public class Main {
    private static final String TEST_INPUT_XML_PATH = "src/main/resources/task1/inputFiles/input.xml";
    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        Parser.parseXML(TEST_INPUT_XML_PATH,"test");
        System.out.println(System.currentTimeMillis()-time1);
    }
}
