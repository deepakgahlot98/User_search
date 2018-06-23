package com.tech.gahlot.UI.searchpage;

public class SearchPageInjector {

    private static SearchPageContract.Data sData;

    private SearchPageInjector() {
    }

    public static SearchPageContract.Presenter getPresenter(SearchPageContract.View view, SearchPageContract.Data data) {
        return new SearchPagePresenter(view, data);
    }

    public static SearchPageContract.Data getData() {
        if (sData == null) {
            sData = new SearchPageData();
        }
        return sData;
    }

}

