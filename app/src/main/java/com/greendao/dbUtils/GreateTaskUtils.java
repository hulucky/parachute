package com.greendao.dbUtils;


import com.greendao.manager.TaskEntityDao;
import com.parachute.administrator.DATAbase.greendao.TaskEntity;
import com.parachute.app.MyApp;

import java.util.List;

public class GreateTaskUtils {

    /**
     * 添加数据，如果有重复则覆盖
     * @param greateTask
     */
    public static Long insert(TaskEntity greateTask) {
       return MyApp.getDaoInstant().getTaskEntityDao().insert(greateTask);
    }

    /**
     * 删除数据
     * @param id
     */
    public static void delete(long id) {
        MyApp.getDaoInstant().getTaskEntityDao().deleteByKey(id);
    }

    /**
     * 更新数据
     *
     * @param greateTaskp
     */
    public static void update(TaskEntity greateTaskp) {
        MyApp.getDaoInstant().getTaskEntityDao().update(greateTaskp);
    }

    /**
     * 查询Id为1的数据
     * @return
     */
    public static TaskEntity query(Long id) {
        return MyApp.getDaoInstant().getTaskEntityDao().queryBuilder().where(TaskEntityDao.Properties.Id.eq(id)).list().get(0);
    }

    /**
     * 查询全部数据
     */
    public static List<TaskEntity> queryAll() {
        return MyApp.getDaoInstant().getTaskEntityDao().loadAll();
    }



}
