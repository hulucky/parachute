package com.parachute.bean;

import com.parachute.utils.MyFunc;

public class WyBean4 implements ISensorInf {

    public float powerVel = 0;//电量
    public float assiVel = 0;//信号
    public Long time = 0L;//获取传感器数据的时间点

    public byte[] bRec;//串口数据包

    public WyBean4(byte[] bRec) {
        this.bRec = bRec;
        caculate(bRec);
    }

    //calculate:计算
    //解析串口数据包，并且更新Bean的属性（共38位）
    public void caculate(byte[] bRec) {
        this.bRec = bRec;
        time = System.currentTimeMillis();
        powerVel = MyFunc.twoBytesToInt(bRec,22);
        assiVel = MyFunc.HexToInt(MyFunc.Byte2Hex(bRec[24]));
    }

    public float getPowerVel() {
        return powerVel;
    }

    public void setPowerVel(float powerVel) {
        this.powerVel = powerVel;
    }

    public float getAssiVel() {
        return assiVel;
    }

    public void setAssiVel(float assiVel) {
        this.assiVel = assiVel;
    }

    public void setTime(Long time) {
        this.time = time;
    }


    @Override
    public int statue() {
        return 0;
    }

    @Override
    public float getPower() {
        return powerVel;
    }

    @Override
    public float getSignal() {
        return assiVel;
    }

    @Override
    public int getSensorType() {
        return 4;
    }

    @Override
    public long getTime() {
        return time;
    }
}
