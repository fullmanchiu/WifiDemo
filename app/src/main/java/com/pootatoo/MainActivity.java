package com.pootatoo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;
    private Switch mSwitch;

    private WifiManager mWifiManager;
    private boolean isWifiOn;
    private WifiBroadCastReceiver mReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        initView();
        updateWifiSwitchStatus();
        initListener();
        registerBroadCastReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    private void registerBroadCastReceiver() {
        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiManager.ACTION_PICK_WIFI_NETWORK);
        mReceiver = new WifiBroadCastReceiver();
        registerReceiver(mReceiver, mIntentFilter);
    }

    private void initListener() {
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mWifiManager.setWifiEnabled(isChecked);
            }
        });
    }

    private void updateWifiSwitchStatus() {
        isWifiOn = mWifiManager.isWifiEnabled();
        mTextView.setText(isWifiOn ? R.string.wifiStatusOn : R.string.wifiStatusOff);
        mSwitch.setChecked(isWifiOn);
    }

    private void initView() {
        mTextView = findViewById(R.id.tv_wifi_status);
        mSwitch = findViewById(R.id.switch_wifi);
    }

    class WifiBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case WifiManager.NETWORK_STATE_CHANGED_ACTION:
                    int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, -1);
                    switch (wifiState) {
                        case WifiManager.WIFI_STATE_DISABLED:
                            break;
                    }
                    break;
            }
        }
    }
}
