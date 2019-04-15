package com.parachute.test;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.flyco.tablayout.listener.OnTabSelectListener;
import com.greendao.dbUtils.GreateTaskUtils;
import com.greendao.manager.DataFZQ;
import com.jaeger.library.StatusBarUtil;
import com.ldoublem.loadingviewlib.view.LVPlayBall;
import com.sensor.SensorData;
import com.sensor.view.SensorView;
import com.parachute.Adapter.TestPagerAdapter;

import com.parachute.Tools.MyFunction;

import com.parachute.administrator.DATAbase.R;
import com.parachute.administrator.DATAbase.greendao.TaskEntity;
import com.parachute.administrator.DATAbase.greendao.TaskResEnity;
import com.parachute.app.MyApp;

import com.parachute.serialport.ComAssistant.SerialHelper;
import com.parachute.serialport.bean.ComBean;
import com.parachute.test.fragment.DataFragment;
import com.parachute.test.fragment.ParameterFragment;
import com.parachute.test.fragment.TestFragment;


import java.io.IOException;
import java.nio.charset.Charset;
import java.security.InvalidParameterException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

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
    public DataFZQ mdata;


    SerialControl ComA;
    DispQueueThread DispQueue;
    ParameterFragment parameterFragment;
    DataFragment dataFragment;
    TestFragment testFragment;
    DecimalFormat df4 = new DecimalFormat("####00.00");
    DecimalFormat df2 = new DecimalFormat("####0.00");

    DecimalFormat df3 = new DecimalFormat("####0.000");

    DecimalFormat df1 = new DecimalFormat("####0.0");


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
    public static int TxDelay = 10;


    float SUMJSD = 0;
    private boolean IsReady;
    private boolean IsWait;
    Boolean IsBuzy = false;
    float[] LengthList = new float[8];// 当前距离
    float[] PreList = new float[8];// 之前距离，参照用
    float[] ChangeList = new float[8];// 位移距离，保存用
    String[] showLength;//当前距离，送显
    String[] showWy;//位移差，送显

    private int indexXk1;
    private int indexXk2;
    private int indexXdzds1;
    private int indexXdzds2;
    private int indexXdhcs1;
    private int indexXdhcs2;
    private int indexXjgd;
    private int indexWd;
    private int indexJsd;
    private long pretime = 0;
    private TaskEntity mtask;

    public static volatile long[] IsTx = new long[8]; // 测试线程修改参数
    public static volatile long CaijiTime = 0;
    private int Leixing;

    List<byte[]> mJsdBuffer = new ArrayList<byte[]>();

    public int getJsdCount() {
        return JsdCount;
    }

    public void setJsdCount(int jsdCount) {
        JsdCount = jsdCount;
    }

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

    public boolean isInit() {
        return IsInit;
    }

    public void setInit(boolean init) {
        IsInit = init;
    }

    private boolean IsInit;
    private Handler handler;

    public Boolean getTg() {
        return IsTg;
    }

    public void setTg(Boolean tg) {
        IsTg = tg;
    }

    private Boolean IsTg = false;//是否在测试脱钩


    public Boolean getBuzy() {
        return IsBuzy;
    }

    public void setBuzy(Boolean buzy) {
        IsBuzy = buzy;
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


    public boolean isStart() {

        if (IsStart == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isDown() {

        if (IsStart == 2) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isWait() {

        if (IsStart == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void setStart(Integer start) {
        IsStart = start;
    }


    public synchronized void SetFiveOne(int index) {
        IsTx[index] = System.currentTimeMillis();
    }

    public synchronized void SetCaiji() {
        CaijiTime = System.currentTimeMillis();
    }

    private long TimeBetween(Long mTime) {
        return System.currentTimeMillis() - mTime;
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
//                        onDestroy();


                    }
                }).setNegativeButton("未完成", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
//                        onDestroy();
                    }
                }).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.tittleBar), 0);
        setBackArrowStyle();
        myApp = MyApp.getInstance();
        handler = new Handler();
        showLength = new String[8];
        showWy = new String[8];
        initSenserIndex();
        showLoading();
        getData();
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
        IsStart=0;

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mtask = MyApp.getTaskEnity();
        ComA = new SerialControl();
        setControls();
        DispQueueStart();
        ComA.setiDelay(10);  // 设置读取串口的时间间隔

        if (IsInit == false) {

            handler.postDelayed(runnable, 1000);
            IsInit = true;
        }
    }

    private void initSenserIndex() {
        // TODO Auto-generated method stub

        indexXk1 = 1;
        indexXk2 = 2;
        indexXdzds1 = 5;
        indexXdzds2 = 6;
        indexXdhcs1 = 3;
        indexXdhcs2 = 4;
        indexXjgd = 7;
        indexWd = 0;
        indexJsd = 0;

    }

    //    ==============================串口控制相关方法================================================//
    // ----------------------------------------------------设置串口
    private void setControls() {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            ComA.setPort("/dev/ttyMT2");
        } else {
            ComA.setPort("/dev/ttyMT1");
        }

        ComA.setBaudRate("115200");
        OpenComPort(ComA);
    }

    //----------------------------------------------------串口控制类
    private class SerialControl extends SerialHelper {

        public SerialControl() {
        }

        protected void onDataReceived(ComBean ComRecData) {
            // TODO Auto-generated method stub
            DispQueue.AddQueue(ComRecData); //线程定时刷新显示(推荐)
        }
    }

    public void DispQueueStart() {
        //串口控制
        ComA = new SerialControl();
        DispQueue = new DispQueueThread();
        setControls();
        DispQueue.start();
    }

    /**
     * function ：打开串口
     */
    public void OpenComPort(SerialHelper ComPort) {
        try {
            ComPort.open();
        } catch (SecurityException e) {
            Toasty.info(this, "打开串口失败:没有串口读/写权限!");
        } catch (IOException e) {
            Toasty.info(this, "打开串口失败:未知错误!");
        } catch (InvalidParameterException e) {
            Toasty.info(this, "打开串口失败:参数错误!");
        }
    }


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
//        parameterFragment = new ParameterFragment();
        dataFragment = new DataFragment();
        dataFragment.setDataManage(false);
        testFragment = new TestFragment();


        //构造适配器
        List<Fragment> fragments = new ArrayList<>();
