package com.parachute.param;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.flyco.tablayout.SlidingTabLayout;
import com.greendao.dbUtils.GreateTaskUtils;
import com.jaeger.library.StatusBarUtil;
import com.parachute.administrator.DATAbase.R;
import com.parachute.administrator.DATAbase.greendao.TaskEntity;
import com.parachute.administrator.DATAbase.greendao.TaskResEnity;
import com.parachute.app.MyApp;
import com.parachute.test.TestActivity;
import com.xzkydz.function.motor.module.ConstantData;
import com.xzkydz.function.utils.DateUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

import static android.text.TextUtils.isEmpty;
import static com.parachute.Tools.Method.IsEmpty;

public class ParamSetActivity extends AppCompatActivity {

    private MyApp myApp;
    private int openlimit = 0; //任务信息 展开、关闭设位置
    TaskEntity mTask;
    private boolean ParamIsSet = false;//设定参数是否展开
    private boolean canSavedjxl1 = false;
    private boolean canSavedjxl2 = false;
    private boolean canSavesd = false;
    private boolean newTask = true;


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.et_task_name)
    public EditText etTaskName;
    @BindView(R.id.et_number)
    public EditText etNumber;
    @BindView(R.id.et_people_name)
    public EditText etPeopleName;
    @BindView(R.id.task_inf_ll)
    LinearLayout llTittle;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.sp_input_param_venti)
    Spinner spventi;
    @BindView(R.id.sp_input_param_csff)
    Spinner spcsff;
    @BindView(R.id.sp_input_param_cdxl1)
    Spinner spcdxl1;
    @BindView(R.id.sp_input_param_motor_csff1)
    Spinner spmcsff1;
    @BindView(R.id.sp_input_param_qblc1)
    Spinner spqblc1;
    @BindView(R.id.sp_input_param_cdxl2)
    Spinner spcdxl2;
    @BindView(R.id.sp_input_param_motor_csff2)
    Spinner spmcsff2;
    @BindView(R.id.sp_input_param_qblc2)
    Spinner spqblc2;
    @BindView(R.id.ly_param_cymj)
    LinearLayout lycymj;
    @BindView(R.id.ly_param_ptgxs)
    LinearLayout lyptgxs;
    @BindView(R.id.ly_param_set)
    LinearLayout lyset;
    @BindView(R.id.tv_param_dlbb)
    TextView tvdlbb;
    @BindView(R.id.tv_param_dybb)
    TextView tvdybb;
    @BindView(R.id.et_param_cfmj)
    EditText etcfmj;
    @BindView(R.id.et_param_cymjd)
    EditText etcymjd;
    @BindView(R.id.et_param_cymjx)
    EditText etcymjx;
    @BindView(R.id.et_param_dlbb1)
    EditText etdlbb1;
    @BindView(R.id.et_param_dybb1)
    EditText etdybb1;
    @BindView(R.id.et_param_dlbb2)
    EditText etdlbb2;
    @BindView(R.id.et_param_dybb2)
    EditText etdybb2;
    @BindView(R.id.et_param_ksqckmj)
    EditText etksqckmj;
    @BindView(R.id.et_param_ptgxs)
    EditText etptgxs;
    @BindView(R.id.et_param_set_cy)
    EditText etcy;
    @BindView(R.id.et_param_set_jy)
    EditText etjy;
    @BindView(R.id.et_param_set_wd)
    EditText etwd;
    @BindView(R.id.et_param_set_sd)
    EditText etsd;
    @BindView(R.id.et_param_set_dqy)
    EditText etdqy;
    @BindView(R.id.et_param_set_fs)
    EditText etfs;
    @BindView(R.id.et_param_set_djgl1)
    EditText etdjgl1;
    @BindView(R.id.et_param_set_djgl2)
    EditText etdjgl2;
    @BindView(R.id.et_param_set_djxl1)
    EditText etdjxl1;
    @BindView(R.id.et_param_set_djxl2)
    EditText etdjxl2;

    @BindView(R.id.et_param_set_edzs)
    EditText etedzs;
    @BindView(R.id.et_param_set_sczs)
    EditText etsczs;

    @BindView(R.id.btn_go_test)
    Button btnGoTest;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedscrollview;
    @BindView(R.id.imageview)
    ImageView imageView;
    @BindView(R.id.img_param_rwxx)
    ImageView imageRwxx;
    @BindView(R.id.tv_param_djxl1)
    TextView tvdjxl1;
    @BindView(R.id.tv_param_djxl2)
    TextView tvdjxl2;
    private boolean canSaveptg;
    private Animation mShakeAnim;
    private String errstr = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_param);
        ButterKnife.bind(this);
        myApp = MyApp.getInstance();
        mTask = new TaskEntity();
        mShakeAnim = AnimationUtils.loadAnimation(ParamSetActivity.this, R.anim.shake_x);
        setSupportActionBar(toolbar);

        StatusBarUtil.setColor(ParamSetActivity.this, getResources().getColor(R.color.tittleBar), 0);
        setDamp();
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.title_activity_create_task));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        spmcsff1.setSelection(2);
        spmcsff2.setSelection(2);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();


    }


