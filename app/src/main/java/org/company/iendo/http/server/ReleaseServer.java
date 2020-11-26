package org.company.iendo.http.server;

import com.hjq.http.config.IRequestServer;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/12/07
 * desc   : 正式环境
 */
public class ReleaseServer implements IRequestServer {
    //    Host：服务器主机的地址
    //
    //    Path：除主机地址之外的路径
    //
    //    Api：业务模块地址

//    http://192.168.128.146:8009/users.aspx   ?username=%E9%B8%BF%E6%B4%8B&&userpassword=XXXXXXX

    @Override
    public String getHost() {
        return "http://192.168.128.146:8009/";
    }

    @Override
    public String getPath() {
        return "";
    }

}