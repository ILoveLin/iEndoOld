package org.company.iendo.mineui.beandb;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * LoveLin
 * <p>
 * Describe 服务器的dbbean
 */
@Entity
public class AddServersBeanDB {
    @Id(autoincrement = true)
    private Long id;
    @Unique
    private String name;  //名称
    private String ip;  //名称
    private String port;  //名称
    private String tag;  //名称
    @Generated(hash = 1334347072)
    public AddServersBeanDB(Long id, String name, String ip, String port,
            String tag) {
        this.id = id;
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.tag = tag;
    }
    @Generated(hash = 906828719)
    public AddServersBeanDB() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getIp() {
        return this.ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getPort() {
        return this.port;
    }
    public void setPort(String port) {
        this.port = port;
    }
    public String getTag() {
        return this.tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
}
