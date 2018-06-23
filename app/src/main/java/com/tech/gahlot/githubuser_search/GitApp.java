package com.tech.gahlot.githubuser_search;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class GitApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            com.facebook.stetho.Stetho.initializeWithDefaults(this);
        }

        if (TextUtils.isEmpty(GitHubCredentials.PERSONAL_ACCESS_TOKEN) || TextUtils.isEmpty(GitHubCredentials.USERNAME)) {
            Toast.makeText(this, "Please specify your Username and Personal Access Token to use app without rate limits", Toast.LENGTH_LONG).show();
            Log.e("GIT 'EM", "Please specify your Username and Personal Access Token to use app without rate limits");
        }
    }
}
