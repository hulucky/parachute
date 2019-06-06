package com.parachute.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.greendao.manager.DataFZQ;
import com.parachute.Tools.Method;
import com.parachute.administrator.DATAbase.R;
import com.parachute.bean.PressBean;
import com.parachute.test.TestActivity;

import java.util.List;


/**
 * 测试 --> 压力  --->  listView 适配器
 */

public class TestPressAdapter extends BaseAdapter {
    private DataFZQ mdata;
    private TestActivity mContext;
    private List<PressBean> list;


    public TestPressAdapter(TestActivity mContext,List<PressBean> mlist) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_listview_testpress, parent, false);
            viewHolder.textView0 = convertView.findViewById(R.id.tv_itempress_tab0);
            viewHolder.textView1 = convertView.findViewById(R.id.tv_itempress_tab1);
            viewHolder.textView2 = convertView.findViewById(R.id.tv_itempress_tab2);
            viewHolder.imgdelete = convertView.findViewById(R.id.img_itempress_delete);
            convertView.setTag(viewHolder);


        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PressBean gridBean = list.get(position);
        viewHolder.textView0.setText((position+1)+"");
        viewHolder.textView1.setText(gridBean.getStr0()+ " Pa");
        viewHolder.textView2.setText(gridBean.getStr1()+" Pa");
        viewHolder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                notifyDataSetChanged();
                float[] mave= Method.AvePress(list);

                mContext.mdata.Refresh();

            }
        });



        return convertView;
    }

    private class ViewHolder {
        TextView textView0;
        TextView textView1;
        TextView textView2;
 ImageView imgdelete;
    }


    /**
     * 返回 GridBean
     *
     * @param item0
     * @param item1
     * @return
     */
    private PressBean getTSGridItem(String item0, String item1 ) {
        PressBean gridBean = new PressBean();
        gridBean.setStr0(item0);
        gridBean.setStr1(item1);

        return gridBean;
    }
}
