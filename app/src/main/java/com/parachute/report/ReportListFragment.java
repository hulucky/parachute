package com.parachute.report;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parachute.Adapter.ReportAdapter;
import com.parachute.administrator.DATAbase.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;



public class ReportListFragment extends Fragment {

    @BindView(R.id.current_path_view)
    TextView currentPathView;
    @BindView(R.id.push)
    ImageButton push;
    @BindView(R.id.current_path_pane)
    RelativeLayout currentPathPane;
    @BindView(R.id.navigation_bar)
    RelativeLayout navigationBar;
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.tv_null)
    TextView tvNull;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;

    List<File> list = new ArrayList<File>();
    public static final String SDCard = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CZD13W矿用防坠器无线多参数测试仪/测试报告";
    public static String currDir = SDCard;
    private ReportAdapter adapter;
    private ReportActivity mActivity;
    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (ReportActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        adapter = new ReportAdapter(mActivity, list);
        lv.setAdapter(adapter);
        getAllFiles();
        return view;
    }

    private void initData() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                File file = list.get(i);
                try {
                    Intent intent = getWordFileIntent(SDCard + "/" + file.getName());
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {

                final String[] items = {"打开", "删除"};
                final AlertDialog.Builder listDialog = new AlertDialog.Builder(mActivity);
                listDialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        File file = list.get(i);
                        switch (which) {
                            case 0:
                                try {
                                    Intent intent = getWordFileIntent(SDCard + "/" + file.getName());
                                    startActivity(intent);
                                } catch (ActivityNotFoundException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 1:
                                if (file.isFile()) {
                                    file.delete();
                                }
                                file.exists();
                                getAllFiles();
                                Toasty.success(mActivity, "成功删除一篇测试报告", 7).show();
                                break;

                        }
                    }
                });
                listDialog.show();
                return true;
            }
        });
    }

    public void getAllFiles() {
        list.clear();
        File file = new File(currDir);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File file2 : files) {
                    list.add(file2);
                }
            }
        }
        // 文件排序
        sort();
        adapter.notifyDataSetChanged();
        tvNull.setText(list.size()==0 ? "未发现测试报告......" : "");
    }

    private void sort() {
        //使用Collection.sort排序，给定一个比较器，使用匿名内部类实现比较器接口
        Collections.sort(list, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.isDirectory() && o2.isDirectory() || o1.isFile() && o2.isFile()) {
                    return o1.compareTo(o2);
                }
                return o1.isDirectory() ? -1 : 1;
            }
        });
    }

    //打开Word
    public static Intent getWordFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.push)
    public void onClick() {

    }
}
