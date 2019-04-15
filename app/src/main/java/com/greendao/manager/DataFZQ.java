package com.greendao.manager;


import com.parachute.administrator.DATAbase.greendao.TaskEntity;
import com.parachute.administrator.DATAbase.greendao.TaskResEnity;
import com.parachute.app.MyApp;

import org.greenrobot.greendao.annotation.Unique;

import java.text.DecimalFormat;
import java.util.List;

public class DataFZQ {
    private TaskEntity mHisTask;
    private TaskResEnity mRes;

    public float getXk1() {
        return xk1;
    }

    public void setXk1(float xk1) {
        this.xk1 = xk1;
    }

    public float getXk2() {
        return xk2;
    }

    public void setXk2(float xk2) {
        this.xk2 = xk2;
    }

    public float getXdzds1() {
        return xdzds1;
    }

    public void setXdzds1(float xdzds1) {
        this.xdzds1 = xdzds1;
    }

    public float getXdzds2() {
        return xdzds2;
    }

    public void setXdzds2(float xdzds2) {
        this.xdzds2 = xdzds2;
    }

    public float getXdhcs1() {
        return xdhcs1;
    }

    public void setXdhcs1(float xdhcs1) {
        this.xdhcs1 = xdhcs1;
    }

    public float getXdhcs2() {
        return xdhcs2;
    }

    public void setXdhcs2(float xdhcs2) {
        this.xdhcs2 = xdhcs2;
    }

    public float getXjgd() {
        return xjgd;
    }

    public void setXjgd(float xjgd) {
        this.xjgd = xjgd;
    }

    public String getCurve() {
        return curve;
    }

    public void setCurve(String curve) {
        this.curve = curve;
    }

    public float getZdjsd() {
        return zdjsd;
    }

    public void setZdjsd(float zdjsd) {
        this.zdjsd = zdjsd;
    }

    public float getPjjsd() {
        return pjjsd;
    }

    public void setPjjsd(float pjjsd) {
        this.pjjsd = pjjsd;
    }

    public float getKxcsj() {
        return kxcsj;
    }

    public void setKxcsj(float kxcsj) {
        this.kxcsj = kxcsj;
    }

    public float getKxcjl() {
        return kxcjl;
    }

    public void setKxcjl(float kxcjl) {
        this.kxcjl = kxcjl;
    }

    public float getZdsj() {
        return zdsj;
    }

    public void setZdsj(float zdsj) {
        this.zdsj = zdsj;
    }

    private float xk1;
    private float xk2;
    private float xdzds1;
    private float xdzds2;
    private float xdhcs1;
    private float xdhcs2;
    private float xjgd;

    private float xk1length;
    private float xk2length;
    private float xdzds1length;
    private float xdzds2length;
    private float xdhcs1length;
    private float xdhcs2length;
    private float xjgdlength;

    public float getXk1length() {
        return xk1length;
    }

    public void setXk1length(float xk1length) {
        this.xk1length = xk1length;
    }

    public float getXk2length() {
        return xk2length;
    }

    public void setXk2length(float xk2length) {
        this.xk2length = xk2length;
    }

    public float getXdzds1length() {
        return xdzds1length;
    }

    public void setXdzds1length(float xdzds1length) {
        this.xdzds1length = xdzds1length;
    }

    public float getXdzds2length() {
        return xdzds2length;
    }

    public void setXdzds2length(float xdzds2length) {
        this.xdzds2length = xdzds2length;
    }

    public float getXdhcs1length() {
        return xdhcs1length;
    }

    public void setXdhcs1length(float xdhcs1length) {
        this.xdhcs1length = xdhcs1length;
    }

    public float getXdhcs2length() {
        return xdhcs2length;
    }

    public void setXdhcs2length(float xdhcs2length) {
        this.xdhcs2length = xdhcs2length;
    }

    public float getXjgdlength() {
        return xjgdlength;
    }

    public void setXjgdlength(float xjgdlength) {
        this.xjgdlength = xjgdlength;
    }

    private String curve;
    private float zdjsd;
    private float pjjsd;
    private float kxcsj;
    private float kxcjl;
    private float zdsj;


    public int getSaveType() {
        return saveType;
    }

    public void setSaveType(int saveType) {
        this.saveType = saveType;
    }

    private int saveType;//0 静负荷 1脱钩
    private String mff;


    private Boolean mventi;
    private Long mId;

    public class sensor {


        public Integer getMpower() {
            return mpower;
        }

        public void setMpower(Integer mpower) {
            this.mpower = mpower;
        }

        public Integer getMsignal() {
            return msignal;
        }

        public void setMsignal(Integer msignal) {
            this.msignal = msignal;
        }

        public Integer getMstate() {
            return mstate;
        }

        public void setMstate(Integer mstate) {
            this.mstate = mstate;
        }

