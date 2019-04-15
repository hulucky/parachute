package com.parachute.test.fragment.childfragment;


import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TestJsdfragment extends Fragment {


    TestActivity mActivity;
    Unbinder unbinder;
    TestPressAdapter mAdpter;
    List<PressBean> mList;


    Resources resources;
    Drawable drawablegreen;
    Drawable drawablered;
    Drawable drawablegray;
    private DrawViewJsd view;


    DecimalFormat df1 = new DecimalFormat("####0.0");
    DecimalFormat df2 = new DecimalFormat("####0.00");

    DecimalFormat df4 = new DecimalFormat("####0.0000");

    DecimalFormat df3 = new DecimalFormat("####0.000");
    @BindView(R.id.switchJsd)
    Switch switchJsd;
    @BindView(R.id.btnReSendJsd)
    Button btnReSendJsd;
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
    @BindView(R.id.edtgzdjl)
    TextView edtgzdjl;
    @BindView(R.id.imgjsd)
    TextView imgjsd;
    @BindView(R.id.tvjsdstate)
    TextView tvjsdstate;
    public float[] mDrawList = new float[100];// 加速度曲线数据
    private float[] mSdList = new float[1];
    private float[] mDisList = new float[1];
    private int mjsdstate = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (TestActivity) getParentFragment().getActivity();
        resources = mActivity.getBaseContext().getResources();
        drawablegreen = resources.getDrawable(R.drawable.deng_green);
        drawablered = resources.getDrawable(R.drawable.deng_red);
        drawablegray = resources.getDrawable(R.drawable.deng_black);


        //   TestData();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        setUserVisibleHint(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_testjsd, null);
        unbinder = ButterKnife.bind(this, view);
        mActivity = (TestActivity) getParentFragment().getActivity();
        mAdpter = new TestPressAdapter(mActivity, mList);
        progressBar1.setMax(60);
        progressBar1.setProgress(0);
        switchJsd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mActivity.setDown(!isChecked);
            }
        });
        initDraw();
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public int getJsdState() {
        return mjsdstate;
    }

    public void setJsdState(int jsdstate) {
        try {
            mjsdstate = jsdstate;
            switch (mjsdstate) {
                case 0://断开
                    imgjsd.setBackground(drawablegray);
                    tvjsdstate.setText("断开");
                    break;
                case 1://待机
                    imgjsd.setBackground(drawablegreen);
                    tvjsdstate.setText("空闲");
                    break;
                case 2://就绪
                    imgjsd.setBackground(drawablegreen);

                    tvjsdstate.setText("就绪");
                    break;
                case 3://通讯
                    imgjsd.setBackground(drawablered);
                    tvjsdstate.setText("通讯");
                    break;
                case 4://闪烁
                    imgjsd.setBackground(drawablegray);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initDraw() {
        view = new DrawViewJsd(mActivity);

        view.SetSpeedDrawOrNot(false);
        view.SetDisplacementDrawOrNot(false);
        view.setMinimumHeight(480);
        view.setMinimumWidth(250);
        view.SetPoint(150, 50);
        view.invalidate();
        view.SetResourceDataY(mDrawList);
        view.SetResourceDataU(mSdList);
        view.SetResourceDataD(mDisList);

        fltgjsdcurve.addView(view);
    }

    public void refresh() {
        try {
            progressBar1.setProgress(mActivity.getJsdCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Draw() {
        try {

            view = new DrawViewJsd(mActivity);

            view.SetSpeedDrawOrNot(false);
            view.SetDisplacementDrawOrNot(false);
            view.setMinimumHeight(480);
            view.setMinimumWidth(250);
            view.SetPoint(150, 50);
            view.invalidate();
            view.SetResourceDataY(mActivity.mDrawList);
            view.SetResourceDataU(mSdList);
            view.SetResourceDataD(mDisList);
            fltgjsdcurve.removeAllViews();
            fltgjsdcurve.addView(view);
            edtgjsd.setText(df2.format(0 - mActivity.getmJsd()));
            edtgpjjsd.setText(df2.format(0 - mActivity.getmPjJsd()));
            edtgkxcjl.setText(df2.format(mActivity.getmKxcjl()));
            edtgkxcsj.setText(df4.format(mActivity.getmKxcsj() ));
            edtgzdjl.setText(df4.format(mActivity.getmZdsj() / 4 ));
//            TVEdTgKxcsj.setText(df4.format(mKxcsj * 1000));
//
//                TVEdTgKxcjl.setText(df4.format(0.5 * stG * mKxcsj * mKxcsj
//                        * 1000));
//                mPjJsd = tmpPjjsd;
//
//                TVEdTgJsd.setText(df4.format(0 - Math.abs(mJsd)));
//                TVEdTgPjjsd.setText(df4.format(0 - Math.abs(tmpPjjsd)));
//                TVEdTgZdjl.setText(df4.format(mZdsj / 4 * 1000));

        } catch (Exception e) {
            e.printStackTrace();
        }
//        fltgjsdcurve
    }

}
