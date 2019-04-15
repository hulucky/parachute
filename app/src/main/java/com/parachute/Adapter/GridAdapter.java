package com.parachute.Adapter;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.greendao.dbUtils.GreateTaskUtils;
import com.parachute.administrator.DATAbase.R;
import com.parachute.administrator.DATAbase.greendao.TaskEntity;
import com.parachute.bean.GridBean;
import com.parachute.test.TestActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;


/**
 * 测试 --> 参数  --->  listView 适配器
 */

public class GridAdapter extends BaseAdapter {
    private TestActivity mContext;
    private List<GridBean> list;
    private int type;
    private TaskEntity taskParInf;
    private Callback mCallback;
    private String functionStr;

    DecimalFormat df0 = new DecimalFormat("####0");
    DecimalFormat df1 = new DecimalFormat("####0.0");
    DecimalFormat df2 = new DecimalFormat("####0.00");

    DecimalFormat df3 = new DecimalFormat("####0.000");

    //	private final TaskEnityDao taskDaoUtils;
    public interface Callback {
        public void click();
    }

    public void setListViewType() {

        switch (type) {
            case 0:
                List<GridBean> taskInfList = new ArrayList<>();
                taskInfList.add(getGridItem("受检单位", taskParInf.getUnitName(), "通风机编号", taskParInf.getNumber()));
                taskInfList.add(getGridItem("测试人员", taskParInf.getPeopleName(), "测试方法", taskParInf.getTestFunction()));
                taskInfList.add(getGridItem("创建日期", taskParInf.getGreateTaskTime(), "", ""));

                this.list = taskInfList;
                break;
        }
    }


    public GridAdapter(TestActivity mContext, TaskEntity taskParInf, int type) {
        super();
        this.mContext = mContext;
        this.type = type;
        this.taskParInf = taskParInf;

        setListViewType();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;


        return convertView;
    }


    private class ViewHolder {
        TextView textView0;
        TextView textView1;
        TextView textView2;
        TextView textView3;
    }



    /**
     * 返回 GridBean
     *
     * @param item0
     * @param item1
     * @param item2
     * @param item3
     * @return
     */
    private GridBean getGridItem(String item0, String item1, String item2, String item3) {
        GridBean gridBean = new GridBean();
        gridBean.setStr0(item0);
        gridBean.setStr1(item1);
        gridBean.setStr2(item2);
        gridBean.setStr3(item3);
        return gridBean;
    }
}
