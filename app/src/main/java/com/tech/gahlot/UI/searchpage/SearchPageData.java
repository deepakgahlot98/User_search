package com.tech.gahlot.UI.searchpage;

import com.tech.gahlot.network.ApiHandler;
import com.tech.gahlot.network.user.User;
import com.tech.gahlot.network.user_list.UserListItem;
import com.tech.gahlot.network.user_list.UserListResponse;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Created on 16/04/18.
 */

public class SearchPageData implements SearchPageContract.Data {

    private static final String QUERY_SEARCH_IN_FULL_NAME = "%s in:fullname";
    private static final String ORDER_BY_DESC = "desc";
    private static final String SORT_BY_FOLLOWERS = "followers";

    public SearchPageData() {
    }

    @Override
    public Observable<User> searchUsersByNameSortedByFollowersInDescendingOrder(String query) {
        return ApiHandler.getInstance().getGithubService()
                .searchUsers(String.format(QUERY_SEARCH_IN_FULL_NAME, query), SORT_BY_FOLLOWERS, ORDER_BY_DESC)
                .map(new Function<UserListResponse, List<UserListItem>>() {
                    @Override
                    public List<UserListItem> apply(UserListResponse userListResponse) throws Exception {
                        return userListResponse.items;
                    }
                })
                .flatMap(new Function<List<UserListItem>, ObservableSource<UserListItem>>() {
                    @Override
                    public ObservableSource<UserListItem> apply(List<UserListItem> userListItems) throws Exception {
                        return Observable.fromIterable(userListItems);
                    }
                })
                .flatMap(new Function<UserListItem, ObservableSource<User>>() {
                    @Override
                    public ObservableSource<User> apply(UserListItem userListItem) throws Exception {
                        return ApiHandler.getInstance().getGithubService().getUser(userListItem.login);
                    }
                });
    }
}