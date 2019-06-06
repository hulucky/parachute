package com.parachute.test.fragment;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.parachute.Adapter.TestShowPagerAdapter;
import com.parachute.administrator.DATAbase.R;
import com.parachute.administrator.DATAbase.greendao.TaskEntity;
import com.parachute.app.MyApp;
import com.parachute.test.TestActivity;
import com.parachute.test.fragment.childfragment.TestJfhfragment;
import com.parachute.test.fragment.childfragment.TestJsdfragment;
import com.parachute.test.fragment.childfragment.TestSensorfragment;
import com.parachute.test.fragment.childfragment.TestWyfragment;
import com.xzkydz.function.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import pl.droidsonroids.gif.GifImageView;

import static com.parachute.app.MyApp.getInstance;


public class TestFragment extends Fragment {

    @BindView(R.id.giv_gif)
    GifImageView gifImageView;
    @BindView(R.id.fr_main)
    CustomViewPager flmain;
    @BindView(R.id.rbn_test_cgq)
    RadioButton rbnTestCgq;
    @BindView(R.id.rbn_test_jfh)
    RadioButton btnjfh;
    @BindView(R.id.rbn_test_tg)
    RadioButton btntg;
    @BindView(R.id.rbn_test_jsd)
    RadioButton btnjsd;
    @BindView(R.id.rbn_test_wy)
    RadioButton btnwy;
    @BindView(R.id.ll)
    RadioGroup ll;

    public Button btnStart;
    public Button btnTestSave;
    TaskEntity mTask;
    MyApp myApp;
    Unbinder unbinder;
    public TestSensorfragment msensorfragment;
    TestJfhfragment mjfhfragment;
    TestJsdfragment mjsdfragment;
    public TestWyfragment mwyfragment;
    List<Fragment> list_fragments;
    TestActivity mActivity;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list_fragments = new ArrayList<Fragment>();

        //把两个子fragment实例化然后装到集合里
        mTask = myApp.getTaskEnity();
        msensorfragment = new TestSensorfragment();//传感器
        mjfhfragment = new TestJfhfragment();//静负荷
        mjsdfragment = new TestJsdfragment();//加速度
        mwyfragment = new TestWyfragment();//位移

