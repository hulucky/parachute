package com.parachute.report;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parachute.administrator.DATAbase.R;
import com.xzkydz.function.ftp.Defaults;
import com.xzkydz.function.ftp.Globals;
import com.xzkydz.function.ftp.MyLog;
import com.xzkydz.function.ftp.UiUpdater;
import com.xzkydz.function.ftp.ftptwo.FTPServerService;

import java.io.File;
import java.net.InetAddress;




/**
 * @Data  创建时间：2017/3/10
 * @Author ： YuKun
 * @Description ：ftp无线文件传输服务
 * @Version
 */

public class TransFragment extends Fragment {

    private TextView ipText;
    protected MyLog myLog = new MyLog(this.getClass().getName());
    private TextView instructionText;
    private TextView instructionTextPre;
    private TextView instructionTextPreInf;
    private View startStopButton;
    private ImageView wifiImg;
    private TextView startStopButtonText;
    private TextView showInf;
    private ReportActivity mActivity;
    private ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (ReportActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trans, container, false);
        ipText = (TextView) view.findViewById(R.id.ip_address);
        instructionText = (TextView) view.findViewById(R.id.instruction);
        instructionTextPre = (TextView) view.findViewById(R.id.instruction_pre);
        instructionTextPreInf = (TextView) view.findViewById(R.id.instruction_pre_inf);
        startStopButton = view.findViewById(R.id.start_stop_button);
        wifiImg = (ImageView) view.findViewById(R.id.wifi_state_image);
        startStopButtonText = (TextView) view.findViewById(R.id.start_stop_button_text);
        showInf = (TextView) view.findViewById(R.id.wifi_state);
        imageView = (ImageView)view.findViewById(R.id.wifi_state_image);
        return  view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("ceui", "onAttach:");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("ceui", "onActivityCreated: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("ceui", "onStart: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("ceui", "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("ceui", "onStop: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("ceui", "onDetach: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("ceui", "onResume: ");
        // Set the application-wide context global, if not already set
        Context myContext = Globals.getContext();

        if (myContext == null) {
            myContext = mActivity.getApplicationContext();
            if (myContext == null) {
                throw new NullPointerException("Null context!?!?!?");
            }
            Globals.setContext(myContext);
        }
        startStopButton.setOnClickListener(startStopListener);
        updateUi();
        UiUpdater.registerClient(handler);
        // quickly turn on or off wifi.
        imageView.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(
                                android.provider.Settings.ACTION_WIFI_SETTINGS);
                        startActivity(intent);
                    }
                });

        registerReceiver();
    }

    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: // We are being told to do a UI update
                    // If more than one UI update is queued up, we only need to
                    // do one.
                    removeMessages(0);
                    updateUi();
                    break;
                case 1: // We are being told to display an error message
                    removeMessages(1);
            }

        }
    };
    /**
     * This will be called by the static UiUpdater whenever the service has
     * changed state in a way that requires us to update our UI. We can't use
     * any myLog.l() calls in this function, because that will trigger an
     * endless loop of UI updates.
     */
    public void updateUi() {
//        myLog.l(Log.DEBUG, "Updating UI", true);
//      WifiManager wifiMgr = (WifiManager)mActivity.getSystemService(Context.WIFI_SERVICE);
//        //int wifiState = wifiMgr.getWifiState();
//        WifiInfo info = wifiMgr.getConnectionInfo();
//        String wifiId = info != null ? info.getSSID() : null;
//        boolean isWifiReady = FTPServerService.isWifiEnabled();
//        setText(showInf, isWifiReady ? wifiId : getString(R.string.no_wifi_hint));
//        wifiImg.setImageResource(isWifiReady ? R.drawable.wifi_state4 : R.drawable.wifi_state0);
//
//        boolean running = FTPServerService.isRunning();
//        if (running) {
//            myLog.l(Log.DEBUG, "updateUi: server is running", true);
//            InetAddress address = FTPServerService.getWifiIp();
//            if (address != null) {
//                String port = ":" + FTPServerService.getPort();
//                ipText.setText("ftp://" + address.getHostAddress() + (FTPServerService.getPort() == 21 ? "" : port) + "/ky");
//            } else {
//                //如果未得到IP地址，结束服务
//                Context context = mActivity.getApplicationContext();
//                Intent intent = new Intent(context, FTPServerService.class);
//                context.stopService(intent);
//                ipText.setText("");
//            }
//        }
//        startStopButton.setEnabled(isWifiReady);
//
//        if (isWifiReady) {
//            startStopButtonText.setText(running ? R.string.stop_server : R.string.start_server);
//            startStopButtonText.setCompoundDrawablesWithIntrinsicBounds(running ? R.drawable.disconnect : R.drawable.connect, 0, 0, 0);
//            startStopButtonText.setTextColor(running ? Color.parseColor("#FF901821") : Color.parseColor("#FFe78018"));
//        } else {
//            if (FTPServerService.isRunning()) {
//                Context context = mActivity.getApplicationContext();
//                Intent intent = new Intent(context, FTPServerService.class);
//                context.stopService(intent);
//            }
//            startStopButtonText.setText(R.string.no_wifi);
//            startStopButtonText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//            startStopButtonText.setTextColor(Color.GRAY);
//        }
//        ipText.setVisibility(running ? View.VISIBLE : View.INVISIBLE);
//        instructionText.setVisibility(running ? View.VISIBLE : View.GONE);
//        instructionTextPre.setVisibility(running ? View.GONE : View.VISIBLE);
//        instructionTextPreInf.setVisibility(running ? View.GONE : View.VISIBLE);
    }

    private void setText(TextView textView, String text) {
//        TextView tv = (TextView) mRootView.findViewById(id);
        textView.setText(text);
    }

    View.OnClickListener startStopListener = new View.OnClickListener() {
        public void onClick(View v) {
            Globals.setLastError(null);
            File chrootDir = new File(Defaults.chrootDir);
            if (!chrootDir.isDirectory())
                return;
            Context context = mActivity.getApplicationContext();
            Intent intent = new Intent(context, FTPServerService.class);
            Globals.setChrootDir(chrootDir);
            if (!FTPServerService.isRunning()) {
                warnIfNoExternalStorage();
                if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                    context.startService(intent);
                }
            } else {
                context.stopService(intent);
            }
        }
    };

    private void warnIfNoExternalStorage() {
        String storageState = Environment.getExternalStorageState();
        if (!storageState.equals(Environment.MEDIA_MOUNTED)) {
            Toast toast = Toast.makeText(mActivity, R.string.storage_warning, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    private  void registerReceiver(){
        IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        mActivity.registerReceiver(wifiReceiver, filter);
    }

    BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        public void onReceive(Context ctx, Intent intent) {
            myLog.l(Log.DEBUG, "Wifi status broadcast received");
            updateUi();
        }
    };

    boolean requiredSettingsDefined() {
        SharedPreferences settings = mActivity.getSharedPreferences(Defaults.getSettingsName(), Defaults.getSettingsMode());
        String username = settings.getString("username", null);
        String password = settings.getString("password", null);
        if (username == null || password == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Get the settings from the FTPServerService if it's running, otherwise load the settings directly from persistent storage.
     */
    SharedPreferences getSettings() {
        SharedPreferences settings = FTPServerService.getSettings();
        if (settings != null) {
            return settings;
        } else {
            return mActivity.getPreferences(Activity.MODE_PRIVATE);
        }
    }
}
