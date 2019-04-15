package com.parachute.administrator.DATAbase.greendao;


import com.xzkydz.function.motor.module.IMotorBean;

public class MotorBean implements IMotorBean {
    private Long id = 10L;
    private String djxlStr = "";
    private String djxhStr = "";
    private String eddyStr = "";
    private String eddlStr = "";
    private String edglStr = "";
    private String edxlStr = "";
    private String kzdlStr = "";
    private String kzglStr = "";
    private String edglysStr = "";
    private String jsStr = "";
    private String wgjjdlStr = "";
    private boolean isAdd = false;


    public String getDjxlStr() {
        return djxlStr;
    }

    public void setDjxlStr(String djxlStr) {
        this.djxlStr = djxlStr;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDjxhStr(String djxhStr) {
        this.djxhStr = djxhStr;
    }

    public void setEddyStr(String eddyStr) {
        this.eddyStr = eddyStr;
    }

    public void setEddlStr(String eddlStr) {
        this.eddlStr = eddlStr;
    }

    public void setEdglStr(String edglStr) {
        this.edglStr = edglStr;
    }

    public void setEdxlStr(String edxlStr) {
        this.edxlStr = edxlStr;
    }

    public void setKzdlStr(String kzdlStr) {
        this.kzdlStr = kzdlStr;
    }

    public void setKzglStr(String kzglStr) {
        this.kzglStr = kzglStr;
    }

    public void setEdglysStr(String edglysStr) {
        this.edglysStr = edglysStr;
    }

    public void setJsStr(String jsStr) {
        this.jsStr = jsStr;
    }

    public void setWgjjdlStr(String wgjjdlStr) {
        this.wgjjdlStr = wgjjdlStr;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getDjxl() {
        return djxlStr;
    }

    @Override
    public String getDjLibName() {
        return djxhStr;
    }

    @Override
    public String getEddy() {
        return eddyStr;
    }

    @Override
    public String getEddl() {
        return eddlStr;
    }

    @Override
    public String getEdgl() {
        return edglStr;
    }

    @Override
    public String getEdxl() {
        return edxlStr;
    }

    @Override
    public String getKzdl() {
        return kzdlStr;
    }

    @Override
    public String getKzgl() {
        return kzglStr;
    }

    @Override
    public String getEdglys() {
        return edglysStr;
    }

    @Override
    public String getJs() {
        return jsStr;
    }

    @Override
    public String getWgjjdl() {
        return wgjjdlStr;
    }

    @Override
    public Boolean getIsAdd() {
        return isAdd;
    }
}
