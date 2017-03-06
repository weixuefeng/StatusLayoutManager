package com.crazy.statuslayout.manager;

import android.content.Context;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import static android.content.ContentValues.TAG;

/**
 * Description
 *
 * @author weixuefeng@lubangame.com
 * @version 1.0
 * @copyright (c) 2016 Beijing ShenJiangHuDong Technology Co., Ltd. All rights reserved.
 */

public class StatusFrameLayout extends FrameLayout {

    /**
     * 状态码：加载中...
     */
    public static final int STATUS_LOADING = 1;
    /**
     * 状态码：加载成功
     */
    public static final int STATUS_SUCCESSED = 2;
    /**
     * 状态码：加载异常
     */
    public static final int STATUS_ERROR = 3;
    /**
     * 状态码：网络异常
     */
    public static final int STATUS_NETWORK_ERROR = 4;
    /**
     * 状态码：空数据
     */
    public static final int STATUS_EMPTY_DATA = 5;
    /**
     * 存放布局集合
     */
    private SparseArray<View> layoutSparseArray = new SparseArray<>();
    /**
     * 布局管理器
     */
    private StatusLayoutManager mStatusLayoutManager;

    public StatusFrameLayout(@NonNull Context context) {
        super(context);
    }

    public StatusFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StatusFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public StatusFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setStatusLayoutManager(StatusLayoutManager statusLayoutManager) {
        this.mStatusLayoutManager = statusLayoutManager;
        addAllLayout();
    }

    private void addAllLayout() {
        if (0 != mStatusLayoutManager.loadingLayoutResId)
            addViewByLayoutRes(mStatusLayoutManager.loadingLayoutResId, STATUS_LOADING);
        if (0 != mStatusLayoutManager.contentLayoutResId)
            addViewByLayoutRes(mStatusLayoutManager.contentLayoutResId, STATUS_SUCCESSED);
        if (null != mStatusLayoutManager.errorVs)
            addView(mStatusLayoutManager.errorVs);
        if (null != mStatusLayoutManager.emptyDataErrorVs)
            addView(mStatusLayoutManager.emptyDataErrorVs);
        if (null != mStatusLayoutManager.netWorkErrorVs)
            addView(mStatusLayoutManager.netWorkErrorVs);
    }

    private void addViewByLayoutRes(@LayoutRes int layoutResId, int statusId) {
        View resView = LayoutInflater.from(mStatusLayoutManager.mContext)
                .inflate(layoutResId, null);
        layoutSparseArray.put(statusId, resView);
        Log.e(TAG, "addViewByLayoutRes: " + statusId );
        addView(resView);
    }

    public void showLoadding(){
        if (null != layoutSparseArray.get(STATUS_LOADING)){
            showStatusViewById(STATUS_LOADING);
        }
    }

    public void showContent(){
        if (null != layoutSparseArray.get(STATUS_SUCCESSED)){
            showStatusViewById(STATUS_SUCCESSED);
        }
    }

    public void showNetWorkError(){
        if (inflateLayout(STATUS_NETWORK_ERROR)){
            showStatusViewById(STATUS_NETWORK_ERROR);
        }
    }

    public void showError(){
        if (inflateLayout(STATUS_ERROR)){
            showStatusViewById(STATUS_ERROR);
        }
    }

    public void showEmptyView(){
        if (inflateLayout(STATUS_EMPTY_DATA)){
            showStatusViewById(STATUS_EMPTY_DATA);
        }
    }

    /**
     * show status View by current statusId.
     * eg:if current status is loadding,you should call this method with STATUS_LOADING.
     *
     * @param statusId
     */
    private void showStatusViewById(int statusId) {
        for (int i = 0; i < layoutSparseArray.size(); i++) {
            int k = layoutSparseArray.keyAt(i);
            Log.e(TAG, "showStatusViewById: statusId:" + statusId + "\ncurrent k : " + k  );
            View v = layoutSparseArray.valueAt(k);
            if (null != v) {
                if (statusId == k) {
                    v.setVisibility(VISIBLE);
                    if (null != mStatusLayoutManager.mStatusListener)
                        mStatusLayoutManager.mStatusListener.showView(v, statusId);
                } else {
                    if (v.getVisibility() != GONE) {
                        v.setVisibility(GONE);
                        mStatusLayoutManager.mStatusListener.hideView(v, statusId);
                    }
                }
            }
        }
    }

    private boolean inflateLayout(int statusId) {
        boolean isShow = true;
        if (null != layoutSparseArray.get(statusId)) return isShow;
        switch (statusId) {
            case STATUS_EMPTY_DATA:
                if (null != mStatusLayoutManager.emptyDataErrorVs) {
                    View view = mStatusLayoutManager.emptyDataErrorVs.inflate();
                    loadRetryView(view,mStatusLayoutManager.emptyDataRetryViewId);
                    layoutSparseArray.put(statusId,view);
                    isShow = true;
                }else{
                    isShow = false;
                }
                break;
            case STATUS_NETWORK_ERROR:
                if (null != mStatusLayoutManager.netWorkErrorVs) {
                    View view = mStatusLayoutManager.netWorkErrorVs.inflate();
                    loadRetryView(view,mStatusLayoutManager.netWorkErrorRetryViewId);
                    layoutSparseArray.put(statusId,view);
                    isShow = true;
                }else{
                    isShow = false;
                }
                break;
            case STATUS_ERROR:
                if (null != mStatusLayoutManager.errorVs) {
                    View view = mStatusLayoutManager.errorVs.inflate();
                    loadRetryView(view,mStatusLayoutManager.errorRetryViewId);
                    layoutSparseArray.put(statusId,view);
                    isShow = true;
                }else{
                    isShow = false;
                }
                break;
            default:
                break;
        }
        return isShow;
    }

    private void loadRetryView(View view,int id){
        View retryView = view.findViewById(mStatusLayoutManager.retryViewId == 0 ? id : mStatusLayoutManager.retryViewId);
        if (null == retryView || null == mStatusLayoutManager.mStatusListener) return;
        retryView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mStatusLayoutManager.mStatusListener.onRetry();
            }
        });
    }
}
