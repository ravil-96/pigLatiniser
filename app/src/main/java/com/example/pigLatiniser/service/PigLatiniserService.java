package com.example.pigLatiniser.service;

import com.example.pigLatiniser.domain.Phrase;
import com.example.pigLatiniser.repository.PigLatiniserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Service
public class PigLatiniserService {
    @Autowired
    private PigLatiniserRepository pigLatiniserRepository;

    public static Phrase translate(Phrase phrase) {
        String originalPhrase = phrase.getOriginalPhrase();
        List<String> splitPhrase = Arrays.stream(originalPhrase.toLowerCase().split(" ")).collect(Collectors.toList());

        StringJoiner phraseJoiner = new StringJoiner(" ");
        for (String s : splitPhrase) {
            phraseJoiner.add(pigLatiniser(s));
        }

        phrase.setTranslatedPhrase(phraseJoiner.toString());
        return phrase;
    }

    public static String pigLatiniser(String word) {

        String[] safeWords = new String[]{"on", "the", "and", "a", "an"};
        if (Arrays.asList(safeWords).contains(word)) {
            return word;
        }

        String safeVowels = "aeiou";
        int splitIdx = 0;
        for (int i = 0; i < word.length(); i++) {
            if (safeVowels.contains(String.valueOf(word.charAt(i)))) {
                splitIdx = i;
                break;
            }
        }
        String beginning = word.substring(0, splitIdx);
        String end = word.substring(splitIdx);
        return end + beginning + "ay";
    }

//    public String translatePhrase(String phrase) {
//        return PigLatiniserService.translate(phrase);
//    }
}

