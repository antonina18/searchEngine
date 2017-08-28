package com.schibsted.search;


import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class SearchEngineTest {

    private SearchEngine searchEngine;


    @Test
    public void shouldNotReadFileCauseItsJson() throws Exception {
        //given
        Path path = Paths.get(ClassLoader.getSystemResource("cherry.json").toURI());
        String stringPath = path.toString();
        searchEngine = new SearchEngine(stringPath);
        Integer expected = 0;

        //when
        Map<String, String> actual = searchEngine.search();

        assertThat(actual.size(), is(equalTo(expected)));

    }


    @Test
    public void shouldReadFileWithSomeWords() throws Exception {
        //given
        Path path = Paths.get(ClassLoader.getSystemResource("cherry.txt").toURI());
        String stringPath = path.toString();
        searchEngine = new SearchEngine(stringPath);
        Integer expected = 4;

        //when
        Map<String, String> actual = searchEngine.search();

        assertThat(actual.size(), is(equalTo(expected)));

    }

    @Test
    public void shouldReadFileWithSomeStrangeSigns() throws Exception {
        //given
        Path path = Paths.get(ClassLoader.getSystemResource("cherry2.txt").toURI());
        String stringPath = path.toString();
        searchEngine = new SearchEngine(stringPath);
        Integer expected = 2;

        //when
        Map<String, String> actual = searchEngine.search();

        assertThat(actual.size(), is(equalTo(expected)));

    }

    @Test
    public void shouldEliminateDuplicate() throws Exception {
        //given
        Path path = Paths.get(ClassLoader.getSystemResource("cherryWithDuplicate.txt").toURI());
        String stringPath = path.toString();
        searchEngine = new SearchEngine(stringPath);
        Integer expected = 2;

        //when
        Map<String, String> actual = searchEngine.search();

        assertThat(actual.size(), is(equalTo(expected)));

    }


}