//        fragments.add(parameterFragment);
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
//                    case 2:
//                        tl.setCurrentTab(2, true);
//                        break;
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

    @Override
    protected void onStart() {
        super.onStart();
        // DataType.DATA_OK_PARSE : 返回整的串口数据包
        // DataType.DATA_NO_PARSE : 返回不进行校验的数据，不按完整数据包返回。


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ComA.close();

        handler.removeCallbacksAndMessages(null);

    }


    //----------------------------------------------------刷新显示线程
    private class DispQueueThread extends Thread {
        private Queue<ComBean> QueueList = new LinkedList<ComBean>();

        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                final ComBean ComData;

                try {
                    while ((ComData = QueueList.poll()) != null) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                DispRecData(ComData);
                            }
                        });
                        try {
                            Thread.sleep(10);//显示性能高的话，可以把此数值调小。
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public synchronized void AddQueue(ComBean ComData) {
            QueueList.add(ComData);
        }
    }

    private void DispRecData(ComBean comRecData) {

        xianshi(comRecData);
    }

    // ----------------smilekun---------------------------------——显示线程中测试数据解析加显示

    void xianshi(ComBean ComRecData) {

        int msingal, mpower;
        // 获取加速度数据
        if (ComRecData.bRec.length > 9) {
        }
        // 如果没有进行传感器设置，会进行传感器检测 （完善重新发送配置指令 ）

        if (ComRecData.bRec.length > 9) {
            Leixing = Math.abs((int) ComRecData.bRec[9]);
        }
        switch (Leixing) {

            case 95:// 激光测距
                int ki = 12;
                int Lcount = ComRecData.bRec[10];// 传感器编号
                byte[] LbufferLA = new byte[8];
                LbufferLA[0] = ComRecData.bRec[ki + 2];
                LbufferLA[1] = ComRecData.bRec[ki + 3];
                LbufferLA[2] = ComRecData.bRec[ki + 4];
                LbufferLA[3] = ComRecData.bRec[ki + 5];
                LbufferLA[4] = ComRecData.bRec[ki + 6];
                LbufferLA[5] = ComRecData.bRec[ki + 7];
                LbufferLA[6] = ComRecData.bRec[ki + 8];
                LbufferLA[7] = ComRecData.bRec[ki + 9];
                // 距离
                String Length = (new String(LbufferLA, 1, 7,
                        Charset.forName("ASCII")));

                try {
                    LengthList[Lcount] = Float.parseFloat(Length) * 1000 + Float.parseFloat(Length.substring(4, 5)) / 10 + (float) (Math.random() - 0.5) / 10;
                    if (IsStart != 2)//点击保存以后，点击停止之前
                    {
                        ChangeList[Lcount] = Math.abs(LengthList[Lcount]
                                - PreList[Lcount]);
                    }

                    msingal = ComRecData.bRec[23] < 0 ? 256 + ComRecData.bRec[23] : ComRecData.bRec[23];
                    mpower = MyFunction.twoBytesToInt(ComRecData.bRec, 21);
                    mdata.setSensor(Lcount, mpower, msingal, 1);
                    SetFiveOne(Lcount);

                    break;
                } catch (Exception e) {
                }
                break;
            case 92: // 加速度
                if (ComRecData.bRec[13] == 7) {//心跳包
//                        TVEdtxTgJsd.setBackground(drawable);
//                        TVSearchJsd.setText("加速度传感器已连接");
                    if (!IsReady) {
                        IsReady = false;
                        IsBuzy = false;
                        IsWait = true;
                    }
                    msingal = ComRecData.bRec[17] < 0 ? 256 + ComRecData.bRec[17] : ComRecData.bRec[17];
                    mpower = MyFunction.twoBytesToInt(ComRecData.bRec, 15);
                    mdata.setSensor(0, mpower, msingal, 1);

                    // SetJsdWait();
                } else if (ComRecData.bRec[13] == 1) {//数据传输

                    IsReady = false;
                    IsBuzy = true;
                    IsWait = false;
                    switch (ComRecData.bRec[14] % 4) {
                    }

                    mJsdBuffer.add(ComRecData.bRec);

                    JsdCount = ComRecData.bRec[14];

                    if (IsBuzy && ComRecData.bRec[14] >= 51) {
                        IsBuzy = false;
                        testFragment.setstartenable();
                    }
                    if (ComRecData.bRec[14] >= 59) {
                        setStart(2);
                        Draw();
                        drawrefresh();
//                            TVSearchJsd.setText("加速度数据采集完成！");
//                            TVEdtxTgJsd.setBackground(drawable);


                    }
                    msingal = ComRecData.bRec[17] < 0 ? 256 + ComRecData.bRec[17] : ComRecData.bRec[17];
                    mpower = MyFunction.twoBytesToInt(ComRecData.bRec, 15);
                    mdata.setSensor(0, mpower, msingal, 1);
                } else if (ComRecData.bRec[13] == 2) {//准备就绪
                    IsReady = true;
                    IsWait = false;
                    IsBuzy = false;
//                        StopButton.setEnabled(true);
//                        StartButton.setEnabled(false);
//                        ExitButton.setEnabled(false);
//                        FenxiButton.setEnabled(false);
//                        ReSendButton.setEnabled(false);
                }
                SetFiveOne(indexJsd);

                break;
        }

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

//            if (shuaXin) {
            DataFZQ.sensor[] ms = mdata.getSensors();

            for (int i = 0; i < 8; i++) {
                showLength[i] = df2.format(LengthList[i]) + "mm";
                showWy[i] = df2.format(ChangeList[i]) + "mm";
            }

            if ((TimeBetween(IsTx[0]) < 1000 || IsReady)) {
                testFragment.msensorfragment.SetSensor("加速度", ms[0].getMpower(), ms[0].getMsignal(), 1);

                testFragment.setJsdState(5);
            } else if (TimeBetween(IsTx[0]) > 1000 * TxDelay) {
                testFragment.setJsdState(0);
            }
            if (TimeBetween(IsTx[1]) < 1000) {
                testFragment.msensorfragment.SetSensor("测距1", ms[1].getMpower(), ms[1].getMsignal(), 1);
            } else if (TimeBetween(IsTx[1]) > 1000 * TxDelay) {
                showLength[1] = "-- mm";
            }
            if (TimeBetween(IsTx[2]) < 1000) {
                testFragment.msensorfragment.SetSensor("测距2", ms[2].getMpower(), ms[2].getMsignal(), 1);
            } else if (TimeBetween(IsTx[2]) > 1000 * TxDelay) {
                showLength[2] = "-- mm";
            }
            if (TimeBetween(IsTx[3]) < 1000) {
                testFragment.msensorfragment.SetSensor("测距3", ms[3].getMpower(), ms[3].getMsignal(), 1);
            } else if (TimeBetween(IsTx[3]) > 1000 * TxDelay) {
                showLength[3] = "-- mm";
            }
            if (TimeBetween(IsTx[4]) < 1000) {
                testFragment.msensorfragment.SetSensor("测距4", ms[4].getMpower(), ms[4].getMsignal(), 1);
            } else if (TimeBetween(IsTx[4]) > 1000 * TxDelay) {
                showLength[4] = "-- mm";
            }
            if (TimeBetween(IsTx[5]) < 1000) {
                testFragment.msensorfragment.SetSensor("测距5", ms[5].getMpower(), ms[5].getMsignal(), 1);
            } else if (TimeBetween(IsTx[5]) > 1000 * TxDelay) {
                showLength[5] = "-- mm";
            }
            if (TimeBetween(IsTx[6]) < 1000) {
                testFragment.msensorfragment.SetSensor("测距6", ms[6].getMpower(), ms[6].getMsignal(), 1);
            } else if (TimeBetween(IsTx[6]) > 1000 * TxDelay) {
                showLength[6] = "-- mm";
            }
            if (TimeBetween(IsTx[7]) < 1000) {
                testFragment.msensorfragment.SetSensor("测距7", ms[7].getMpower(), ms[7].getMsignal(), 1);
            } else if (TimeBetween(IsTx[7]) > 1000 * TxDelay) {
                showLength[7] = "-- mm";
            }


            if (System.currentTimeMillis() > pretime + 1000) {
                pretime = System.currentTimeMillis();
                if (!IsReady) {
                    if (testFragment.getJsdState() != 0) {
                        testFragment.setJsdState(4);
                    }
                }
                if (!IsBuzy && !IsReady && !IsWait) {
//                    Draw();
//                    drawrefresh();
//                    Toasty.info(TestActivity.this, "Draw");


                }
                refresh();

            } else if (testFragment.getJsdState() != 0) {
                if (IsBuzy) {
                    testFragment.setJsdState(3);
                }
                if (IsWait) {
                    testFragment.setJsdState(1);
                }
                if (IsReady) {
                    testFragment.setJsdState(2);
                }
            }

            // }

            handler.postDelayed(this, 250);
        }
    };

    public void refresh() {

        TestFragment.refresh();
    }

    public void initdata() {
        dataFragment.init();
    }

    public boolean SaveData() {
        boolean isSave = false;
        TaskResEnity mres = new TaskResEnity();
        mdata.CopyRes(mres);
        try {
            mres.setSaveType(IsTg ? 1 : 0);
            mres.setXk1(ChangeList[indexXk1]);
            mres.setXk2(ChangeList[indexXk1]);
            mres.setXdzds1(ChangeList[indexXk1]);
            mres.setXdzds2(ChangeList[indexXk1]);
            mres.setXdhcs1(ChangeList[indexXk1]);
            mres.setXdhcs2(ChangeList[indexXk1]);
            if (IsTg) {
                mres.setXjgd(ChangeList[indexXk1]);
                String CurveData = mDrawList[0] + "";
                for (int i = 1; i < mDrawList.length; i++) {

                    CurveData = CurveData + "|" + mDrawList[i];
                }
                mres.setCurve(CurveData);
                mres.setZdjsd(mJsd);
                mres.setPjjsd(mPjJsd);
                mres.setKxcjl(mKxcjl);
                mres.setKxcsj(mKxcsj);
                mres.setZdsj(mZdsj);
            }
            mres.setSaveTime(getSysTime());
            MyApp.getDaoInstant().getTaskResEnityDao().insert(mres);
            dataFragment.GetData();
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

    // -------------------------------------------------——-------------------获取系统时间
    String getSysTime() {
// shijain
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

    private void SetSensor(SensorView msv, Integer index) {
        DataFZQ.sensor mS = mdata.getSensors()[index];
        SensorData svData = new SensorData();
        // 第二步：设置 SensorData 属性
        svData.setStatus(mS.getMstate())
                .setPower(mS.getMpower())
                .setSignal(mS.getMsignal());

        // 第三步：给SensorView 赋值
        msv.setData(svData);
    }

    public void StartJsd() {
        if (IsStart == 2)//点击停止后
        {

        } else if (IsStart == 1)//点击开始后
        {
            for (int i = 0; i < 8; i++) {
                PreList[i] = LengthList[i];
            }
            if (IsTg) {
                mJsdBuffer = new ArrayList<byte[]>();
                new Handler().postDelayed(new Runnable() {

                    public void run() {

                        sendPortData(ComA, "4B590CA40100010206A401000002010a"); // 发送加速度准备
                    }
                }, 100);
            }
        } else if (IsStart == 0)//点击保存后
        {
            for (int i = 0; i < 8; i++) {
                PreList[i] = 0;
                ChangeList[i]=0;
            }
        }


    }

    public void drawrefresh() {
        fenxi();
        testFragment.drawjsd();
    }


    // ----------------------------------------------------串口发送
    private void sendPortData(SerialHelper ComPort, String sOut) {
        if (ComPort != null && ComPort.isOpen()) {
            ComPort.sendHex(sOut);
        }
    }

    public void fenxi() {
        DecimalFormat df4 = new DecimalFormat("####00.00");
        DecimalFormat df2 = new DecimalFormat("####00");
        // TODO Auto-generated method stub
        // 空行程时间：稳定状态时间 判断拐点，突变点
        //
        // 空行程距离：空行程时间和稳定加速度计算
        //
        // 最大加速度：筛选得到
        //
        // 平均加速度：待定
        //
        // 制动时间：判断拐点。
        //
        //
        // 遍历->1 存入pre和max（？）
        // 2及以后，与pre(+?)-exKxc判断，未超限，下一个
        // 超限，置拐点标志位,存入endkxc与startzd，存入max
        // 继续遍历，与0+-enZero判断。进入范围，存入EndZd，置零点标志位
        // 遍历结束，重置标志位

        try {
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

                // mKxcsj1 = 0;
                maxSd = 0;
                // maxDis = 0;
                // mZdsj1 = 0;
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
                float mXjgd = Math.abs(LengthList[indexXjgd]
                        - PreList[indexXjgd]);
                float mZds = Math.max(Math.abs(LengthList[indexXdzds1]
                        - PreList[indexXdzds1]), Math
                        .abs(LengthList[indexXdzds2] - PreList[indexXdzds2]));
                float mHcs = Math.min(Math.abs(LengthList[indexXdhcs1]
                        - PreList[indexXdhcs1]), Math
                        .abs(LengthList[indexXdhcs2] - PreList[indexXdhcs2]));
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
//                    Toast.makeText(Test_TG_Activity.this, "正在绘制曲线。", 0).show();
                }
                // zdsj = (float) (stG * mKxcsj * 1000 * 4 / (tmpPjjsd));
//                Toast.makeText(Test_TG_Activity.this, "正在绘制曲线。。。", 0).show();

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

                // float mmkxcjl = 0.0f;
                // for (int i = 1; i < mSdList.length; i++) {
                //
                // // 前距离+（速度1+速度2）/2 * 时间（1/1000/4）*1000
                // mDisList[i] = mDisList[i - 1]
                // + (mSdList[i] + mSdList[i - 1]) / 2 / 1000 / 4
                // * 1000;
                //
                // if (i < EndKxc) {
                // mmkxcjl = mmkxcjl + (mSdList[i] + mSdList[i - 1]) / 2
                // / 1000 / 4 * 1000;
                // }
                // }
                // mKxcjl = mmkxcjl;

                // 计算相关值
                mJsd = ((float) MaxJsd);

                // mKxcsj = (float) ((EndKxc-StartKxc) * 0.25 / 1000);
                mKxcsj = (float) ((EndKxc) * 0.25 / 1000);
                mZdsj = (float) (zdsj / 1000);
                mKxcjl = (float) (0.5 * stG * mKxcsj * mKxcsj * 1000);


//                TVEdTgKxcsj.setText(df4.format(mKxcsj * 1000));
//
//                TVEdTgKxcjl.setText(df4.format(0.5 * stG * mKxcsj * mKxcsj
//                        * 1000));
                mPjJsd = tmpPjjsd;
//
//                TVEdTgJsd.setText(df4.format(0 - Math.abs(mJsd)));
//                TVEdTgPjjsd.setText(df4.format(0 - Math.abs(tmpPjjsd)));
//                TVEdTgZdjl.setText(df4.format(mZdsj / 4 * 1000));


                // float endSd = (mSdList[mSdList.length - 1]);
                // TVEdTgZdjl.setText((df2.format(mZdsj * 1000) + "位移"
                // + df2.format(maxDis) + "倒制" + df2.format(mZdsj2 * 1000)
                // + "速空" + df4.format(mKxcsj1 * 1000))
                // + "末速" + df4.format(endSd));
            }
//            view = new DrawViewJsd(this);
//
//            view.SetSpeedDrawOrNot(DrawSd);
//            view.SetDisplacementDrawOrNot(DrawDis);
//            view.setMinimumHeight(550);
//            view.setMinimumWidth(290);
//            view.SetPoint(50, 50);
//            view.invalidate();
//            // view.SetResourceDataY(mJsdList);
//            view.SetResourceDataY(mDrawList);
//            view.SetResourceDataU(mSdList);
//            view.SetResourceDataD(mDisList);
//            breathWave.removeAllViews();
//            breathWave.addView(view);
        } catch (Exception e) {
            String a = e.toString();
        }
    }

    @SuppressLint("CommitTransaction")
    private void Draw() {
        int mIndex = 0;


        MaxIndex = 0;// 最大加速度点索引号
        MaxJsd = 0;// 最大加速度
        try {
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
                        if (isDown) {
                            mJsdList[mIndex] = (int) tmp / 10;
                        } else {
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

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
}
