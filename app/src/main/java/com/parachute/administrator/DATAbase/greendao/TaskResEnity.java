package com.parachute.administrator.DATAbase.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

@Entity
public class TaskResEnity {

    //    @Entity：告诉GreenDao该对象为实体，只有被@Entity注释的Bean类才能被dao类操作
    //    @Id：对象的Id，使用Long类型作为EntityId，否则会报错。(autoincrement = true)表示主键会自增，如果false就会使用旧值
    //    @Property：可以自定义字段名，注意外键不能使用该属性
    //    @NotNull：属性不能为空
    //    @Transient：使用该注释的属性不会被存入数据库的字段中
    //    @Unique：该属性值必须在数据库中是唯一值
    //    @Generated：编译后自动生成的构造函数、方法等的注释，提示构造函数、方法等不能被修改

    //不能用int
    @Id(autoincrement = true)
    private Long id;

    private Long taskId;
    private String csff; // 测试方法
    private String tffs; // 通风方式
    private int saveIndex;
    private String SaveTime;
    private int saveType;//0 静负荷 1脱钩


    private float xk1;
    private float xk2;
    private float xdzds1;
    private float xdzds2;
    private float xdhcs1;
    private float xdhcs2;

    private float xjgd;
    private String curve;
    private float zdjsd;
    private float pjjsd;
    private float kxcsj;
    private float kxcjl;
    private float zdsj;

    @Generated(hash = 1994661030)
    public TaskResEnity(Long id, Long taskId, String csff, String tffs, int saveIndex,
                        String SaveTime, int saveType, float xk1, float xk2, float xdzds1, float xdzds2,
                        float xdhcs1, float xdhcs2, float xjgd, String curve, float zdjsd, float pjjsd,
                        float kxcsj, float kxcjl, float zdsj) {
        this.id = id;
        this.taskId = taskId;
        this.csff = csff;
        this.tffs = tffs;
        this.saveIndex = saveIndex;
        this.SaveTime = SaveTime;
        this.saveType = saveType;
        this.xk1 = xk1;
        this.xk2 = xk2;
        this.xdzds1 = xdzds1;
        this.xdzds2 = xdzds2;
        this.xdhcs1 = xdhcs1;
        this.xdhcs2 = xdhcs2;
        this.xjgd = xjgd;
        this.curve = curve;
        this.zdjsd = zdjsd;
        this.pjjsd = pjjsd;
        this.kxcsj = kxcsj;
        this.kxcjl = kxcjl;
        this.zdsj = zdsj;
    }

    @Generated(hash = 131024579)
    public TaskResEnity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskId() {
        return this.taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getCsff() {
        return this.csff;
    }

    public void setCsff(String csff) {
        this.csff = csff;
    }

    public String getTffs() {
        return this.tffs;
    }

    public void setTffs(String tffs) {
        this.tffs = tffs;
    }

    public int getSaveIndex() {
        return this.saveIndex;
    }

    public void setSaveIndex(int saveIndex) {
        this.saveIndex = saveIndex;
    }

    public String getSaveTime() {
        return this.SaveTime;
    }

    public void setSaveTime(String SaveTime) {
        this.SaveTime = SaveTime;
    }

    public int getSaveType() {
        return this.saveType;
    }

    public void setSaveType(int saveType) {
        this.saveType = saveType;
    }

    public float getXk1() {
        return this.xk1;
    }

    public void setXk1(float xk1) {
        this.xk1 = xk1;
    }

    public float getXk2() {
        return this.xk2;
    }

    public void setXk2(float xk2) {
        this.xk2 = xk2;
    }

    public float getXdzds1() {
        return this.xdzds1;
    }

    public void setXdzds1(float xdzds1) {
        this.xdzds1 = xdzds1;
    }

    public float getXdzds2() {
        return this.xdzds2;
    }

    public void setXdzds2(float xdzds2) {
        this.xdzds2 = xdzds2;
    }

    public float getXdhcs1() {
        return this.xdhcs1;
    }

    public void setXdhcs1(float xdhcs1) {
        this.xdhcs1 = xdhcs1;
    }

    public float getXdhcs2() {
        return this.xdhcs2;
    }

    public void setXdhcs2(float xdhcs2) {
        this.xdhcs2 = xdhcs2;
    }

    public float getXjgd() {
        return this.xjgd;
    }

    public void setXjgd(float xjgd) {
        this.xjgd = xjgd;
    }

    public String getCurve() {
        return this.curve;
    }

    public void setCurve(String curve) {
        this.curve = curve;
    }

    public float getZdjsd() {
        return this.zdjsd;
    }

    public void setZdjsd(float zdjsd) {
        this.zdjsd = zdjsd;
    }

    public float getPjjsd() {
        return this.pjjsd;
    }

    public void setPjjsd(float pjjsd) {
        this.pjjsd = pjjsd;
    }

    public float getKxcsj() {
        return this.kxcsj;
    }

    public void setKxcsj(float kxcsj) {
        this.kxcsj = kxcsj;
    }

    public float getKxcjl() {
        return this.kxcjl;
    }

    public void setKxcjl(float kxcjl) {
        this.kxcjl = kxcjl;
    }

    public float getZdsj() {
        return this.zdsj;
    }

    public void setZdsj(float zdsj) {
        this.zdsj = zdsj;
    }


}