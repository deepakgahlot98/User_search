package com.tech.gahlot.network.UserList;

import com.tech.gahlot.UI.adapter.user.UserListItem;

import java.util.List;

public class UserListResponse {

    public long total_count;

    public boolean incomplete_results;

    public List<UserListItem> items;

}
