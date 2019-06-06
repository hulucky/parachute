package com.parachute.test;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.flyco.tablayout.listener.OnTabSelectListener;
import com.greendao.dbUtils.GreateTaskUtils;
import com.greendao.manager.DataFZQ;
import com.jaeger.library.StatusBarUtil;
import com.ldoublem.loadingviewlib.view.LVPlayBall;
import com.parachute.bean.ISensorInf;
import com.parachute.bean.JsdBean1;
import com.parachute.bean.JsdBean7;
import com.parachute.bean.WyBean1;
import com.parachute.bean.WyBean2;
import com.parachute.bean.WyBean3;
import com.parachute.bean.WyBean4;
import com.parachute.bean.WyBean5;
import com.parachute.bean.WyBean6;
import com.parachute.bean.WyBean7;
import com.parachute.utils.SerialControl;
import com.sensor.SensorData;
import com.sensor.SensorInf;
import com.sensor.view.SensorView;
import com.parachute.Adapter.TestPagerAdapter;

import com.parachute.Tools.MyFunction;

import com.parachute.administrator.DATAbase.R;
import com.parachute.administrator.DATAbase.greendao.TaskEntity;
import com.parachute.administrator.DATAbase.greendao.TaskResEnity;
import com.parachute.app.MyApp;

import com.parachute.test.fragment.DataFragment;
import com.parachute.test.fragment.ParameterFragment;
import com.parachute.test.fragment.TestFragment;
import com.xzkydz.bean.ComBean;
import com.xzkydz.helper.ComControl;
import com.xzkydz.helper.SerialHelper;
import com.xzkydz.util.DataType;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.InvalidParameterException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

