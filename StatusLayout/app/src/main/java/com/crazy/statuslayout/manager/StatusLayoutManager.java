package com.crazy.statuslayout.manager;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.view.ViewStub;

/**
 * Description
 *
 * @author weixuefeng@lubangame.com
 * @version 1.0
 * @copyright (c) 2016 Beijing ShenJiangHuDong Technology Co., Ltd. All rights reserved.
 */

public class StatusLayoutManager {
    final Context mContext;
    final int loadingLayoutResId;
    final int contentLayoutResId;
    final ViewStub netWorkErrorVs;
    final int netWorkErrorRetryViewId;
    final ViewStub emptyDataErrorVs;
    final int emptyDataRetryViewId;
    final ViewStub errorVs;
    final int errorRetryViewId;
    final int retryViewId;
    final OnStatusListener mStatusListener;
    final StatusFrameLayout statusFrameLayout;

    public StatusLayoutManager(Builder builder) {
        this.mContext = builder.mContext;
        this.loadingLayoutResId = builder.loadingLayoutResId;
        this.contentLayoutResId = builder.contentLayoutResId;
        this.netWorkErrorRetryViewId = builder.netWorkErrorRetryViewId;
        this.netWorkErrorVs = builder.netWorkErrorVs;
        this.emptyDataErrorVs = builder.emptyDataErrorVs;
        this.emptyDataRetryViewId = builder.emptyDataRetryViewId;
        this.errorRetryViewId = builder.errorRetryViewId;
        this.errorVs = builder.errorVs;
        this.retryViewId = builder.retryViewId;
        this.mStatusListener = builder.onStatusListener;
        statusFrameLayout = new StatusFrameLayout(mContext);
        statusFrameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        statusFrameLayout.setStatusLayoutManager(this);
    }

    public static Builder newBuilder(Context context){
        return new Builder(context);
    }

    public void showLoaddingView(){
        statusFrameLayout.showLoadding();
    }

    public void showContentView(){
        statusFrameLayout.showContent();
    }

    public void showErrorView(){
        statusFrameLayout.showError();
    }

    public void showEmptyView(){
        statusFrameLayout.showEmptyView();
    }

    public void showNetWorkErrorView(){
        statusFrameLayout.showNetWorkError();
    }

    public StatusFrameLayout getStatusFrameLayout(){
        return statusFrameLayout;
    }


    public static final class Builder {
        private Context mContext;
        private int loadingLayoutResId;
        private int contentLayoutResId;
        private ViewStub netWorkErrorVs;
        private int netWorkErrorRetryViewId;
        private ViewStub emptyDataErrorVs;
        private int emptyDataRetryViewId;
        private ViewStub errorVs;
        private int errorRetryViewId;
        private int retryViewId;
        private OnStatusListener onStatusListener;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder loadingView(@LayoutRes int loadingLayoutResId){
            this.loadingLayoutResId = loadingLayoutResId;
            return this;
        }

        public Builder netWorkErrorView(@LayoutRes int netWorkErrorViewRes){
            netWorkErrorVs = new ViewStub(mContext,netWorkErrorViewRes);
            return this;
        }

        public Builder emptyDataView(@LayoutRes int emptyDataViewRes){
            emptyDataErrorVs = new ViewStub(mContext,emptyDataViewRes);
            return this;
        }

        public Builder errorView(@LayoutRes int errorViewRes){
            errorVs = new ViewStub(mContext,errorViewRes);
            return this;
        }

        public Builder contentView(@LayoutRes int contentLayoutResId){
            this.contentLayoutResId = contentLayoutResId;
            return this;
        }

        public Builder netWorkErrorRetryViewId(int netWorkErrorRetryViewId){
            this.netWorkErrorRetryViewId = netWorkErrorRetryViewId;
            return this;
        }

        public Builder emptyDataRetryViewId(int emptyDataRetryViewId){
            this.emptyDataRetryViewId = emptyDataRetryViewId;
            return this;
        }

        public Builder retryViewId(int retryViewId){
            this.retryViewId = retryViewId;
            return this;
        }

        public Builder errorRetryViewId(int errorRetryViewId){
            this.errorRetryViewId = errorRetryViewId;
            return this;
        }

        public Builder onStatusListener(OnStatusListener onStatusListener){
            this.onStatusListener = onStatusListener;
            return this;
        }
        public StatusLayoutManager build() {
            return new StatusLayoutManager(this);
        }
    }
}
