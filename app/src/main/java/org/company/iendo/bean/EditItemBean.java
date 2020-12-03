package org.company.iendo.bean;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * LoveLin
 * <p>
 * Describe
 */
@SuppressLint("ParcelCreator")
public class EditItemBean implements Parcelable {
    private String ID;
    private String ParentId;
    private String DictName;
    private String DictItem;
    private String EndoType;

    public void getItemData(JSONObject obj) {
        try {
            setID(obj.getString("ID"));
            setParentId(obj.getString("ParentId"));
            setDictName(obj.getString("DictName"));
            setDictItem(obj.getString("DictItem"));
            setEndoType(obj.getString("EndoType"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<EditItemBean> CREATOR = new Creator<EditItemBean>() {
        public EditItemBean createFromParcel(Parcel source) {
            EditItemBean person = new EditItemBean();
            person.ID = source.readString();
            person.ParentId = source.readString();
            person.DictName = source.readString();
            person.DictItem = source.readString();
            person.EndoType = source.readString();
            return person;
        }

        @Override
        public EditItemBean[] newArray(int size) {
            return new EditItemBean[size];
        }

    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ID);
        parcel.writeString(ParentId);
        parcel.writeString(DictName);
        parcel.writeString(DictItem);
        parcel.writeString(EndoType);

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String parentId) {
        ParentId = parentId;
    }

    public String getDictName() {
        return DictName;
    }

    public void setDictName(String dictName) {
        DictName = dictName;
    }

    public String getDictItem() {
        return DictItem;
    }

    public void setDictItem(String dictItem) {
        DictItem = dictItem;
    }

    public String getEndoType() {
        return EndoType;
    }

    public void setEndoType(String endoType) {
        EndoType = endoType;
    }

    @Override
    public String toString() {
        return "EditItemBean{" +
                "ID='" + ID + '\'' +
                ", ParentId='" + ParentId + '\'' +
                ", DictName='" + DictName + '\'' +
                ", DictItem='" + DictItem + '\'' +
                ", EndoType='" + EndoType + '\'' +
                '}';
    }
}
