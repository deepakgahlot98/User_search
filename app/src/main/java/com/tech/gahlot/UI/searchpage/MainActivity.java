package com.tech.gahlot.UI.searchpage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListAdapter;

import com.tech.gahlot.UI.adapter.BaseListItem;
import com.tech.gahlot.UI.adapter.CustomListAdapter;
import com.tech.gahlot.UI.adapter.error.ErrorListItem;
import com.tech.gahlot.githubuser_search.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchPageContract.View {

    private SearchPageContract.Presenter mPresenter;
    private CustomListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = SearchPageInjector.getPresenter(this, SearchPageInjector.getData());

        // Customize toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.lightTextColor));
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        // Setup Recyclerview
        mListAdapter = new CustomListAdapter(new ArrayList<BaseListItem>());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter((RecyclerView.Adapter) mListAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // Searchbox
        EditText searchBox = (EditText) findViewById(R.id.search_box);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPresenter.onTypeText(s.toString());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onOpenPage();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onClosePage();
    }

    @Override
    public void showLoadingList() {
        mListAdapter.showLoadingView();
    }

    @Override
    public void showEmptyList() {
        mListAdapter.showEmptyView();
    }

    @Override
    public void showBlankList() {
        mListAdapter.showBlankView();
    }

    @Override
    public void showErrorList(String message) {
        mListAdapter.showErrorView(message, new ErrorListItem.RetryButtonListener() {
            @Override
            public void onClickRetryButton() {
                mPresenter.onClickRetryButtonDuringError();
            }
        });
    }

    @Override
    public void showUserList(List<BaseListItem> users) {
        mListAdapter.setData(users);
    }
}

