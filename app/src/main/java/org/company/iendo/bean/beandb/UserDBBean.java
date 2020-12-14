package org.company.iendo.bean.beandb;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * LoveLin
 * <p>
 * Describe
 */
@Entity
public class UserDBBean {
    @Id (autoincrement = true)
    private Long id;
    @Unique
    private String username;
    private String password;
    private String userType;   //0是超级管理员
    private String tag;

    @Generated(hash = 1938892055)
    public UserDBBean(Long id, String username, String password, String userType,
            String tag) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.tag = tag;
    }

    @Generated(hash = 202817274)
    public UserDBBean() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
