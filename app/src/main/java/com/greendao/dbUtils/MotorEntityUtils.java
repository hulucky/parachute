package com.greendao.dbUtils;


import com.greendao.manager.TaskEntityDao;
import com.parachute.administrator.DATAbase.greendao.MotorEnity;
import com.parachute.app.MyApp;

import java.util.List;

public class MotorEntityUtils {

    /**
     * 添加数据，如果有重复则覆盖
     * @param motorEnity
     */
    public static void insertLove(MotorEnity motorEnity) {
        MyApp.getDaoInstant().getMotorEnityDao().insertOrReplace(motorEnity);
    }

    /**
     * 删除数据
     * @param id
     */
    public static void deleteLove(long id) {
        MyApp.getDaoInstant().getMotorEnityDao().deleteByKey(id);
    }

    /**
     * 更新数据
     *
     * @param MotorEnity
     */
    public static void updateLove(MotorEnity MotorEnity) {
        MyApp.getDaoInstant().getMotorEnityDao().update(MotorEnity);
    }

    /**
     * 查询Id为1的数据
     * @return
     */
    public static List<MotorEnity> queryLove() {
        return MyApp.getDaoInstant().getMotorEnityDao().queryBuilder().where(TaskEntityDao.Properties.Id.eq(0l)).list();
    }

    /**
     * 查询全部数据
     */
    public static List<MotorEnity> queryAll() {
        return MyApp.getDaoInstant().getMotorEnityDao().loadAll();
    }


}
