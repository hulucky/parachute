package com.parachute.test.fragment.childfragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.parachute.administrator.DATAbase.R;
import com.parachute.app.MyApp;
import com.parachute.test.TestActivity;
import com.sensor.SensorData;
import com.sensor.SensorInf;
import com.sensor.view.SensorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TestSensorfragment extends Fragment {
    TestActivity mActivity;
    Unbinder unbinder;
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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_testsensor, null);
        mActivity = (TestActivity) getParentFragment().getActivity();
        unbinder = ButterKnife.bind(this, view);
        Log.d("ooo", "onCreateView:--- ");
        //关闭硬件加速
        wyTest1.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        wyTest2.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        wyTest3.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        wyTest4.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        wyTest5.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        wyTest6.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        wyTest7.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        jsdTest.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("ooo", "onStart: ");
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

    public class MyStatusChangeListener implements SensorView.OnStatusChangeListener{

        @Override
        public void status(View view, int previousStatus, int thisStatus) {
            if(thisStatus == SensorInf.SEARCHING){
                switch (view.getId()){
                    case R.id.wy_test_1:
                        MyApp.wy1Connected = false;
                        Log.d("ooo", "楔块1中断: ");
                        Toast.makeText(getContext(), "楔块传感器1中断！", Toast.LENGTH_SHORT).show();
                        mActivity.setStart(2);
                        mActivity.refreshNullData();
                        break;
                    case R.id.wy_test_2:
                        MyApp.wy2Connected = false;
                        Toast.makeText(getContext(), "楔块传感器2中断！", Toast.LENGTH_SHORT).show();
                        mActivity.setStart(2);
                        mActivity.refreshNullData();
                        break;
                    case R.id.wy_test_3:
                        MyApp.wy3Connected = false;
                        Toast.makeText(getContext(), "制动传感器1中断！", Toast.LENGTH_SHORT).show();
                        mActivity.setStart(2);
                        mActivity.refreshNullData();
                        break;
                    case R.id.wy_test_4:
                        MyApp.wy4Connected = false;
                        Toast.makeText(getContext(), "制动传感器2中断！", Toast.LENGTH_SHORT).show();
                        mActivity.setStart(2);
                        mActivity.refreshNullData();
                        break;
                    case R.id.wy_test_5:
                        MyApp.wy5Connected = false;
                        Toast.makeText(getContext(), "缓冲传感器1中断！", Toast.LENGTH_SHORT).show();
                        mActivity.setStart(2);
                        mActivity.refreshNullData();
                        break;
                    case R.id.wy_test_6:
                        MyApp.wy6Connected = false;
                        Toast.makeText(getContext(), "缓冲传感器2中断！", Toast.LENGTH_SHORT).show();
                        mActivity.setStart(2);
                        mActivity.refreshNullData();
                        break;
                    case R.id.wy_test_7:
                        MyApp.wy7Connected = false;
                        Toast.makeText(getContext(), "下降传感器中断！", Toast.LENGTH_SHORT).show();
                        mActivity.setStart(2);
                        mActivity.refreshNullData();
                        break;
                    case R.id.jsd_test:
                        Log.d("ooo", "加速度传感器中断: ");
                        MyApp.jsdConnected = false;
                        Toast.makeText(getContext(), "加速度传感器中断！", Toast.LENGTH_SHORT).show();
                        mActivity.setStart(2);
                        mActivity.refreshNullData();
                        mActivity.refreshJsdNull();
                        break;
                }
            }
        }
    }


    private void SetSensorState(SensorView msv, float mpower, float msignal, int minf) {
        SensorData svData = new SensorData();
        // 第二步：设置 SensorData 属性
        svData.setStatus(minf)
                .setPower(mpower)
                .setSignal(msignal);

        // 第三步：给SensorView 赋值
        msv.setData(svData);
    }

//    public void SetSensor(String str, float mpower, float msignal, int minf) {
//        try {
//            switch (str) {
//                case "测距1":
//                    SetSensorState(wyTest1, mpower, msignal, minf);
//                    break;
//                case "测距2":
//                    SetSensorState(wyTest2, mpower, msignal, minf);
//                    break;
//                case "测距3":
//                    SetSensorState(wyTest3, mpower, msignal, minf);
//                    break;
//                case "测距4":
//                    SetSensorState(wyTest4, mpower, msignal, minf);
//                    break;
//                case "测距5":
//                    SetSensorState(wyTest5, mpower, msignal, minf);
//                    break;
//                case "测距6":
//                    SetSensorState(wyTest6, mpower, msignal, minf);
//                    break;
//                case "测距7":
//                    SetSensorState(wyTest7, mpower, msignal, minf);
//                    break;
//                case "加速度":
//                    SetSensorState(jsdTest, mpower, msignal, minf);
//                    break;
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        setUserVisibleHint(true);
    }

    public void setWy1(SensorData sensorData) {
        if (wyTest1 != null) {
            wyTest1.setData(sensorData);
        }
    }

    public void setWy2(SensorData sensorData) {
        if (wyTest2 != null) {
            wyTest2.setData(sensorData);
        }
    }

    public void setWy3(SensorData sensorData) {
        if (wyTest3 != null) {
            wyTest3.setData(sensorData);
        }
    }

    public void setWy4(SensorData sensorData) {
        if (wyTest4 != null) {
            wyTest4.setData(sensorData);
        }
    }

    public void setWy5(SensorData sensorData) {
        if (wyTest5 != null) {
            wyTest5.setData(sensorData);
        }
    }

    public void setWy6(SensorData sensorData) {
        if (wyTest6 != null) {
            wyTest6.setData(sensorData);
        }
    }

    public void setWy7(SensorData sensorData) {
        if (wyTest7 != null) {
            wyTest7.setData(sensorData);
        }
    }

    public void setJsd(SensorData sensorData) {
        if (jsdTest != null) {
            jsdTest.setData(sensorData);
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ooo", "onCreate: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("ooo", "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("ooo", "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("ooo", "onStop: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("ooo", "onDestroyView: ");
        wyTest1.destroy();
        wyTest2.destroy();
        wyTest3.destroy();
        wyTest4.destroy();
        wyTest5.destroy();
        wyTest6.destroy();
        wyTest7.destroy();
        jsdTest.destroy();

        unbinder.unbind();
    }
}