        list_fragments.add(msensorfragment);//0
        list_fragments.add(mjfhfragment);//1
        list_fragments.add(mjsdfragment);//2
        list_fragments.add(mwyfragment);//3

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, null);
        myApp = getInstance();
        mTask = myApp.getTaskEnity();
        btnTestSave = view.findViewById(R.id.btn_test_save);
        btnStart = view.findViewById(R.id.btn_test_start);
        unbinder = ButterKnife.bind(this, view);
        Log.d("cca", "onCreateView ");
        mActivity = (TestActivity) getActivity();
        flmain.setScanScroll(false);
        flmain.setOffscreenPageLimit(4);
        if (list_fragments != null) {
            TestShowPagerAdapter msgAdapter = new TestShowPagerAdapter(getChildFragmentManager(), list_fragments);
            flmain.setAdapter(msgAdapter);
            flmain.setCurrentItem(0);
        }
        gifImageView.setBackgroundResource(R.drawable.jfhs);
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


    @OnClick({R.id.rbn_test_cgq, R.id.rbn_test_tg, R.id.rbn_test_jfh, R.id.rbn_test_wy, R.id.rbn_test_jsd, R.id.btn_test_save, R.id.btn_test_start})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rbn_test_tg://脱钩
                flmain.setCurrentItem(2, false);
                btnjsd.setVisibility(View.VISIBLE);
                btnwy.setVisibility(View.VISIBLE);
                mActivity.setTg(true);
                if (!mActivity.isStart()) {
                    gifImageView.setBackgroundResource(R.drawable.tgs);
                }
                break;
            case R.id.rbn_test_cgq://传感器
                flmain.setCurrentItem(0, false);
                break;
            case R.id.rbn_test_jfh://静负荷测试
                flmain.setCurrentItem(1, false);
                btnjsd.setVisibility(View.INVISIBLE);
                btnwy.setVisibility(View.INVISIBLE);
                mActivity.setTg(false);
                if (!mActivity.isStart()) {
                    gifImageView.setBackgroundResource(R.drawable.jfhs);
                }
                break;
            case R.id.rbn_test_wy://位移
                flmain.setCurrentItem(3, false);
                mActivity.setTg(true);
                if (!mActivity.isStart()) {
                    gifImageView.setBackgroundResource(R.drawable.tgs);
                }
                break;
            case R.id.rbn_test_jsd://加速度
                flmain.setCurrentItem(2, false);
                mActivity.setTg(true);
                if (!mActivity.isStart()) {
                    gifImageView.setBackgroundResource(R.drawable.tgs);
                }
                break;
            case R.id.btn_test_save://保存
                if (flmain.getCurrentItem() == 1 ) {//静负荷测试
                    if (MyApp.wy1Connected && MyApp.wy2Connected && MyApp.wy3Connected
                            && MyApp.wy4Connected && MyApp.wy5Connected && MyApp.wy6Connected) {
                        if (mActivity.isDown()) {//此时是停止状态
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
                            mActivity.saveJfh();//清空记录的数据
                            //mActivity.setStart(0);
                            //mActivity.StartJsd();
                            btnjfh.setEnabled(true);
                            btntg.setEnabled(true);
                            btnjsd.setEnabled(true);
                            btnwy.setEnabled(true);
                        } else if (mActivity.isStart()) {//此时是开始状态
                            Toasty.info(mActivity, "测试进行中，需停止测试后再进行保存操作！").show();
                        } else {//此时是保存状态
                            Toasty.info(mActivity, "没有未保存数据！").show();
                        }
                    } else {
                        Toasty.info(mActivity, "传感器未连接，无法保存！").show();
                    }
                } else if (flmain.getCurrentItem() == 2 || flmain.getCurrentItem() == 3) {//脱钩测试
                    if (MyApp.wy1Connected && MyApp.wy2Connected && MyApp.wy3Connected && MyApp.wy4Connected &&
                            MyApp.wy5Connected && MyApp.wy6Connected && MyApp.wy7Connected && MyApp.jsdConnected) {
                        if (mActivity.isDown()) {//此时是停止状态
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
                            mActivity.saveTg();//清空记录的数据
                            //mActivity.setStart(0);
//                                mActivity.StartJsd();
                            btnjfh.setEnabled(true);
                            btntg.setEnabled(true);
                            btnjsd.setEnabled(true);
                            btnwy.setEnabled(true);
                        } else if (mActivity.isStart()) {//此时是开始状态
                            Toasty.info(mActivity, "测试进行中，需停止测试后再进行保存操作！").show();
                        } else {//此时是保存状态
                            Toasty.info(mActivity, "没有未保存数据！").show();
                        }
                    } else {
                        Toasty.info(mActivity, "传感器未连接，无法保存！").show();
                    }
                }else {
                    Toasty.info(mActivity, "请切换到对应界面进行保存！").show();
                }
                break;
            case R.id.btn_test_start://开始测试
                //先判断此时viewPager处于哪个fragment
                //静负荷测试
                if (flmain.getCurrentItem() == 1) {
                    //如果传感器都连接了
                    if (MyApp.wy1Connected && MyApp.wy2Connected && MyApp.wy3Connected
                            && MyApp.wy4Connected && MyApp.wy5Connected && MyApp.wy6Connected) {
                        if (mActivity.isDown()) {//如果停止了，那么可以开始（开始点击事件）
                            Toasty.info(mActivity, "开始静负荷测试").show();
                            mActivity.setTg(false);
                            gifImageView.setBackgroundResource(R.drawable.jfh);
//                            mActivity.saveJfh();//清空记录的数据
                            btnStart.setText("停止");
                            btntg.setEnabled(false);
                            btnjsd.setEnabled(false);
                            btnwy.setEnabled(false);
                            btnStart.setBackgroundResource(R.mipmap.rightbar_btn_red);
                            mActivity.setStart(1);//设置目前状态，1代表开始
                            mActivity.startJfh();//把当前显示的距离作为初始值赋给位移
                        } else {//停止测试(停止点击事件)
                            Toasty.info(mActivity, "停止静负荷测试").show();
                            mActivity.setStart(2);//2代表停止
                            btntg.setEnabled(true);
                            btnwy.setEnabled(true);
                            btnjsd.setEnabled(true);
                            btnStart.setText("开始");
                            btnStart.setBackgroundResource(R.mipmap.rightbar_btn_blue);
                        }
//                    mActivity.StartJsd();
//                    if (mActivity.getTg()) {
//                        gifImageView.setBackgroundResource(R.drawable.tg);
//                        btnjfh.setEnabled(false);
//                        ckLock.setEnabled(false);
//                        Toasty.info(mActivity, "开始脱钩测试").show();
//                    } else {
//                        Toasty.info(mActivity, "开始静负荷测试").show();
//                        gifImageView.setBackgroundResource(R.drawable.jfh);
//                        btntg.setEnabled(false);
//                        btnjsd.setEnabled(false);
//                        btnwy.setEnabled(false);
//                    }
                    } else {
                        Toast.makeText(getContext(), "传感器尚未连接，无法开始！", Toast.LENGTH_SHORT).show();
//                    if (mActivity.isStart()) {
//                        mActivity.drawrefresh();
//                    }
//                    mActivity.setStart(2);//2代表停止
//                    mActivity.StartJsd();
////                    btnjfh.setEnabled(true);
////                    btntg.setEnabled(true);
////                    btnjsd.setEnabled(true);
////                    btnwy.setEnabled(true);
//                    if (mActivity.getTg()) {
//                        gifImageView.setBackgroundResource(R.drawable.tge);
//                    } else {
//                        gifImageView.setBackgroundResource(R.drawable.jfhe);
//                    }
                    }
                    //加速度和位移测试(脱钩测试)
                } else if (flmain.getCurrentItem() == 2 || flmain.getCurrentItem() == 3) {
                    if (MyApp.wy1Connected && MyApp.wy2Connected && MyApp.wy3Connected && MyApp.wy4Connected
                            && MyApp.wy5Connected && MyApp.wy6Connected && MyApp.wy7Connected && MyApp.jsdConnected) {
                        if (mActivity.isDown()) {//开始点击事件
                            Toasty.info(mActivity, "开始脱钩测试").show();
                            mActivity.setTg(true);
                            gifImageView.setBackgroundResource(R.drawable.tg);
                            btnjfh.setEnabled(false);
                            initTg();//初始化加速度界面
                            mActivity.clearTg();//清空记录的数据
                            mActivity.StartJsd();//发送开始测试指令
                            btnStart.setText("停止");
                            btnStart.setBackgroundResource(R.mipmap.rightbar_btn_red);
                            mActivity.setStart(1);
                            mActivity.startTg();//把当前显示的距离作为初始值赋给位移
                        } else {//停止点击事件
                            Toasty.info(mActivity, "停止脱钩测试").show();
                            btnjfh.setEnabled(true);
                            mActivity.setStart(2);
                            btnStart.setText("开始");
                            btnStart.setBackgroundResource(R.mipmap.rightbar_btn_blue);
                        }
                    } else {
                        Toast.makeText(getContext(), "传感器尚未连接，无法开始！", Toast.LENGTH_SHORT).show();
                    }
                } else {//在传感器界面不能开始测试
                    Toast.makeText(getContext(), "请切换到对应的测试界面进行测试！", Toast.LENGTH_SHORT).show();
                }
                break;
