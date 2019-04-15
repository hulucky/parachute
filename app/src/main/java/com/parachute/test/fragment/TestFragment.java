package com.parachute.test.fragment;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.parachute.Adapter.TestShowPagerAdapter;
import com.parachute.administrator.DATAbase.R;
import com.parachute.administrator.DATAbase.greendao.TaskEntity;
import com.parachute.app.MyApp;
import com.parachute.test.TestActivity;
import com.parachute.test.fragment.childfragment.TestJsdfragment;
import com.parachute.test.fragment.childfragment.TestSensorfragment;
import com.parachute.test.fragment.childfragment.TestJfhfragment;
import com.parachute.test.fragment.childfragment.TestWyfragment;


import com.parachute.view.MyNoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;

import static com.parachute.app.MyApp.getInstance;

public class TestFragment extends Fragment {

    @BindView(R.id.fr_main)
    MyNoScrollViewPager flmain;
    @BindView(R.id.giv_gif)
    pl.droidsonroids.gif.GifImageView gifImageView;
    @BindView(R.id.rbn_test_tg)
    Button btntg;
    @BindView(R.id.rbn_test_jfh)
    Button btnjfh;
    @BindView(R.id.rbn_test_jsd)
    Button btnjsd;
    @BindView(R.id.rbn_test_wy)
    Button btnwy;

    static CheckBox ckLock;
//    @BindView(R.id.cb_testdj_lock)

    TaskEntity mTask;
    MyApp myApp;
    Unbinder unbinder;
    public TestSensorfragment msensorfragment;
    static TestJfhfragment mjfhfragment;
    static TestJsdfragment mjsdfragment;
    public static TestWyfragment mwyfragment;
    List<Fragment> list_fragments;
    TestActivity mActivity;




    static Button btnTestSave;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list_fragments = new ArrayList<Fragment>();

        //把两个子fragment实例化然后装到集合里
        mTask = myApp.getTaskEnity();
        msensorfragment = new TestSensorfragment();
        list_fragments.add(msensorfragment);//0
        mjfhfragment = new TestJfhfragment();
        list_fragments.add(mjfhfragment);//1
        mjsdfragment = new TestJsdfragment();
        list_fragments.add(mjsdfragment);//2

