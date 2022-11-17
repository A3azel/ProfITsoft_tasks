package lectures3_4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import profITsoft.lectures3_4.task1.Parser;

import java.io.*;

public class TestTask1 {
    private static final String DEFAULT_OUTPUT_FILE_PATH = "C:\\Users\\Lenovo\\IdeaProjects\\profITsoft_tasks\\src\\main\\java\\profITsoft\\lectures3_4\\task1\\";
    private static final String DEFAULT_INPUT_FILE_PATH = "C:\\Users\\Lenovo\\IdeaProjects\\profITsoft_tasks\\src\\main\\java\\profITsoft\\lectures3_4\\task1\\input.xml";
    private static final String DEFAULT_CORRECT_FILE_PATH = "C:\\Users\\Lenovo\\IdeaProjects\\profITsoft_tasks\\src\\main\\java\\profITsoft\\lectures3_4\\task1\\output.xml";
    private static final String XML_SUFFIX = ".xml";

    @Test
    public void isCreatedXMLFileExist() {
        Parser.parseXML(DEFAULT_INPUT_FILE_PATH, "testParseXML");
        File testParseXMLFile = new File(DEFAULT_OUTPUT_FILE_PATH + "testParseXML" + XML_SUFFIX);
        Assertions.assertTrue(testParseXMLFile.exists());
    }
}