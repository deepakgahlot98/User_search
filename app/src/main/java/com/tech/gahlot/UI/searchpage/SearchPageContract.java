package com.tech.gahlot.UI.searchpage;

import com.tech.gahlot.UI.adapter.BaseListItem;
import com.tech.gahlot.network.user.User;

import java.util.List;

import io.reactivex.Observable;

public interface SearchPageContract {

    interface Data {

        Observable<User> searchUsersByNameSortedByFollowersInDescendingOrder(String query);

    }

    interface View {

        void showLoadingList();

        void showEmptyList();

        void showBlankList();

        void showErrorList(String message);

        void showUserList(List<BaseListItem> users);
    }

    interface Presenter {

        void onOpenPage();

        void onClosePage();

        void onTypeText(String searchQuery);

        void onClickRetryButtonDuringError();
    }
}
