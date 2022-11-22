package profITsoft.lectures3_4.task1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static final String DEFAULT_OUTPUT_FILE_PATH = "src/main/resources/task1/outputFiles/";
    private static final String XML_SUFFIX = ".xml";
    // finding person tags with all spaces in front of it
    private static final String XML_REGEX = "\\s*[\\s\\S]+?/>";
    // I add \\W because then we will not find surname
    private static final String XML_NAME = "\\W(name\\s?=\\s?\"\\S*)\"";
    // clear name, I use this to replace name for new name after removing surname
    private static final String XML_NAME_FOR_REPLAYS = "name\\s?=\\s?\"\\S*\"";
    // finding surname
    private static final String XML_SURNAME = "\\ssurname\\s?=\\s?\"(\\S*\")";

    public static void parseXML(String inputFilePath, String outputFileName){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFilePath));
            //You need to remove the second attribute and uncommented outputXMLString to use the second solution
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(DEFAULT_OUTPUT_FILE_PATH + outputFileName + XML_SUFFIX,true))) {
            StringBuilder inputXMLString = new StringBuilder();
            //StringBuilder outputXMLString = new StringBuilder();
            String xmlLine;

            //         In this solution I write each <person> separately so that I don't have to keep everything in memory
            while((xmlLine=bufferedReader.readLine())!=null){
                inputXMLString.append(xmlLine).append("\n");
                Pattern pattern = Pattern.compile(XML_REGEX);
                Matcher matcher = pattern.matcher(inputXMLString.toString());

                while (matcher.find()){
                    Matcher nameMatcher = Pattern.compile(XML_NAME).matcher(inputXMLString);
                    Matcher surnameMatcher = Pattern.compile(XML_SURNAME).matcher(inputXMLString);
                    if(nameMatcher.find() && surnameMatcher.find()) {
                        String name = nameMatcher.group(1);
                        String surname = surnameMatcher.group(1);
                        String newNameTag = name + " " + surname;
                        String changedParsonTag = inputXMLString.toString().replaceAll(XML_SURNAME,"").replaceAll(XML_NAME_FOR_REPLAYS,newNameTag);
                        bufferedWriter.write(changedParsonTag);
                        inputXMLString.setLength(0);
                    }
                }
            }
            bufferedWriter.write("</persons>");

            /*while((xmlLine=bufferedReader.readLine())!=null){
                inputXMLString.append(xmlLine).append("\n");
            }
            Pattern pattern = Pattern.compile(XML_REGEX);
            Matcher matcher = pattern.matcher(inputXMLString.toString());
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
            bufferedWriter.write(outputXMLString.toString());*/

        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }
}
