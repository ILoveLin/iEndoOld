package org.company.iendo.http.request;

import com.hjq.http.config.IRequestApi;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2019/12/07
 *    desc   : 用户登录
 */
public final class LoginApi implements IRequestApi {
//    Host：服务器主机的地址
//
//    Path：除主机地址之外的路径
//
//    Api：业务模块地址
//    http://192.168.64.17:8009/users.aspx   ?username=%E9%B8%BF%E6%B4%8B&&userpassword=XXXXXXX
//                              users.aspx?username=&&userpassword=
    @Override
    public String getApi() {
        return "users.aspx";
    }

    /** 手机号 */
    private String phone;
    /** 登录密码 */
    private String password;

    public LoginApi setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public LoginApi setPassword(String password) {
        this.password = password;
        return this;
    }
}