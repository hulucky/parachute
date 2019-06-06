package com.parachute.test.fragment.childfragment;


import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.parachute.Adapter.TestPressAdapter;
import com.parachute.Tools.DrawViewJsd;
import com.parachute.administrator.DATAbase.R;
import com.parachute.bean.PressBean;
import com.parachute.test.TestActivity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TestJsdfragment extends Fragment {
    TestActivity mActivity;
    Unbinder unbinder;

    private DrawViewJsd view;

    DecimalFormat df1 = new DecimalFormat("0.0");
    DecimalFormat df2 = new DecimalFormat("0.00");
    DecimalFormat df3 = new DecimalFormat("0.000");
    DecimalFormat df4 = new DecimalFormat("0.0000");

    @BindView(R.id.switchJsd)
    Switch switchJsd;
    @BindView(R.id.fltgjsdcurve)
    FrameLayout fltgjsdcurve;
    @BindView(R.id.progressBar1)
    ProgressBar progressBar1;
    @BindView(R.id.edtgjsd)
    TextView edtgjsd;
    @BindView(R.id.edtgpjjsd)
    TextView edtgpjjsd;
    @BindView(R.id.edtgkxcsj)
    TextView edtgkxcsj;
    @BindView(R.id.edtgkxcjl)
    TextView edtgkxcjl;
    @BindView(R.id.edtgzdsj)
    TextView edtgzdsj;
    @BindView(R.id.imgjsd)
    ImageView imgjsd;
    @BindView(R.id.tvjsdstate)
    TextView tvjsdstate;
    @BindView(R.id.jsd_edit)
    TextView tvjsdedit;

    public float[] mDrawList = new float[100];// 加速度曲线数据
    private float[] mSdList = new float[1];
    private float[] mDisList = new float[1];
    private int mjsdstate = 0;

    private Handler timeHandler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        progressBar1.setMax(60);
        progressBar1.setProgress(0);
        switchJsd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //setDown  是否停止了
                //checkBox选中时，是否停止置为 false
                mActivity.setDown(!isChecked);
            }
        });
        initDraw();
        //只有界面view改变才会触发传感器断开监听，这里给一个空字符，为了能触发传感器断开监听
        timeHandler = new Handler();
        timeHandler.post(updateTime);

    }

    //更新时间
    Runnable updateTime = new Runnable() {
        @Override
        public void run() {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String format = sdf.format(new Date());
            tvjsdedit.setText(" ");
            if (timeHandler == null) {
                return;
            }
            timeHandler.postDelayed(updateTime, 1000);
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_testjsd, null);
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mActivity = (TestActivity) getParentFragment().getActivity();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timeHandler != null) {
            timeHandler.removeCallbacks(updateTime);
            timeHandler = null;
        }
    }

    public int getJsdState() {
        return mjsdstate;
    }

    public void setJsdState(int jsdstate) {
        mjsdstate = jsdstate;
        switch (jsdstate) {
            case 0://断开
                imgjsd.setImageResource(R.mipmap.deng_black);
                tvjsdstate.setText("断开");
                break;
            case 1://待机
                imgjsd.setImageResource(R.mipmap.deng_green);
                tvjsdstate.setText("空闲");
                break;
            case 2://就绪
                imgjsd.setImageResource(R.mipmap.deng_green);
                tvjsdstate.setText("就绪");
                break;
            case 3://通讯
                imgjsd.setImageResource(R.mipmap.deng_red);
                tvjsdstate.setText("通讯");
                break;
        }
    }

    public void initDraw() {
        fltgjsdcurve.removeAllViews();
        view = new DrawViewJsd(mActivity);
        view.SetSpeedDrawOrNot(false);
        view.SetDisplacementDrawOrNot(false);
        view.setMinimumHeight(480);
        view.setMinimumWidth(250);
        view.SetPoint(50, 50);
        view.invalidate();
        view.SetResourceDataY(mDrawList);
        view.SetResourceDataU(mSdList);
        view.SetResourceDataD(mDisList);
        fltgjsdcurve.addView(view);
    }

    public void reFreshProgressBar(int dataId) {
        progressBar1.setProgress(dataId);
    }

    public void updateTextView() {
        edtgjsd.setText("0");
        edtgpjjsd.setText("0");
        edtgkxcsj.setText("0");
        edtgkxcjl.setText("0");
        edtgzdsj.setText("0");
    }

    public void Draw() {
        view = new DrawViewJsd(mActivity);
        view.SetSpeedDrawOrNot(false);
        view.SetDisplacementDrawOrNot(false);
        view.setMinimumHeight(480);
        view.setMinimumWidth(250);
        view.SetPoint(50, 50);
        view.invalidate();
        view.SetResourceDataY(mActivity.mDrawList);
        Log.d("pdd", "mActivity.mDrawList: " + Arrays.toString(mActivity.mDrawList));
        view.SetResourceDataU(mActivity.mSdList);
        view.SetResourceDataD(mActivity.mDisList);
        fltgjsdcurve.removeAllViews();
        fltgjsdcurve.addView(view);
        edtgjsd.setText(df2.format(0 - mActivity.getmJsd()));//加速度
        edtgpjjsd.setText(df2.format(0 - mActivity.getmPjJsd()));//平均加速度
        edtgkxcsj.setText(df4.format(mActivity.getmKxcsj()));//空行程时间
        edtgkxcjl.setText(df2.format(mActivity.getmKxcjl()));//空行程距离
        edtgzdsj.setText(df4.format(mActivity.getmZdsj() / 4));//制动时间
    }
}
