package com.parachute.main;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.greendao.manager.DataFZQ;
import com.jaeger.library.StatusBarUtil;
import com.parachute.Tools.MyFunction;
import com.parachute.administrator.DATAbase.R;
import com.sensor.SensorData;
import com.sensor.view.SensorView;
import com.xzkydz.bean.ComBean;
import com.xzkydz.helper.ComControl;
import com.xzkydz.helper.SerialHelper;
import com.xzkydz.util.DataType;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.InvalidParameterException;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Queue;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class SensorActivity extends AppCompatActivity {


    private static int Leixing;
    static float[] LengthList = new float[8];// 当前距离
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.wy_test_1)
    SensorView wyTest1;
    @BindView(R.id.wy_test_2)
    SensorView wyTest2;
    @BindView(R.id.wy_test_3)
    SensorView wyTest3;
    @BindView(R.id.wy_test_4)
    SensorView wyTest4;
    @BindView(R.id.wy_test_5)
    SensorView wyTest5;
    @BindView(R.id.wy_test_6)
    SensorView wyTest6;
    @BindView(R.id.wy_test_7)
    SensorView wyTest7;
    @BindView(R.id.jsd_test)
    SensorView jsdTest;

    DecimalFormat df2 = new DecimalFormat("####0.00");

    String[] showLength = new String[8];//当前距离，送显
    @BindView(R.id.tv_test_tg_length1)
    TextView tvTestTgLength1;
    @BindView(R.id.tv_test_tg_length2)
    TextView tvTestTgLength2;
    @BindView(R.id.tv_test_tg_length3)
    TextView tvTestTgLength3;
    @BindView(R.id.tv_test_tg_length4)
    TextView tvTestTgLength4;
    @BindView(R.id.tv_test_tg_length5)
    TextView tvTestTgLength5;
    @BindView(R.id.tv_test_tg_length6)
    TextView tvTestTgLength6;
    @BindView(R.id.tv_test_tg_length7)
    TextView tvTestTgLength7;
    @BindView(R.id.tv_test_tg_length8)
    TextView tvTestTgLength8;
    @BindView(R.id.btn_show)
    Button btnShow;
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.ll2)
    LinearLayout ll2;
    @BindView(R.id.ll3)
    LinearLayout ll3;
    @BindView(R.id.ll4)
    LinearLayout ll4;
    private boolean shuaXin;
    private static float[] mFsList;
    private static long pretime = 0;


    public static volatile long[] IsTx = new long[21]; // 测试线程修改参数
    public static volatile long CaijiTime = 0;
    public static int TxDelay = 5;
    private float JyZero = 0f;
    private float CyZero = 0f;
    private static float Jy = 0f;
    private static float Cy = 0f;
    public static DataFZQ mdata;

    private int indexXk1;
    private int indexXk2;
    private int indexXdzds1;
    private int indexXdzds2;
    private int indexXdhcs1;
    private int indexXdhcs2;
    private int indexXjgd;
    private int indexWd;
    private static int indexJsd;

    //<editor-fold desc="Description">
    private boolean IsStart;
    //</editor-fold>
    private Handler handler;
    static DecimalFormat df4 = new DecimalFormat("####0.00");

    SerialControl ComA;
    DispQueueThread DispQueue;
    private boolean showdata = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //保持屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        this.setContentView(R.layout.activity_sensor);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //决定左上角的图标是否可以点击
        StatusBarUtil.setColor(this, getResources().getColor(R.color.tittleBar), 0);
        mdata = new DataFZQ();
        mdata.initSensors();
        handler = new Handler();

        mFsList = new float[16];
        shuaXin = true;
        indexXk1 = 1;
        indexXk2 = 2;
        indexXdzds1 = 5;
        indexXdzds2 = 6;
        indexXdhcs1 = 3;
        indexXdhcs2 = 4;
        indexXjgd = 7;
        indexWd = 0;
        indexJsd = 0;

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ComA = new SerialControl(this, DataType.DATA_OK_PARSE);
        ComA.setiDelay(50);
        DispQueueStart();
        ComControl.OpenComPort(ComA);
        if (IsStart == false) {
            handler.postDelayed(runnable, 1000);
            IsStart = true;
        }

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

    @OnClick(R.id.btn_show)
    public void onViewClicked() {
        if (showdata)//当前状态为显示
        {
            showdata = false;
            btnShow.setBackgroundResource(R.drawable.cgq_float_xs);
            ll1.setVisibility(View.GONE);
            ll2.setVisibility(View.GONE);
            ll3.setVisibility(View.GONE);
            ll4.setVisibility(View.GONE);
        } else {
            showdata = true;
            btnShow.setBackgroundResource(R.drawable.cgq_float_yc);
            ll1.setVisibility(View.VISIBLE);
            ll2.setVisibility(View.VISIBLE);
            ll3.setVisibility(View.VISIBLE);
            ll4.setVisibility(View.VISIBLE);
        }
    }

    //----------------------------------------------------刷新显示线程


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

        xianshi(shuaXin, comRecData);
    }

    // ----------------smilekun---------------------------------——显示线程中测试数据解析加显示

    static void xianshi(boolean xianshi, ComBean ComRecData) {

        int msingal, mpower;
        // 获取加速度数据
        if (ComRecData.recData.length > 9) {
        }
        // 如果没有进行传感器设置，会进行传感器检测 （完善重新发送配置指令 ）

        if (ComRecData.recData.length > 9) {
            Leixing = Math.abs((int) ComRecData.recData[9]);
        }
        switch (Leixing) {

            case 95:// 激光测距
                int ki = 12;
                int Lcount = ComRecData.recData[10];// 传感器编号
                byte[] LbufferLA = new byte[8];
                LbufferLA[0] = ComRecData.recData[ki + 2];
                LbufferLA[1] = ComRecData.recData[ki + 3];
                LbufferLA[2] = ComRecData.recData[ki + 4];
                LbufferLA[3] = ComRecData.recData[ki + 5];
                LbufferLA[4] = ComRecData.recData[ki + 6];
                LbufferLA[5] = ComRecData.recData[ki + 7];
                LbufferLA[6] = ComRecData.recData[ki + 8];
                LbufferLA[7] = ComRecData.recData[ki + 9];
                // 距离
                String Length = (new String(LbufferLA, 1, 7,
                        Charset.forName("ASCII")));

                try {
                    LengthList[Lcount] = Float.parseFloat(Length) * 1000 + Float.parseFloat(Length.substring(4, 5)) / 10 + (float) (Math.random() - 0.5) / 10;


                    msingal = ComRecData.recData[23] < 0 ? 256 + ComRecData.recData[23] : ComRecData.recData[23];
                    mpower = MyFunction.twoBytesToInt(ComRecData.recData, 21);
                    mdata.setSensor(Lcount, mpower, msingal, 1);
                    SetFiveOne(Lcount);

                    break;
                } catch (Exception e) {
                }
                break;
            case 92: // 加速度
                if (ComRecData.recData[13] == 7) {//心跳包
//                        TVEdtxTgJsd.setBackground(drawable);
//                        TVSearchJsd.setText("加速度传感器已连接");

                    msingal = ComRecData.recData[17] < 0 ? 256 + ComRecData.recData[17] : ComRecData.recData[17];
                    mpower = MyFunction.twoBytesToInt(ComRecData.recData, 15);
                    mdata.setSensor(0, mpower, msingal, 1);
                    // SetJsdWait();
                }
                SetFiveOne(indexJsd);

                break;
        }
        // ShowData();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            DataFZQ.sensor[] ms = mdata.getSensors();

            for (int i = 0; i < 8; i++) {
                showLength[i] = df2.format(LengthList[i]) + "mm";

            }

            if ((TimeBetween(IsTx[0]) < 1000)) {
                SetSensor("加速度", ms[0].getMpower(), ms[0].getMsignal(), 1);
            }
            if (TimeBetween(IsTx[1]) < 1000) {
                SetSensor("测距1", ms[1].getMpower(), ms[1].getMsignal(), 1);
            } else if (TimeBetween(IsTx[1]) > 1000 * TxDelay) {
                showLength[1] = "-- mm";
            }
            if (TimeBetween(IsTx[2]) < 1000) {
                SetSensor("测距2", ms[2].getMpower(), ms[2].getMsignal(), 1);
            } else if (TimeBetween(IsTx[2]) > 1000 * TxDelay) {
                showLength[2] = "-- mm";
            }
            if (TimeBetween(IsTx[3]) < 1000) {
                SetSensor("测距3", ms[3].getMpower(), ms[3].getMsignal(), 1);
            } else if (TimeBetween(IsTx[3]) > 1000 * TxDelay) {
                showLength[3] = "-- mm";
            }
            if (TimeBetween(IsTx[4]) < 1000) {
                SetSensor("测距4", ms[4].getMpower(), ms[4].getMsignal(), 1);
            } else if (TimeBetween(IsTx[4]) > 1000 * TxDelay) {
                showLength[4] = "-- mm";
            }
            if (TimeBetween(IsTx[5]) < 1000) {
                SetSensor("测距5", ms[5].getMpower(), ms[5].getMsignal(), 1);
            } else if (TimeBetween(IsTx[5]) > 1000 * TxDelay) {
                showLength[5] = "-- mm";
            }
            if (TimeBetween(IsTx[6]) < 1000) {
                SetSensor("测距6", ms[6].getMpower(), ms[6].getMsignal(), 1);
            } else if (TimeBetween(IsTx[6]) > 1000 * TxDelay) {
                showLength[6] = "-- mm";
            }
            if (TimeBetween(IsTx[7]) < 1000) {
                SetSensor("测距7", ms[7].getMpower(), ms[7].getMsignal(), 1);
            } else if (TimeBetween(IsTx[7]) > 1000 * TxDelay) {
                showLength[7] = "-- mm";
            }


            if (System.currentTimeMillis() > pretime + 1000) {
                pretime = System.currentTimeMillis();

                refresh();


            }

            // }

            handler.postDelayed(this, 250);
        }
    };

    private void refresh() {
        tvTestTgLength1.setText(showLength[1]);
        tvTestTgLength2.setText(showLength[2]);
        tvTestTgLength3.setText(showLength[3]);
        tvTestTgLength4.setText(showLength[4]);
        tvTestTgLength5.setText(showLength[5]);
        tvTestTgLength6.setText(showLength[6]);
        tvTestTgLength7.setText(showLength[7]);
    }

    public static synchronized void SetFiveOne(int index) {
        IsTx[index] = System.currentTimeMillis();
    }


    private long TimeBetween(Long mTime) {
        return System.currentTimeMillis() - mTime;
    }

    private static void SetSensorState(SensorView msv, float mpower, float msignal, int minf) {
        SensorData svData = new SensorData();
        // 第二步：设置 SensorData 属性
        svData.setStatus(minf)
                .setPower(mpower)
                .setSignal(msignal);

        // 第三步：给SensorView 赋值
        msv.setData(svData);
    }

    private static void SetSensor(SensorView msv, Integer index) {
        try {
            DataFZQ.sensor mS = mdata.getSensors()[index];
            if (mS != null) {
                SensorData svData = new SensorData();
                // 第二步：设置 SensorData 属性
                svData.setStatus(mS.getMstate())
                        .setPower(mS.getMpower())
                        .setSignal(mS.getMsignal());

                // 第三步：给SensorView 赋值
                msv.setData(svData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //----------------------------------------------------串口控制类
    private class SerialControl extends SerialHelper {
        public SerialControl(Context context, int mDataType) {
            super(context, mDataType);
        }

        public SerialControl(Context context, String sPort, String sBaudRate, int mDataType) {
            super(context, sPort, sBaudRate, mDataType);
        }

        @Override
        protected void onDataReceived(ComBean comBean) {
            DispQueue.AddQueue(comBean); //线程定时刷新显示(推荐)
        }

    }

    public void DispQueueStart() {
        DispQueue = new DispQueueThread();
        DispQueue.start();
    }


    public void SetSensor(String str, float mpower, float msignal, int minf) {
        try {
            switch (str) {
                case "测距1":
                    SetSensorState(wyTest1, mpower, msignal, minf);
                    break;
                case "测距2":
                    SetSensorState(wyTest2, mpower, msignal, minf);
                    break;
                case "测距3":
                    SetSensorState(wyTest3, mpower, msignal, minf);
                    break;
                case "测距4":
                    SetSensorState(wyTest4, mpower, msignal, minf);
                    break;
                case "测距5":
                    SetSensorState(wyTest5, mpower, msignal, minf);
                    break;
                case "测距6":
                    SetSensorState(wyTest6, mpower, msignal, minf);
                    break;
                case "测距7":
                    SetSensorState(wyTest7, mpower, msignal, minf);
                    break;
                case "加速度":
                    SetSensorState(jsdTest, mpower, msignal, minf);
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
