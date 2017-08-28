package com.schibsted.search;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class SearchEngine {

    private final String path;

    public SearchEngine(String path) {
        this.path = path;
    }

    public Map<String, Set<String>> search() {
        try {
            return Files.walk(Paths.get(path))
                    .filter(file -> !Files.isDirectory(file))
                    .map(this::transformToWordsInFile)
                    .flatMap(Collection::stream)
                    .filter(element -> !element.getValue().isEmpty())
                    .collect(toMap(WordInFile::getValue, WordInFile::getFiles, this::addUp));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    private List<WordInFile> transformToWordsInFile(Path path) {
        String filename = path.toString();
        try (Stream<String> stream = Files.lines(path, Charset.forName("ISO-8859-1"))) {
            return stream
                    .flatMap(content -> Arrays.stream(content.split("\\W+")))
                    .map(String::toUpperCase)
                    .map(word -> new WordInFile(word, asSet(filename)))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private Set<String> asSet(String filename) {
        return Stream.of(filename).collect(Collectors.toSet());
    }

    private Set addUp(Set oldValue, Set newValue) {
        oldValue.addAll(newValue);
        return oldValue;
    }

    private class WordInFile {
        private String value;
        private Set<String> files;

        WordInFile(String value, Set<String> files) {
            this.value = value;
            this.files = files;
        }

        String getValue() {
            return value;
        }

        Set<String> getFiles() {
            return files;
        }
    }
}