//                    if (mActivity.SaveData()) {
//                        Toasty.success(mActivity, "已保存一组数据！", Toast.LENGTH_SHORT, true).show();
//                    } else {
//                        Toasty.error(mActivity, "数据保存失败！", Toast.LENGTH_SHORT, true).show();
//                    }
        }
    }

    public void initTg() {//开始脱钩测试时，初始化数据
        mjsdfragment.reFreshProgressBar(0);//进度条置零
        mjsdfragment.initDraw();//初始化图像
        mjsdfragment.updateTextView();//textView置零
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void refresh() {
        mjfhfragment.refresh();//静负荷6个传感器分别显示数据
        mwyfragment.refresh();//位移7个传感器分别显示数据
//        mjsdfragment.refresh();
    }

    //当传感器断开时，用此方法将静负荷界面数据全置为 0
    //并且将位移界面的数据置零
    //并且将此时的按钮置为开始、换背景
    public void refreshNull() {
        mjfhfragment.refreshNull();
        mwyfragment.refreshNull();
        btnStart.setText("开始");
        btnStart.setBackgroundResource(R.mipmap.rightbar_btn_blue);
    }

    //将加速度的状态置为断开
    public void refreshJsdNull() {
        mjsdfragment.setJsdState(0);//加速度断开
    }

    //刷新加速度界面的进度条
    public void refreshProgressBar(int dataId) {
        mjsdfragment.reFreshProgressBar(dataId);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        setUserVisibleHint(true);
    }

    public void setJsdState(int jsdstate) {
        mjsdfragment.setJsdState(jsdstate);
    }

    public int getJsdState() {
        return mjsdfragment.getJsdState();
    }

    public void drawjsd() {
        mjsdfragment.Draw();
    }

    public void setStartEnable() {
        btnStart.setEnabled(true);
        btnTestSave.setEnabled(true);
    }

    public void setStartDisable() {
        btnStart.setEnabled(false);
        btnTestSave.setEnabled(false);
    }
}
