package com.parachute.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.greendao.manager.DataFZQ;
import com.parachute.administrator.DATAbase.R;
import com.parachute.app.MyApp;
import com.parachute.bean.GridBean;
import com.parachute.test.TestActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * 测试 --> 参数  --->  listView 适配器
 */

public class TestShowAdapter extends BaseAdapter {
    private DataFZQ mdata;
    private TestActivity mContext;
    private List<GridBean> list;
    private int type;

    DecimalFormat df0 = new DecimalFormat("####0");
    DecimalFormat df1 = new DecimalFormat("####0.0");
    DecimalFormat df2 = new DecimalFormat("####0.00");

    DecimalFormat df3 = new DecimalFormat("####0.000");


    private String functionStr;
    //	private final TaskEnityDao taskDaoUtils;

    public void setListViewType() {

        switch (type) {
            case 0://电机1参数


                break;

            case 1: // 电机2参数
                List<GridBean> dj2List = new ArrayList<>();

                this.list = dj2List;
                break;
            case 2: // 工况测量值
                List<GridBean> gk1list = new ArrayList<>();





                this.list = gk1list;
                break;
            case 3: // 输入的参数
                List<GridBean> gk2list = new ArrayList<>();

                this.list = gk2list;
                break;
            case 4://
                List<GridBean> bkhs1list = new ArrayList<>();
                          this.list = bkhs1list;
                break;
            case 5://
                List<GridBean> bkhshlist = new ArrayList<>();
              this.list = bkhshlist;
                break;
        }
    }


    public TestShowAdapter(TestActivity mContext, DataFZQ mdata, int type) {
        super();
        this.mContext = mContext;
        this.type = type;
        this.mdata = mdata;
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
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_listview_testshow, parent, false);
            viewHolder.textView0 = convertView.findViewById(R.id.tv_item_tab0);
            viewHolder.textView1 = convertView.findViewById(R.id.tv_item_tab1);
            viewHolder.textView2 = convertView.findViewById(R.id.tv_item_tab2);
            viewHolder.textView3 = convertView.findViewById(R.id.tv_item_tab3);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        GridBean gridBean = list.get(position);
        viewHolder.textView0.setText(gridBean.getStr0());
        viewHolder.textView1.setText(gridBean.getStr1());
        viewHolder.textView2.setText(gridBean.getStr2());
        viewHolder.textView3.setText(gridBean.getStr3());

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
    private GridBean getTSGridItem(String item0, String item1, String item2, String item3) {
        GridBean gridBean = new GridBean();
        gridBean.setStr0(item0);
        gridBean.setStr1(item1);
        gridBean.setStr2(item2);
        gridBean.setStr3(item3);
        return gridBean;
    }
}
