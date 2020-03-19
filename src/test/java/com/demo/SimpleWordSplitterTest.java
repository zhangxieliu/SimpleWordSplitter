package com.demo;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

public class SimpleWordSplitterTest {

    @Test
    public void parsingWord1() {
        List<List<String>> result = SimpleWordSplitter.parsingWord("ilikesamsungmobile");
        for (List<String> list : result) {
            System.out.println(list.stream().collect(Collectors.joining(" ")));
        }
    }

    @Test
    public void parsingWord2() {
        List<List<String>> result = SimpleWordSplitter.parsingWord("ilikeicecreamandmango");
        for (List<String> list : result) {
            System.out.println(list.stream().collect(Collectors.joining(" ")));
        }
    }
}