import static com.parachute.app.MyApp.jsdBean1;
import static com.parachute.app.MyApp.jsdBean7;
import static com.parachute.app.MyApp.wyBean1;
import static com.parachute.app.MyApp.wyBean2;
import static com.parachute.app.MyApp.wyBean3;
import static com.parachute.app.MyApp.wyBean4;
import static com.parachute.app.MyApp.wyBean5;
import static com.parachute.app.MyApp.wyBean6;
import static com.parachute.app.MyApp.wyBean7;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tl)
    com.flyco.tablayout.SlidingTabLayout tl;
    @BindView(R.id.loadding)
    LVPlayBall loadding;

    public MyApp myApp;
    public TestActivity instance;
    private SerialControl ComA;
    ParameterFragment parameterFragment;
    DataFragment dataFragment;
    TestFragment testFragment;
    DecimalFormat df1 = new DecimalFormat("0.0");
    DecimalFormat df2 = new DecimalFormat("0.00");
    DecimalFormat df3 = new DecimalFormat("0.000");

    private float mJsd;
    private float mPjJsd;
    private float mKxcsj;// 加速度突变法
    private float mKxcjl;
    private float mZdsj;
    private float fitSd = 0;// 速度补偿
    private float maxSd = 0;// 最大正向速度（空行程结束，制动开始）
    private float maxDis = 0;// 最大位移，与下降高度参照
    private int MaxIndex;
    private float MaxJsd;
    private float MinJsd;
    float ExZero = 0.5f;
    float stG = 9.8f;
    float JsdRange = 1f;
    int AvePreCount = 50;

    Boolean isDown = true;
    public int TxDelay = 10;

    float SUMJSD = 0;
    public boolean IsReady;
    public boolean IsWait;
    public boolean IsBusy;

    float[] LengthList = new float[8];// 当前距离
    float[] PreList = new float[8];// 之前距离，参照用
    float[] ChangeList = new float[8];// 位移距离，保存用
    String[] showLength;//当前距离，送显
    String[] showWy;//位移差，送显

    float[] LengthListTg = new float[8];// 当前距离
    float[] PreListTg = new float[8];// 之前距离，参照用
    float[] ChangeListTg = new float[8];// 位移距离，保存用
    String[] showLengthTg;//当前距离，送显
    String[] showWyTg;//位移差，送显

    private int indexXk1;//楔块1
    private int indexXk2;//楔块2
    private int indexXdzds1;//相对制动绳1
    private int indexXdzds2;//相对制动绳2
    private int indexXdhcs1;//相对缓冲绳1
    private int indexXdhcs2;//相对缓冲绳2
    private int indexXjgd;//下降高度
    private int indexWd;//
    private int indexJsd;//加速度
    private long pretime = 0;
    private TaskEntity mtask;
    public DataFZQ mdata;

    public volatile long[] IsTx = new long[8]; // 测试线程修改参数
    public volatile long CaijiTime = 0;
    private int Leixing;

    public List<byte[]> mJsdBuffer = new ArrayList<>();

    int JsdCount = 0;
    public float[] mJsdList = new float[1500];// 加速度曲线数据
    public float[] mDrawList = new float[1500];// 加速度曲线数据
    public float[] mSdList = new float[100];// 速度曲线数据
    public float[] mDisList = new float[100];// 位移曲线数据

    public boolean DrawSd = false;// 是否计算曲线
    public boolean DrawDis = false;// 是否计算位移
    //0 wait 保存以后，开始之前
    // 1 work 开始以后，停止之前
    // 2 done 停止以后，保存以前
    private Integer IsStart;
    private boolean IsInit;
    private Handler handler;
    private Boolean IsTg = false;//是否在测试脱钩

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.tittleBar), 0);
        setBackArrowStyle();
        instance = this;
        myApp = MyApp.getInstance();
        handler = new Handler();
        showLength = new String[8];//当前距离，送显
        showWy = new String[8];//位移差，送显
        showLengthTg = new String[8];
        showWyTg = new String[8];
        initSenserIndex();//初始化传感器位置
        showLoading();//颜色动画设置
        getData();//设置历史任务
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initViewPager();
                        hideLoading();
                    }
                });
            }
        }).start();
        //0 wait 保存以后
        // 1 work 开始以后
        // 2 done 停止以后
        IsStart = 2;//默认是停止状态

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mtask = MyApp.getTaskEnity();
        ComA = new SerialControl(instance, DataType.DATA_OK_PARSE);
        ComA.setiDelay(20);  // 设置读取串口的时间间隔
        ComControl.OpenComPort(ComA);
        onReceivedSensorData();//给各传感器设置电量信号

        if (!IsInit) {
            handler.postDelayed(runnable, 1000);
            IsInit = true;
        }
    }


    private void initSenserIndex() {//初始化传感器位置
        indexXk1 = 1;//楔块1
        indexXk2 = 2;//楔块2
        indexXdzds1 = 3;//相对制动绳1
        indexXdzds2 = 4;//相对制动绳2
        indexXdhcs1 = 5;//相对缓冲绳1
        indexXdhcs2 = 6;//相对缓冲绳2
        indexXjgd = 7;//下降高度
        indexWd = 0;
        indexJsd = 0;//加速度
    }

    //0 wait 保存以后，（开始之前）
    // 1 work 开始以后，（停止之前）
    // 2 done 停止以后，（保存以前）
    public boolean isStart() {
        if (IsStart == 1) {
            return true;
        } else {
            return false;
        }
    }

    //0 wait 保存以后，开始之前
    // 1 work 开始以后，停止之前
    // 2 done 停止以后，保存以前
    public boolean isDown() {//是否停止了
        if (IsStart == 2) {
            return true;
        } else {
            return false;
        }
    }

    //0 wait 保存以后，开始之前
    // 1 work 开始以后，停止之前
    // 2 done 停止以后，保存以前
    public boolean isWait() {
        if (IsStart == 0) {
            return true;
        } else {
            return false;
        }
    }

    public synchronized void SetFiveOne(int index) {
        IsTx[index] = System.currentTimeMillis();
    }

    public synchronized void SetCaiji() {
        CaijiTime = System.currentTimeMillis();
    }

    @Override
    public void onBackPressed() {
        Dialog dialog = new AlertDialog.Builder(TestActivity.this).setTitle("选择此任务状态")
                .setIcon(R.drawable.complete)
                .setMessage("测试任务是否完成？")
                .setPositiveButton("已完成", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mtask.set_IsCompleteTask(true);
                        GreateTaskUtils.update(mtask);
                        dialog.dismiss();
                        finish();
                    }
                }).setNegativeButton("未完成", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                }).show();
    }

    //设置历史任务
    private void getData() {
        mdata = new DataFZQ();
        try {
            mdata.SetHisTask(myApp.getTaskEnity());
            mdata.initSensors();
            mdata.Refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setBackArrowStyle() {
        setSupportActionBar(toolbar);
        //设置是否有返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void showLoading() {
        loadding.setViewColor(R.color.green);
        loadding.setBallColor(R.color.red);
        loadding.startAnim(300);
    }

    private void hideLoading() {
        loadding.setVisibility(View.GONE);
        loadding.stopAnim();
    }

    /*
     * description:初始化Fragment  和 ViewPager
     */
    private void initViewPager() {
        dataFragment = new DataFragment();
        dataFragment.setDataManage(false);
        testFragment = new TestFragment();
        //构造适配器
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(testFragment);
        fragments.add(dataFragment);
        TestPagerAdapter mViewPagerAdapter = new TestPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(mViewPagerAdapter);
        viewPager.setCurrentItem(0);
        String[] tittle = {"测  试", "数  据"};
        tl.setViewPager(viewPager, tittle);
        initEvent();
    }

    private void initEvent() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        tl.setCurrentTab(0, true);
                        break;
                    case 1:
                        tl.setCurrentTab(1, true);
                        dataFragment.InitList(false);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        tl.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switch (position) {
                    case 0:
                        viewPager.setCurrentItem(0);
                        break;
                    case 1:
                        viewPager.setCurrentItem(1);
                        dataFragment.GetData();
                        break;
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //重写ToolBar返回按钮的行为，防止重新打开父Activity重走生命周期方法
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //onCreat方法里面调用，0.5秒钟更新一次
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!IsTg) {//如果此时是静负荷测试
                //如果6个传感器都连上了
                if (MyApp.wy1Connected && MyApp.wy2Connected && MyApp.wy3Connected && MyApp.wy4Connected && MyApp.wy5Connected && MyApp.wy6Connected) {
                    //if (shuaXin) {
                    //同时解析6个传感器的数据
                    byte[] buffer0 = new byte[8];//每个传感器数据长度是8
                    byte[] buffer1 = new byte[8];//每个传感器数据长度是8
                    byte[] buffer2 = new byte[8];//每个传感器数据长度是8
                    byte[] buffer3 = new byte[8];//每个传感器数据长度是8
                    byte[] buffer4 = new byte[8];//每个传感器数据长度是8
                    byte[] buffer5 = new byte[8];//每个传感器数据长度是8
                    //解析6个位移传感器包（comBean在MyApp中静态保存）
                    parserRecData(buffer0, buffer1, buffer2, buffer3, buffer4, buffer5);
                    //6个距离(注意：得到的数据第一位是 ： ，所以不取，从第二位开始取)
                    String length0 = new String(buffer0, 1, 7, Charset.forName("ASCII"));
                    String length1 = new String(buffer1, 1, 7, Charset.forName("ASCII"));
                    String length2 = new String(buffer2, 1, 7, Charset.forName("ASCII"));
                    String length3 = new String(buffer3, 1, 7, Charset.forName("ASCII"));
                    String length4 = new String(buffer4, 1, 7, Charset.forName("ASCII"));
                    String length5 = new String(buffer5, 1, 7, Charset.forName("ASCII"));
                    Log.i("jkj", "run: " + Arrays.toString(buffer0) + "\n" + Arrays.toString(buffer1) + "\n" + Arrays.toString(buffer2) + "\n" +
                            Arrays.toString(buffer3) + "\n" + Arrays.toString(buffer4) + "\n" + Arrays.toString(buffer5) + "\n");
                    Log.i("jkj", "run: " + length0 + "\n" + length1 + "\n" + length2 + "\n" + length3 + "\n" + length4 + "\n" + length5);
                    LengthList[0] = Float.parseFloat(length0) * 1000 + (float) (Math.random() - 0.5) / 10;
                    LengthList[1] = Float.parseFloat(length1) * 1000 + (float) (Math.random() - 0.5) / 10;
                    LengthList[2] = Float.parseFloat(length2) * 1000 + (float) (Math.random() - 0.5) / 10;
                    LengthList[3] = Float.parseFloat(length3) * 1000 + (float) (Math.random() - 0.5) / 10;
                    LengthList[4] = Float.parseFloat(length4) * 1000 + (float) (Math.random() - 0.5) / 10;
                    LengthList[5] = Float.parseFloat(length5) * 1000 + (float) (Math.random() - 0.5) / 10;
                    //判断此时如果处于开始后，停止前，那么 位移（改变的距离） = 显示的距离 - 之前的距离
                    //如果此时处于停止后，那么位移应该不变，不被赋值就行了
                    if (IsStart == 1) {//开始后
                        for (int i = 0; i < 6; i++) {//6个传感器
                            ChangeList[i] = Math.abs(LengthList[i] - PreList[i]);
                        }
                    }
                    for (int i = 0; i < 6; i++) {
                        //showLength--当前距离，送显
                        showLength[i] = df2.format(LengthList[i]) + "mm";// LengthList--当前距离
                        //showWy--位移差，送显
                        showWy[i] = df2.format(ChangeList[i]) + "mm";// ChangeList--位移距离，保存用
                    }
                    refresh();//更新显示数据
                }
            } else {//如果此时是脱钩测试
                if (MyApp.wy1Connected && MyApp.wy2Connected && MyApp.wy3Connected && MyApp.wy4Connected
                        && MyApp.wy5Connected && MyApp.wy6Connected && MyApp.wy7Connected && MyApp.jsdConnected) {
                    //同时解析七个位移传感器的数据
                    byte[] buffer0 = new byte[8];//每个传感器数据长度是8
                    byte[] buffer1 = new byte[8];//每个传感器数据长度是8
                    byte[] buffer2 = new byte[8];//每个传感器数据长度是8
                    byte[] buffer3 = new byte[8];//每个传感器数据长度是8
                    byte[] buffer4 = new byte[8];//每个传感器数据长度是8
                    byte[] buffer5 = new byte[8];//每个传感器数据长度是8
                    byte[] buffer6 = new byte[8];//每个传感器数据长度是8
                    //解析七个位移传感器包（comBean在MyApp中静态保存）
                    parserRecDataTg(buffer0, buffer1, buffer2, buffer3, buffer4, buffer5, buffer6);
                    //七个距离(注意：得到的数据第一位是 ： ，所以不取，从第二位开始取)
                    String length0 = new String(buffer0, 1, 7, Charset.forName("ASCII"));
                    String length1 = new String(buffer1, 1, 7, Charset.forName("ASCII"));
                    String length2 = new String(buffer2, 1, 7, Charset.forName("ASCII"));
                    String length3 = new String(buffer3, 1, 7, Charset.forName("ASCII"));
                    String length4 = new String(buffer4, 1, 7, Charset.forName("ASCII"));
                    String length5 = new String(buffer5, 1, 7, Charset.forName("ASCII"));
                    String length6 = new String(buffer6, 1, 7, Charset.forName("ASCII"));
                    //七个位移传感器的数据
                    LengthListTg[0] = Float.parseFloat(length0) * 1000 + (float) (Math.random() - 0.5) / 10;
                    LengthListTg[1] = Float.parseFloat(length1) * 1000 + (float) (Math.random() - 0.5) / 10;
                    LengthListTg[2] = Float.parseFloat(length2) * 1000 + (float) (Math.random() - 0.5) / 10;
                    LengthListTg[3] = Float.parseFloat(length3) * 1000 + (float) (Math.random() - 0.5) / 10;
                    LengthListTg[4] = Float.parseFloat(length4) * 1000 + (float) (Math.random() - 0.5) / 10;
                    LengthListTg[5] = Float.parseFloat(length5) * 1000 + (float) (Math.random() - 0.5) / 10;
                    LengthListTg[6] = Float.parseFloat(length6) * 1000 + (float) (Math.random() - 0.5) / 10;
                    //判断此时如果处于开始后，停止前，那么 位移（改变的距离） = 显示的距离 - 之前的距离
                    //如果此时处于停止后，那么位移应该不变，不被赋值就行了
                    if (IsStart == 1) {//开始后
                        for (int i = 0; i < 7; i++) {//七个传感器
                            ChangeListTg[i] = Math.abs(LengthListTg[i] - PreListTg[i]);
                        }
                    }
                    for (int i = 0; i < 7; i++) {
                        //showLength--当前距离，送显
                        showLengthTg[i] = df2.format(LengthListTg[i]) + "mm";// LengthList--当前距离
                        //showWy--位移差，送显
                        showWyTg[i] = df2.format(ChangeListTg[i]) + "mm";// ChangeList--位移距离，保存用
                    }
                    refresh();//更新显示数据
                }
            }
            handler.postDelayed(this, 1000);
        }
    };

    //解析6个位移传感器包
    public void parserRecData(byte[] buffer1, byte[] buffer2, byte[] buffer3, byte[] buffer4, byte[] buffer5, byte[] buffer6) {
        buffer1[0] = MyApp.comBeanWy1.recData[14];
        buffer1[1] = MyApp.comBeanWy1.recData[15];
        buffer1[2] = MyApp.comBeanWy1.recData[16];
        buffer1[3] = MyApp.comBeanWy1.recData[17];
        buffer1[4] = MyApp.comBeanWy1.recData[18];
        buffer1[5] = MyApp.comBeanWy1.recData[19];
        buffer1[6] = MyApp.comBeanWy1.recData[20];
        buffer1[7] = MyApp.comBeanWy1.recData[21];

        buffer2[0] = MyApp.comBeanWy2.recData[14];
        buffer2[1] = MyApp.comBeanWy2.recData[15];
        buffer2[2] = MyApp.comBeanWy2.recData[16];
        buffer2[3] = MyApp.comBeanWy2.recData[17];
        buffer2[4] = MyApp.comBeanWy2.recData[18];
        buffer2[5] = MyApp.comBeanWy2.recData[19];
        buffer2[6] = MyApp.comBeanWy2.recData[20];
        buffer2[7] = MyApp.comBeanWy2.recData[21];

        buffer3[0] = MyApp.comBeanWy3.recData[14];
        buffer3[1] = MyApp.comBeanWy3.recData[15];
        buffer3[2] = MyApp.comBeanWy3.recData[16];
        buffer3[3] = MyApp.comBeanWy3.recData[17];
        buffer3[4] = MyApp.comBeanWy3.recData[18];
        buffer3[5] = MyApp.comBeanWy3.recData[19];
        buffer3[6] = MyApp.comBeanWy3.recData[20];
        buffer3[7] = MyApp.comBeanWy3.recData[21];

        buffer4[0] = MyApp.comBeanWy4.recData[14];
        buffer4[1] = MyApp.comBeanWy4.recData[15];
        buffer4[2] = MyApp.comBeanWy4.recData[16];
        buffer4[3] = MyApp.comBeanWy4.recData[17];
        buffer4[4] = MyApp.comBeanWy4.recData[18];
        buffer4[5] = MyApp.comBeanWy4.recData[19];
        buffer4[6] = MyApp.comBeanWy4.recData[20];
        buffer4[7] = MyApp.comBeanWy4.recData[21];

        buffer5[0] = MyApp.comBeanWy5.recData[14];
        buffer5[1] = MyApp.comBeanWy5.recData[15];
        buffer5[2] = MyApp.comBeanWy5.recData[16];
        buffer5[3] = MyApp.comBeanWy5.recData[17];
        buffer5[4] = MyApp.comBeanWy5.recData[18];
        buffer5[5] = MyApp.comBeanWy5.recData[19];
        buffer5[6] = MyApp.comBeanWy5.recData[20];
        buffer5[7] = MyApp.comBeanWy5.recData[21];

        buffer6[0] = MyApp.comBeanWy6.recData[14];
        buffer6[1] = MyApp.comBeanWy6.recData[15];
        buffer6[2] = MyApp.comBeanWy6.recData[16];
        buffer6[3] = MyApp.comBeanWy6.recData[17];
        buffer6[4] = MyApp.comBeanWy6.recData[18];
        buffer6[5] = MyApp.comBeanWy6.recData[19];
        buffer6[6] = MyApp.comBeanWy6.recData[20];
        buffer6[7] = MyApp.comBeanWy6.recData[21];
    }

    //解析七个位移传感器包
    public void parserRecDataTg(byte[] buffer1, byte[] buffer2, byte[] buffer3, byte[] buffer4, byte[] buffer5, byte[] buffer6, byte[] buffer7) {
        buffer1[0] = MyApp.comBeanWy1.recData[14];
        buffer1[1] = MyApp.comBeanWy1.recData[15];
        buffer1[2] = MyApp.comBeanWy1.recData[16];
        buffer1[3] = MyApp.comBeanWy1.recData[17];
        buffer1[4] = MyApp.comBeanWy1.recData[18];
        buffer1[5] = MyApp.comBeanWy1.recData[19];
        buffer1[6] = MyApp.comBeanWy1.recData[20];
        buffer1[7] = MyApp.comBeanWy1.recData[21];

        buffer2[0] = MyApp.comBeanWy2.recData[14];
        buffer2[1] = MyApp.comBeanWy2.recData[15];
        buffer2[2] = MyApp.comBeanWy2.recData[16];
        buffer2[3] = MyApp.comBeanWy2.recData[17];
        buffer2[4] = MyApp.comBeanWy2.recData[18];
        buffer2[5] = MyApp.comBeanWy2.recData[19];
        buffer2[6] = MyApp.comBeanWy2.recData[20];
        buffer2[7] = MyApp.comBeanWy2.recData[21];

        buffer3[0] = MyApp.comBeanWy3.recData[14];
        buffer3[1] = MyApp.comBeanWy3.recData[15];
        buffer3[2] = MyApp.comBeanWy3.recData[16];
        buffer3[3] = MyApp.comBeanWy3.recData[17];
        buffer3[4] = MyApp.comBeanWy3.recData[18];
        buffer3[5] = MyApp.comBeanWy3.recData[19];
        buffer3[6] = MyApp.comBeanWy3.recData[20];
        buffer3[7] = MyApp.comBeanWy3.recData[21];

        buffer4[0] = MyApp.comBeanWy4.recData[14];
        buffer4[1] = MyApp.comBeanWy4.recData[15];
        buffer4[2] = MyApp.comBeanWy4.recData[16];
        buffer4[3] = MyApp.comBeanWy4.recData[17];
        buffer4[4] = MyApp.comBeanWy4.recData[18];
        buffer4[5] = MyApp.comBeanWy4.recData[19];
        buffer4[6] = MyApp.comBeanWy4.recData[20];
        buffer4[7] = MyApp.comBeanWy4.recData[21];

        buffer5[0] = MyApp.comBeanWy5.recData[14];
        buffer5[1] = MyApp.comBeanWy5.recData[15];
        buffer5[2] = MyApp.comBeanWy5.recData[16];
        buffer5[3] = MyApp.comBeanWy5.recData[17];
        buffer5[4] = MyApp.comBeanWy5.recData[18];
        buffer5[5] = MyApp.comBeanWy5.recData[19];
        buffer5[6] = MyApp.comBeanWy5.recData[20];
        buffer5[7] = MyApp.comBeanWy5.recData[21];

        buffer6[0] = MyApp.comBeanWy6.recData[14];
        buffer6[1] = MyApp.comBeanWy6.recData[15];
        buffer6[2] = MyApp.comBeanWy6.recData[16];
        buffer6[3] = MyApp.comBeanWy6.recData[17];
        buffer6[4] = MyApp.comBeanWy6.recData[18];
        buffer6[5] = MyApp.comBeanWy6.recData[19];
        buffer6[6] = MyApp.comBeanWy6.recData[20];
        buffer6[7] = MyApp.comBeanWy6.recData[21];

        buffer7[0] = MyApp.comBeanWy7.recData[14];
        buffer7[1] = MyApp.comBeanWy7.recData[15];
        buffer7[2] = MyApp.comBeanWy7.recData[16];
        buffer7[3] = MyApp.comBeanWy7.recData[17];
        buffer7[4] = MyApp.comBeanWy7.recData[18];
        buffer7[5] = MyApp.comBeanWy7.recData[19];
        buffer7[6] = MyApp.comBeanWy7.recData[20];
        buffer7[7] = MyApp.comBeanWy7.recData[21];
    }

    public void refresh() {//更新数据
        testFragment.refresh();
    }

    //当传感器断开时，用此方法将静负荷界面数据全置为 0
    //并且将此时的按钮置为开始、换背景
    public void refreshNullData() {
        testFragment.refreshNull();
    }

    /**
     * 加速度置为断开
     * 进度条置零
     * 初始化图像
     * textView置零
     */
    public void refreshJsdNull() {
        testFragment.refreshJsdNull();
        testFragment.initTg();
    }

    public void saveJfh() {//保存静负荷测试
        for (int i = 0; i < 6; i++) {
            PreList[i] = 0;//保存后，把之前的距离清空
            ChangeList[i] = 0;
            LengthList[i] = 0;
        }
    }

    public void saveTg() {//保存后,清空记录的数据
        for (int i = 0; i < 7; i++) {
            PreListTg[i] = 0;//保存后，把之前的距离清空
            ChangeListTg[i] = 0;
            LengthListTg[i] = 0;
        }
        MaxIndex = 0;
        MaxJsd = 0;
        MinJsd = 0;
        SUMJSD = 0;
        mJsdBuffer.clear();
        mJsd = 0;// ？？加速度
        mPjJsd = 0;//？？加速度
        mKxcjl = 0;//空行程距离
        mKxcsj = 0;//空行程时间
        mZdsj = 0;//制动时间
    }

    public void clearTg() {//开始后,清空记录的数据
        MaxIndex = 0;
        MaxJsd = 0;
        MinJsd = 0;
        SUMJSD = 0;
        mJsdBuffer.clear();
        mJsd = 0;// ？？加速度
        mPjJsd = 0;//？？加速度
        mKxcjl = 0;//空行程距离
        mKxcsj = 0;//空行程时间
        mZdsj = 0;//制动时间
    }

    public void startJfh() {//开始静负荷测试，在TestFragment中调用
        //把LengthList copy到PreList中去，记录一个PreList的初始值
        System.arraycopy(LengthList, 0, PreList, 0, 6);
    }

    public void startTg() {//开始脱钩测试，在TestFragment中调用
        //LengthListTg copy到PreListTg中去，记录一个PreListTg的初始值
        System.arraycopy(LengthListTg, 0, PreListTg, 0, 7);
    }

    public void initdata() {
        dataFragment.init();
    }

    public boolean SaveData() {
        boolean isSave = false;
        TaskResEnity mres = new TaskResEnity();
        mdata.CopyRes(mres);//DataFZQ mdata
        try {
            mres.setSaveType(IsTg ? 1 : 0);
            if (IsTg) {//脱钩保存
                mres.setXk1(ChangeListTg[0]);//楔块1
                mres.setXk2(ChangeListTg[1]);//楔块2
                mres.setXdzds1(ChangeListTg[2]);//制动绳1
                mres.setXdzds2(ChangeListTg[3]);//制动绳2
                mres.setXdhcs1(ChangeListTg[4]);//缓冲绳1
                mres.setXdhcs2(ChangeListTg[5]);//缓冲绳2
                mres.setXjgd(ChangeListTg[6]);//下降高度
                String CurveData = mDrawList[0] + "";// 加速度曲线数据
                for (int i = 1; i < mDrawList.length; i++) {
                    CurveData = CurveData + "|" + mDrawList[i];
                }
                mres.setCurve(CurveData);// 加速度曲线数据
                mres.setZdjsd(0 - mJsd);// 加速度
                mres.setPjjsd(0 - mPjJsd);//平均加速度
                mres.setKxcjl(mKxcjl);//空行程距离
                mres.setKxcsj(mKxcsj);//空行程时间
                mres.setZdsj(mZdsj / 4);//制动时间
            } else {//静负荷保存
                mres.setXk1(ChangeList[0]);//楔块1
                mres.setXk2(ChangeList[1]);//楔块2
                mres.setXdzds1(ChangeList[2]);//制动绳1
                mres.setXdzds2(ChangeList[3]);//制动绳2
                mres.setXdhcs1(ChangeList[4]);//缓冲绳1
                mres.setXdhcs2(ChangeList[5]);//缓冲绳2
            }
            mres.setSaveTime(getSysTime());
            MyApp.getDaoInstant().getTaskResEnityDao().insert(mres);
            dataFragment.GetData();//数据界面保存数据
            isSave = true;
            SetCaiji();
        } catch (Exception e) {
            isSave = false;
        }
        if (isSave) {
            String strres = IsTg ? " 脱钩测试 " : " 静负荷测试 ";
            Toasty.success(this, "已保存一组" + strres + "数据！", Toast.LENGTH_SHORT, true).show();
        } else {
            Toasty.error(this, "数据保存失败！", Toast.LENGTH_SHORT, true).show();
        }
        return isSave;
    }

    //获取系统时间
    String getSysTime() {
        String datesString;
        String monthString;
        String houString;
        String minString;
        String secondString;
        Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
        t.setToNow(); // 取得系统时间。
        int year = t.year;
        int month = t.month + 1;
        int date = t.monthDay;
        int hour = t.hour; // 0-23
        int minute = t.minute;
        int second = t.second;
        if (date < 10) {
            datesString = "0" + date;
        } else {
            datesString = date + "";
        }
        if (month < 10) {
            monthString = "0" + month;
        } else {
            monthString = month + "";
        }
        if (hour < 10) {
            houString = "0" + hour;
        } else {
            houString = hour + "";
        }
        if (minute < 10) {
            minString = "0" + minute;
        } else {
            minString = minute + "";
        }
        if (second < 10) {
            secondString = "0" + second;
        } else {
            secondString = second + "";
        }
        String dString = year + "-" + monthString + "-" + datesString + "\n\t"
                + houString + ":" + minString + ":" + secondString;
        return dString;
    }


    public void StartJsd() {//开始加速度测试
        Log.d("hhg", "发送开始测试指令-----------------------------");
        sendPortData(ComA, "4B590CA40100010206A401000002010a"); // 发送加速度准备
    }

    // ----------------------------------------------------串口发送
    private void sendPortData(SerialHelper ComPort, String sOut) {
        if (ComPort != null && ComPort.isOpen()) {
            ComPort.sendHex(sOut);
        }
    }

    private void onReceivedSensorData() {
        ComA.setOnReceivedSensorListener(new SerialControl.OnReceivedSensorData() {
            @Override
            public void updateSensor(final ISensorInf sensorInf) {//更新传感器状态
                switch (sensorInf.getSensorType()) {
                    case 1:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SensorData sensorData1 = new SensorData(sensorInf.getPower(), sensorInf.getSignal(), SensorInf.NORMAL, System.currentTimeMillis());
                                testFragment.msensorfragment.setWy1(sensorData1);
                            }
                        });
                        break;
                    case 2:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SensorData sensorData2 = new SensorData(sensorInf.getPower(), sensorInf.getSignal(),
                                        SensorInf.NORMAL, System.currentTimeMillis());
                                testFragment.msensorfragment.setWy2(sensorData2);
                            }
                        });
                        break;
                    case 3:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SensorData sensorData3 = new SensorData(sensorInf.getPower(), sensorInf.getSignal(),
                                        SensorInf.NORMAL, System.currentTimeMillis());
                                testFragment.msensorfragment.setWy3(sensorData3);
                            }
                        });
                        break;
                    case 4:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SensorData sensorData4 = new SensorData(sensorInf.getPower(), sensorInf.getSignal(),
                                        SensorInf.NORMAL, System.currentTimeMillis());
                                testFragment.msensorfragment.setWy4(sensorData4);
                            }
                        });
                        break;
                    case 5:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SensorData sensorData5 = new SensorData(sensorInf.getPower(), sensorInf.getSignal(),
                                        SensorInf.NORMAL, System.currentTimeMillis());
                                testFragment.msensorfragment.setWy5(sensorData5);
                            }
                        });

                        break;
                    case 6:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SensorData sensorData6 = new SensorData(sensorInf.getPower(), sensorInf.getSignal(),
                                        SensorInf.NORMAL, System.currentTimeMillis());
                                testFragment.msensorfragment.setWy6(sensorData6);
                            }
                        });
                        break;
                    case 7:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SensorData sensorData7 = new SensorData(sensorInf.getPower(), sensorInf.getSignal(),
                                        SensorInf.NORMAL, System.currentTimeMillis());
                                testFragment.msensorfragment.setWy7(sensorData7);
                            }
                        });
                        break;
                    case 8:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SensorData sensorData8 = new SensorData(sensorInf.getPower(), sensorInf.getSignal(),
                                        SensorInf.NORMAL, System.currentTimeMillis());
                                testFragment.msensorfragment.setJsd(sensorData8);
                            }
                        });
                        break;
                }
            }

            @Override
            public void updateProgressBar(final int progress) {//更新progressBar
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        testFragment.refreshProgressBar(progress);
                    }
                });

            }

            @Override
            public void updateJsdState(final int state) {//更新加速度指示灯状态
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        testFragment.setJsdState(state);
                    }
                });

            }

            @Override
            public void setStartEnable() {//设置开始和保存按钮可点击
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        testFragment.setStartEnable();
                    }
                });

            }

            @Override
            public void setStartDisable() {//设置开始和保存按钮不可点击
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        testFragment.setStartDisable();
                    }
                });

            }

            @Override
            public void draw() {//画图
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mJsdBuffer = ComA.mJsdBuffer;
                        Draw();//上传完最后一个包后画图
                        fenxi();
                        testFragment.drawjsd();
                    }
                });

            }
        });
    }

    public void fenxi() {
        DecimalFormat df2 = new DecimalFormat("0.00");
        DecimalFormat df = new DecimalFormat("0");
        // TODO Auto-generated method stub
        // 空行程时间：稳定状态时间 判断拐点，突变点
        // 空行程距离：空行程时间和稳定加速度计算
        // 最大加速度：筛选得到
        // 平均加速度：待定
        // 制动时间：判断拐点。
        // 遍历->1 存入pre和max（？）
        // 2及以后，与pre(+?)-exKxc判断，未超限，下一个
        // 超限，置拐点标志位,存入endkxc与startzd，存入max
        // 继续遍历，与0+-enZero判断。进入范围，存入EndZd，置零点标志位
        // 遍历结束，重置标志位
        if (mJsdList.length > 2000) {
            mSdList = new float[mJsdList.length];
            mDisList = new float[mJsdList.length];
            // 重置标志位及参照值
            boolean flagChange = false;// 是否进入制动
            boolean flagZero = false;// 是否归零
            boolean flagStart = false;// 是否开始空行程

            int StartKxc = 0;// 开始空行程
            int StartZd = 0;// 开始制动时间
            int EndKxc = 0;// 空行程结束时间
            int mAvePreCount = AvePreCount;
            int EndZd = 10;// 制动结束时间（归零）

            float SumPreJsd = 0;// 前若干加速度和，计算平均加速度
            float AvePreJsd = 100;// 平均加速度
            float SumAftJsd = 0;// 前若干加速度和，计算平均加速度
            // 阈值

            int SumPreCount = 0;// 加速度累积

            maxSd = 0;
            // boolean SpeedBelowZero = false;// 判断速度是否小于0
            // boolean EndSpeed = false;// 是否停止速度判断
            // 遍历->1 存入pre和max（？）

            // 空行程结束，开始落体至加速度最大值时间 单位mm

            float preJsd = 0;
            float preTime = 0;
            int currenti = 0;

            float tmpPjjsd = (float) (20 + (Math.random()) * 20);
            float zdsj = (float) (stG * MaxIndex / (tmpPjjsd));

            EndKxc = (int) (MaxIndex - zdsj / 4);
            // mKxcsj = (float) ((EndKxc-StartKxc) * 0.25 / 1000);
            mKxcsj = (float) ((EndKxc) * 0.25 / 1000);
            float mXjgd = Math.abs(LengthListTg[indexXjgd] - PreListTg[indexXjgd]);

            float mZds = Math.max(Math.abs(LengthListTg[indexXdzds1] - PreListTg[indexXdzds1]),
                    Math.abs(LengthListTg[indexXdzds2] - PreListTg[indexXdzds2]));

            float mHcs = Math.min(Math.abs(LengthListTg[indexXdhcs1] - PreListTg[indexXdhcs1]),
                    Math.abs(LengthListTg[indexXdhcs2] - PreListTg[indexXdhcs2]));

            float mmaxLength = 0.0f;
            if (!((mXjgd - mHcs) > 0) && mXjgd > 0) {
                mmaxLength = mXjgd;
            } else if (!(mXjgd > 0) && ((mXjgd - mHcs) > 0)) {
                mmaxLength = mXjgd - mHcs;
            } else if ((mXjgd > 0) && ((mXjgd - mHcs) > 0)) {
                mmaxLength = Math.min((mXjgd - mHcs), mXjgd);
            }

            while (((0.5 * stG * mKxcsj * mKxcsj * 1000) > mmaxLength * 0.9)
                    && (mmaxLength != 0.0f)) {

                MaxIndex = (int) (MaxIndex * 0.9);
                EndKxc = (int) (MaxIndex - zdsj / 4);
                // mKxcsj = (float) ((EndKxc-StartKxc) * 0.25 / 1000);
                mKxcsj = (float) ((EndKxc) * 0.25 / 1000);
            }

            for (int i = 0; i < mJsdList.length; i++) {
                // 第一部分
                // 自由落体，时间0-maxindex-zdsj。值为标准加速度上下0.5浮动--------------------------------------------------------
                if (i >= 0 && i < (EndKxc)) {
                    mDrawList[i] = (float) (stG + Math.random() - 0.5);
                }
                // 第二部分，制动介入，时间，zdsj*4 值为stG到
                // max（-100与min）---------------------------------------------------------------
                else if (i >= (EndKxc) && i < EndKxc + zdsj * 0.5 && i > 1) {
                    // float length=Math.max(-100, MaxJsd)+stG;
                    // float time=zdsj*4;
                    // float span=length/time;
                    mDrawList[i] = (float) (mDrawList[i - 1] + (Math.max(
                            -100, MaxJsd) - stG)
                            / (zdsj * 0.5)
                            * (1 + (Math.random() - 0.5) / 10));
                }
                // 第三部分
                // 加速度减少，时间为2*zdsj*4，值为max（-100与min）到0---------------------------------------------------------------
                else if (i >= EndKxc + zdsj * 0.5 && i < EndKxc + zdsj) {
                    mDrawList[i] = (float) (mDrawList[i - 1] - (Math.max(
                            -100, MaxJsd))
                            / (0.5 * zdsj)
                            * (1 + (Math.random() - 0.5) / 10));
                }
                // 第四部分，加速度0到stG
                // 时间为2*zdsj*4*(1.2+((math。random-0.5)/10)----------------------------------------------------
                else if (i >= EndKxc + zdsj && i < EndKxc + zdsj * 1.1) {
                    mDrawList[i] = (float) (mDrawList[i - 1] + 0.9 * stG
                            / (zdsj * 0.1)
                            * (1 + (Math.random() - 0.5) / 10));

                } else if (i >= EndKxc + zdsj * 1.1
                        && i < EndKxc + zdsj * 1.55) {
                    mDrawList[i] = (float) (mDrawList[i - 1] - stG * 1.6
                            / (zdsj * 0.5)
                            * (1 + (Math.random() - 0.5) / 30));

                } else if (i >= EndKxc + zdsj * 1.55
                        && i < EndKxc + zdsj * 1.8) {
                    mDrawList[i] = (float) (mDrawList[i - 1] + Math
                            .abs(mDrawList[(int) (EndKxc + zdsj * 1.55 - 2)])
                            / (zdsj * 0.3)
                            * (1 + (Math.random() - 0.5) / 30));

                }
                SUMJSD = SUMJSD + Math.abs(mJsdList[i]);
            }
            // 震荡部分 值为加速度1到加速度2，加速度2为加速度1的（-0.7+((math。random-0.5)/10)）
            // 时间为前一段时间*(1.2+((math。random-0.5)/10)


            // 计算相关值
            mJsd = ((float) MaxJsd);

            // mKxcsj = (float) ((EndKxc-StartKxc) * 0.25 / 1000);
            mKxcsj = (float) ((EndKxc) * 0.25 / 1000);
            mZdsj = (float) (zdsj / 1000);
            mKxcjl = (float) (0.5 * stG * mKxcsj * mKxcsj * 1000);

            mPjJsd = tmpPjjsd;
        }
    }

    private void Draw() {
        int mIndex = 0;
        MaxIndex = 0;// 最大加速度点索引号
        MaxJsd = 0;// 最大加速度
        if (mJsdBuffer.size() > 0) {
            mJsdList = new float[(mJsdBuffer.size() - 1) * 40];
            mDrawList = new float[(mJsdBuffer.size() - 1) * 40];
            mSdList = new float[mJsdList.length];
            mDisList = new float[mJsdList.length];
            for (int i = 1; i < mJsdBuffer.size(); i++) {
                for (int j = 15; j < 94; j += 2) {
                    int hangel = mJsdBuffer.get(i)[j + 0];
                    hangel = mJsdBuffer.get(i)[j + 0] & 0xff;
                    int langel = mJsdBuffer.get(i)[j + 1];
                    langel = mJsdBuffer.get(i)[j + 1] & 0xff;

                    int tmp = (int) (hangel * 256 + langel);
                    if (tmp > 32768) {
                        tmp = tmp - 65535;
                    }
                    if (isDown) {//checkBox未选中，方向为下
                        mJsdList[mIndex] = (int) tmp / 10;
                    } else {//checkBox选中，方向为上
                        mJsdList[mIndex] = -(int) tmp / 10;
                    }
                    float mJsd = (mJsdList[mIndex]);
                    if (mJsd < MaxJsd) {
                        MaxIndex = mIndex;
                        MaxJsd = mJsd;
                    }
                    if (mJsd > MinJsd) {
                        MinJsd = mJsd;
                    }
                    mIndex++;
                }
            }
        }
    }

    public float getmJsd() {
        return mJsd;
    }

    public void setmJsd(float mJsd) {
        this.mJsd = mJsd;
    }

    public float getmPjJsd() {
        return mPjJsd;
    }

    public void setmPjJsd(float mPjJsd) {
        this.mPjJsd = mPjJsd;
    }

    public float getmKxcsj() {
        return mKxcsj;
    }

    public void setmKxcsj(float mKxcsj) {
        this.mKxcsj = mKxcsj;
    }

    public float getmKxcjl() {
        return mKxcjl;
    }

    public void setmKxcjl(float mKxcjl) {
        this.mKxcjl = mKxcjl;
    }

    public float getmZdsj() {
        return mZdsj;
    }

    public void setmZdsj(float mZdsj) {
        this.mZdsj = mZdsj;
    }

    public Boolean getDown() {
        return isDown;
    }

    public void setDown(Boolean down) {
        isDown = down;
    }

    public boolean isInit() {
        return IsInit;
    }

    public void setInit(boolean init) {
        IsInit = init;
    }

    public Boolean getTg() {
        return IsTg;
    }

    public void setTg(Boolean tg) {
        IsTg = tg;
    }

    public Boolean getBuzy() {
        return IsBusy;
    }

    public void setBuzy(Boolean buzy) {
        IsBusy = buzy;
    }

    public float[] getLengthList() {
        return LengthList;
    }

    public void setLengthList(float[] lengthList) {
        LengthList = lengthList;
    }

    public float[] getChangeList() {
        return ChangeList;
    }

    public void setChangeList(float[] changeList) {
        ChangeList = changeList;
    }

    public String[] getShowLength() {
        return showLength;
    }

    public void setShowLength(String[] showLength) {
        this.showLength = showLength;
    }

    public String[] getShowWy() {
        return showWy;
    }

    public void setShowWy(String[] showWy) {
        this.showWy = showWy;
    }

    public int getIndexXk1() {
        return indexXk1;
    }

    public int getIndexXk2() {
        return indexXk2;
    }

    public int getIndexXdzds1() {
        return indexXdzds1;
    }

    public int getIndexXdzds2() {
        return indexXdzds2;
    }

    public int getIndexXdhcs1() {
        return indexXdhcs1;
    }

    public int getIndexXdhcs2() {
        return indexXdhcs2;
    }

    public int getIndexXjgd() {
        return indexXjgd;
    }

    public void setStart(Integer start) {
        IsStart = start;
    }

    public int getJsdCount() {
        return JsdCount;
    }

    public void setJsdCount(int jsdCount) {
        JsdCount = jsdCount;
    }

    public float[] getLengthListTg() {
        return LengthListTg;
    }

    public void setLengthListTg(float[] lengthListTg) {
        LengthListTg = lengthListTg;
    }

    public float[] getPreListTg() {
        return PreListTg;
    }

    public void setPreListTg(float[] preListTg) {
        PreListTg = preListTg;
    }

    public float[] getChangeListTg() {
        return ChangeListTg;
    }

    public void setChangeListTg(float[] changeListTg) {
        ChangeListTg = changeListTg;
    }

    public String[] getShowLengthTg() {
        return showLengthTg;
    }

    public void setShowLengthTg(String[] showLengthTg) {
        this.showLengthTg = showLengthTg;
    }

    public String[] getShowWyTg() {
        return showWyTg;
    }

    public void setShowWyTg(String[] showWyTg) {
        this.showWyTg = showWyTg;
    }

    public void removeRunnable() {
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ComA.close();
        removeRunnable();
        //传感器是否连接全部置为false
        MyApp.wy1Connected = false;
        MyApp.wy2Connected = false;
        MyApp.wy3Connected = false;
        MyApp.wy4Connected = false;
        MyApp.wy5Connected = false;
        MyApp.wy6Connected = false;
        MyApp.wy7Connected = false;
        MyApp.jsdConnected = false;
    }


}
