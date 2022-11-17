package profITsoft.lectures1_2.task2;

import java.util.*;
import java.util.function.Function;
import java.util.regex.MatchResult;
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
        List<String> strings = stringList.stream()
                .flatMap(str -> Pattern.compile(FIND_TAG).matcher(str).results())
                .map(MatchResult::group)
                .map(str -> str.replaceAll("[\\s.,$!@%^&*()\\-+=]",""))
                .distinct()
                .collect(Collectors.toList());
        return strings.stream()
                .collect(Collectors.toMap(Function.identity(),c->1,(a,b) -> a+=1))
                .entrySet()
                .stream()
                .sorted(Collections
                        .reverseOrder(Map.Entry.<String, Integer>comparingByValue())
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey
                        ,Map.Entry::getValue
                        ,(a, b) -> a
                        , LinkedHashMap::new));
       /* if(stringList==null){
            throw new IllegalArgumentException("passed null instead List<String>");
        }
        List<String> strings = new ArrayList<>();
        for (String s : stringList){
            Pattern.compile(FIND_TAG).matcher(s)
                    .results()
                    .map(MatchResult::group)
                    .map(str -> str.replaceAll("[\\s.,$!@%^&*()\\-+=]",""))
                    .distinct()
                    .forEach(strings::add);
        }
        return strings.stream()
                .collect(Collectors.toMap(Function.identity(),c->1,(a,b) -> a+=1))
                .entrySet()
                .stream()
                .sorted(Collections
                        .reverseOrder(Map.Entry.<String, Integer>comparingByValue())
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey
                        ,Map.Entry::getValue
                        ,(a, b) -> a
                        , LinkedHashMap::new));*/
    }

    // or this solution

    /*public static Map<String,Integer> getMostPopularTags(List<String> stringList){
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
    }*/
}