        Integer mpower=100;
        Integer msignal=100;
        Integer mstate=0;
    }

    public sensor[] getSensors() {
        return sensors;
    }

    public void setSensors(sensor[] sensors) {
        this.sensors = sensors;
    }

    public void initSensors()
    {
        if (sensors == null) {
            sensors = new sensor[21];
            for (int i=0;i<21;i++){
            sensors[i]=new sensor();}
        }
    }

    public void setSensor(Integer index, Integer power, Integer signal, Integer state) {

        sensor ms = new sensor();
        ms.setMpower(power);
        ms.setMsignal(signal);
        ms.setMstate(state);
        sensors[index] = ms;
        MyApp.getInstance().SetSensorConnectStateTrue(index);
    }

    private sensor[] sensors;


    DecimalFormat df1 = new DecimalFormat("#.0");
    DecimalFormat df2 = new DecimalFormat("#.00");
    DecimalFormat df3 = new DecimalFormat("#.000");

    public void SetHisTask(TaskEntity mtask) {
        this.mHisTask = mtask;
        mff = mHisTask.getCsff();


    }

    public TaskEntity GetHisTask() {
//		mHisTask.setFluid(mSczs);
//		mHisTask.setPsgd((float) mCfmj);
//
//		if (mff == 3) {
//			mHisTask.setJkyl((float) mPtgxs);
//		} else if (mff == 4) {
//			mHisTask.setJkyl(mCymjx);
//			mHisTask.setBwc(mCymjd);
//		}
        if (mHisTask != null) {
            return this.mHisTask;
        } else if (mRes != null) {
            return MyApp.getDaoInstant().getTaskEntityDao().queryBuilder().where(TaskEntityDao.Properties.Id.eq(mRes.getTaskId())).list().get(0);
        } else {
            return new TaskEntity();
        }
    }

    public void SetResOnly(TaskResEnity mres) {
        this.mRes = mres;
        List<TaskEntity> mlist = MyApp.getDaoInstant().getTaskEntityDao().queryBuilder().where(TaskEntityDao.Properties.Id.eq(mres.getTaskId())).list();
        if (mlist.size() > 0) {
            SetHisTask(mlist.get(0));
        }
        mId = mres.getId();
        if (mff == null) {
            mff = mres.getCsff();
        }
        setXk1(mres.getXk1());
        setXk2(mres.getXk2());
        setXdzds1(mres.getXdzds1());
        setXdzds2(mres.getXdzds2());
        setXdhcs1(mres.getXdhcs1());
        setXdhcs2(mres.getXdhcs2());
        setXjgd(mres.getXjgd());

        setCurve(mres.getCurve());
        setZdjsd(mres.getZdjsd());
        setPjjsd(mres.getPjjsd());
        setKxcsj(mres.getKxcsj());
        setKxcjl(mres.getKxcjl());
        setZdsj(mres.getZdsj());


    }

    public void SetRes(TaskResEnity mres) {
        this.mRes = mres;
        mId = mres.getId();

    }

    public void CopyRes(TaskResEnity mRes) {
        mRes.setTaskId(mHisTask.getId());

        mRes.setCsff(mff);
        mRes.setXk1(xk1);
        mRes.setXk2(xk2);
        mRes.setXdzds1(xdzds1);
        mRes.setXdzds2(xdzds2);
        mRes.setXdhcs1(xdhcs1);
        mRes.setXdhcs2(xdhcs2);
        mRes.setXjgd(xjgd);
        mRes.setCurve(curve);
        mRes.setZdjsd(zdjsd);
        mRes.setPjjsd(pjjsd);
        mRes.setKxcsj(kxcsj);
        mRes.setKxcjl(kxcjl);
        mRes.setZdsj(zdsj);

    }

    public TaskResEnity GetRes() {
        TaskResEnity mmRes = new TaskResEnity();
        mmRes.setId(mId);
        if (mHisTask != null) {
            mmRes.setTaskId(mHisTask.getId());
        } else {
            mmRes.setTaskId(mRes.getTaskId());
        }

        mmRes.setSaveTime(mRes.getSaveTime());
        mmRes.setXk1(xk1);
        mmRes.setXk2(xk2);
        mmRes.setXdzds1(xdzds1);
        mmRes.setXdzds2(xdzds2);
        mmRes.setXdhcs1(xdhcs1);
        mmRes.setXdhcs2(xdhcs2);
        mmRes.setXjgd(xjgd);
        mmRes.setCurve(curve);
        mmRes.setZdjsd(zdjsd);
        mmRes.setPjjsd(pjjsd);
        mmRes.setKxcsj(kxcsj);
        mmRes.setKxcjl(kxcjl);
        mmRes.setZdsj(zdsj);

        return mmRes;
    }

    public boolean Refresh() {
    return true;
    }



}
