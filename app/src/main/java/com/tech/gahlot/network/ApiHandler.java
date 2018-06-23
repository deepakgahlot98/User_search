package com.tech.gahlot.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class ApiHandler {

    private static final String URL = "https://api.github.com";

    private static ApiHandler apiHandler = new ApiHandler();

    private GitHubManager mGithubManager;

    private ApiHandler() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .client(
                        OKHttpClientFactory.getInstance())
                .addConverterFactory(
                        MoshiConverterFactory.create(
                                MoshiFactory.getInstance()))
                .addCallAdapterFactory(
                        RxJava2CallAdapterFactory.create())
                .build();

        mGithubManager = retrofit.create(GitHubManager.class);
    }

    public GitHubManager getGithubService() {
        return mGithubManager;
    }

    public static ApiHandler getInstance() {
        return apiHandler;
    }
}
