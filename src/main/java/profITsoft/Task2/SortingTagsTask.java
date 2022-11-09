package profITsoft.Task2;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SortingTagsTask {
    /*
    * #tag - tag
    * aeq#tag - not tag
    * #tag1&#tag2 - two tags
    * qwerty-#tag - tag
    * */
    public static final String FIND_TAG = "([\\s.,$!@%^&*()\\-+=]+|\\A)#\\w*";

    public static Map<String,Integer> getMostPopularTags(List<String> stringList){
        // find all unique tags in the every sentence and put them to map
        if(stringList==null){
            throw new IllegalArgumentException("passed null instead List<String>");
        }
        Map<String,Integer> resultMap = new HashMap<>();
        for (String value : stringList) {
            List<String> tagsList = new ArrayList<>();
            Pattern pattern = Pattern.compile(FIND_TAG);
            Matcher matcher = pattern.matcher(value);
            while (matcher.find()) {
                String finderTag = matcher.group().replaceAll("[\\s.,$!@%^&*()\\-+=]","");
                if (!tagsList.contains(finderTag)) {
                    tagsList.add(finderTag);
                    resultMap.put(finderTag, resultMap.containsKey(finderTag) ? resultMap.get(finderTag) + 1 : 1);
                }
            }
        }
        // sorted map by value and then by key
        return resultMap.entrySet()
                .stream()
                .sorted(Collections
                        .reverseOrder(Map.Entry.<String, Integer>comparingByValue())
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(5)
                .collect(Collectors
                        .toMap(Map.Entry::getKey
                                ,Map.Entry::getValue
                                ,(a, b) -> a
                                , LinkedHashMap::new));
    }
}
