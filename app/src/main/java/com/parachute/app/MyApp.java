package com.parachute.app;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ViewUtils;
import android.text.format.Time;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.greendao.manager.DaoMaster;
import com.greendao.manager.DaoSession;
import com.greendao.manager.MotorTxtToDb;
import com.nrs.utils.tools.CrashHandler;
import com.parachute.administrator.DATAbase.R;
import com.parachute.administrator.DATAbase.greendao.TaskEntity;
import com.xzkydz.function.app.KyApp;
import com.xzkydz.function.style.AppStyle;
import com.xzkydz.function.utils.SharedPreferencesUtils;

public class MyApp extends KyApp {
    private static MyApp mInstance;
    private static DaoSession daoSession;
    private static TaskEntity taskEntity = new TaskEntity(); //测试任务
    private static Boolean isSetMotor1;
    private Integer[] sensorstate;

    public static String getSnapName() {
        return snapName;
    }

    public static void setSnapName() {
        MyApp.snapName = getSysTime();
    }

    static String getSysTime() {
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

    private static String snapName;

    public static Boolean getIsSetMotor1() {
        return isSetMotor1;
    }

    public static void setIsSetMotor1(Boolean isSetMotor1) {
        MyApp.isSetMotor1 = isSetMotor1;
    }

    @Override

    public void onCreate() {
        super.onCreate();

        setupDatabase();
        initAllData();
        insertTxtMotor();
        mInstance = MyApp.this;
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
        sensorstate=new Integer[21];
        for(int i=0;i<21;i++)
        {
            sensorstate[i]=0;
        }


    }
    public void SetSensorConnectStateFalse(Integer mindex)
    {
        sensorstate[mindex]=0;
    }
    public void SetSensorConnectStateTrue(Integer mindex)
    {
        sensorstate[mindex]=1;
    }

    public Integer getSensorstateByIndex(Integer mindex) {
        return sensorstate[mindex];
    }

    public  Integer getAllSensorDisconnect(Integer findex,Integer eindex)
    {
        Integer res=0;
        for (int i=findex;i<=eindex;i++)
        {
            res=res+sensorstate[i];
        }
        return res;
    }

    @Override
    public void setAppStyleColor() {
        super.setAppStyleColor();
        //设置APP的名称
        AppStyle.appNameId = R.string.app_name;
        //设置APP的主题色（Toolbar的颜色）
        AppStyle.appToolbarColor = R.color.colorPrimary;
        //实例化SharedPreferenceUtils工具类方便调用
        SharedPreferencesUtils.init(this);
    }

    /**
     * 配置数据库
     */
    private void setupDatabase() {
        //gasepump.db"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getApplicationContext(), "database", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }

    /*
     * description：将Txt格式的电机数据库转换为sqlite
     */
    private void insertTxtMotor() {
        MotorTxtToDb motorTxtToDb = new MotorTxtToDb();
        motorTxtToDb.getMotorDb(getApplicationContext());
    }

    /**
     * Description: 初始化全局变量，确保在对一次任务进行测试、增删改查的时候，所用的变量只需一个地方获取，全局通用。
     */
    private void initAllData() {
        taskEntity = null; // 正在操作的测试任务

    }

    //用于返回Application实例
    public static MyApp getInstance() {
        return mInstance;
    }

    public static TaskEntity getTaskEnity() {
        if (taskEntity == null) {
            taskEntity = new TaskEntity();
        }
        return taskEntity;
    }


    public static void setTaskEnity(TaskEntity taskEnity) {
        MyApp.taskEntity = taskEnity;
    }

    public static void setCustomDialogStyle(android.app.AlertDialog dialog) {
        final Resources res = dialog.getContext().getResources();
        try {
            int topPanelId = res.getIdentifier("topPanel", "id", "android");
            //获取顶部
            LinearLayout topPanel = (LinearLayout) dialog.findViewById(topPanelId);
            topPanel.setBackgroundResource(R.drawable.bg_gv);//设置顶部背景
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, //设置顶部高度
                    dp2px(dialog.getContext(), 80));
            topPanel.setLayoutParams(params);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            int dividerId = res.getIdentifier("titleDivider", "id", "android");//设置分隔线
            View divider = dialog.findViewById(dividerId);
            divider.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            int titleId = res.getIdentifier("alertTitle", "id", "android");//获取标题title
            TextView title = (TextView) dialog.findViewById(titleId);//设置标题
            title.setTextColor(Color.WHITE);//标题文字颜色
            title.setTextSize(18);//文字大小
            title.setGravity(Gravity.CENTER);//文字位置
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            int customPanelId = res.getIdentifier("customPanel", "id", "android");//设置内容
            FrameLayout customPanel = (FrameLayout) dialog.findViewById(customPanelId);
            customPanel.setBackgroundColor(Color.CYAN);//背景透明
            customPanel.getChildAt(0).setBackgroundColor(Color.WHITE);
            customPanel.setPadding(dp2px(dialog.getContext(), 8), 0, dp2px(dialog.getContext(), 8), 0);//设置padding

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            int buttonPanelId = res.getIdentifier("buttonPanel", "id", "android");//获取底部
            LinearLayout buttonPanel = (LinearLayout) dialog.findViewById(buttonPanelId);
            buttonPanel.setBackgroundResource(R.drawable.bg_btn_start);//设置底部背景
            buttonPanel.setPadding(dp2px(dialog.getContext(), 8), 1, dp2px(dialog.getContext(), 8), 0);
            Button button1 = (Button) dialog.findViewById(android.R.id.button1);//设置底部Button
            button1.setTextColor(Color.RED);//文字颜色
            button1.setTextSize(18);//文字大小
            button1.setBackgroundResource(R.drawable.bg_divider);//Button圆形背景框
            Button button2 = (Button) dialog.findViewById(android.R.id.button2);
            button2.setTextColor(Color.WHITE);
            button2.setTextSize(18);
            button2.setBackgroundResource(R.drawable.bg_motor_xh);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static int dp2px(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    String strtest="";
    public void setStrTest(String s) {
        strtest=s;
    }
    public String getStrTest()
    {
        return strtest;
    }
    String strstate="";
    public void setStrTest1(String s) {
        strstate+=s;
    }
    public void initStrstate()
    {
        strstate="";
    }
    public String getStrstate(){return  strstate;}
}

