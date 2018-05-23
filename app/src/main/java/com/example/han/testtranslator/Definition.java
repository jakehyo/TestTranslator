package com.example.han.testtranslator;

/**
 * Created by per6 on 5/23/18.
 */

public class Definition {
    private String definition;
    private String partOfSpeech;

    public Definition() {
        definition = null;
        partOfSpeech = null;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }
}
