package com.parachute.pdf;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toolbar;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.parachute.administrator.DATAbase.R;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PdfActivity extends AppCompatActivity implements OnPageChangeListener , OnLoadCompleteListener {


    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;
    @BindView(R.id.pdfView)
    PDFView pdfView;
    @BindView(R.id.text)
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //保持屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_pdf);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //决定左上角的图标是否可以点击
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        initEvent();
    }

    private void initEvent() {
        String[] img_text = { "AQ 2019-2008 金属非金属矿山竖井提升系统防坠器安全性能检测检验规范"
        };
        Intent intent = getIntent();
        int dex = intent.getIntExtra("pdf", 0);
        try {
            display("standard"+dex+".pdf");
            this.setTitle(img_text[dex]);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @Description ：读取并且显示pdf  开源库：https://github.com/barteksc/AndroidPdfViewer
     */
    private void display(String assetFileName) {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("assets/" + assetFileName);
        pdfView.fromStream(inputStream)
                //                .pages(0, 0, 0, 0, 0, 0) // 默认全部显示，pages属性可以过滤性显示
                .defaultPage(1)//默认展示第一页
                .onPageChange(PdfActivity.this)//监听页面切换
                .enableSwipe(true)   //是否允许翻页，默认是允许翻页
                .defaultPage(1)
                .enableSwipe(true)
                .swipeHorizontal(true)
                .enableDoubletap(true)
//                .onDraw(onDrawListener)
                .onLoad(PdfActivity.this)
//                .onPageScroll(onPageScrollListener)
//                .onError(onErrorListener)
                .defaultPage(0)
                .onLoad(PdfActivity.this)
                .enableAnnotationRendering(false)
                .password(null)
                .scrollHandle(null)
                .load();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        text.setText((page + 1) + "/" + pageCount);
    }

    @Override
    public void loadComplete(int nbPages) {

    }

}
