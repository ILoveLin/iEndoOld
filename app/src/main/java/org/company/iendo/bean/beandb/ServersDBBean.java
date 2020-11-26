package org.company.iendo.bean.beandb;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * LoveLin
 * <p>
 * Describe
 */
@Entity
public class ServersDBBean {
    @Id(autoincrement = true)
    private Long id;

    private String name;  //名称
    private String ip;  //ip
    private String port;  //端口
    private String tag;  //tag
    @Generated(hash = 26812560)
    public ServersDBBean(Long id, String name, String ip, String port, String tag) {
        this.id = id;
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.tag = tag;
    }
    @Generated(hash = 426272784)
    public ServersDBBean() {
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
