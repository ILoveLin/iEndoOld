package org.company.iendo.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * LoveLin
 * <p>
 * Describe
 */

public class EditDataBean {

    private List<List<EditItemBean>> m00List = new ArrayList<>();

    public void getData(String str) {
        try {
            JSONObject obj = new JSONObject(str);
            String ds = obj.getString("ds");
            if (ds.equals("")) {
                return;
            }
            //最外层DS数组
            JSONArray m01Array = obj.getJSONArray("ds");
            List<List<EditItemBean>> m01List = new ArrayList<>();
            for (int i = 0; i < m01Array.length(); i++) {
                //第二层类别数组
                List<EditItemBean> m02List = new ArrayList<>();
                JSONArray m02Array = (JSONArray) m01Array.get(i);

                for (int y = 0; y < m02Array.length(); y++) {
                    //第三层Bean数据
                    EditItemBean mItemBean = new EditItemBean();
                    JSONObject jsonObject = (JSONObject) m02Array.get(y);
                    mItemBean.getItemData(jsonObject);
                    m02List.add(mItemBean);
                }
                m01List.add(m02List);
            }
            setM00List(m01List);



//     {"ID":"1","ParentId":"0","DictName":"100","DictItem":"性别","EndoType":"3"}

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public List<List<EditItemBean>> getM00List() {
        return m00List;
    }

    public void setM00List(List<List<EditItemBean>> m00List) {
        this.m00List = m00List;
    }

}
