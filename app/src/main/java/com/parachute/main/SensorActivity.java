package com.parachute.main;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.greendao.manager.DataFZQ;
import com.jaeger.library.StatusBarUtil;
import com.parachute.Tools.MyFunction;
import com.parachute.administrator.DATAbase.R;
import com.parachute.app.MyApp;
import com.parachute.bean.ISensorInf;
import com.parachute.utils.CGQSerialControl;
import com.sensor.SensorData;
import com.sensor.SensorInf;
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

    private boolean IsStart;
    private Handler handler;
    static DecimalFormat df4 = new DecimalFormat("####0.00");

    CGQSerialControl comA;
    float[] LengthListTg = new float[8];// 当前距离
    private boolean showdata = true;
    private SensorActivity instance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //保持屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        instance = this;
        this.setContentView(R.layout.activity_sensor);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //决定左上角的图标是否可以点击
        StatusBarUtil.setColor(this, getResources().getColor(R.color.tittleBar), 0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //关闭硬件加速
        wyTest1.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        wyTest2.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        wyTest3.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        wyTest4.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        wyTest5.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        wyTest6.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        wyTest7.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        jsdTest.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        handler = new Handler();
        comA = new CGQSerialControl(instance, DataType.DATA_OK_PARSE);
        comA.setiDelay(50);
        ComControl.OpenComPort(comA);
        onReceivedSensorData();//给各传感器设置电量信号
        if (!IsStart) {
            handler.post(runnable);
            IsStart = true;
        }

        //给传感器设置断开监听
        wyTest1.setOnStatusChangeListener(new MyStatusChangeListener());
        wyTest2.setOnStatusChangeListener(new MyStatusChangeListener());
        wyTest3.setOnStatusChangeListener(new MyStatusChangeListener());
        wyTest4.setOnStatusChangeListener(new MyStatusChangeListener());
        wyTest5.setOnStatusChangeListener(new MyStatusChangeListener());
        wyTest6.setOnStatusChangeListener(new MyStatusChangeListener());
        wyTest7.setOnStatusChangeListener(new MyStatusChangeListener());
        jsdTest.setOnStatusChangeListener(new MyStatusChangeListener());
    }

    private void onReceivedSensorData() {
        comA.setOnReceivedSensorListener(new CGQSerialControl.OnReceivedSensorData() {
            @Override
            public void updateSensor(final ISensorInf sensorInf) {
                switch (sensorInf.getSensorType()) {
                    case 1:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SensorData sensorData1 = new SensorData(sensorInf.getPower(), sensorInf.getSignal(), SensorInf.NORMAL, System.currentTimeMillis());
                                if (wyTest1 != null) {
                                    wyTest1.setData(sensorData1);
                                }
                            }
                        });
                        break;
                    case 2:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SensorData sensorData2 = new SensorData(sensorInf.getPower(), sensorInf.getSignal(), SensorInf.NORMAL, System.currentTimeMillis());
                                if (wyTest2 != null) {
                                    wyTest2.setData(sensorData2);
                                }
                            }
                        });
                        break;
                    case 3:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SensorData sensorData3 = new SensorData(sensorInf.getPower(), sensorInf.getSignal(), SensorInf.NORMAL, System.currentTimeMillis());
                                if (wyTest3 != null) {
                                    wyTest3.setData(sensorData3);
                                }
                            }
                        });
                        break;
                    case 4:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SensorData sensorData4 = new SensorData(sensorInf.getPower(), sensorInf.getSignal(), SensorInf.NORMAL, System.currentTimeMillis());
                                if (wyTest4 != null) {
                                    wyTest4.setData(sensorData4);
                                }
                            }
                        });
                        break;
                    case 5:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SensorData sensorData5 = new SensorData(sensorInf.getPower(), sensorInf.getSignal(), SensorInf.NORMAL, System.currentTimeMillis());
                                if (wyTest5 != null) {
                                    wyTest5.setData(sensorData5);
                                }
                            }
                        });
                        break;
                    case 6:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SensorData sensorData6 = new SensorData(sensorInf.getPower(), sensorInf.getSignal(), SensorInf.NORMAL, System.currentTimeMillis());
                                if (wyTest6 != null) {
                                    wyTest6.setData(sensorData6);
                                }
                            }
                        });
                        break;
                    case 7:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SensorData sensorData7 = new SensorData(sensorInf.getPower(), sensorInf.getSignal(), SensorInf.NORMAL, System.currentTimeMillis());
                                if (wyTest7 != null) {
                                    wyTest7.setData(sensorData7);
                                }
                            }
                        });
                        break;
                    case 8:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SensorData sensorData8 = new SensorData(sensorInf.getPower(), sensorInf.getSignal(), SensorInf.NORMAL, System.currentTimeMillis());
                                if (jsdTest != null) {
                                    jsdTest.setData(sensorData8);
                                }
                            }
                        });
                        break;
                }
            }
        });
    }

    public class MyStatusChangeListener implements SensorView.OnStatusChangeListener {
        @Override
        public void status(View view, int i, int i1) {
            if (i1 == SensorInf.SEARCHING) {
                switch (view.getId()) {
                    case R.id.wy_test_1:
                        MyApp.wy1Connected = false;
                        Log.d("ooo", "楔块1中断: ");
                        Toast.makeText(instance, "楔块传感器1中断！", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.wy_test_2:
                        MyApp.wy2Connected = false;
                        Toast.makeText(instance, "楔块传感器2中断！", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.wy_test_3:
                        MyApp.wy3Connected = false;
                        Toast.makeText(instance, "制动传感器1中断！", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.wy_test_4:
                        MyApp.wy4Connected = false;
                        Toast.makeText(instance, "制动传感器2中断！", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.wy_test_5:
                        MyApp.wy5Connected = false;
                        Toast.makeText(instance, "缓冲传感器1中断！", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.wy_test_6:
                        MyApp.wy6Connected = false;
                        Toast.makeText(instance, "缓冲传感器2中断！", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.wy_test_7:
                        MyApp.wy7Connected = false;
                        Toast.makeText(instance, "下降传感器中断！", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.jsd_test:
                        Log.d("ooo", "加速度传感器中断: ");
                        MyApp.jsdConnected = false;
                        Toast.makeText(instance, "加速度传感器中断！", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //同时解析7个传感器的数据
            byte[] buffer0 = new byte[8];//每个传感器数据长度是8
            byte[] buffer1 = new byte[8];//每个传感器数据长度是8
            byte[] buffer2 = new byte[8];//每个传感器数据长度是8
            byte[] buffer3 = new byte[8];//每个传感器数据长度是8
            byte[] buffer4 = new byte[8];//每个传感器数据长度是8
            byte[] buffer5 = new byte[8];//每个传感器数据长度是8
            byte[] buffer6 = new byte[8];//每个传感器数据长度是8
            //解析七个位移传感器包（comBean在MyApp中静态保存）
            parserRecDataTg(buffer0, buffer1, buffer2, buffer3, buffer4, buffer5, buffer6);
            if (MyApp.wy1Connected) {
                //七个距离(注意：得到的数据第一位是 ： ，所以不取，从第二位开始取)
                String length0 = new String(buffer0, 1, 7, Charset.forName("ASCII"));
                if (!"".equals(length0)) {
                    //七个位移传感器的数据
                    LengthListTg[0] = Float.parseFloat(length0) * 1000 + (float) (Math.random() - 0.5) / 10;
                }
            }
            if (MyApp.wy2Connected) {
                String length1 = new String(buffer1, 1, 7, Charset.forName("ASCII"));
                if (!"".equals(length1)) {
                    LengthListTg[1] = Float.parseFloat(length1) * 1000 + (float) (Math.random() - 0.5) / 10;
                }
            }
            if (MyApp.wy3Connected) {
                String length2 = new String(buffer2, 1, 7, Charset.forName("ASCII"));
                if (!"".equals(length2)) {
                    LengthListTg[2] = Float.parseFloat(length2) * 1000 + (float) (Math.random() - 0.5) / 10;
                }
            }
            if (MyApp.wy4Connected) {
                String length3 = new String(buffer3, 1, 7, Charset.forName("ASCII"));
                if (!"".equals(length3)) {
                    LengthListTg[3] = Float.parseFloat(length3) * 1000 + (float) (Math.random() - 0.5) / 10;
                }
            }
            if (MyApp.wy5Connected) {
                String length4 = new String(buffer4, 1, 7, Charset.forName("ASCII"));
                if (!"".equals(length4)) {
                    LengthListTg[4] = Float.parseFloat(length4) * 1000 + (float) (Math.random() - 0.5) / 10;
                }
            }
            if (MyApp.wy6Connected) {
                String length5 = new String(buffer5, 1, 7, Charset.forName("ASCII"));
//                Log.i("kkk", "length5: " + length5);
                if (!"".equals(length5)) {
                    LengthListTg[5] = Float.parseFloat(length5) * 1000 + (float) (Math.random() - 0.5) / 10;
                }
            }
            if (MyApp.wy7Connected) {
                String length6 = new String(buffer6, 1, 7, Charset.forName("ASCII"));
                if (!"".equals(length6)) {
                    LengthListTg[6] = Float.parseFloat(length6) * 1000 + (float) (Math.random() - 0.5) / 10;
                }
            }
            //=================================================显示数据
            if (MyApp.wy1Connected) {
                tvTestTgLength1.setText(df2.format(LengthListTg[0]) + "mm");// LengthList--当前距离
            } else {
                tvTestTgLength1.setText("--");
            }
            if (MyApp.wy2Connected) {
                tvTestTgLength2.setText(df2.format(LengthListTg[1]) + "mm");// LengthList--当前距离
            } else {
                tvTestTgLength2.setText("--");
            }
            if (MyApp.wy3Connected) {
                tvTestTgLength3.setText(df2.format(LengthListTg[2]) + "mm");// LengthList--当前距离
            } else {
                tvTestTgLength3.setText("--");
            }
            if (MyApp.wy4Connected) {
                tvTestTgLength4.setText(df2.format(LengthListTg[3]) + "mm");// LengthList--当前距离
            } else {
                tvTestTgLength4.setText("--");
            }
            if (MyApp.wy5Connected) {
                tvTestTgLength5.setText(df2.format(LengthListTg[4]) + "mm");// LengthList--当前距离
            } else {
                tvTestTgLength5.setText("--");
            }
            if (MyApp.wy6Connected) {
                tvTestTgLength6.setText(df2.format(LengthListTg[5]) + "mm");// LengthList--当前距离
            } else {
                tvTestTgLength6.setText("--");
            }
            if (MyApp.wy7Connected) {
                tvTestTgLength7.setText(df2.format(LengthListTg[6]) + "mm");// LengthList--当前距离
            } else {
                tvTestTgLength7.setText("--");
            }
            handler.postDelayed(this, 1000);
        }
    };


    //解析七个位移传感器包
    public void parserRecDataTg(byte[] buffer1, byte[] buffer2, byte[] buffer3, byte[] buffer4, byte[] buffer5, byte[] buffer6, byte[] buffer7) {
        if (MyApp.wy1Connected) {
            buffer1[0] = MyApp.comBeanWy1.recData[14];
            buffer1[1] = MyApp.comBeanWy1.recData[15];
            buffer1[2] = MyApp.comBeanWy1.recData[16];
            buffer1[3] = MyApp.comBeanWy1.recData[17];
            buffer1[4] = MyApp.comBeanWy1.recData[18];
            buffer1[5] = MyApp.comBeanWy1.recData[19];
            buffer1[6] = MyApp.comBeanWy1.recData[20];
            buffer1[7] = MyApp.comBeanWy1.recData[21];
        }

        if (MyApp.wy2Connected) {
            buffer2[0] = MyApp.comBeanWy2.recData[14];
            buffer2[1] = MyApp.comBeanWy2.recData[15];
            buffer2[2] = MyApp.comBeanWy2.recData[16];
            buffer2[3] = MyApp.comBeanWy2.recData[17];
            buffer2[4] = MyApp.comBeanWy2.recData[18];
            buffer2[5] = MyApp.comBeanWy2.recData[19];
            buffer2[6] = MyApp.comBeanWy2.recData[20];
            buffer2[7] = MyApp.comBeanWy2.recData[21];
        }

        if (MyApp.wy3Connected) {
            buffer3[0] = MyApp.comBeanWy3.recData[14];
            buffer3[1] = MyApp.comBeanWy3.recData[15];
            buffer3[2] = MyApp.comBeanWy3.recData[16];
            buffer3[3] = MyApp.comBeanWy3.recData[17];
            buffer3[4] = MyApp.comBeanWy3.recData[18];
            buffer3[5] = MyApp.comBeanWy3.recData[19];
            buffer3[6] = MyApp.comBeanWy3.recData[20];
            buffer3[7] = MyApp.comBeanWy3.recData[21];
        }

        if (MyApp.wy4Connected) {
            buffer4[0] = MyApp.comBeanWy4.recData[14];
            buffer4[1] = MyApp.comBeanWy4.recData[15];
            buffer4[2] = MyApp.comBeanWy4.recData[16];
            buffer4[3] = MyApp.comBeanWy4.recData[17];
            buffer4[4] = MyApp.comBeanWy4.recData[18];
            buffer4[5] = MyApp.comBeanWy4.recData[19];
            buffer4[6] = MyApp.comBeanWy4.recData[20];
            buffer4[7] = MyApp.comBeanWy4.recData[21];
        }

        if (MyApp.wy5Connected) {
            buffer5[0] = MyApp.comBeanWy5.recData[14];
            buffer5[1] = MyApp.comBeanWy5.recData[15];
            buffer5[2] = MyApp.comBeanWy5.recData[16];
            buffer5[3] = MyApp.comBeanWy5.recData[17];
            buffer5[4] = MyApp.comBeanWy5.recData[18];
            buffer5[5] = MyApp.comBeanWy5.recData[19];
            buffer5[6] = MyApp.comBeanWy5.recData[20];
            buffer5[7] = MyApp.comBeanWy5.recData[21];
        }

        if (MyApp.wy6Connected) {
            buffer6[0] = MyApp.comBeanWy6.recData[14];
            buffer6[1] = MyApp.comBeanWy6.recData[15];
            buffer6[2] = MyApp.comBeanWy6.recData[16];
            buffer6[3] = MyApp.comBeanWy6.recData[17];
            buffer6[4] = MyApp.comBeanWy6.recData[18];
            buffer6[5] = MyApp.comBeanWy6.recData[19];
            buffer6[6] = MyApp.comBeanWy6.recData[20];
            buffer6[7] = MyApp.comBeanWy6.recData[21];
        }

        if (MyApp.wy7Connected) {
            buffer7[0] = MyApp.comBeanWy7.recData[14];
            buffer7[1] = MyApp.comBeanWy7.recData[15];
            buffer7[2] = MyApp.comBeanWy7.recData[16];
            buffer7[3] = MyApp.comBeanWy7.recData[17];
            buffer7[4] = MyApp.comBeanWy7.recData[18];
            buffer7[5] = MyApp.comBeanWy7.recData[19];
            buffer7[6] = MyApp.comBeanWy7.recData[20];
            buffer7[7] = MyApp.comBeanWy7.recData[21];
        }
    }


    @OnClick(R.id.btn_show)
    public void onViewClicked() {
        if (showdata) {//当前状态为显示
            showdata = false;
            btnShow.setBackgroundResource(R.drawable.cgq_float_xs);
            tvTestTgLength1.setVisibility(View.INVISIBLE);
            tvTestTgLength2.setVisibility(View.INVISIBLE);
            tvTestTgLength3.setVisibility(View.INVISIBLE);
            tvTestTgLength4.setVisibility(View.INVISIBLE);
            tvTestTgLength5.setVisibility(View.INVISIBLE);
            tvTestTgLength6.setVisibility(View.INVISIBLE);
            tvTestTgLength7.setVisibility(View.INVISIBLE);
        } else {
            showdata = true;
            btnShow.setBackgroundResource(R.drawable.cgq_float_yc);
            tvTestTgLength1.setVisibility(View.VISIBLE);
            tvTestTgLength2.setVisibility(View.VISIBLE);
            tvTestTgLength3.setVisibility(View.VISIBLE);
            tvTestTgLength4.setVisibility(View.VISIBLE);
            tvTestTgLength5.setVisibility(View.VISIBLE);
            tvTestTgLength6.setVisibility(View.VISIBLE);
            tvTestTgLength7.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        comA.close();
        handler.removeCallbacksAndMessages(null);
        handler = null;
        MyApp.wy1Connected = false;
        MyApp.wy2Connected = false;
        MyApp.wy3Connected = false;
        MyApp.wy4Connected = false;
        MyApp.wy5Connected = false;
        MyApp.wy6Connected = false;
        MyApp.wy7Connected = false;
        MyApp.jsdConnected = false;
        MyApp.comBeanWy1 = null;
        MyApp.comBeanWy2 = null;
        MyApp.comBeanWy3 = null;
        MyApp.comBeanWy4 = null;
        MyApp.comBeanWy5 = null;
        MyApp.comBeanWy6 = null;
        MyApp.comBeanWy7 = null;
        MyApp.comBeanJsd7 = null;
        MyApp.wyBean1 = null;
        MyApp.wyBean2 = null;
        MyApp.wyBean3 = null;
        MyApp.wyBean4 = null;
        MyApp.wyBean5 = null;
        MyApp.wyBean6 = null;
        MyApp.wyBean7 = null;
        MyApp.jsdBean7 = null;
    }

}
