package com.example.lenovo.wanandroid.model.bean.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DbBean {

    @Id(autoincrement = true)
    Long id;
    String img;
    String name;
    String chanlename;
    String title;
    String time;
    @Generated(hash = 1044848566)
    public DbBean(Long id, String img, String name, String chanlename, String title,
            String time) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.chanlename = chanlename;
        this.title = title;
        this.time = time;
    }
    @Generated(hash = 1953169116)
    public DbBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getImg() {
        return this.img;
    }
    public void setImg(String img) {
        this.img = img;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getChanlename() {
        return this.chanlename;
    }
    public void setChanlename(String chanlename) {
        this.chanlename = chanlename;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }


}
