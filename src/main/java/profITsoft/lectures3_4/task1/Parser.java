package profITsoft.lectures3_4.task1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static final String DEFAULT_OUTPUT_FILE_PATH = "C:\\Users\\Lenovo\\IdeaProjects\\profITsoft_tasks\\src\\main\\java\\profITsoft\\lectures3_4\\task1\\";
    private static final String XML_SUFFIX = ".xml";
    // finding person tags with all spaces in front of it
    public static final String XML_REGEX = "\\s[\\s\\S]+?/>";
    // I add \\W because then we will not find surname
    public static final String XML_NAME = "\\W(name\\s?=\\s?\"\\S*)\"";
    // clear name, I use this to replace name for new name after removing surname
    public static final String XML_NAME_FOR_REPLAYS = "name\\s?=\\s?\"\\S*\"";
    // finding surname
    public static final String XML_SURNAME = "\\ssurname\\s?=\\s?\"(\\S*\")";

    public static void parseXML(String inputFilePath, String outputFileName){
        // BufferedReader constructor with buffer
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFilePath),16384 /*16 KB*/);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(DEFAULT_OUTPUT_FILE_PATH + outputFileName + XML_SUFFIX))) {
            StringBuilder inputXMLString = new StringBuilder();
            StringBuilder outputXMLString = new StringBuilder();
            String xmlLine;

            while((xmlLine=bufferedReader.readLine())!=null){
                inputXMLString.append(xmlLine).append("\n");
            }
            Pattern pattern = Pattern.compile(XML_REGEX);
            Matcher matcher = pattern.matcher(inputXMLString.toString());
            outputXMLString.append("<persons>");
            while (matcher.find()){
                String person = matcher.group();
                Matcher nameMatcher = Pattern.compile(XML_NAME).matcher(person);
                Matcher surnameMatcher = Pattern.compile(XML_SURNAME).matcher(person);
                if(nameMatcher.find() && surnameMatcher.find()) {
                    String name = nameMatcher.group(1);
                    String surname = surnameMatcher.group(1);
                    String newNameTag = name + " " + surname;
                    String changedParsonTag = person.replaceAll(XML_SURNAME,"").replaceAll(XML_NAME_FOR_REPLAYS,newNameTag);
                    outputXMLString.append(changedParsonTag);
                }
            }
            outputXMLString.append("\n").append("</persons>");
            bufferedWriter.write(outputXMLString.toString());
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }
}
