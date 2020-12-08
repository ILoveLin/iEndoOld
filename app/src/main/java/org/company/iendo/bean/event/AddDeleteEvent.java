package org.company.iendo.bean.event;

import org.company.iendo.bean.CaseManagerListBean;

/**
 * LoveLin
 * <p>
 * Describe
 */
public class AddDeleteEvent {
    private CaseManagerListBean.DsDTO bean;

    private String type;
    private int position;

    public AddDeleteEvent(CaseManagerListBean.DsDTO bean, String type,int position) {
        this.bean = bean;
        this.type = type;
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CaseManagerListBean.DsDTO getBean() {
        return bean;
    }

    public void setBean(CaseManagerListBean.DsDTO bean) {
        this.bean = bean;
    }
}
