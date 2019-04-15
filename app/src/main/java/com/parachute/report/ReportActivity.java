package com.parachute.report;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.parachute.Adapter.MyFragmentPageAadpter;
import com.parachute.administrator.BaseActivity;
import com.parachute.administrator.DATAbase.R;
import com.xzkydz.function.ftp.ftptwo.FTPServerService;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReportActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.vp_show_data)
    ViewPager vpShowData;
    @BindView(R.id.rb_group)
    RadioButton rbGroup;
    @BindView(R.id.rb_data)
    RadioButton rbData;
    @BindView(R.id.rg_select)
    RadioGroup rgSelect;
    @BindView(R.id.content_report)
    RelativeLayout contentReport;

    private TransFragment transFragment;
    private ReportListFragment listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //决定左上角的图标是否可以点击
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        initEvent();
    }

    /**
     * function ：事件
     */
    private void initEvent() {
        //toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //决定左上角的图标是否可以点击
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //ViewPage
        FragmentManager fManager = getSupportFragmentManager();
        ArrayList<Fragment> fragmentsList = new ArrayList<Fragment>();

        transFragment = new TransFragment();
        listFragment = new ReportListFragment();

        fragmentsList.add(listFragment);
        fragmentsList.add(transFragment);
        MyFragmentPageAadpter mAdapter = new MyFragmentPageAadpter(fManager, fragmentsList);
        vpShowData.setAdapter(mAdapter);
        vpShowData.setOnPageChangeListener(new MyOnPageChangeListener());
        rgSelect.setOnCheckedChangeListener(new MyStatusChang());
    }

    /**
     * function ：设置指定跳转到某一页,解决隔页跳转闪烁问题
     */
    private void setViewPager(int i, ViewPager viewPager) {
        // 先强制设置到指定页面  
        try {
            Field field = viewPager.getClass().getField("mCurItem");
            field.setAccessible(true);
            field.setInt(viewPager, i);
        } catch (Exception e) {
            e.printStackTrace();
            ;
        }
        viewPager.setCurrentItem(i);
    }


    class MyStatusChang implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (i) {
                case R.id.rb_group:
                    setViewPager(0, vpShowData);
                    break;
                case R.id.rb_data:
                    setViewPager(1, vpShowData);
                    break;
//                case R.id.rb_temporary:
//                    setViewPager(2, vpShowData);
//                    break;
            }
        }
    }


    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        /**
         * 当滑动状态改变时调用
         */
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        /**
         * 当当前页面被滑动时调用
         */

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        /**
         * 当新的页面被选中时调用
         */
        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    toolbar.setTitle("查询");
                    rgSelect.check(R.id.rb_group);
                    break;
                case 1:
                    toolbar.setTitle("传输");
                    rgSelect.check(R.id.rb_data);
                    break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //如果未得到IP地址，结束服务
            if (FTPServerService.isRunning()) {
                Intent intent = new Intent(ReportActivity.this, FTPServerService.class);
                ReportActivity.this.stopService(intent);
            }
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}
