package com.schibsted.output;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class CounterTest {

    private Counter counter;
    Map<String, String> wordsDepot;

    @Test
    public void shouldNotDisplayAnything() throws Exception {
        //given
        String expected = "";
        String fileName = "cherry.txt";
        wordsDepot = new HashMap<>();
        wordsDepot.put("CHERRY", fileName);
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        System.setOut(new PrintStream(actual));
        counter = new Counter(wordsDepot);

        //when
        counter.printResult("some");


        //then
        assertThat(actual.toString(), is(equalTo(expected)));
    }

    @Test
    public void shouldReturnSomePercentage() throws Exception {
        //given
        String expected = "cherry.txt : 66.66667%\n";
        String fileName = "cherry.txt";
        wordsDepot = new HashMap<>();
        wordsDepot.put("CHERRY", fileName);
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        System.setOut(new PrintStream(actual));
        counter = new Counter(wordsDepot);

        //when
        counter.printResult("cherry cherry lady");


        //then
        assertThat(actual.toString(), is(equalTo(expected)));
    }

}
