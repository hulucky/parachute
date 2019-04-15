package com.parachute.administrator.DATAbase.greendao;

import com.xzkydz.function.search.module.ITaskRoot;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class TaskEntity implements ITaskRoot {

    @Id(autoincrement = true)
    private Long id;
    //受检单位名称
    private String unitName;
    //编号
    private String gasePumpNumber;
    //测试员
    private String peopleName;
    //测试任务的状态
    private boolean _IsCompleteTask;
    //测试任务已经测得测试数据条数
    private int taskHaveGetData;
    //测试任务的创建时间
    private String greateTaskTime;

    private String csff ; // 测试方法

    private String by1 ; // 备用1
    private String by2 ; // 备用2
    private String by3 ; // 备用3
    private String by4 ; // 备用4
    private String by5 ; // 备用5
    private String by6 ; // 备用6
    private String by7 ; // 备用7
    private String by8 ; // 备用8
    private String by9 ; // 备用9
    private String by10 ; // 备用10
    private String by11 ; // 备用11
    private String by12 ; // 备用12
    @Generated(hash = 2144082666)
    public TaskEntity(Long id, String unitName, String gasePumpNumber,
            String peopleName, boolean _IsCompleteTask, int taskHaveGetData,
            String greateTaskTime, String csff, String by1, String by2, String by3,
            String by4, String by5, String by6, String by7, String by8, String by9,
            String by10, String by11, String by12) {
        this.id = id;
        this.unitName = unitName;
        this.gasePumpNumber = gasePumpNumber;
        this.peopleName = peopleName;
        this._IsCompleteTask = _IsCompleteTask;
        this.taskHaveGetData = taskHaveGetData;
        this.greateTaskTime = greateTaskTime;
        this.csff = csff;
        this.by1 = by1;
        this.by2 = by2;
        this.by3 = by3;
        this.by4 = by4;
        this.by5 = by5;
        this.by6 = by6;
        this.by7 = by7;
        this.by8 = by8;
        this.by9 = by9;
        this.by10 = by10;
        this.by11 = by11;
        this.by12 = by12;
    }
    @Generated(hash = 397975341)
    public TaskEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUnitName() {
        return this.unitName;
    }
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
    public String getGasePumpNumber() {
        return this.gasePumpNumber;
    }
    public void setGasePumpNumber(String gasePumpNumber) {
        this.gasePumpNumber = gasePumpNumber;
    }
    public String getPeopleName() {
        return this.peopleName;
    }
    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }
    public boolean get_IsCompleteTask() {
        return this._IsCompleteTask;
    }
    public void set_IsCompleteTask(boolean _IsCompleteTask) {
        this._IsCompleteTask = _IsCompleteTask;
    }
    public int getTaskHaveGetData() {
        return this.taskHaveGetData;
    }
    public void setTaskHaveGetData(int taskHaveGetData) {
        this.taskHaveGetData = taskHaveGetData;
    }
    public String getGreateTaskTime() {
        return this.greateTaskTime;
    }
    public void setGreateTaskTime(String greateTaskTime) {
        this.greateTaskTime = greateTaskTime;
    }
    public String getCsff() {
        return this.csff;
    }
    public void setCsff(String csff) {
        this.csff = csff;
    }
    public String getBy1() {
        return this.by1;
    }
    public void setBy1(String by1) {
        this.by1 = by1;
    }
    public String getBy2() {
        return this.by2;
    }
    public void setBy2(String by2) {
        this.by2 = by2;
    }
    public String getBy3() {
        return this.by3;
    }
    public void setBy3(String by3) {
        this.by3 = by3;
    }
    public String getBy4() {
        return this.by4;
    }
    public void setBy4(String by4) {
        this.by4 = by4;
    }
    public String getBy5() {
        return this.by5;
    }
    public void setBy5(String by5) {
        this.by5 = by5;
    }
    public String getBy6() {
        return this.by6;
    }
    public void setBy6(String by6) {
        this.by6 = by6;
    }
    public String getBy7() {
        return this.by7;
    }
    public void setBy7(String by7) {
        this.by7 = by7;
    }
    public String getBy8() {
        return this.by8;
    }
    public void setBy8(String by8) {
        this.by8 = by8;
    }
    public String getBy9() {
        return this.by9;
    }
    public void setBy9(String by9) {
        this.by9 = by9;
    }
    public String getBy10() {
        return this.by10;
    }
    public void setBy10(String by10) {
        this.by10 = by10;
    }
    public String getBy11() {
        return this.by11;
    }
    public void setBy11(String by11) {
        this.by11 = by11;
    }
    public String getBy12() {
        return this.by12;
    }
    public void setBy12(String by12) {
        this.by12 = by12;
    }

    @Override
    public Long getTaskId() {
        return id;
    }

    @Override
    public String getTestFunction() {
        return csff;
    }
    @Override
    public String getNumber() {
        return gasePumpNumber;
    }
    @Override
    public boolean getTaskStatue() {
        return _IsCompleteTask;
    }
  
}
