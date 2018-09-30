package com.zhilink.permission;

/**
 * 6.0以上权限申请管控
 * @author xiemeng
 * @date 2018-4-6 10:35
 */

public interface PermissionInterface {

    /**
     * 可设置请求权限请求码不可以重复
     * @return 可设置请求权限请求码不可以重复
     */
    int getPermissionsRequestCode();

    /**
     * 设置需要请求的权限
     * @return  设置需要请求的权限
     */
    String[] getPermissions();

    /**
     * 请求权限成功回调
     */
    void requestPermissionsSuccess();

    /**
     * 请求权限失败回调
     */
    void requestPermissionsFail();

}