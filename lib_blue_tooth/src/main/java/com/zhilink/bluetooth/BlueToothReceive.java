package com.zhilink.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;


/**
 * 蓝牙广播断开监听
 *
 * @author xiemeng
 * @date 2018-11-6
 */

public class BlueToothReceive {

    public interface BlueToothOffListener {
        /**
         * 蓝牙断开
         */
        void doSomething();
    }


    private static final String TAG = BlueToothReceive.class.getSimpleName();

    private static BroadcastReceiver mBTReceiver;


    /**
     * 蓝牙状态发生改变时该广播响应
     */
    public static void getReceiver(Activity activity, final BlueToothOffListener listener) {
        mBTReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try {
                    final String action = intent.getAction();
                    if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                        int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                                BluetoothAdapter.ERROR);
                        switch (state) {
                            case BluetoothAdapter.STATE_OFF:
                                if (null != listener) {
                                    listener.doSomething();
                                }
                                break;
                            case BluetoothAdapter.STATE_TURNING_OFF:
                                if (null != listener) {
                                    listener.doSomething();
                                }
                                break;
                            case BluetoothAdapter.STATE_ON:
                                break;
                            case BluetoothAdapter.STATE_TURNING_ON:
                                break;
                            default:
                                break;
                        }
                    }
                    switch (action) {
                        case BluetoothDevice.ACTION_FOUND:
                            break;
                        case BluetoothDevice.ACTION_ACL_CONNECTED:
                            break;
                        case BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED:
                            if (null != listener) {
                                listener.doSomething();
                            }
                            break;
                        case BluetoothDevice.ACTION_ACL_DISCONNECTED:
                            if (null != listener) {
                                listener.doSomething();
                            }
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        IntentFilter btDiscoveryFilter = new IntentFilter();
        btDiscoveryFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        btDiscoveryFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        btDiscoveryFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);

        btDiscoveryFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        btDiscoveryFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        btDiscoveryFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        activity.registerReceiver(mBTReceiver, btDiscoveryFilter);
    }

    /**
     * 取消注册
     *
     * @param activity 蓝牙监听
     */
    public static void unregister(Activity activity) {
        try {
            activity.unregisterReceiver(mBTReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
