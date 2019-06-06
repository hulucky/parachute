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
import com.parachute.administrator.DATAbase.greendao.TaskEntity;
import com.parachute.test.TestActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TestJfhfragment extends Fragment {


    @BindView(R.id.tv_test_jfh_length1)
    TextView tvTestJfhLength1;
    @BindView(R.id.tv_test_jfh_wy1)
    TextView tvTestJfhWy1;
    @BindView(R.id.tv_test_jfh_length2)
    TextView tvTestJfhLength2;
    @BindView(R.id.tv_test_jfh_wy2)
    TextView tvTestJfhWy2;
    @BindView(R.id.tv_test_jfh_length3)
    TextView tvTestJfhLength3;
    @BindView(R.id.tv_test_jfh_wy3)
    TextView tvTestJfhWy3;
    @BindView(R.id.tv_test_jfh_length4)
    TextView tvTestJfhLength4;
    @BindView(R.id.tv_test_jfh_wy4)
    TextView tvTestJfhWy4;
    @BindView(R.id.tv_test_jfh_length5)
    TextView tvTestJfhLength5;
    @BindView(R.id.tv_test_jfh_wy5)
    TextView tvTestJfhWy5;
    @BindView(R.id.tv_test_jfh_length6)
    TextView tvTestJfhLength6;
    @BindView(R.id.tv_test_jfh_wy6)
    TextView tvTestJfhWy6;
    private TestActivity mActivity;
    Unbinder unbinder;
    public int fragflag;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (TestActivity) getParentFragment().getActivity();
        fragflag = 0;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_testjfh, null);
        mActivity = (TestActivity) getParentFragment().getActivity();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = (TestActivity) getParentFragment().getActivity();


    }

    @Override
    public void onStart() {
        super.onStart();
        mActivity = (TestActivity) getParentFragment().getActivity();
    }

    private TaskEntity getTaskEntity() {
        TaskEntity TaskEntity = mActivity.myApp.getInstance().getTaskEnity();

        return TaskEntity;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public void refresh() {
        tvTestJfhLength1.setText(mActivity.getShowLength()[0]);
        tvTestJfhWy1.setText(mActivity.getShowWy()[0]);

        tvTestJfhLength2.setText(mActivity.getShowLength()[1]);
        tvTestJfhWy2.setText(mActivity.getShowWy()[1]);

        tvTestJfhLength3.setText(mActivity.getShowLength()[2]);
        tvTestJfhWy3.setText(mActivity.getShowWy()[2]);

        tvTestJfhLength4.setText(mActivity.getShowLength()[3]);
        tvTestJfhWy4.setText(mActivity.getShowWy()[3]);

        tvTestJfhLength5.setText(mActivity.getShowLength()[4]);
        tvTestJfhWy5.setText(mActivity.getShowWy()[4]);

        tvTestJfhLength6.setText(mActivity.getShowLength()[5]);
        tvTestJfhWy6.setText(mActivity.getShowWy()[5]);
    }


    public void refreshNull() {
        if (tvTestJfhLength1 != null && tvTestJfhWy1 != null) {
            tvTestJfhLength1.setText("--");
            tvTestJfhWy1.setText("--");
        }
        if (tvTestJfhLength2 != null && tvTestJfhWy2 != null) {
            tvTestJfhLength2.setText("--");
            tvTestJfhWy2.setText("--");
        }
        if (tvTestJfhLength3 != null && tvTestJfhWy3 != null) {
            tvTestJfhLength3.setText("--");
            tvTestJfhWy3.setText("--");
        }
        if (tvTestJfhLength4 != null && tvTestJfhWy4 != null) {
            tvTestJfhLength4.setText("--");
            tvTestJfhWy4.setText("--");
        }
        if (tvTestJfhLength5 != null && tvTestJfhWy5 != null) {
            tvTestJfhLength5.setText("--");
            tvTestJfhWy5.setText("--");
        }
        if (tvTestJfhLength6 != null && tvTestJfhWy6 != null) {
            tvTestJfhLength6.setText("--");
            tvTestJfhWy6.setText("--");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        setUserVisibleHint(true);
    }
}
