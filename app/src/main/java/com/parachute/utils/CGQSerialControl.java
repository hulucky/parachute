package com.parachute.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.parachute.app.MyApp;
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
import com.xzkydz.bean.ComBean;
import com.xzkydz.helper.SerialHelper;

import java.util.Arrays;

import static com.parachute.app.MyApp.jsdBean7;
import static com.parachute.app.MyApp.wyBean1;
import static com.parachute.app.MyApp.wyBean2;
import static com.parachute.app.MyApp.wyBean3;
import static com.parachute.app.MyApp.wyBean4;
import static com.parachute.app.MyApp.wyBean5;
import static com.parachute.app.MyApp.wyBean6;
import static com.parachute.app.MyApp.wyBean7;

public class CGQSerialControl extends SerialHelper {


    public CGQSerialControl(Context context, int mDataType) {
        super(context, mDataType);
    }

    public CGQSerialControl(Context context, String sPort, String sBaudRate, int mDataType) {
        super(context, sPort, sBaudRate, mDataType);
    }

    @Override
    protected void onDataReceived(ComBean comBean) {
        switch (comBean.recDataType) {
            case -95://激光测距
                int bianHao = comBean.recData[10];
                switch (bianHao) {
                    case 1://楔块1
                        MyApp.wy1Connected = true;
                        MyApp.comBeanWy1 = comBean;
                        if (wyBean1 == null) {
                            wyBean1 = new WyBean1(comBean.recData);
                        } else {
                            wyBean1.caculate(comBean.recData);
                        }
                        if (receivedSensorData != null) {
                            receivedSensorData.updateSensor(wyBean1);
                        }
                        break;
                    case 2://楔块2
                        MyApp.wy2Connected = true;
                        MyApp.comBeanWy2 = comBean;
                        if (wyBean2 == null) {
                            wyBean2 = new WyBean2(comBean.recData);
                        } else {
                            wyBean2.caculate(comBean.recData);
                        }
                        if (receivedSensorData != null) {
                            receivedSensorData.updateSensor(wyBean2);
                        }
                        break;
                    case 3://制动1
                        MyApp.wy3Connected = true;
                        MyApp.comBeanWy3 = comBean;
                        if (wyBean3 == null) {
                            wyBean3 = new WyBean3(comBean.recData);
                        } else {
                            wyBean3.caculate(comBean.recData);
                        }
                        if (receivedSensorData != null) {
                            receivedSensorData.updateSensor(wyBean3);
                        }
                        break;
                    case 4://制动2
                        MyApp.wy4Connected = true;
                        MyApp.comBeanWy4 = comBean;
                        if (wyBean4 == null) {
                            wyBean4 = new WyBean4(comBean.recData);
                        } else {
                            wyBean4.caculate(comBean.recData);
                        }
                        if (receivedSensorData != null) {
                            receivedSensorData.updateSensor(wyBean4);
                        }
                        break;
                    case 5://缓冲1
                        MyApp.wy5Connected = true;
                        MyApp.comBeanWy5 = comBean;
                        if (wyBean5 == null) {
                            wyBean5 = new WyBean5(comBean.recData);
                        } else {
                            wyBean5.caculate(comBean.recData);
                        }
                        if (receivedSensorData != null) {
                            receivedSensorData.updateSensor(wyBean5);
                        }
                        break;
                    case 6://缓冲2
                        MyApp.wy6Connected = true;
                        MyApp.comBeanWy6 = comBean;
                        if (wyBean6 == null) {
                            wyBean6 = new WyBean6(comBean.recData);
                        } else {
                            wyBean6.caculate(comBean.recData);
                        }
                        if (receivedSensorData != null) {
                            receivedSensorData.updateSensor(wyBean6);
                        }
                        break;
                    case 7://下降7
                        MyApp.wy7Connected = true;
                        MyApp.comBeanWy7 = comBean;
                        if (wyBean7 == null) {
                            wyBean7 = new WyBean7(comBean.recData);
                        } else {
                            wyBean7.caculate(comBean.recData);
                        }
                        if (receivedSensorData != null) {
                            receivedSensorData.updateSensor(wyBean7);
                        }
                        break;
                }
                break;
            case -92://加速度
                MyApp.jsdConnected = true;
//                Log.d("hhg", "onDataReceived:" + "数据长度为：" + comBean.recData.length + "       应用：" + comBean.recData[13] + "         数据为：" + Arrays.toString(comBean.recData));
                //更新加速度电量信号用 心跳包
                switch (comBean.recData[13]) {//应用
                    case 7://心跳包
                        MyApp.comBeanJsd7 = comBean;
                        if (jsdBean7 == null) {
                            jsdBean7 = new JsdBean7(comBean.recData);
                        } else {
                            jsdBean7.caculate(comBean.recData);
                        }
//                        Log.i("hhh", "onDataReceived: " + Arrays.toString(comBean.recData));
                        if (receivedSensorData != null) {
                            receivedSensorData.updateSensor(jsdBean7);
                        }
                        break;
                }
                break;
        }
    }

    //设置监听更新传感器信号电量
    private OnReceivedSensorData receivedSensorData;

    public void setOnReceivedSensorListener(@Nullable OnReceivedSensorData l) {
        this.receivedSensorData = l;
    }

    public interface OnReceivedSensorData {
        void updateSensor(ISensorInf sensorInf);

    }

}
