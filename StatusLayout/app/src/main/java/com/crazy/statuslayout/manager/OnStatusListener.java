package com.crazy.statuslayout.manager;

import android.view.View;

/**
 * Description
 *
 * @author weixuefeng@lubangame.com
 * @version 1.0
 * @copyright (c) 2016 Beijing ShenJiangHuDong Technology Co., Ltd. All rights reserved.
 */

public interface OnStatusListener {
    void showView(View view,int statusId);
    void hideView(View view,int statusId);
    void onRetry();
}
