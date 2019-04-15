package com.parachute.test.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.greendao.manager.DataFZQ;
import com.greendao.manager.TaskResEnityDao;
import com.parachute.Tools.DrawViewJsd;
import com.parachute.administrator.DATAbase.R;
import com.parachute.administrator.DATAbase.greendao.TaskEntity;
import com.parachute.administrator.DATAbase.greendao.TaskResEnity;
import com.parachute.app.MyApp;
import com.parachute.data.DataDetailActivity;
import com.parachute.service.FileService;
import com.parachute.test.TestActivity;

import org.greenrobot.greendao.query.QueryBuilder;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class DataFragment extends Fragment {


    List<TaskResEnity> mjfhreslist;
    List<TaskResEnity> mtgreslist;

    Activity mActivity;
    TaskEntity mTask;
    TaskResEnity mjfhshowres;
    TaskResEnity mtgshowres;

    TaskResEnity mjfhoutres;
    TaskResEnity mtgoutres;
    Long moutputjfhresId;
    Long moutputtgresId;
    MyApp myApp;
    Unbinder unbinder;
    DecimalFormat df4 = new DecimalFormat("####0.00");
    DecimalFormat df2 = new DecimalFormat("####0.00");
    DecimalFormat df5 = new DecimalFormat("####0.0000");
    @BindView(R.id.rb_res_jfh)
    RadioButton rbResJfh;
    @BindView(R.id.rb_res_tg)
    RadioButton rbResTg;
    @BindView(R.id.img_res_message)
    ImageView imgResMessage;
    @BindView(R.id.img_res_report)
    ImageView imgResReport;
    @BindView(R.id.sp_res_datalist)
    AppCompatSpinner spResDatalist;
    @BindView(R.id.ck_res_choose)
    CheckBox ckResChoose;
    @BindView(R.id.fl_res_jsdcurve)
    FrameLayout flResJsdcurve;
    @BindView(R.id.tv_res_tg_jsd)
    TextView tvResJsd;
    @BindView(R.id.tv_res_tg_pjjsd)
    TextView tvResPjjsd;
    @BindView(R.id.tv_res_tg_kxcsj)
    TextView tvResKxcsj;
    @BindView(R.id.tv_res_tg_kxcjl)
    TextView tvResKxcjl;
    @BindView(R.id.tv_res_tg_zdsj)
    TextView tvResZdsj;
    @BindView(R.id.ly_res_tgsj)
    LinearLayout lyResTgsj;
    @BindView(R.id.tv_res_tg_length1)
    TextView tvResLength1;
    @BindView(R.id.tv_res_tg_length2)
    TextView tvResLength2;
    @BindView(R.id.tv_res_tg_length3)
    TextView tvResLength3;
    @BindView(R.id.tv_res_tg_length4)
    TextView tvResLength4;
    @BindView(R.id.tv_res_tg_length5)
    TextView tvResLength5;
    @BindView(R.id.tv_res_tg_length6)
    TextView tvResLength6;
    @BindView(R.id.tv_res_tg_length7)
    TextView tvResLength7;
    @BindView(R.id.tv_res_tg_wy7)
    TextView tvResWy7;

    private FileService fileService;
    long SystemTime = 0;
    List<Long> Res_id;

    private Map<Integer, Integer> selected;
    private Boolean IsTg = false;


    DecimalFormat df1 = new DecimalFormat("####0.0");


    private int jfhSelectIndex = 0;
    private int tgSelectIndex = 0;

    public boolean isDataManage() {
        return isDataManage;
    }

    public void setDataManage(boolean dataManage) {
        isDataManage = dataManage;
    }

    boolean isDataManage = true;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mjfhshowres = new TaskResEnity();
        mtgshowres = new TaskResEnity();
        mjfhoutres = new TaskResEnity();
        mtgoutres = new TaskResEnity();
        moutputjfhresId = new Long(0);
        moutputtgresId = new Long(0);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_res_data, null);
        myApp = MyApp.getInstance();
        mTask = myApp.getTaskEnity();

        fileService = new FileService(mActivity);
        unbinder = ButterKnife.bind(this, view);
        mjfhreslist = new ArrayList<TaskResEnity>();
        mtgreslist = new ArrayList<TaskResEnity>();
        rbResJfh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IsTg = false;
                BandSpJfh();
                if(mjfhreslist.size()>0){
                if (mjfhreslist.get(0).getId().equals(moutputjfhresId)) {
                    ckResChoose.setChecked(true);
                } else {
                    ckResChoose.setChecked(false);
                }}
                setView();
            }
        });
        rbResTg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IsTg = true;
                BandSpTg();
                if(mtgreslist.size()>0){
                if (mtgreslist.get(0).getId().equals(moutputtgresId)) {
                    ckResChoose.setChecked(true);
                } else {
                    ckResChoose.setChecked(false);
                }}
                setView();
            }
        });
        ckResChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ckResChoose.isChecked()) {

                } else {
                    if (IsTg) {
                        moutputtgresId = mtgshowres.getId();
                        TaskResEnityDao mdao = MyApp.getDaoInstant().getTaskResEnityDao();
                        QueryBuilder qb = mdao.queryBuilder();
                        mtgoutres = mdao.queryBuilder().where(TaskResEnityDao.Properties.Id.eq(moutputtgresId)).list().get(0);
                        //截图
                        myApp.setSnapName();
                        Bitmap a = convertViewToBitmap(flResJsdcurve, 600, 400);
                        String fileName = fileService.saveBitmapToSDCard("" + myApp.getSnapName()
                                + ".png", a);
                    } else {
                        moutputjfhresId = mjfhshowres.getId();
                        TaskResEnityDao mdao = MyApp.getDaoInstant().getTaskResEnityDao();
                        QueryBuilder qb = mdao.queryBuilder();
                        mjfhoutres = mdao.queryBuilder().where(TaskResEnityDao.Properties.Id.eq(moutputjfhresId)).list().get(0);
                    }
                }
            }
        });
        try {
            mActivity = (TestActivity) getActivity();
        } catch (Exception e) {
            mActivity = (DataDetailActivity) getActivity();

            e.printStackTrace();
        }


        GetData();


        imgResMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog();
            }
        });
        imgResReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mjfhoutres != null && mtgoutres != null) {
                    updateExcel();
                }
            }
        });
        return view;
    }

    private void showdialog() {
        final String[] items = {"单位名称：" + mTask.getUnitName(),
                "防坠器编号：" + mTask.getNumber(),
                "测试员：" + mTask.getPeopleName(),
                "创建时间：" + mTask.getGreateTaskTime(),
                "静负荷测试条数：" + mjfhreslist.size() + "条",
                "已选第：" + moutputjfhresId + "条",
                "脱钩测试条数：" + mtgreslist.size() + "条"};
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(mActivity);
        listDialog.setTitle("任务信息");
        listDialog.setItems(items, null);
        listDialog.show();
    }

    public static Bitmap convertViewToBitmap(View view, int bitmapWidth, int bitmapHeight) {
        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmap));

        return bitmap;
    }

    public void RefreshList() {

    }

    private void setView() {

        if (IsTg && mtgshowres != null)//脱钩
        {
            tvResLength1.setText(df2.format(mtgshowres.getXk1()) + " mm");
            tvResLength2.setText(df2.format(mtgshowres.getXk2()) + " mm");
            tvResLength3.setText(df2.format(mtgshowres.getXdzds1()) + " mm");
            tvResLength4.setText(df2.format(mtgshowres.getXdzds2()) + " mm");
            tvResLength5.setText(df2.format(mtgshowres.getXdhcs1()) + " mm");
            tvResLength6.setText(df2.format(mtgshowres.getXdhcs2()) + " mm");
            tvResLength7.setText(df2.format(mtgshowres.getXjgd()) + " mm");
            tvResJsd.setText(df2.format(mtgshowres.getZdjsd()) + " m/s²");
            tvResPjjsd.setText(df2.format(mtgshowres.getPjjsd()) + " m/s²");
            tvResKxcjl.setText(df2.format(mtgshowres.getKxcjl()) + " mm");
            tvResKxcsj.setText(df5.format(mtgshowres.getKxcsj() ) + " s");
            tvResZdsj.setText(df5.format(mtgshowres.getZdsj() ) + " s");
            try {
                String mCurve = mtgshowres.getCurve();
                if (mCurve != null) {
                    String[] mCL = mCurve.split("\\|");
                    mDrawList = new float[mCL.length];
                    for (int i = 0; i < mCL.length; i++) {
                        mDrawList[i] = Float.parseFloat(mCL[i]);
                    }
                }

            } catch (Exception e) {
                Exception a = e;
            }
            Draw();
        } else if (!IsTg && mjfhshowres != null)//静负荷
        {
            tvResLength1.setText(df2.format(mjfhshowres.getXk1()) + " mm");
            tvResLength2.setText(df2.format(mjfhshowres.getXk2()) + " mm");
            tvResLength3.setText(df2.format(mjfhshowres.getXdzds1()) + " mm");
            tvResLength4.setText(df2.format(mjfhshowres.getXdzds2()) + " mm");
            tvResLength5.setText(df2.format(mjfhshowres.getXdhcs1()) + " mm");
            tvResLength6.setText(df2.format(mjfhshowres.getXdhcs2()) + " mm");

            tvResLength7.setText("--" + " mm");
            tvResJsd.setText("--" + " m/s²");
            tvResPjjsd.setText("--" + " m/s²");
            tvResKxcjl.setText("--" + " mm");
            tvResKxcsj.setText("--" + " ms");
            tvResZdsj.setText("--" + " ms");
            flResJsdcurve.removeAllViews();
        }
    }

    private DrawViewJsd view;
    public float[] mDrawList = new float[100];// 加速度曲线数据
    private float[] mSdList = new float[1];
    private float[] mDisList = new float[1];

    private void Draw() {
        view = new DrawViewJsd(mActivity);

        view.SetSpeedDrawOrNot(false);
        view.SetDisplacementDrawOrNot(false);
        view.setMinimumHeight(520);
        view.setMinimumWidth(330);
        view.SetPoint(100, 50);
        view.invalidate();
        view.SetResourceDataY(mDrawList);
        view.SetResourceDataU(mSdList);
        view.SetResourceDataD(mDisList);
        flResJsdcurve.removeAllViews();
        flResJsdcurve.addView(view);
    }

    public void InitList(Boolean mIsTg) {

        GetDataList();
//        mreslist = mdao.queryBuilder().and(TaskResEnityDao.Properties.TaskId.eq(taskId),TaskResEnityDao.Properties.SaveType.eq(0)).list();
        int count = 1;

        BandSpJfh();

    }

    private void BandSpJfh() {
        String[] mItems = null;
        if (mjfhreslist != null) {

            mItems = new String[mjfhreslist.size()];
            for (int i = 0; i < mjfhreslist.size(); i++) {
                mItems[i] = (i + 1) + " " + mjfhreslist.get(i).getSaveTime();
            }
        } else {
            mItems = new String[0];
        }
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(mActivity, android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 绑定 Adapter到控件
        spResDatalist.setAdapter(adapter);
        spResDatalist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ShowToast")
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                jfhSelectIndex = pos;
                mjfhshowres = mjfhreslist.get(jfhSelectIndex);
                if (mjfhshowres.getId().equals(moutputjfhresId)) {
                    ckResChoose.setChecked(true);
                } else {
                    ckResChoose.setChecked(false);
                }
                setView();
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

    private void BandSpTg() {
        String[] mItems = null;
        if (mtgreslist != null) {
            mItems = new String[mtgreslist.size()];
            for (int i = 0; i < mtgreslist.size(); i++) {
                mItems[i] = (i + 1) + " " + mtgreslist.get(i).getSaveTime();
            }
        } else {
            mItems = new String[0];
        }
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(mActivity, android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 绑定 Adapter到控件
        spResDatalist.setAdapter(adapter);
        spResDatalist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ShowToast")
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                tgSelectIndex = pos;
                mtgshowres = mtgreslist.get(tgSelectIndex);
                if (mtgshowres.getId().equals(moutputtgresId)) {
                    ckResChoose.setChecked(true);
                } else {
                    ckResChoose.setChecked(false);
                }
                setView();
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

    private void GetDataList() {
        Long taskId = mTask.getId();
        TaskResEnityDao mdao = MyApp.getDaoInstant().getTaskResEnityDao();
        QueryBuilder qb = mdao.queryBuilder();

        mjfhreslist = mdao.queryBuilder().where(TaskResEnityDao.Properties.TaskId.eq(taskId), TaskResEnityDao.Properties.SaveType.eq(0)).list();
        mtgreslist = mdao.queryBuilder().where(TaskResEnityDao.Properties.TaskId.eq(taskId), TaskResEnityDao.Properties.SaveType.eq(1)).list();
    }

    public void GetData() {
        try {
            InitList(IsTg);
            rbResJfh.setText("静负荷数据(" + mjfhreslist.size() + ")");
            rbResTg.setText("脱钩数据(" + mtgreslist.size() + ")");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onStart() {
        super.onStart();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * jxl暂时不提供修改已经存在的数据表,这里通过一个小办法来达到这个目的,不适合大型数据更新! 这里是通过覆盖原文件来更新的.
     */
    public void updateExcel() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyMMdd-HHmmss");
        String date = sDateFormat.format(new java.util.Date());
        try {

            // Workbook workbook = Workbook.getWorkbook(new
            // File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/防坠器综合测试仪/.SystemFiles/SystemFiles/flow(禁止删除).ky"));
            WorkbookSettings setting = new WorkbookSettings();

            // setting.setCharacterEncoding("gb2312");
            Workbook workbook = Workbook.getWorkbook(new File(Environment
                    .getExternalStorageDirectory().getAbsolutePath()
                    + "/CZD13W矿用防坠器无线多参数测试仪/.报告模板/防坠器报告.xls"), setting);

            WritableWorkbook writableWorkbook = Workbook.createWorkbook(
                    new File(Environment.getExternalStorageDirectory()
                            .getAbsolutePath()
                            + "/CZD13W矿用防坠器无线多参数测试仪/测试报告/"
                            + mTask.getUnitName()
                            + "_"
                            + mTask.getNumber()
                            + "_"
                            + date + ".xls"), workbook);// copy
            WritableSheet ws5 = writableWorkbook.getSheet(5);
            int r = 2;// 列
            int c = 4;// 行

            // mjfhxk1 = mRes.getSbxl();
            // mjfhxk2 = mRes.getSbyc();
            // mjfhxdzds1 = mRes.getSbdh();
            // mjfhxdzds2 = mRes.getLtsd();
            // mjfhxdhcs1 = mRes.getSsll();
            // mjfhxdhcs2 = mRes.getGlxl();

            WritableCell wc = ws5.getWritableCell(r, c); // 列 然后 是 行

            Label l = (Label) wc; // (强转)得到单元格的Label()对象
            l.setString(df4.format(mjfhoutres.getXk1())); // 重新设置值

            c++;
            wc = ws5.getWritableCell(r, c); // 列 然后 是 行
            l = (Label) wc; // (强转)得到单元格的Label()对象
            l.setString(df4.format(mjfhoutres.getXk2())); // 重新设置值

            c++;
            wc = ws5.getWritableCell(r, c); // 列 然后 是 行
            l = (Label) wc; // (强转)得到单元格的Label()对象
            l.setString(df4.format(mjfhoutres.getXdzds1())); // 重新设置值

            c++;
            wc = ws5.getWritableCell(r, c); // 列 然后 是 行
            l = (Label) wc; // (强转)得到单元格的Label()对象
            l.setString(df4.format(mjfhoutres.getXdzds2())); // 重新设置值

            c++;
            wc = ws5.getWritableCell(r, c); // 列 然后 是 行
            l = (Label) wc; // (强转)得到单元格的Label()对象
            l.setString(df4.format(mjfhoutres.getXdhcs1())); // 重新设置值

            c++;
            wc = ws5.getWritableCell(r, c); // 列 然后 是 行
            l = (Label) wc; // (强转)得到单元格的Label()对象
            l.setString(df4.format(mjfhoutres.getXdhcs2())); // 重新设置值

            WritableSheet ws6 = writableWorkbook.getSheet(6);
            // mtgxk1 = mRes.getSbxl();
            // mtgxk2 = mRes.getSbyc();
            // mtgxdzds1 = mRes.getSbdh();
            // mtgxdzds2 = mRes.getLtsd();
            // mtgxdhcs1 = mRes.getSsll();
            // mtgxdhcs2 = mRes.getGlxl();
            // mtgxjgd = mRes.getGj();
            //
            // mtgjsd = mRes.getCkyl();
            // mtgpjjsd = mRes.getDl();
            // mtgkxcsj = mRes.getCgqjl();
            // mtgkxcjl = mRes.getXh();
            // mtgzdjl = mRes.getJkwd();
            r = 2;
            c = 3;
            wc = ws6.getWritableCell(r, c); // 列 然后 是 行
            l = (Label) wc; // (强转)得到单元格的Label()对象
            l.setString(df4.format(mtgoutres.getXk1())); // 重新设置值

            c++;
            wc = ws6.getWritableCell(r, c); // 列 然后 是 行
            l = (Label) wc; // (强转)得到单元格的Label()对象
            l.setString(df4.format(mtgoutres.getXk2())); // 重新设置值

            c++;
            wc = ws6.getWritableCell(r, c); // 列 然后 是 行
            l = (Label) wc; // (强转)得到单元格的Label()对象
            l.setString(df4.format(mtgoutres.getXdzds1())); // 重新设置值

            c++;
            wc = ws6.getWritableCell(r, c); // 列 然后 是 行
            l = (Label) wc; // (强转)得到单元格的Label()对象
            l.setString(df4.format(mtgoutres.getXdzds2())); // 重新设置值

            c++;
            wc = ws6.getWritableCell(r, c); // 列 然后 是 行
            l = (Label) wc; // (强转)得到单元格的Label()对象
            l.setString(df4.format(mtgoutres.getXdhcs1())); // 重新设置值

            c++;
            wc = ws6.getWritableCell(r, c); // 列 然后 是 行
            l = (Label) wc; // (强转)得到单元格的Label()对象
            l.setString(df4.format(mtgoutres.getXdhcs2())); // 重新设置值

            c++;
            wc = ws6.getWritableCell(r, c); // 列 然后 是 行
            l = (Label) wc; // (强转)得到单元格的Label()对象
            l.setString(df5.format(mtgoutres.getKxcsj() )); // 重新设置值

            c++;
            wc = ws6.getWritableCell(r, c); // 列 然后 是 行
            l = (Label) wc; // (强转)得到单元格的Label()对象
            l.setString(df4.format(mtgoutres.getKxcjl())); // 重新设置值

            c++;
            wc = ws6.getWritableCell(r, c); // 列 然后 是 行
            l = (Label) wc; // (强转)得到单元格的Label()对象
            l.setString(df4.format(mtgoutres.getZdjsd())); // 重新设置值

            c++;
            wc = ws6.getWritableCell(r, c); // 列 然后 是 行
            l = (Label) wc; // (强转)得到单元格的Label()对象
            l.setString(df4.format(mtgoutres.getPjjsd())); // 重新设置值

            c++;
            wc = ws6.getWritableCell(r, c); // 列 然后 是 行
            l = (Label) wc; // (强转)得到单元格的Label()对象
            l.setString(df5.format(mtgoutres.getZdsj() / 4 )); // 重新设置值

            c++;
            wc = ws6.getWritableCell(r, c); // 列 然后 是 行
            l = (Label) wc; // (强转)得到单元格的Label()对象
            l.setString(df4.format(mtgoutres.getXjgd())); // 重新设置值
            c++;
            // 添加图片

            File mCurve = new File("mnt/sdcard" + "/CZD13W矿用防坠器无线多参数测试仪/.防坠器加速度曲线图/"
                    + myApp.getSnapName() + "" + ".png");
            if (mCurve != null) {
                ws6.addImage(new WritableImage(1, 18, 2, 15, mCurve));
            }
            writableWorkbook.write();
            writableWorkbook.close();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Intent intent = getExcelFileIntent(new File(Environment
                    .getExternalStorageDirectory().getAbsolutePath()
                    + "/CZD13W矿用防坠器无线多参数测试仪/测试报告/"
                    + mTask.getUnitName()
                    + "_"
                    + mTask.getNumber() + "_" + date + ".xls"));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    // ��Word
    public static Intent getWordFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    //android��ȡһ�����ڴ�Excel�ļ���intent
    public static Intent getExcelFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    private void initSize(WebSettings settings) {

        settings.setBuiltInZoomControls(true);

        int screenDensity = getResources().getDisplayMetrics().densityDpi;
        WebSettings.ZoomDensity zoomDensity = WebSettings.ZoomDensity.MEDIUM;
        switch (screenDensity) {
            // ����Ĭ�����ŷ�ʽ�ߴ���far ?��MEDIUM�ɣ�
            case DisplayMetrics.DENSITY_LOW:
                zoomDensity = WebSettings.ZoomDensity.CLOSE;
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                zoomDensity = WebSettings.ZoomDensity.MEDIUM;
                break;
            case DisplayMetrics.DENSITY_HIGH:
                zoomDensity = WebSettings.ZoomDensity.FAR;
                break;
        }
        settings.setDefaultZoom(zoomDensity);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        setUserVisibleHint(true);
    }

    public void init() {
    }

    @OnClick({R.id.img_res_message, R.id.img_res_report})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_res_message:
                break;
            case R.id.img_res_report:
                break;
        }
    }

}
