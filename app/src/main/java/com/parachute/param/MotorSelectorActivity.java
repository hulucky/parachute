package com.parachute.param;







import com.parachute.administrator.DATAbase.greendao.MotorBean;
import com.parachute.administrator.DATAbase.greendao.MotorEnity;
import com.parachute.app.MyApp;
import com.xzkydz.function.motor.module.IMotorBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MotorSelectorActivity  extends com.xzkydz.function.motor.view.MotorSelectorActivity{

    MyApp myApp = MyApp.getInstance();
    private List<IMotorBean> iMotorBeans;

    @Override
    public List<IMotorBean> getMotorList() {
        iMotorBeans = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<MotorEnity> motorBeanList = myApp.getDaoInstant().getMotorEnityDao().loadAll();
                Collections.reverse(motorBeanList);
                for (MotorEnity mo : motorBeanList) {
                    MotorBean motorBean = new MotorBean();
                    motorBean.setId(mo.getId());
                    motorBean.setAdd(mo.getIsAdd());
                    motorBean.setDjxlStr(mo.getDJXL());
                    motorBean.setDjxhStr(mo.getDJ_LIB_NAME());
                    motorBean.setEddyStr(mo.getEDDY());
                    motorBean.setEddlStr(mo.getEddl());
                    motorBean.setEdglStr(mo.getEdgl());
                    motorBean.setEdxlStr(mo.getEdxl());
                    motorBean.setKzdlStr(mo.getKzdl());
                    motorBean.setKzglStr(mo.getKzgl());
                    motorBean.setEdglysStr(mo.getEdgl());
                    motorBean.setJsStr(mo.getJS());
                    motorBean.setWgjjdlStr(mo.getWgjjdl());
                    iMotorBeans.add(motorBean);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideDialog();
                        initListView(iMotorBeans);
                    }
                });
            }
        }).start();

        return iMotorBeans;
    }



    @Override
    public void insertNewMotor(final com.xzkydz.function.transform.MotorBean motorBean) {
        MotorBean motorBean1 = new MotorBean();
        motorBean1.setDjxhStr(motorBean.getDJ_LIB_NAME());
        motorBean1.setEddyStr(motorBean.getEDDY());
        motorBean1.setEddlStr(motorBean.getEDDL());
        motorBean1.setEdglStr(motorBean.getEDGL());
        motorBean1.setEdxlStr(motorBean.getEDXL());
        motorBean1.setJsStr(motorBean.getJS());
        motorBean1.setKzdlStr(motorBean.getKZDL());
        motorBean1.setKzglStr(motorBean.getKZGL());
        motorBean1.setWgjjdlStr(motorBean.getWGJJDL());
        motorBean1.setAdd(true);
        motorBean1.setDjxlStr("新创建");
        iMotorBeans.add(0,motorBean1);
        setMotorAllList(iMotorBeans);
        initListView(iMotorBeans);

        new Thread(new Runnable() {
            @Override
            public void run() {
                MotorEnity motorEnity = new MotorEnity();
                motorEnity.setIsAdd(true);
                motorEnity.setDJ_LIB_NAME(motorBean.getDJ_LIB_NAME());
                motorEnity.setEDDY(motorBean.getEDDY());
                motorEnity.setEDDL(motorBean.getEDDL());
                motorEnity.setEDGL(motorBean.getEDGL());
                motorEnity.setEDXL(motorBean.getEDXL());
                motorEnity.setJS(motorBean.getJS());
                motorEnity.setKZDL(motorBean.getKZDL());
                motorEnity.setKZGL(motorBean.getKZGL());
                motorEnity.setWGJJDL(motorBean.getWGJJDL());
                motorEnity.setDJXL("录入");
                myApp.getDaoInstant().getMotorEnityDao().insert(motorEnity);
            }
        }).start();
    }

    @Override
    public void deleteMotor(IMotorBean iMotorBean) {
        iMotorBeans.remove(iMotorBean);
        setMotorAllList(iMotorBeans);
        initListView(iMotorBeans);
    }
}
