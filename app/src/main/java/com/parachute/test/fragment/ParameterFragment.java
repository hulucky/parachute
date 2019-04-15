package com.parachute.test.fragment;


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
import com.parachute.app.MyApp;
import com.parachute.test.TestActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ParameterFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.tv_tittle_task)
    TextView tvTittleTask;
    @BindView(R.id.tv_testparam_tg_length1)
    TextView tvTestparamname;
    @BindView(R.id.tv_testparam_tg_wy1)
    TextView tvTestparamno;
    @BindView(R.id.tv_testparam_tg_length2)
    TextView tvTestparamry;
    @BindView(R.id.tv_testparam_tg_wy2)
    TextView tvTestparamriqi;
    private TestActivity mActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_parameter, null);
        unbinder = ButterKnife.bind(this, view);
        mActivity = (TestActivity) getActivity();
        initListView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mActivity.initdata();
    }

    private TaskEntity getTaskEntity() {
        TaskEntity TaskEntity = mActivity.myApp.getInstance().getTaskEnity();

        return TaskEntity;
    }

    /*
     * description:初始化界面
     */
    private void initListView() {

        TaskEntity taskParInf= MyApp.getInstance().getTaskEnity();
        tvTestparamname.setText( taskParInf.getUnitName());
        tvTestparamno.setText(taskParInf.getNumber());
        tvTestparamry.setText( taskParInf.getPeopleName());
        tvTestparamriqi.setText( taskParInf.getGreateTaskTime());
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
}
