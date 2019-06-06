package com.parachute.data;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.greendao.manager.TaskEntityDao;
import com.parachute.administrator.DATAbase.R;
import com.parachute.administrator.DATAbase.greendao.TaskEntity;
import com.parachute.app.MyApp;
import com.parachute.test.fragment.DataFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DataDetailActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.root)
    FrameLayout root;
    private DataFragment mdatafragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //保持屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datadetail);
        ButterKnife.bind(this);
        FrameLayout root = (FrameLayout) findViewById(R.id.root);

        initData();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //决定左上角的图标是否可以点击
        mdatafragment = new DataFragment();
        mdatafragment.setDataManage(true);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        mdatafragment.GetData();
        transaction.add(R.id.root, mdatafragment);
        transaction.commit();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    protected void initData() {

        Intent intent = getIntent();
        Long taskID = intent.getLongExtra("TASKID", 1L);
        List<TaskEntity> mlist;
        try {
            mlist = MyApp.getDaoInstant().getTaskEntityDao().queryBuilder().where(TaskEntityDao.Properties.Id.eq(taskID)).list();
            if (mlist != null) {
                MyApp.setTaskEnity(mlist.get(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