        mwyfragment = new TestWyfragment();
        list_fragments.add(mwyfragment);//3


    }

    @Override
    public void onResume() {
        super.onResume();
//        gifImageView.play();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, null);
        myApp = getInstance();
        mTask = myApp.getTaskEnity();
        unbinder = ButterKnife.bind(this, view);
        btnTestSave = (Button) view.findViewById(R.id.btn_test_save);

        ckLock = (CheckBox) view.findViewById(R.id.cb_test_start);
        mActivity = (TestActivity) getActivity();
        flmain.setNoScroll(true);
        if (list_fragments != null) {
            TestShowPagerAdapter msgAdapter = new TestShowPagerAdapter(getChildFragmentManager(), list_fragments);
            flmain.setAdapter(msgAdapter);
            flmain.setCurrentItem(0);
        }
        gifImageView.setBackgroundResource(R.drawable.jfhs);

//        loadgif();


        ckLock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ckLock.setText("停止");



                   mActivity.setStart(1);
                    mActivity.StartJsd();
                   if (mActivity.getTg()) {
                        gifImageView.setBackgroundResource(R.drawable.tg);
                        btnjfh.setEnabled(false);
                        ckLock.setEnabled(false);
                        Toasty.info(mActivity,"开始脱钩测试"        ).show();       } else {
                        Toasty.info(mActivity,"开始静负荷测试"        ).show();
                        gifImageView.setBackgroundResource(R.drawable.jfh);
                        btntg.setEnabled(false);
                        btnjsd.setEnabled(false);
                        btnwy.setEnabled(false);
                    }
                } else {
                    ckLock.setText("开始");
                    if(mActivity.isStart()){
                    mActivity.drawrefresh();}
                    mActivity.setStart(2);
                    mActivity.StartJsd();

//                    btnjfh.setEnabled(true);
//                    btntg.setEnabled(true);
//                    btnjsd.setEnabled(true);
//                    btnwy.setEnabled(true);
                    if (mActivity.getTg()) {
                        gifImageView.setBackgroundResource(R.drawable.tge);
                    } else {
                        gifImageView.setBackgroundResource(R.drawable.jfhe);
                    }
                }
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mActivity.initdata();
    }


    @Override
    public void onPause() {
        super.onPause();
//        gifImageView.pause();
    }


    @OnClick({R.id.rbn_test_cgq, R.id.rbn_test_tg, R.id.rbn_test_jfh, R.id.rbn_test_wy, R.id.rbn_test_jsd, R.id.btn_test_save})
    public void onViewClicked(View view) {
        try {
            switch (view.getId()) {

                case R.id.rbn_test_tg:
                    if (mActivity.isStart() && !mActivity.getTg()) {
                        Toasty.info(mActivity,"静负荷测试进行中！"        ).show();
                    }

//                    else if (mActivity.isDown() && !mActivity.getTg()) {
//
//                        Toasty.info(mActivity,"静负荷测试数据未保存！"        ).show();
//                    }
                    else {
                        flmain.setCurrentItem(2, false);
                        btnjsd.setVisibility(View.VISIBLE);
                        btnwy.setVisibility(View.VISIBLE);
                        mActivity.setTg(true);
                        if (!mActivity.isStart()) {
                            gifImageView.setBackgroundResource(R.drawable.tgs);
                        }
                    }
                    break;
                case R.id.rbn_test_cgq:
                    flmain.setCurrentItem(0, false);
                    break;
                case R.id.rbn_test_jfh:
                    if (mActivity.isStart() && mActivity.getTg()) {
                        Toasty.info(mActivity,"脱钩测试进行中！"        ).show();
                    }

//                    else if (mActivity.isDown() && mActivity.getTg()) {
//
//                        Toasty.info(mActivity,"脱钩测试数据未保存！"        ).show();
//                    }
                    else {
                        flmain.setCurrentItem(1, false);
                        btnjsd.setVisibility(View.GONE);
                        btnwy.setVisibility(View.GONE);
                        mActivity.setTg(false);
                        if (!mActivity.isStart()) {
                            gifImageView.setBackgroundResource(R.drawable.jfhs);
                        }
                    }
                    break;
                case R.id.rbn_test_wy:
                    if (mActivity.isStart() && !mActivity.getTg()) {
                        Toasty.info(mActivity,"静负荷测试进行中！"        ).show();
                    }

//                    else if (mActivity.isDown() && !mActivity.getTg()) {
//
//                        Toasty.info(mActivity,"静负荷测试数据未保存！"        ).show();
//                    }
                    else {                        flmain.setCurrentItem(3, false);
                        mActivity.setTg(true);
                        if (!mActivity.isStart()) {
                            gifImageView.setBackgroundResource(R.drawable.tgs);
                        }
                    }
                    break;
                case R.id.rbn_test_jsd:
                    if (mActivity.isStart() && !mActivity.getTg()) {
                        Toasty.info(mActivity,"静负荷测试进行中！"        ).show();
                    }

//                    else if (mActivity.isDown() && !mActivity.getTg()) {
//
//                        Toasty.info(mActivity,"静负荷测试数据未保存！"        ).show();
//                    }
                    else {
                        flmain.setCurrentItem(2, false);
                        mActivity.setTg(true);
                        if (!mActivity.isStart()) {
                            gifImageView.setBackgroundResource(R.drawable.tgs);
                        }
                    }
                    break;
                case R.id.btn_test_save:
                    if(mActivity.isDown()) {
                        btnTestSave.setEnabled(false);
                        CountDownTimer timer = new CountDownTimer(3000 + 500, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                if (getActivity() != null && !getActivity().isFinishing()) {
                                    btnTestSave.setEnabled(false);
                                    btnTestSave.setText(String.valueOf(millisUntilFinished / 1000));
                                }
                            }

                            @Override
                            public void onFinish() {
                                //非空判断
                                if (getActivity() != null && !getActivity().isFinishing()) {
                                    btnTestSave.setEnabled(true);
                                    btnTestSave.setText("保存");
                                }
                            }
                        }.start();
                        mActivity.SaveData();
                        mActivity.setStart(0);
                        mActivity.StartJsd();
                        btnjfh.setEnabled(true);
                        btntg.setEnabled(true);
                        btnjsd.setEnabled(true);
                        btnwy.setEnabled(true);
                    }else if(mActivity.isStart())
                    {
                        Toasty.info(mActivity,"测试进行中，需停止测试后再进行保存操作！" ).show();
                    }else
                    { Toasty.info(mActivity,"没有未保存数据！" ).show();}
//                    if (mActivity.SaveData()) {
//                        Toasty.success(mActivity, "已保存一组数据！", Toast.LENGTH_SHORT, true).show();
//                    } else {
//                        Toasty.error(mActivity, "数据保存失败！", Toast.LENGTH_SHORT, true).show();
//                    }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();


    }

    public static void refresh() {
        mjfhfragment.refresh();
        mwyfragment.refresh();
        mjsdfragment.refresh();


//        ckdjLock.setText(MyApp.getInstance().getStrTest());
//        ckLock.setText(MyApp.getInstance().getStrstate());
//        MyApp.getInstance().initStrstate();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        setUserVisibleHint(true);
    }

    public void setJsdState(int jsdstate) {
        mjsdfragment.setJsdState(jsdstate);
    }

    public int getJsdState(){return  mjsdfragment.getJsdState();}

    public void drawjsd() {
        mjsdfragment.Draw();
    }

    public void setstartenable() {
        ckLock.setEnabled(true);
    }
}
