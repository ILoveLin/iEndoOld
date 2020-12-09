package org.company.iendo.bean.beandb.image;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;

/**
 * LoveLin
 * <p>
 * Describe 离线图片Bean
 */
@Entity
public class ImageListDownDBBean {

    @Id(autoincrement = true)
    private Long id;

    @Unique
    private String itemID;

    private String tag;       //这个是查询是否存在的条件
    private String downTag;   //默认0没有点击下载离线模式下不加载，点击了下载设置更新为1，离线模式显示

    @Convert(columnType = String.class, converter = DimConverter.class)
    private List<DimImageBean> mDimImageList;


    @Convert(columnType = String.class, converter = ReallyConverter.class)
    private List<ReallyImageBean> mReallyImageList;


    @Generated(hash = 2016571591)
    public ImageListDownDBBean(Long id, String itemID, String tag, String downTag,
            List<DimImageBean> mDimImageList,
            List<ReallyImageBean> mReallyImageList) {
        this.id = id;
        this.itemID = itemID;
        this.tag = tag;
        this.downTag = downTag;
        this.mDimImageList = mDimImageList;
        this.mReallyImageList = mReallyImageList;
    }


    @Generated(hash = 274140388)
    public ImageListDownDBBean() {
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getItemID() {
        return this.itemID;
    }


    public void setItemID(String itemID) {
        this.itemID = itemID;
    }


    public String getTag() {
        return this.tag;
    }


    public void setTag(String tag) {
        this.tag = tag;
    }


    public String getDownTag() {
        return this.downTag;
    }


    public void setDownTag(String downTag) {
        this.downTag = downTag;
    }


    public List<DimImageBean> getMDimImageList() {
        return this.mDimImageList;
    }


    public void setMDimImageList(List<DimImageBean> mDimImageList) {
        this.mDimImageList = mDimImageList;
    }


    public List<ReallyImageBean> getMReallyImageList() {
        return this.mReallyImageList;
    }


    public void setMReallyImageList(List<ReallyImageBean> mReallyImageList) {
        this.mReallyImageList = mReallyImageList;
    }

}