//



    public void setDamp() {
        nestedscrollview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == KeyEvent.ACTION_UP) {
                    appBar.setExpanded((openlimit > -170 ? true : false), true);
                    if (openlimit > -170 ? true : false) {
                        llTittle.setAlpha(1);
                        collapsingToolbarLayout.setTitle("");
                    } else {
                        collapsingToolbarLayout.setTitle(getResources().getString(R.string.title_activity_create_task));
                    }
                    return true;
                }

                return false;
            }
        });

        //缩放中心点坐标
        final float height = 0;    // 根据布局文件中Linearlayout的宽度获得
        final float width = 720;     // 我屏幕宽度
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                openlimit = verticalOffset;
                float vel = 1f - (float) Math.abs(verticalOffset) / 262f;
                llTittle.setAlpha(vel);
                llTittle.setScaleX(vel);
                llTittle.setScaleY(vel);
                llTittle.setPivotX(width / 2);
                llTittle.setPivotY(height);
            }
        });
    }

    @OnClick({R.id.fab, R.id.btn_go_test, R.id.img_param_rwxx, R.id.tv_param_djxl1, R.id.tv_param_djxl2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab:
                Intent intent = new Intent();
                intent.setClass(ParamSetActivity.this, com.parachute.param.HistoryTaskSearchActivity.class);
                startActivityForResult(intent, ConstantData.HistoryTask_resultCode);
                break;
            case R.id.btn_go_test:
                // 参数合理性检测

                    SaveTask();
                    Intent intent1 = new Intent();

                    Toasty.success(ParamSetActivity.this, "开始测试！").show();
                    intent1.setClass(ParamSetActivity.this, TestActivity.class);
                    startActivity(intent1);

                break;
            case R.id.img_param_rwxx:
                if (ParamIsSet) {
                    lyset.setVisibility(View.GONE);
                    imageRwxx.setImageResource(R.drawable.rwxx_icon_open);
                    ParamIsSet = false;
                } else {
                    lyset.setVisibility(View.VISIBLE);
                    imageRwxx.setImageResource(R.drawable.rwxx_icon_close);
                    ParamIsSet = true;
                }
                break;
            case R.id.tv_param_djxl1:
                myApp.getInstance().setIsSetMotor1(true);
                intent = new Intent();
                intent.setClass(ParamSetActivity.this, MotorSelectorActivity.class);
                startActivityForResult(intent, ConstantData.Motor_requestCode);
                break;
            case R.id.tv_param_djxl2:
                myApp.getInstance().setIsSetMotor1(false);
                intent = new Intent();
                intent.setClass(ParamSetActivity.this, MotorSelectorActivity.class);
                startActivityForResult(intent, ConstantData.Motor_requestCode);

        }
    }

    private void SaveTask() {

        try {
            if (!etTaskName.getText().toString().equals("")) {
                mTask.setUnitName(etTaskName.getText().toString());
            } else {
                mTask.setUnitName("未设定单位");
            }
            if (!etNumber.getText().toString().equals("")) {
                mTask.setGasePumpNumber(etNumber.getText().toString());
            } else {
                mTask.setGasePumpNumber("未设定编号");
            }
            if (!etPeopleName.getText().toString().equals("")) {
                mTask.setPeopleName(etPeopleName.getText().toString());
            } else {
                mTask.setPeopleName("未设定人员");
            }


                mTask.setCsff(" ");




            if (!mTask.get_IsCompleteTask()) {

                mTask.setGreateTaskTime(DateUtil.getGreatedTaskTime());

            }
            TaskEntity tmpTask = new TaskEntity();
            CopyTask(tmpTask, mTask);
            tmpTask.setGreateTaskTime(DateUtil.getGreatedTaskTime());
            new GreateTaskUtils().insert(tmpTask);
            MyApp.getInstance().setTaskEnity(tmpTask);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //EditText的监听器
    class TextChangeDYBB implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            try {

                Animation mShakeAnim = AnimationUtils.loadAnimation(ParamSetActivity.this, R.anim.shake_x);
                DecimalFormat df4 = new DecimalFormat("0.0");
                double mDybbone, mDybbtwo;
                double dybb;
                mDybbone = Double.parseDouble(etdybb1.getText().toString());
                mDybbtwo = Double.parseDouble(etdybb2.getText().toString());
                if (mDybbtwo == 0.0f) {
                    etdybb2.startAnimation(mShakeAnim);
                    tvdybb.setText((""));

                } else {
                    if (mDybbtwo > mDybbone) {
                        dybb = mDybbtwo / mDybbone;
                        etdybb1.setTextColor(Color.argb(160, 255, 140, 0));

                        etdybb2.setTextColor(Color.argb(160, 255, 140, 0));
                    } else {
                        dybb = mDybbone / mDybbtwo;
                        etdybb1.setTextColor(Color.BLACK);

                        etdybb2.setTextColor(Color.BLACK);
                    }
                    tvdybb.setText(df4.format(dybb));
                }

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        }

    }//EditText的监听器

    class TextChangeDLBB implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            try {


                DecimalFormat df4 = new DecimalFormat("0.0");
                double mDlbbone, mDlbbtwo;
                double dlbb;
                mDlbbone = Double.parseDouble(etdlbb1.getText().toString());
                mDlbbtwo = Double.parseDouble(etdlbb2.getText().toString());
                if (mDlbbtwo == 0.0f) {
                    etdlbb2.startAnimation(mShakeAnim);
                    tvdlbb.setText((""));
                } else {
                    if (mDlbbtwo > mDlbbone) {
                        dlbb = mDlbbtwo / mDlbbone;
                        etdlbb1.setTextColor(Color.argb(160, 255, 140, 0));

                        etdlbb2.setTextColor(Color.argb(160, 255, 140, 0));
                    } else {
                        dlbb = mDlbbone / mDlbbtwo;
                        etdlbb1.setTextColor(Color.BLACK);

                        etdlbb2.setTextColor(Color.BLACK);
                    }
                    tvdlbb.setText(df4.format(dlbb));
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * 根据值, 设置spinner默认选中:
     *
     * @param spinner
     * @param value
     */
    public static void setSpinnerItemSelectedByValue(Spinner spinner, String value) {
        SpinnerAdapter apsAdapter = spinner.getAdapter(); //得到SpinnerAdapter对象
        int k = apsAdapter.getCount();
        for (int i = 0; i < k; i++) {
            if (value.equals(apsAdapter.getItem(i).toString())) {
                spinner.setSelection(i, true);// 默认选中项
                break;
            }
        }
    }

    // -------------------------------------------------——-------------------获取系统时间
    String getSysTime() {
// shijain
        String datesString;
        String monthString;
        String houString;
        String minString;
        String secondString;
        Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
        t.setToNow(); // 取得系统时间。
        int year = t.year;
        int month = t.month + 1;
        int date = t.monthDay;
        int hour = t.hour; // 0-23
        int minute = t.minute;
        int second = t.second;
        if (date < 10) {
            datesString = "0" + date;
        } else {
            datesString = date + "";
        }
        if (month < 10) {
            monthString = "0" + month;
        } else {
            monthString = month + "";
        }
        if (hour < 10) {
            houString = "0" + hour;
        } else {
            houString = hour + "";
        }
        if (minute < 10) {
            minString = "0" + minute;
        } else {
            minString = minute + "";
        }
        if (second < 10) {
            secondString = "0" + second;
        } else {
            secondString = second + "";
        }
        String dString = year + "-" + monthString + "-" + datesString + "\n\t"
                + houString + ":" + minString + ":" + secondString;
        return dString;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        int motorResultCode = ConstantData.Motor_resultCode; //

        switch (resultCode) {


            case 55: // 历史任务返回码  ConstantData.HistoryTask_resultCode
                Long taskId = data.getLongExtra(ConstantData.HistoryTask_ID_resultCode, 1L);
                GreateTaskUtils greateTaskUtils = new GreateTaskUtils();
                // 这个只是复用参数的历史任务，参数有可能在此基础上更改，不一定就是测试任务。


                TaskEntity mmTask = greateTaskUtils.query(taskId);
                if (mmTask.get_IsCompleteTask() == true) {
                    mTask = new TaskEntity();
                }
                CopyTask(mTask, mmTask);
                this.setParForHistoryTask(mTask);

                break;
            default:
                break;
        }
    }

    private void CopyTask(TaskEntity mTask, TaskEntity mmTask) {

        mTask.setBy12(mmTask.getBy12());
        mTask.setBy11(mmTask.getBy11());
        mTask.setBy10(mmTask.getBy10());
        mTask.setBy9(mmTask.getBy9());
        mTask.setBy8(mmTask.getBy8());
        mTask.setBy7(mmTask.getBy7());
        mTask.setBy6(mmTask.getBy6());
        mTask.setBy5(mmTask.getBy5());
        mTask.setBy4(mmTask.getBy4());
        mTask.setBy3(mmTask.getBy3());
        mTask.setBy2(mmTask.getBy2());
        mTask.setBy1(mmTask.getBy1());

        mTask.setCsff(mmTask.getCsff());
//        mTask.setGreateTaskTime(mmTask.getGreateTaskTime());
//        mTask.setTaskHaveGetData(mmTask. getTaskHaveGetData());
//        mTask.set_IsCompleteTask(mmTask. get_IsCompleteTask());
        mTask.setPeopleName(mmTask.getPeopleName());
        mTask.setGasePumpNumber(mmTask.getGasePumpNumber());
        mTask.setUnitName(mmTask.getUnitName());

    }

    public void setParForHistoryTask(TaskEntity taskEnity) {


        try {
            etTaskName.setText(taskEnity.getUnitName());
            etNumber.setText(taskEnity.getGasePumpNumber());
            etPeopleName.setText(taskEnity.getPeopleName());


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
