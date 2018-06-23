package com.tech.gahlot.network;

import com.tech.gahlot.network.user_list.UserListResponse;
import com.tech.gahlot.network.user.User;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubManager {

    @GET("search/users")
    Observable<UserListResponse> searchUsers(@Query("q") String query, @Query("sort") String sortBy, @Query("order") String orderBy);

    @GET("users/{username}")
    Observable<User> getUser(@Path("username") String username);
}
