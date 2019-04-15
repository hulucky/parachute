package com.greendao.manager;


import com.parachute.administrator.DATAbase.greendao.MotorEnity;
import com.parachute.app.MyApp;
import com.xzkydz.function.transform.InsertMotorToLib;
import com.xzkydz.function.transform.MotorBean;

import java.util.List;

public class MotorTxtToDb extends InsertMotorToLib {

    /*
     * description:
     */
    @Override
    public void insertMotor(final List<MotorBean> list) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 插入数据库
                for (MotorBean mo :list) {
                    MotorEnity mlib = new MotorEnity();
                    mlib.setDJXL(mo.getDJXL());//序列/名称
                    mlib.setDJ_LIB_NAME(mo.getDJ_LIB_NAME());//型号
                    mlib.setJS(mo.getJS());//级数
                    mlib.setEDGL(mo.getEDGL());//额定功率
                    mlib.setEDDL(mo.getEDDL());//额定电流
                    mlib.setEDXL(mo.getEDXL());//额定效率
                    mlib.setKZDL(mo.getKZDL());//空载电流
                    mlib.setKZGL(mo.getKZGL());//空载功率
                    mlib.setEDDY(mo.getEDDY());//额定电压
                    mlib.setEDGLYS("0.84");//额定功率因数
                    mlib.setWGJJDL("0.08");//无功经济当量
                    mlib.setIsAdd(false);
                    MyApp.getDaoInstant().getMotorEnityDao().insert(mlib);
                }
            }
        }).start();
    }
}
