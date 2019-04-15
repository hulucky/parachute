package com.parachute.administrator.DATAbase.greendao;


import com.xzkydz.function.motor.module.IMotorBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class MotorEnity implements IMotorBean {
    @Id(autoincrement = true)
    private Long id;
    private String DJXL;
    private String DJ_LIB_NAME;
    private String EDDY;
    private String EDDL;
    private String EDGL;
    private String EDXL;
    private String KZDL;
    private String KZGL;
    private String EDGLYS;
    private String JS;
    private String WGJJDL;
    private Boolean isAdd;
    private String by0;
    private String by1;
    private String by2;
    private String by3;
    private String by4;
    private String by5;
    @Generated(hash = 812203172)
    public MotorEnity(Long id, String DJXL, String DJ_LIB_NAME, String EDDY,
            String EDDL, String EDGL, String EDXL, String KZDL, String KZGL,
            String EDGLYS, String JS, String WGJJDL, Boolean isAdd, String by0,
            String by1, String by2, String by3, String by4, String by5) {
        this.id = id;
        this.DJXL = DJXL;
        this.DJ_LIB_NAME = DJ_LIB_NAME;
        this.EDDY = EDDY;
        this.EDDL = EDDL;
        this.EDGL = EDGL;
        this.EDXL = EDXL;
        this.KZDL = KZDL;
        this.KZGL = KZGL;
        this.EDGLYS = EDGLYS;
        this.JS = JS;
        this.WGJJDL = WGJJDL;
        this.isAdd = isAdd;
        this.by0 = by0;
        this.by1 = by1;
        this.by2 = by2;
        this.by3 = by3;
        this.by4 = by4;
        this.by5 = by5;
    }
    @Generated(hash = 542550759)
    public MotorEnity() {
    }
    public Long getId() {
        return this.id;
    }

    @Override
    public String getDjxl() {
        return DJXL;
    }

    @Override
    public String getDjLibName() {
        return DJ_LIB_NAME;
    }

    @Override
    public String getEddy() {
        return EDDY;
    }

    @Override
    public String getEddl() {
        return EDDL;
    }

    @Override
    public String getEdgl() {
        return EDGL;
    }

    @Override
    public String getEdxl() {
        return EDXL;
    }

    @Override
    public String getKzdl() {
        return KZDL;
    }

    @Override
    public String getKzgl() {
        return KZGL;
    }

    @Override
    public String getEdglys() {
        return EDGLYS;
    }

    @Override
    public String getJs() {
        return JS;
    }

    @Override
    public String getWgjjdl() {
        return WGJJDL;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getDJXL() {
        return this.DJXL;
    }
    public void setDJXL(String DJXL) {
        this.DJXL = DJXL;
    }
    public String getDJ_LIB_NAME() {
        return this.DJ_LIB_NAME;
    }
    public void setDJ_LIB_NAME(String DJ_LIB_NAME) {
        this.DJ_LIB_NAME = DJ_LIB_NAME;
    }
    public String getEDDY() {
        return this.EDDY;
    }
    public void setEDDY(String EDDY) {
        this.EDDY = EDDY;
    }
    public String getEDDL() {
        return this.EDDL;
    }
    public void setEDDL(String EDDL) {
        this.EDDL = EDDL;
    }
    public String getEDGL() {
        return this.EDGL;
    }
    public void setEDGL(String EDGL) {
        this.EDGL = EDGL;
    }
    public String getEDXL() {
        return this.EDXL;
    }
    public void setEDXL(String EDXL) {
        this.EDXL = EDXL;
    }
    public String getKZDL() {
        return this.KZDL;
    }
    public void setKZDL(String KZDL) {
        this.KZDL = KZDL;
    }
    public String getKZGL() {
        return this.KZGL;
    }
    public void setKZGL(String KZGL) {
        this.KZGL = KZGL;
    }
    public String getEDGLYS() {
        return this.EDGLYS;
    }
    public void setEDGLYS(String EDGLYS) {
        this.EDGLYS = EDGLYS;
    }
    public String getJS() {
        return this.JS;
    }
    public void setJS(String JS) {
        this.JS = JS;
    }
    public String getWGJJDL() {
        return this.WGJJDL;
    }
    public void setWGJJDL(String WGJJDL) {
        this.WGJJDL = WGJJDL;
    }
    public Boolean getIsAdd() {
        return this.isAdd;
    }
    public void setIsAdd(Boolean isAdd) {
        this.isAdd = isAdd;
    }
    public String getBy0() {
        return this.by0;
    }
    public void setBy0(String by0) {
        this.by0 = by0;
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
   
}
