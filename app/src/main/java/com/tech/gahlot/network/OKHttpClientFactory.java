package com.tech.gahlot.network;

import android.text.TextUtils;
import android.util.Base64;

import com.tech.gahlot.githubuser_search.BuildConfig;
import com.tech.gahlot.githubuser_search.GitHubCredentials;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OKHttpClientFactory {
    private static final Object lock = new Object();
    private static OkHttpClient sClient;

    private OKHttpClientFactory() {
    }

    public static OkHttpClient getInstance() {
        if (sClient == null) {
            synchronized (lock) {
                if (sClient == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder()
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(10, TimeUnit.SECONDS)
                            .addNetworkInterceptor(new Interceptor() { // Add Interceptor to add necessary Headers
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    Request.Builder reqBuilder = chain.request().newBuilder();
                                    reqBuilder.addHeader("Accept", "application/vnd.github.v3+json");
                                    if (!TextUtils.isEmpty(GitHubCredentials.USERNAME) && !TextUtils.isEmpty(GitHubCredentials.PERSONAL_ACCESS_TOKEN)) {
                                        reqBuilder.addHeader("Authorization", "Basic " +
                                                Base64.encodeToString(String.format("%s:%s", GitHubCredentials.USERNAME, GitHubCredentials.PERSONAL_ACCESS_TOKEN).getBytes(), Base64.NO_WRAP));
                                    }
                                    return chain.proceed(reqBuilder.build());
                                }
                            });

                    if (BuildConfig.DEBUG) {
                        builder.addNetworkInterceptor(new com.facebook.stetho.okhttp3.StethoInterceptor()); // Add Stetho Interceptor for easy debugging
                    }

                    sClient = builder.build();
                }
            }
        }
        return sClient;
    }
}
