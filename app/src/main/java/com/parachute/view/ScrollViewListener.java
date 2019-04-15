package com.parachute.view;

/**
 *  * Created by：YuKun on 2017/3/6 14:20
 *  * Email：yk_coding@163.com
 *  自定义ScrollView的滑动事件接口
 */

public interface ScrollViewListener {
    void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy);
}
