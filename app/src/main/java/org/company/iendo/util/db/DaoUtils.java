//package org.company.iendo.util.db;
//
//import android.content.Context;
//import android.util.Log;
//
//
//import org.company.iendo.my.db.DaoSession;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by Koterwong on 2016/7/31.
// */
//public class DaoUtils<Entity> {
//
//    private static final String TAG = "DaoUtils";
//
//    private final DaoSession daoSession;
//
//    public DaoUtils(Context context) {
//        DaoManager manager = DaoManager.getInstance(context);
//        daoSession = manager.getDaoSession();
//    }
//
//    /**
//     * entity 表的插入操作
//     *
//     * @param entity 实体类
//     * @return
//     */
//    public boolean insertEntity(Entity entity) {
//        boolean flag = false;
//
//        try {
//            flag = daoSession.insert(entity) != -1 ? true : false;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Log.i("CommonUtils", "----insertStudent--result is -->>" + flag);
//        return flag;
//    }
//
//    /**
//     * 插入多条记录，需要开辟新的线程
//     *
//     * @param entitys
//     * @return
//     */
//    public boolean insertMultEntity(final List<Entity> entitys) {
//        boolean flag = false;
//
//        try {
//            daoSession.runInTx(new Runnable() {
//                @Override
//                public void run() {
//                    for (Entity entity : entitys) {
//                        daoSession.insertOrReplace(entity);
//                    }
//                }
//            });
//            flag = true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return flag;
//    }
//
//    /**
//     * 完成对entity的某一条记录的修改
//     *
//     * @param entity
//     * @return
//     */
//    public boolean updateStudent(Entity entity) {
//        boolean flag = false;
//        try {
//            daoSession.update(entity);
//            flag = true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return flag;
//    }
//    /**完成对entity的某一条记录的删除
//     * @param entity
//     * @return
//     */
//    public boolean delete_Entity (Entity entity) {
//        boolean flag = false;
//        try {
//            //按照指定的id进行删除 delete from student where _id = ?
//            daoSession.delete(entity);
//            //manager.getDaoSession().deleteAll();//删除所有的记录
//            flag = true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return flag;
//    }
//    /**
//     *删除所有的实体记录
//     */
//    public boolean  deleteAllEntity(Class cls){
//        boolean flag = false;
//        try {
//            //按照指定的id进行删除 delete from student where _id = ?
//            daoSession.deleteAll(cls);
//            //manager.getDaoSession().deleteAll();//删除所有的记录
//            flag = true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return flag;
//    }
//
//    /**
//     * 返回多行记录
//     *
//     * @return
//     */
//    public ArrayList<A> listAll() {
//        return (ArrayList<A>) daoSession.loadAll(A.class);
//    }
//
//    /**
//     * 按照主键返回单行记录
//     *
//     * @param key
//     * @return
//     */
//    public Entity listOneStudent(long key) {
//        return (Entity) daoSession.load(getClass(), key);
//    }
//
////    /**
////     * 按条件查询
////     */
////    public void query3() {
////
////        //逻辑与 和 逻辑或 是双目运算符
////        QueryBuilder<Student> builder =daoSession.queryBuilder(Student.class);
////        //select * from student where (address='北京' or age > 50) and name like '%张%'
////        builder.whereOr(StudentDao.Properties.Address.eq("北京"), StudentDao.Properties.Age.ge(50));
////        builder.whereOr(StudentDao.Properties.Id.ge(2), StudentDao.Properties.Age.ge(10)).limit(3);//区前三条数据
////
////        //builder.where(StudentDao.Properties.Name.like("张"));
////        List<Student> list = builder.list();
////        Log.i("--->>", "" + list);
////    }
//
//
//}
