package com.example.lenovo.wanandroid.utils;

import com.example.lenovo.wanandroid.app.MyApp;
import com.example.lenovo.wanandroid.dao.DaoMaster;
import com.example.lenovo.wanandroid.dao.DaoSession;
import com.example.lenovo.wanandroid.dao.DbBeanDao;
import com.example.lenovo.wanandroid.model.bean.dao.DbBean;

import java.util.List;

public class MyDbHelper {
    private static MyDbHelper myDbHelper;
    private final DbBeanDao dbBeanDao;

            public MyDbHelper() {
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(MyApp.app, "info.db");
        DaoMaster daoMaster = new DaoMaster(openHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        dbBeanDao = daoSession.getDbBeanDao();
    }

    public static MyDbHelper getMyDbHelper() {
        if (myDbHelper==null){
            synchronized (MyDbHelper.class){
                if (myDbHelper==null){
                    myDbHelper=new MyDbHelper();
                }
            }
        }
        return myDbHelper;
    }

    public void insert(DbBean bean){
        if (has(bean)){
            return;
        }
        dbBeanDao.insert(bean);
    }

    public void delete(DbBean bean){
        dbBeanDao.delete(bean);
    }

    public List<DbBean> query(){
        return dbBeanDao.queryBuilder().list();
    }



    private boolean has(DbBean bean) {
        List<DbBean> list = dbBeanDao.queryBuilder().where(DbBeanDao.Properties.Name.eq(bean.getName()),
                DbBeanDao.Properties.Title.eq(bean.getTitle())).list();
        if (list!=null&&list.size()>0){
            return true;
        }
        return false;
    }

    public List<DbBean> query1(String name,String title){
                return dbBeanDao.queryBuilder().where(DbBeanDao.Properties.Name.eq(name),
                        DbBeanDao.Properties.Title.eq(title)).list();
    }

    public void delete(List<DbBean> dbBeans1) {
        dbBeanDao.deleteInTx(dbBeans1);
    }
    public DbBean queryBean(DbBean dbBean){
        return dbBeanDao.queryBuilder().where(DbBeanDao.Properties.Title.eq(dbBean.getTitle())).unique();
    }
}
