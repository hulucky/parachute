package com.greendao.dbUtils;


import com.greendao.manager.TaskResEnityDao;
import com.parachute.administrator.DATAbase.greendao.TaskResEnity;
import com.parachute.app.MyApp;

import java.util.List;

public class TaskResUtils {

    /**
     * 添加数据，如果有重复则覆盖
     * @param enity
     */
    public static void insert(TaskResEnity enity) {
        MyApp.getDaoInstant().getTaskResEnityDao().insert(enity);
    }

    /**
     * 删除数据
     * @param id
     */
    public static void delete(long id) {
        MyApp.getDaoInstant().getTaskResEnityDao().deleteByKey(id);
    }

    /**
     * 更新数据
     *
     * @param enity
     */
    public static void update(TaskResEnity enity) {
        MyApp.getDaoInstant().getTaskResEnityDao().update(enity);
    }

    /**
     * 查询Id为1的数据
     * @return
     */
    public static List<TaskResEnity> query(Long id) {
        return MyApp.getDaoInstant().getTaskResEnityDao().queryBuilder().where(TaskResEnityDao.Properties.Id.eq(id)).list();
    }

    /**
     * 查询全部数据
     */
    public static List<TaskResEnity> queryAll() {
        return MyApp.getDaoInstant().getTaskResEnityDao().loadAll();
    }


    public static void deleteByTaskId(Long taskId) {
        try {
            MyApp.getDaoInstant().getTaskEntityDao().deleteByKey(taskId);
            List<TaskResEnity> mlist=MyApp.getDaoInstant().getTaskResEnityDao().queryBuilder().where(TaskResEnityDao.Properties.TaskId.eq(taskId)).list();
            for (TaskResEnity mres:mlist
                 ) {
                MyApp.getDaoInstant().getTaskResEnityDao().delete(mres);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
