package profITsoft.Task2;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SortingTagsTask {
    public static final String FIND_TAG = "#\\w*";
    //public static final String FIND_TAG = "([\\s.,$!@%^&*()\\-+=]+|\\A)#\\w*";

    public static Map<String,Integer> getMostPopularTags(List<String> stringList){
        // find all unique tags in the every sentence and put them to map
        Map<String,Integer> resultMap = new HashMap<>();
        for (int i = 0; i < stringList.size(); i++) {
            List<String> tagsList = new ArrayList<>();
            Pattern pattern = Pattern.compile(FIND_TAG);
            Matcher matcher = pattern.matcher(stringList.get(i));
            while (matcher.find()){
                String finderTag =  matcher.group();
                if(!tagsList.contains(finderTag)){
                    tagsList.add(finderTag);
                }
            }
            for (int j = 0; j < tagsList.size(); j++) {
                resultMap.put(tagsList.get(j), resultMap.containsKey(tagsList.get(j)) ? resultMap.get((tagsList.get(j))) + 1 : 1);
            }
        }
        // sorted map by value
        return resultMap.entrySet()
                .stream()
                .sorted(Collections
                        .reverseOrder(Map.Entry.<String, Integer>comparingByValue())
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(5)
                .collect(Collectors
                        .toMap(Map.Entry::getKey
                                ,Map.Entry::getValue
                                ,(a, b) -> a,
                                LinkedHashMap::new));
    }
    // I think there are more elegant solutions, so can you give me some advice?
}
