package org.company.iendo.common;

import android.content.Context;
import android.util.Log;


import org.company.iendo.util.SharePreferenceUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Lovelin on 2019/5/10
 * <p>
 * Describe:拦截器  添加header
 */
public class MyInterceptor implements Interceptor {
    private Context mContext;

    public MyInterceptor(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
//                .addHeader("device", "android")
//                .addHeader("token", token)
                .build();

        return chain.proceed(request);

    }
}
