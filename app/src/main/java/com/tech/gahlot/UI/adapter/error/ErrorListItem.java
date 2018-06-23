package com.tech.gahlot.UI.adapter.error;

import com.tech.gahlot.UI.adapter.BaseListItem;

public class ErrorListItem extends BaseListItem {

    private String errorText;
    private RetryButtonListener retryButtonListener;

    public ErrorListItem(String errorText, RetryButtonListener retryButtonListener) {
        super(-3);
        this.errorText = errorText;
        this.retryButtonListener = retryButtonListener;
    }

    public String getErrorText() {
        return errorText;
    }

    public RetryButtonListener getRetryButtonListener() {
        return retryButtonListener;
    }

    public interface RetryButtonListener {
        void onClickRetryButton();
    }
}

