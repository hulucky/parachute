package com.parachute.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.parachute.administrator.DATAbase.R;
import com.parachute.bean.ListBean;

import java.util.List;


/**
 * 测试 --> 数据  --->  listView 适配器
 */

class TestDataListAdapter1 extends BaseAdapter {

    private Activity mContext;
    private List<ListBean> list;





    public TestDataListAdapter1(Activity mContext, List<ListBean> mlist) {
        super();
        this.mContext = mContext;
       this.list=mlist;


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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_listview_testdatalistl, parent, false);
            viewHolder.checkBox=convertView.findViewById(R.id.chb_testdetaillist_xh);
            viewHolder.textView0 = convertView.findViewById(R.id.tv_testdatalist_tab0);
            viewHolder.textView1 = convertView.findViewById(R.id.tv_testdatalist_tab1);
            viewHolder.textView2 = convertView.findViewById(R.id.tv_testdatalist_tab2);
            viewHolder.textView3 = convertView.findViewById(R.id.tv_testdatalist_tab3);
            viewHolder.textView4 = convertView.findViewById(R.id.tv_testdatalist_tab4);
            viewHolder.textView5 = convertView.findViewById(R.id.tv_testdatalist_tab5);

            convertView.setTag(viewHolder);


        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ListBean gridBean = list.get(position);
        viewHolder.checkBox.setText((position+1)+"");
        viewHolder.checkBox.setTag(position);
        viewHolder.textView0.setText(gridBean.getStr0());
        viewHolder.textView1.setText(gridBean.getStr1() );





        return convertView;
    }

    private class ViewHolder {
        CheckBox checkBox;
        TextView textView0;
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        TextView textView5;
    }


    /**
     * 返回 GridBean
     *
     * @param item0
     * @param item1
     * @return
     */
    private ListBean getTSGridItem(String item0, String item1 ) {
        ListBean gridBean = new ListBean();
        gridBean.setStr0(item0);
        gridBean.setStr1(item1);

        return gridBean;
    }
}
