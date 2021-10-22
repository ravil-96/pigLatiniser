package com.example.pigLatiniser.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Phrase {
    private @Id @GeneratedValue Integer id;
    private String originalPhrase;
    private String translatedPhrase;

    public Phrase(){}

    public Phrase(String originalPhrase, String translatedPhrase){
        this.originalPhrase = originalPhrase;
        this.translatedPhrase = translatedPhrase;
    }

    public Integer getId() {
        return this.id;
    }

    public String getOriginalPhrase() {
        return originalPhrase;
    }

    public String getTranslatedPhrase() {
        return translatedPhrase;
    }

    public void setOriginalPhrase(String originalPhrase) {
        this.originalPhrase = originalPhrase;
    }

    public void setTranslatedPhrase(String translatedPhrase) {
        this.translatedPhrase = translatedPhrase;
    }


}
