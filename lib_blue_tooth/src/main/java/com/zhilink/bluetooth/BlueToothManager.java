package com.zhilink.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * 蓝牙设置
 *
 * @author xiemeng
 * @date 2018-11-6
 */

public class BlueToothManager {
    private static final String TAG = BlueToothManager.class.getSimpleName();
    /**
     * 蓝牙系统适配
     */
    private BluetoothAdapter mBTAdapter;

    private BluetoothSocket mBTSocket;

    private PrintSend mPrintSend;

    private UUID dvcUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private String mDeviceName = "";

    private BlueToothManager() {
        mBTAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public static BlueToothManager getInstance() {
        return BlueToothManager.MainHolder.ZHI_LINK;
    }


    private static class MainHolder {
        private static final BlueToothManager ZHI_LINK = new BlueToothManager();
    }


    /**
     * 获取已经绑定过的打印设备列表
     * int deviceType = device.getBluetoothClass().getMajorDeviceClass();
     * 不同设备类型该值不同，比如computer蓝牙为256、phone 蓝牙为512、打印机蓝牙为1536等等。
     */
    public List<BlueToothShowBean> getPrintDevices() {
        Set<BluetoothDevice> pairedDevices = mBTAdapter.getBondedDevices();
        List<BlueToothShowBean> list = new ArrayList<>();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                int deviceType = device.getBluetoothClass().getMajorDeviceClass();
                if (1536 == deviceType) {
                    BlueToothShowBean toothShowBean = new BlueToothShowBean();
                    toothShowBean.setDeviceName(device.getName());
                    toothShowBean.setAddress(device.getAddress());
                    list.add(toothShowBean);
                }
            }
        }
        return list;
    }

    /**
     * 获取已经绑定过的设备列表
     */
    public List<BlueToothShowBean> getAllDevices() {
        Set<BluetoothDevice> pairedDevices = mBTAdapter.getBondedDevices();
        List<BlueToothShowBean> list = new ArrayList<>();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                BlueToothShowBean toothShowBean = new BlueToothShowBean();
                toothShowBean.setDeviceName(device.getName());
                toothShowBean.setAddress(device.getAddress());
                list.add(toothShowBean);
            }
        }
        return list;
    }

    public interface ConnectListener {
        /**
         * 连接成功
         */
        void onSuccess();

        /**
         * 连接失败
         */
        void onFailed();
    }

    /**
     * 连接设备
     *
     * @param blueToothShowBean 蓝牙地址,蓝牙名称 必填
     */
    public void connect(final BlueToothShowBean blueToothShowBean, final ConnectListener listener) {
        if (null == mBTAdapter) {
            listener.onFailed();
            return;
        }
        BluetoothDevice mBTDevice = mBTAdapter.getRemoteDevice(blueToothShowBean.getAddress());
        try {
            mBTSocket = mBTDevice.createInsecureRfcommSocketToServiceRecord(dvcUUID);
        } catch (IOException e) {
            listener.onFailed();
            e.printStackTrace();
        }
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) {
                try {
                    mBTSocket.connect();
                    mPrintSend = new PrintSend(mBTSocket);
                    mDeviceName = blueToothShowBean.getDeviceName();
                    e.onNext(true);
                } catch (IOException ex) {
                    e.onNext(false);
                    ex.printStackTrace();
                } catch (Exception ex) {
                    e.onNext(false);
                    ex.printStackTrace();
                }

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean s) {
                        setConnected(s);
                        if (s) {
                            listener.onSuccess();
                        } else {
                            listener.onFailed();
                        }
                    }
                });
    }

    private boolean isConnected;

    /**
     * 确认蓝牙是否开启并连接成功
     */
    public boolean getConnected() {
        return isConnected;
    }

    /**
     * 确认蓝牙是否开启并连接成功
     */
    public void setConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }


    /**
     * 获取连接设备名称
     *
     * @return 设备名称
     */
    public String getDeviceName() {
        return mDeviceName;
    }

    public PrintSend getPrintSend() {
        return mPrintSend;
    }

    public void setPrintSend(PrintSend mPrintSend) {
        this.mPrintSend = mPrintSend;
    }

    /**
     * 关闭蓝牙
     */
    public void close() {
        setConnected(false);
        try {
            mBTAdapter.disable();
            mBTSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
