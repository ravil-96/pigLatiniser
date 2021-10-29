package com.example.pigLatiniser;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class PhraseNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(PhraseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String phraseNotFoundHandler(PhraseNotFoundException ex){
        return ex.getMessage();
    }
}
