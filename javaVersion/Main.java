package javaVersion;

import java.util.Map;
import java.util.HashMap;
import java.io.File;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("kjv.txt");
        long start = System.currentTimeMillis();
        findTopWords(file);
        System.out.printf("total execution time = %dms\n", System.currentTimeMillis() - start);
    }

    public static void findTopWords(File file) throws IOException {
        Map<String, Integer> map = new HashMap<>();
        Scanner fileReader = new Scanner(file);
        while(fileReader.hasNext()) {
            String word = fileReader.next();
            word = word.replaceAll("\\W+","");
            if(map.containsKey(word)) {
                int newCount = map.get(word) + 1;
                map.replace(word, newCount);
            } else {
                map.put(word, 1);
            }
        }
        final Map<String, Integer> sortedMap = map.entrySet()
                                                  .stream()
                                                  .sorted((Map.Entry.<String,Integer>comparingByValue().reversed()))
                                                  .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        int count = 0;
        for(Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
            if(count >= 10) return;
            System.out.printf("%2d. %-10s:%7d\n", count+1, entry.getKey(), entry.getValue());
            count++;
        }
    }
}