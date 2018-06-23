package com.tech.gahlot.UI.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tech.gahlot.UI.adapter.empty.EmptyListItem;
import com.tech.gahlot.UI.adapter.empty.EmptyViewHolder;
import com.tech.gahlot.UI.adapter.error.ErrorListItem;
import com.tech.gahlot.UI.adapter.error.ErrorViewHolder;
import com.tech.gahlot.UI.adapter.loading.LoadingListItem;
import com.tech.gahlot.UI.adapter.loading.LoadingViewHolder;
import com.tech.gahlot.UI.adapter.user_item.UserListItem;
import com.tech.gahlot.UI.adapter.user_item.UserViewHolder;
import com.tech.gahlot.Utils.BindingUtils;
import com.tech.gahlot.Utils.OpenIntentUtils;
import com.tech.gahlot.githubuser_search.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomListAdapter extends RecyclerView.Adapter {

    private List<BaseListItem> data;

    public CustomListAdapter(List<BaseListItem> data) {
        if (data == null) {
            throw new IllegalArgumentException("Invalid");
        }
        this.data = data;
        setHasStableIds(true);
    }

    public void setData(List<BaseListItem> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void showLoadingView() {
        List<BaseListItem> items = new ArrayList<>();
        items.add(new LoadingListItem());
        setData(items);
    }

    public void showEmptyView() {
        List<BaseListItem> items = new ArrayList<>();
        items.add(new EmptyListItem());
        setData(items);
    }

    public void showErrorView(String message, ErrorListItem.RetryButtonListener retryButtonListener) {
        List<BaseListItem> items = new ArrayList<>();
        items.add(new ErrorListItem(message, retryButtonListener));
        setData(items);
    }

    public void showBlankView() {
        setData(Collections.<BaseListItem>emptyList());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);

        switch (viewType) {
            case R.layout.list_item_user:
                return new UserViewHolder(viewItem);

            case R.layout.list_item_loading:
                return new LoadingViewHolder(viewItem);

            case R.layout.list_item_empty:
                return new EmptyViewHolder(viewItem);

            case R.layout.list_item_error:
                return new ErrorViewHolder(viewItem);

            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case R.layout.list_item_user:
                UserViewHolder userViewHolder = (UserViewHolder) holder;
                final UserListItem userListItem = (UserListItem) data.get(position);
                BindingUtils.BindText(userViewHolder.getName(), userListItem.getFullName());
                BindingUtils.BindText(userViewHolder.getUsername(), userListItem.getUsername());
                BindingUtils.BindText(userViewHolder.getFollowers(), String.valueOf(userListItem.getNoOfFollowers()));
                BindingUtils.BindImage(userViewHolder.getAvatar(), userListItem.getAvatarUrl(), 0);

                if (!TextUtils.isEmpty(userListItem.getProfileUrl())) {
                    userViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            OpenIntentUtils.openUrl(holder.itemView.getContext(), userListItem.getProfileUrl());
                        }
                    });
                }
                break;


            case R.layout.list_item_empty:
            case R.layout.list_item_loading:
                // nothing to bind
                break;

            case R.layout.list_item_error:
                ErrorViewHolder errorViewHolder = (ErrorViewHolder) holder;
                final ErrorListItem errorListItem = (ErrorListItem) data.get(position);
                BindingUtils.BindText(errorViewHolder.getErrorText(), errorListItem.getErrorText());
                if (errorListItem.getRetryButtonListener() == null) {
                    errorViewHolder.getRetryButton().setVisibility(View.GONE);
                } else {
                    errorViewHolder.getRetryButton().setVisibility(View.VISIBLE);
                    errorViewHolder.getRetryButton().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            errorListItem.getRetryButtonListener().onClickRetryButton();
                        }
                    });
                }
                break;

            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public int getItemViewType(int position) {
        if (data == null) {
            return 0;
        }

        BaseListItem item = data.get(position);
        if (item instanceof UserListItem) {
            return R.layout.list_item_user;
        } else if (item instanceof LoadingListItem) {
            return R.layout.list_item_loading;
        } else if (item instanceof EmptyListItem) {
            return R.layout.list_item_empty;
        } else if (item instanceof ErrorListItem) {
            return R.layout.list_item_error;

        } else {
            return 0;
        }
    }

}
