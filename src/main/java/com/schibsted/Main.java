package com.schibsted;

import com.schibsted.output.Counter;
import com.schibsted.search.SearchEngine;

import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("No directory given to index");
        }
        String path = args[0];
        Map<String, String> wordsDepot = new SearchEngine(path).search();
        Counter counter = new Counter(wordsDepot);
        runInteractiveConsole(counter);
    }

    private static void runInteractiveConsole(Counter counter) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("search> ");
            String nextLine = scanner.nextLine();
            if (nextLine.equals(":quit")) {
                break;
            } else {
                counter.printResult(nextLine);
            }
        }
    }
}
