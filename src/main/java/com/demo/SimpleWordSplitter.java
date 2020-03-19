package com.demo;

import java.util.*;
import java.util.stream.Collectors;

public class SimpleWordSplitter {
    /**
     * Used to save the list of words contained in an initial word
     * key: The first letter of a word
     * value: The first letter of a word contains all the words
     */
    private static Map<Character, Set<String>> firstLetterWordMap = new HashMap<>();
    /**
     * The longest word length used to hold the first letter of a word
     * key: The first letter of a word
     * value: The maximum length of an initial word
     */
    private static Map<Character, Integer> firstLetterWordMaxLength = new HashMap<>();

    static {
        String dictionary = "i,like,sam,sung,samsung,mobile,ice,cream,man go";
        String[] split = dictionary.split("[,| ]");
        for (String word : split) {
            String lowerCaseWord = word.toLowerCase();
            char key = lowerCaseWord.charAt(0);
            int length = word.length();
            if (firstLetterWordMap.containsKey(key)) {
                firstLetterWordMap.get(key).add(lowerCaseWord);
                if (firstLetterWordMaxLength.get(key) < length) {
                    firstLetterWordMaxLength.put(key, length);
                }
            } else {
                Set<String> list = new HashSet<>();
                list.add(lowerCaseWord);
                firstLetterWordMap.put(key, list);
                firstLetterWordMaxLength.put(key, length);
            }
        }
    }

    public static void main(String[] args) {
        String test1 = "ilikesamsungmobile";
        String test2 = "ilikeicecreamandmango";
        List<List<String>> result = parsingWord(test1);
        for (List<String> l : result) {
            System.out.println(l.stream().collect(Collectors.joining(" ")));
        }
    }

    /**
     * Parse the word string into a sentence
     * @param wordStr
     * @return
     */
    public static List<List<String>> parsingWord(String wordStr) {
        List<List<String>> result = new ArrayList<>();
        enter(0, false, result, wordStr);
        return result;
    }

    /**
     * Parse string
     * @param i Cursor position currently being traversed
     * @param isPreUnknownFirstLetter
     * @param result
     * @param testString
     */
    private static void enter(int i,
                              boolean isPreUnknownFirstLetter,
                              List<List<String>> result,
                              String testString) {
        // Record the words that have been parsed
        List<String> resolvedWordList;
        if (i == 0) {
            // Do the initialization
            resolvedWordList = new ArrayList<>();
            result.add(resolvedWordList);
        } else {
            // Gets the last parsed word
            resolvedWordList = result.get(result.size() - 1);
        }
        int length = testString.length();// Total string length
        if (i < length) {
            char key = testString.toLowerCase().charAt(i);
            if (!firstLetterWordMap.containsKey(key)) {
                // Words that do not contain the beginning of the letter
                if (isPreUnknownFirstLetter) {
                    resolvedWordList.set(resolvedWordList.size() - 1, resolvedWordList.get(resolvedWordList.size() - 1) + key);
                } else {
                    resolvedWordList.add(String.valueOf(key));
                }
                enter(i + 1, true, result, testString);
            } else {
                Set<String> works = firstLetterWordMap.get(key);// Gets a list of all the words with the first letter
                Integer wordMaxLen = Math.min(length, firstLetterWordMaxLength.get(key) + i + 1);
                boolean isFirst = true;
                ArrayList<String> temp = new ArrayList<>();
                temp.addAll(resolvedWordList);
                for (int j = i + 1; j <= wordMaxLen; j++) {
                    String str = testString.substring(i, j);
                    if (works.contains(str.toLowerCase())) {
                        if (j > i + 1 && !isFirst) {
                            resolvedWordList = temp;
                            result.add(resolvedWordList);
                        }
                        resolvedWordList.add(str);
                        enter(j, false, result, testString);
                        isFirst = false;
                    }
                }
                if (isFirst) {
                    result.remove(result.size() - 1);
                }
            }
        }
    }
}