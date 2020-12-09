package org.company.iendo.bean.beandb.image;

/**
 * LoveLin
 * <p>
 * Describe 模糊图
 */
public class DimImageBean {
    private String dimFilePath;

    public String getDimFilePath() {
        return dimFilePath;
    }

    public void setDimFilePath(String dimFilePath) {
        this.dimFilePath = dimFilePath;
    }

    @Override
    public String toString() {
        return "DimImageBean{" +
                "dimFilePath='" + dimFilePath + '\'' +
                '}';
    }
}
