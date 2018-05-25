package com.example.han.testtranslator;

import java.util.List;

/**
 * Created by per6 on 5/23/18.
 */

public class DefinitionList {
    private String word;
    private List<Definition> definitions;

    public DefinitionList() {
        word = null;
        definitions = null;
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<Definition> definitions) {
        this.definitions = definitions;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "DefinitionList{" +
                "word='" + word + '\'' +
                ", definitions=" + definitions +
                '}';
    }
}
