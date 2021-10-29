package com.example.pigLatiniser;

public class PhraseNotFoundException extends RuntimeException {
    public PhraseNotFoundException(Integer id) {
        super("Could not find phrase " + id);
    }
}
