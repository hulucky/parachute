package com.parachute.data;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.greendao.dbUtils.GreateTaskUtils;
import com.greendao.dbUtils.TaskResUtils;
import com.parachute.administrator.DATAbase.greendao.TaskEntity;
import com.parachute.app.MyApp;
import com.xzkydz.function.data.view.DataMagerActivity;
import com.xzkydz.function.search.module.ITaskRoot;

import java.util.ArrayList;
import java.util.List;

public class DataManager extends DataMagerActivity {


    @Override
    public void getTaskList() {
//        List<ITaskRoot> list = new ArrayList<>();
//        List<TaskEntity> taskEnities = MyApp.getInstance().getDaoInstant().getTaskEntityDao().loadAll();
//        list.addAll(taskEnities);
//        //注意：最设置数据
//        setDataAndPushView(list);

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<ITaskRoot> list = new ArrayList<>();
                List<TaskEntity> taskEnities = MyApp.getInstance().getDaoInstant().getTaskEntityDao().loadAll();
                list.addAll(taskEnities);
                //注意：最设置数据
                setDataAndPushView(list);
            }
        }).start();

    }

    @NonNull
    @Override
    public Class<?> getDataDetailActivity() {
        return DataDetailActivity.class;
    }

    /**
     * 设置任务为完成状态：成功后返回为true
     * @param task
     * @return
     */
    @Override
    public boolean setTaskComplete(ITaskRoot task) {
        TaskEntity taskEnity = (TaskEntity)task;
        taskEnity.set_IsCompleteTask(true);
        GreateTaskUtils.update(taskEnity);
        return true;
    }
    /**
     * 删除测试任务：成功后返回为true
     * @param task
     * @return 成功删除后返回为True
     */
    @Override
    public boolean deleteTaskAndData(ITaskRoot task) {
        GreateTaskUtils.delete(task.getTaskId());
        TaskResUtils.deleteByTaskId(task.getTaskId());
        return true;
    }

}
