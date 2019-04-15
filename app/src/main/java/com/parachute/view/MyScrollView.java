package com.parachute.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 *  * Created by：YuKun on 2017/3/31 14:05
 *  * Email：yk_coding@163.com
 *  
 */

public class MyScrollView extends ScrollView {

    private ScrollListener mListener;

    public static interface ScrollListener {//声明接口，用于传递数据
        public void scrollOritention(int l, int t, int oldl, int oldt);
    }

    public MyScrollView(Context context, AttributeSet attrs,
                        int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public MyScrollView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        // TODO Auto-generated method stub
        super.onScrollChanged(l, t, oldl, oldt);
        if (mListener != null) {
            mListener.scrollOritention(l, t, oldl, oldt);
        }
    }

    public void setScrollListener(ScrollListener l) {
        this.mListener = l;
    }

}
