package com.parachute.test.fragment.childfragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parachute.administrator.DATAbase.R;
import com.parachute.test.TestActivity;
import com.sensor.SensorData;
import com.sensor.view.SensorView;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TestWyfragment extends Fragment {


    DecimalFormat df4 = new DecimalFormat("####0.0");
    TestActivity mActivity;
    Unbinder unbinder;


    boolean showfb = true;
    @BindView(R.id.tv_test_tg_length1)
    TextView tvTestTgLength1;
    @BindView(R.id.tv_test_tg_wy1)
    TextView tvTestTgWy1;
    @BindView(R.id.tv_test_tg_length2)
    TextView tvTestTgLength2;
    @BindView(R.id.tv_test_tg_wy2)
    TextView tvTestTgWy2;
    @BindView(R.id.tv_test_tg_length3)
    TextView tvTestTgLength3;
    @BindView(R.id.tv_test_tg_wy3)
    TextView tvTestTgWy3;
    @BindView(R.id.tv_test_tg_length4)
    TextView tvTestTgLength4;
    @BindView(R.id.tv_test_tg_wy4)
    TextView tvTestTgWy4;
    @BindView(R.id.tv_test_tg_length5)
    TextView tvTestTgLength5;
    @BindView(R.id.tv_test_tg_wy5)
    TextView tvTestTgWy5;
    @BindView(R.id.tv_test_tg_length6)
    TextView tvTestTgLength6;
    @BindView(R.id.tv_test_tg_wy6)
    TextView tvTestTgWy6;
    @BindView(R.id.tv_test_tg_length7)
    TextView tvTestTgLength7;
    @BindView(R.id.tv_test_tg_wy7)
    TextView tvTestTgWy7;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_testwy, null);
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mActivity = (TestActivity) getParentFragment().getActivity();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        setUserVisibleHint(true);
    }


    public void refresh() {

        try {
            tvTestTgLength1.setText(mActivity.getShowLength()[1]);
            tvTestTgWy1.setText(mActivity.getShowWy()[1]);

            tvTestTgLength2.setText(mActivity.getShowLength()[2]);
            tvTestTgWy2.setText(mActivity.getShowWy()[2]);

            tvTestTgLength3.setText(mActivity.getShowLength()[3]);
            tvTestTgWy3.setText(mActivity.getShowWy()[3]);

            tvTestTgLength4.setText(mActivity.getShowLength()[4]);
            tvTestTgWy4.setText(mActivity.getShowWy()[4]);

            tvTestTgLength5.setText(mActivity.getShowLength()[5]);
            tvTestTgWy5.setText(mActivity.getShowWy()[5]);

            tvTestTgLength6.setText(mActivity.getShowLength()[6]);
            tvTestTgWy6.setText(mActivity.getShowWy()[6]);

            tvTestTgLength7.setText(mActivity.getShowLength()[7]);
            tvTestTgWy7.setText(mActivity.getShowWy()[7]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
