package com.ccortez.desafiohurbapplication.wrapper;

import com.ccortez.desafiohurbapplication.service.model.Suggestion;

import java.util.List;

public class SuggestionDataWrapper {

    private List<Suggestion> suggestions;

    public List<Suggestion> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }
}
