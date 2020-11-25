package org.company.iendo.mineui.activity.casemsg.inter;

/**
 * LoveLin
 * <p>
 * Describe 病例信息详情界面---点击直播，打印，删除，下载，编辑，回调给四个fragment的接口
 */
public interface CaseOperatorAction {
    void onLive();

    void onPrint();

    void onDelete();

    void onDownload();

    void onEdit();
}
