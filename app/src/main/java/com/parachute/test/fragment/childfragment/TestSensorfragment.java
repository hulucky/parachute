package com.parachute.test.fragment.childfragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parachute.administrator.DATAbase.R;
import com.parachute.test.TestActivity;
import com.sensor.SensorData;
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
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        setUserVisibleHint(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
