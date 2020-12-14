package org.company.iendo.util.db;

import android.util.Log;

import org.company.iendo.bean.beandb.image.ImageListDownDBBean;
import org.company.iendo.common.MyApplication;
import org.company.iendo.my.db.DaoSession;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * LoveLin
 * <p>
 * Describe 用户表的CURD操作工具类
 */
public class ImageDBUtils {
//
//    insert： 会进行去重，保存第一次的数据，也就是不会进行更新。至于是 由于主键去重，还是有重复的元素就去我还会在看看
//
//    insertOrReplace：  会去重，保存最新的数据，也就是会进行更新
//
//    save：  不会去重，保存所有数据

    /**
     * 增  --插入数据
     *
     * @param bean
     */
    public static void insertData(ImageListDownDBBean bean) {
        MyApplication.getInstance().getDaoSession().insert(bean);
    }

    /**
     * 增  --数据存在则替换，数据不存在则插入
     *
     * @param bean
     */
    public static void insertOrReplaceData(ImageListDownDBBean bean) {
        MyApplication.getInstance().getDaoSession().insertOrReplace(bean);
    }


    /**
     * 删--delete()和deleteAll()；分别表示删除单个和删除所有。
     */

    public static void deleteData(ImageListDownDBBean bean) {
        MyApplication.getInstance().getDaoSession().delete(bean);

    }

    public static void deleteAllData(Class clazz) {
        MyApplication.getInstance().getDaoSession().deleteAll(clazz);

    }


    /**
     * 改--通过update来进行修改：
     */


    public static void updateData(ImageListDownDBBean bean) {
        MyApplication.getInstance().getDaoSession().update(bean);

    }


    /**
     * 查询
     * loadAll()：查询所有数据。
     * queryRaw()：根据条件查询
     * queryBuilder() : 方便查询的创建，后面详细讲解。
     */


    public static List queryAll(Class clazz) {

        List list = MyApplication.getInstance().getDaoSession().loadAll(clazz);
        return list;
    }

    public static List queryRaw(ImageListDownDBBean bean, Long id) {

        List<ImageListDownDBBean> beanLis = (List<ImageListDownDBBean>)
                MyApplication.getInstance().getDaoSession().queryRaw(ImageListDownDBBean.class, " where id = ?", id + "");
        return beanLis;
    }

    /**
     * 根据用户名条件查询,返回password
     *
     * @param name
     * @return
     */
    public static ImageListDownDBBean queryListByMessageToGetPassword(String name) {
        boolean isExist = queryListIsExist(name);
        if (isExist) {
            List<ImageListDownDBBean> ImageListDownDBBeanList = queryListByMessage(name);
            Log.e("path=====:=====", ImageListDownDBBeanList.size() + ""); //   /storage/emulated/0/1604026573438.mp4
            for (int i = 0; i < ImageListDownDBBeanList.size(); i++) {

                return ImageListDownDBBeanList.get(0);

            }
        } else {
            return new ImageListDownDBBean();
        }
        return new ImageListDownDBBean();

    }
    public static List<ImageListDownDBBean> queryListByTag(String tag) {
        //tag 就是itemID
        DaoSession daoSession = MyApplication.getInstance().getDaoSession();
        QueryBuilder<ImageListDownDBBean> qb = daoSession.queryBuilder(ImageListDownDBBean.class);
        List<ImageListDownDBBean> ImageListDownDBBeans = daoSession.queryRaw(ImageListDownDBBean.class, "where tag = ?", tag);
        return ImageListDownDBBeans;
    }
    /**
     * 根据单个条件查询
     *
     * @param tag
     * @return
     */
    public static List<ImageListDownDBBean> queryListByMessage(String tag) {
        DaoSession daoSession = MyApplication.getInstance().getDaoSession();
        QueryBuilder<ImageListDownDBBean> qb = daoSession.queryBuilder(ImageListDownDBBean.class);
        List<ImageListDownDBBean> students = daoSession.queryRaw(ImageListDownDBBean.class, "where tag = ?", tag);
        return students;
    }

    /**
     * @param tag
     * @return 查询是否存在
     */
    public static boolean queryListIsExist(String tag) {
        DaoSession daoSession = MyApplication.getInstance().getDaoSession();
        QueryBuilder<ImageListDownDBBean> qb = daoSession.queryBuilder(ImageListDownDBBean.class);
        List<ImageListDownDBBean> students = daoSession.queryRaw(ImageListDownDBBean.class, " where tag = ?", tag);
//        List<Student> students = daoSession.loadAll(Student.class);
//        return students;
        Log.e("path=====Start:=====", students.size() + ""); //   /storage/emulated/0/1604026573438.mp4

        if (students.size() != 0) {  //存在
            return true;
        } else {
            return false;
        }
    }


}
