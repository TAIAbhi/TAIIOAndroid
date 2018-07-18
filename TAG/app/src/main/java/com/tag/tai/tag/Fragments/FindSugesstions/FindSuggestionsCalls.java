package com.tag.tai.tag.Fragments.FindSugesstions;

/**
 * Created by Jam on 05-04-2018.
 */

public interface FindSuggestionsCalls {

    void onEditSuggestionClicked(String suggestionId,int isCopyOrEdit);

    void onEndOfRecycler();

    void onAddRequestClicked(String requestedSuggestionsId);

    void onDeleteSuggestionClicked(String suggestionId,int position);
}
