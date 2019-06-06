package com.parachute.main;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.parachute.Tools.Method;
import com.parachute.administrator.DATAbase.R;
import com.parachute.data.DataManager;
import com.parachute.param.ParamSetActivity;
import com.parachute.pdf.StandardListActivity;
import com.parachute.report.ReportActivity;
import com.xzkydz.function.main.NavigationActivity;
import com.xzkydz.function.main.style.NavigationActivityManager;
import com.xzkydz.function.main.style.NavigationActivityManagerInf;
import com.xzkydz.function.pdf.ConstantPDF;
import com.xzkydz.function.pdf.PdfActivity;



public class MyNavigationActivity extends NavigationActivity{
    @Override
    public NavigationActivityManagerInf getNavigationActivityManager() {
        // 如果切图命名和库中使用的命名相同，引用库会直接替换切图，无需手动设置。
        // new 一个主界面管理者
        NavigationActivityManager navigationActivity = new NavigationActivityManager();
        // 侧边栏显示打印Item
        // 侧边栏显示配置Item
        // 侧边栏顶部字体颜色
        // 侧边栏Item 字体颜色
        // 设置左上角Tittle内容
        navigationActivity.setShowPrints(true)
                .setShowConfiguration(true)
                .setLeftHeadTextColor(R.color.lib_black)
                .setLeftItemTextColor(R.color.lib_black)
                .setAppName(R.string.app_name);
        return navigationActivity;
    }
    // 测试按钮点击事件
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onTestButtonClickListener() {
        Method.toActivity(MyNavigationActivity.this,ParamSetActivity.class);
//        Toast.makeText(getApplicationContext(),"测试按钮",Toast.LENGTH_SHORT).show();
    }
    // 数据管理按钮点击事件
    @Override
    public void onDataManagerClickListener() {
        Method.toActivity(MyNavigationActivity.this,DataManager.class);
        Toast.makeText(MyNavigationActivity.this,"数据管理",Toast.LENGTH_SHORT).show();
    }
    // "传感器"按钮点击事件
    @Override
    public void onSensorButtonClicklistener() {
        Method.toActivity(MyNavigationActivity.this,SensorActivity.class);
        Toast.makeText(MyNavigationActivity.this,"传感器",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onQueryReportClicklistener() {
        startActivity(new Intent(MyNavigationActivity.this, ReportActivity.class));
    }

    // "测试标准"的点击事件
    @Override
    public void OnTestStandarClicklistener() {
        //直接跳转到自定义的PdfListActivity子类即可
        startActivity(new Intent(MyNavigationActivity.this, StandardListActivity.class));
    }
    // 跳转到说明书界面,不需要自己继承类，直接跳转到库中的 PdfActivity类
    // 需要传入“说明书名称” 、“pdf文档在项目中的路径”
    @Override
    public void onSpecificationClickListenter() {
        super.onSpecificationClickListenter();
        Intent intent = new Intent(MyNavigationActivity.this, com.parachute.pdf.PdfguideActivity.class);
        intent.putExtra(ConstantPDF.nameFiles, "说明书");
        intent.putExtra(ConstantPDF.urlFiles, "pdf/说明书.pdf");
        startActivity(intent);
    }
//    // 侧边栏 打印Item 点击事件，不显示则无需复写
//    @Override
//    public void onPrintsItemClickListener() {
//        super.onPrintsItemClickListener();
//        Toast.makeText(MyNavigationActivity.this,"打印",Toast.LENGTH_SHORT).show();
//    }
//    // 侧边栏 配置Item 点击事件，不显示则无需复写
//    @Override
//    public void onConfigurationItemClickListener() {
//        super.onConfigurationItemClickListener();
//        Toast.makeText(MyNavigationActivity.this,"配置",Toast.LENGTH_SHORT).show();
//    }
    // 返回显示 “产品信息”、“企业信息” 的 MarkdownActivity
    @NonNull
    @Override
    public Class<?> getMarkdownActivity() {
        return VMarkdownActivity.class;
    }
}
