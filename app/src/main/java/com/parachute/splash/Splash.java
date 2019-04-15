package com.parachute.splash;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;

import com.parachute.administrator.DATAbase.R;
import com.parachute.main.MyNavigationActivity;
import com.xzkydz.function.splash.SplashActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Splash extends SplashActivity {
    String filePath = android.os.Environment.getExternalStorageDirectory()
            + "/CZD13W矿用防坠器无线多参数测试仪/.报告模板";
    // 需要跳转的主界面
    @Override
    public Class<?> getNavigationActivity() {
        return MyNavigationActivity.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(new Runnable() {
            public void run() {
                WriteToSD(Splash.this);
            }
        }).start();
    }

    // 设置闪屏页显示的内容
    @Override
    public void setShowContent() {
        super.setShowContent();
            setAppName("CZD13W矿用防坠器无线多参数测试仪");
//        setLawContent("我是法律声明");
//        setVersionName("我是版本号");
        // 设置闪屏页的背景,参数直接传入图片id即可
        setBgImag(R.drawable.splash);
    }
    public void WriteToSD(Context context) {
        // 创建文件夹
        new Thread() {
            @Override
            // 创建文件夹
            public void run() {
                File sd = Environment.getExternalStorageDirectory();
                String path = sd.getPath() + "/CZD13W矿用防坠器无线多参数测试仪/.报告模板";
                String path2 = sd.getPath() + "/CZD13W矿用防坠器无线多参数测试仪/测试报告";
                String path3 = sd.getPath() + "/CZD13W矿用防坠器无线多参数测试仪/.防坠器加速度曲线图";
                String path4 = sd.getPath() + "/CZD13W矿用防坠器无线多参数测试仪/软件";
                File file = new File(path);
                File file2 = new File(path2);
                File file3 = new File(path3);
                File file4 = new File(path4);
                if (!file.exists())
                    file.mkdir();
                if (!file2.exists())
                    file2.mkdir();
                if (!file3.exists())
                    file3.mkdir();
                if (!file4.exists())
                    file4.mkdir();
            }
        }.start();
        String from = "module.xls";
        String to = "/防坠器报告.xls";

        File file = new File(filePath + to);

        if (!file.exists()) {
            write(from, to, context, filePath);
        }
        String from3 = "WPSOffice.apk";
        String to3 = "/WPSOffice.apk";
        String from4 = "xunfei.apk";
        String to4 = "/讯飞输入法(Pad版).apk";


        String filePath1;
        filePath1 = Environment.getExternalStorageDirectory() + "/CZD13W矿用防坠器无线多参数测试仪/软件";

               File file3 = new File(filePath1 + to3);
        File file4 = new File(filePath1 + to4);

        if (!file3.exists()) {
            write(from3, to3, context, filePath1);
        }
        if (!file4.exists()) {
           write(from4, to4, context, filePath1);
        }


    }

    private void write(String from, String to, Context context, String mfilePath) {
        InputStream inputStream;
        try {
            inputStream = context.getResources().getAssets().open(from);
            File file = new File(mfilePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(mfilePath
                    + to);
            byte[] buffer = new byte[512];
            int count = 0;
            while ((count = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, count);

            }
            fileOutputStream.flush();
            fileOutputStream.close();
            inputStream.close();
            System.out.println("success");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
