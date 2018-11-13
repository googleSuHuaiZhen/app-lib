package com.zhilink.bluetooth;

/**
 * 蓝牙显示
 *
 * @author xiemeng
 * @date 2018-11-6 11:39
 */
public class BlueToothShowBean {
    /**
     * 未连接过
     */
    public static final int STATE_NEVER = 0;
    /**
     * 连接错误
     */
    public static final int STATE_ERROR = 1;
    /**
     * 连接正确
     */
    public static final int STATE_RIGHT = 2;

    private String deviceName;

    private String address;

    public BlueToothShowBean() {
        setConnected(STATE_NEVER);
    }

    /**
     * 连接状态
     * 0
     */
    private int state;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getConnected() {
        return state;
    }

    public void setConnected(int connected) {
        state = connected;
    }
}
