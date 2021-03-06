package com.schibsted.output;

import java.util.*;
import java.util.function.Function;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Counter {

    private final Map<String, String> wordsDepot;

    public Counter(Map<String, String> wordsDepot) {
        this.wordsDepot = wordsDepot;
    }

    public void printResult(String nextLine) {
        String[] inputtedWords = nextLine.split("\\W+");
        int size = inputtedWords.length;
        stream(inputtedWords)
                .map(String::toUpperCase)
                .map(wordsDepot::get)
                .filter(Objects::nonNull)
                .map(concatFiles -> concatFiles.split("\\|"))
                .flatMap(Arrays::stream)
                .collect(groupingBy(Function.identity(), counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .limit(10)
                .forEach(element ->
                        System.out.println(element.getKey() + " : " + ((float) element.getValue() / size) * 100 + "%"));
    }


}
