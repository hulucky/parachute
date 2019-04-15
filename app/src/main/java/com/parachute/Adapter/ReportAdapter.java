package com.parachute.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parachute.administrator.DATAbase.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;




/**
 *   测试报告列表
 */

public class ReportAdapter extends BaseAdapter {
    Context context;
    List<File> list;

    public ReportAdapter(Context context, List<File> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            //布局实例化
            convertView = View.inflate(context, R.layout.layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // 设置数据
        File file = (File) getItem(position);
        if (file.isDirectory()) {
            viewHolder.img.setImageResource(R.drawable.folder);
        } else {
            if (file.getName().endsWith(".xls")){
                viewHolder.img.setImageResource(R.drawable.home_ico_file_xlsx);
            } else if (file.getName().endsWith(".doc")){
                viewHolder.img.setImageResource(R.drawable.home_ico_file_docx);
            } else if (file.getName().endsWith(".pdf")){
                viewHolder.img.setImageResource(R.drawable.home_ico_file_pdf);
            }
        }
        viewHolder.name.setText(file.getName());
        viewHolder.time.setText(new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new Date(file.lastModified())));
        return convertView;
    }

    class ViewHolder {
        ImageView img;
        TextView name;
        TextView time;
        public ViewHolder(View convertView) {
            img = (ImageView) convertView.findViewById(R.id.img);
            name = (TextView) convertView.findViewById(R.id.name);
            time = (TextView) convertView.findViewById(R.id.time);
        }
    }
}
