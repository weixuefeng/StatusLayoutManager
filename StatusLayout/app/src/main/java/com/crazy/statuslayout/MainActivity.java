package com.crazy.statuslayout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.crazy.statuslayout.manager.OnStatusListener;
import com.crazy.statuslayout.manager.StatusLayoutManager;

public class MainActivity extends AppCompatActivity {

    protected StatusLayoutManager statusLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolBar();

        LinearLayout mainLinearLayout = (LinearLayout) findViewById(R.id.main_rl);
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.activity_content)
                .emptyDataView(R.layout.activity_emptydata)
                .errorView(R.layout.activity_error)
                .loadingView(R.layout.activity_loading)
                .netWorkErrorView(R.layout.activity_networkerror)
                .retryViewId(R.id.button_try)
                .onStatusListener(new OnStatusListener() {
                    @Override
                    public void showView(View view, int statusId) {

                    }

                    @Override
                    public void hideView(View view, int statusId) {

                    }

                    @Override
                    public void onRetry() {
                        statusLayoutManager.showLoaddingView();
                        new Thread(){
                            @Override
                            public void run() {
                                try {
                                    sleep(2000);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            statusLayoutManager.showContentView();
                                        }
                                    });
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                    }
                }).build();

        mainLinearLayout.addView(statusLayoutManager.getStatusFrameLayout(), 1);

        statusLayoutManager.showLoaddingView();
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_bar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("StatusLayout");
        toolbar.inflateMenu(R.menu.base_toolbar_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.action_neirong) {
                    statusLayoutManager.showContentView();
                }
                if(item.getItemId() == R.id.action_emptyData) {
                    statusLayoutManager.showEmptyView();
                }
                if(item.getItemId() == R.id.action_error) {
                    statusLayoutManager.showErrorView();
                }
                if(item.getItemId() == R.id.action_networkError) {
                    statusLayoutManager.showNetWorkErrorView();
                }
                if(item.getItemId() == R.id.action_loading) {
                    statusLayoutManager.showLoaddingView();
                }
                return true;
            }
        });
    }
}
