package com.zhilink.app_middle.net;

/**
 * @author xiemeng
 * @des
 * @date 2018-8-24 14:18
 */

public class LoginBean {


    private String loginName;


    private String password;

    private String appid="123";

    private String no;

    private String description;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                ", appid='" + appid + '\'' +
                ", no='" + no + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
