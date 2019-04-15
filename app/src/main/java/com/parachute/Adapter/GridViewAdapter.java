package com.parachute.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parachute.administrator.DATAbase.R;

/**
 * @Data  创建时间：2017/2/14
 * @Author ： YuKun
 * @Description ：检测标准
 * @Version
 */

public class GridViewAdapter extends BaseAdapter {
	private Context mContext;
	public int[] imgs = { R.drawable.standard0_1};
	public String[] img_text = { "金属非金属矿山竖井提升系统防坠器安全性能检测检验规范"
			};

	public GridViewAdapter(Context mContext) {
		super();
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imgs.length;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gridview_stabtard, parent, false);
			convertView.setPadding(8, 8, 8, 8);
		}
		TextView tv = BaseViewHolder.get(convertView, R.id.tv_item_stantard);
		ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item_stantard);
		iv.setBackgroundResource(imgs[position]);
		tv.setText(img_text[position]);
		return convertView;
	}
}
