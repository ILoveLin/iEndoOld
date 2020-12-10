package org.company.iendo.bean.beandb.image;

import com.google.gson.Gson;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LoveLin
 * <p>
 * Describe 实现PropertyConverter,从而实现存储list
 */
public class ReallyConverter implements PropertyConverter<List<ReallyImageBean>,String> {
    //将数据库中的值，转化为实体Bean类对象(比如List<String>)
    @Override
    public List<ReallyImageBean> convertToEntityProperty(String databaseValue) {
        if (databaseValue==null){
            return null;
        }
        List<String> list_str = Arrays.asList(databaseValue.split(","));
        List<ReallyImageBean> list_transport = new ArrayList<>();
        for (String s : list_str) {
            list_transport.add(new Gson().fromJson(s, ReallyImageBean.class));
        }
        return list_transport;
    }





    //将实体Bean类(比如List<String>)转化为数据库中的值(比如String)
    @Override
    public String convertToDatabaseValue(List<ReallyImageBean> arrays) {
        if (arrays == null) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            for (ReallyImageBean array : arrays) {
                String str = new Gson().toJson(array);
                sb.append(str);
                sb.append(",");
            }
            return sb.toString();

        }
    }
}