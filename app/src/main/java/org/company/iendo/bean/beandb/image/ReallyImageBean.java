package org.company.iendo.bean.beandb.image;

/**
 * LoveLin
 * <p>
 * Describe 原图
 */
public class ReallyImageBean {
    private String reallyFilePath;

    public String getReallyFilePath() {
        return reallyFilePath;
    }

    public void setReallyFilePath(String reallyFilePath) {
        this.reallyFilePath = reallyFilePath;
    }

    @Override
    public String toString() {
        return "ReallyImageBean{" +
                "reallyFilePath='" + reallyFilePath + '\'' +
                '}';
    }
}
