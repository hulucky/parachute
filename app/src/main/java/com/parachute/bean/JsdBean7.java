package com.parachute.bean;

import com.parachute.utils.MyFunc;

//心跳包
public class JsdBean7 implements ISensorInf {

    public float powerVel = 0;//电量
    public float assiVel = 0;//信号
    public Long time = 0L;//获取传感器数据的时间点

    public byte[] bRec;//串口数据包

    public JsdBean7(byte[] bRec) {
        this.bRec = bRec;
        caculate(bRec);
    }

    //calculate:计算
    //解析串口数据包，并且更新Bean的属性（共19位）
    public void caculate(byte[] bRec) {
        this.bRec = bRec;
        time = System.currentTimeMillis();
        powerVel = MyFunc.twoBytesToInt(bRec,15);
        assiVel = MyFunc.HexToInt(MyFunc.Byte2Hex(bRec[17]));
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
        return 8;
    }

    @Override
    public long getTime() {
        return time;
    }
}
