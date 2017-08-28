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

    public Map<String, String> search() {
        try {
            return Files.walk(Paths.get(path))
                    .filter(file -> !Files.isDirectory(file))
                    .map(this::transformToWordsInFile)
                    .flatMap(Collection::stream)
                    .filter(element -> !element.getValue().isEmpty())
                    .collect(toMap(WordInFile::getValue, WordInFile::getFile, this::concat));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    private String concat(String oldValue, String newValue) {
        if (oldValue.contains(newValue)) {
            return oldValue;
        }
        return oldValue + "\\|" + newValue;
    }

    private List<WordInFile> transformToWordsInFile(Path path) {
        String filename = path.toString();
        try (Stream<String> stream = Files.lines(path, Charset.forName("ISO-8859-1"))) {
            return stream
                    .flatMap(content -> Arrays.stream(content.split("\\W+")))
                    .map(String::toUpperCase)
                    .map(word -> new WordInFile(word, filename))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private class WordInFile {
        private String value;
        private String file;

        WordInFile(String value, String file) {
            this.value = value;
            this.file = file;
        }

        String getValue() {
            return value;
        }

        String getFile() {
            return file;
        }
    }
}
