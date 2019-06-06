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
            tvTestTgLength1.setText(mActivity.getShowLengthTg()[0]);
            tvTestTgWy1.setText(mActivity.getShowWyTg()[0]);

            tvTestTgLength2.setText(mActivity.getShowLengthTg()[1]);
            tvTestTgWy2.setText(mActivity.getShowWyTg()[1]);

            tvTestTgLength3.setText(mActivity.getShowLengthTg()[2]);
            tvTestTgWy3.setText(mActivity.getShowWyTg()[2]);

            tvTestTgLength4.setText(mActivity.getShowLengthTg()[3]);
            tvTestTgWy4.setText(mActivity.getShowWyTg()[3]);

            tvTestTgLength5.setText(mActivity.getShowLengthTg()[4]);
            tvTestTgWy5.setText(mActivity.getShowWyTg()[4]);

            tvTestTgLength6.setText(mActivity.getShowLengthTg()[5]);
            tvTestTgWy6.setText(mActivity.getShowWyTg()[5]);

            tvTestTgLength7.setText(mActivity.getShowLengthTg()[6]);
            tvTestTgWy7.setText(mActivity.getShowWyTg()[6]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void refreshNull() {
        if (tvTestTgLength1 != null && tvTestTgWy1 != null) {
            tvTestTgLength1.setText("--");
            tvTestTgWy1.setText("--");
        }
        if (tvTestTgLength2 != null && tvTestTgWy2 != null) {
            tvTestTgLength2.setText("--");
            tvTestTgWy2.setText("--");
        }
        if (tvTestTgLength3 != null && tvTestTgWy3 != null) {
            tvTestTgLength3.setText("--");
            tvTestTgWy3.setText("--");
        }
        if (tvTestTgLength4 != null && tvTestTgWy4 != null) {
            tvTestTgLength4.setText("--");
            tvTestTgWy4.setText("--");
        }
        if (tvTestTgLength5 != null && tvTestTgWy5 != null) {
            tvTestTgLength5.setText("--");
            tvTestTgWy5.setText("--");
        }
        if (tvTestTgLength6 != null && tvTestTgWy6 != null) {
            tvTestTgLength6.setText("--");
            tvTestTgWy6.setText("--");
        }
        if (tvTestTgLength7 != null && tvTestTgWy7 != null) {
            tvTestTgLength7.setText("--");
            tvTestTgWy7.setText("--");
        }
    }
}
